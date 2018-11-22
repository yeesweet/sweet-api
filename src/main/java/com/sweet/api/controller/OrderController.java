package com.sweet.api.controller;


import com.alibaba.fastjson.JSON;
import com.sweet.api.commons.PageFinder;
import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.constants.ShoppingCartConstant;
import com.sweet.api.entity.*;
import com.sweet.api.entity.pay.PayTradeInfo;
import com.sweet.api.entity.req.SettlementParamReq;
import com.sweet.api.entity.res.OrderDetailResp;
import com.sweet.api.entity.res.OrderListResp;
import com.sweet.api.entity.res.OrderResp;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.entity.vo.CommodityColumnVo;
import com.sweet.api.entity.vo.OrderBaseVo;
import com.sweet.api.entity.vo.ShoppingCartVo;
import com.sweet.api.service.IOrderService;
import com.sweet.api.service.IPayService;
import com.sweet.api.service.IShoppingcartService;
import com.sweet.api.service.IUserAddressService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-12
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IShoppingcartService shoppingcartService;
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private IPayService payService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/submit")
    public Object createorder(SettlementParamReq settlementParam, SessionUserInfo user) {
        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }
        settlementParam.setLoginId(userId);
        if (StringUtils.isBlank(settlementParam.getAddressId())) {
            return new ResponseMessage<>(1, "请选择收货地址后重试");
        }

        try {
            final UserAddress userAddress = userAddressService.selectById(settlementParam.getAddressId());
            if (null == userAddress) {
                return new ResponseMessage<>(1, "请选择收货地址后重试");
            }
            if (StringUtils.isBlank(userAddress.getPhone())) {
                return new ResponseMessage<>(1, "联系方式未填写，请修改后重试");
            }
            //获取购物车数据
            ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
            info.setLinkBuy(settlementParam.getLinkBuy());
            info.setLoginId(userId);
            ShoppingCartVo shoppingCartVo = shoppingcartService.checkAndCalculate(info, true);
            if (null == shoppingCartVo) {
                return new ResponseMessage<>(1, "购物车空空如也，先去逛逛！");
            }
            //商品校验
            final List<CommodityColumnVo> commodityColumnVoList = shoppingCartVo.getCommodityColumnVoList();
            for (CommodityColumnVo columnVo : commodityColumnVoList) {
                final Integer buyStatus = columnVo.getBuyStatus();
                final Integer status = columnVo.getStatus();
                if (buyStatus == ShoppingCartConstant.BUY_STATUS_CHECKED) {
                    if (status == 1 || status == 7) {
                        return new ResponseMessage<>(1, "您选择的商品已下架或者售罄，请重新选择！");
                    }
                }
            }
            //最新成单金额限制
            if (!shoppingCartVo.isLimitAllow()) {
                return new ResponseMessage<>(1, "结算金额低于" + shoppingCartVo.getOrderAmountLimit() + "元，请您继续选购");
            }
            //组装orderBaseVo,创建订单
            OrderBaseVo orderBaseVo = transOrderParam(settlementParam, shoppingCartVo);
            Order order = orderService.createOrder(orderBaseVo);
            if (order == null) {
                return new ResponseMessage<>(1, "提交订单失败，请稍后重试");
            }
            //获取小程序支付信息
            PayTradeInfo tradeInfo = new PayTradeInfo();
            tradeInfo.setOrderNo(order.getOrderNo());
            tradeInfo.setPayment(settlementParam.getPayment());
            tradeInfo.setUser(user);
            tradeInfo.setTradeType("pay");
            PayInfo payInfo = payService.payorder(tradeInfo);
            if (payInfo != null) {
                return new ResponseMessage<>(0, "success", payInfo);
            } else {
                return new ResponseMessage<>(1, "提交订单失败，请稍后重试");
            }
        } catch (Exception e) {
            logger.error("提交订单失败：", JSON.toJSONString(settlementParam));
            return new ResponseMessage<>(1, "提交订单失败，请稍后重试");
        }

    }

    /**
     * 查看订单列表
     *
     * @param type        0 全部 1 待付款 2 待发货 3 待收货
     * @param currentPage
     * @param pageSize
     * @param user
     * @return
     */
    @PostMapping("/list")
    public Object getorders(@RequestParam(defaultValue = "0") Integer type,
                            @RequestParam(defaultValue = "1") Integer pageNo,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            SessionUserInfo user) {
        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }
        //查询订单列表
        PageFinder<Order> orders = orderService.getMyOrders(userId, type, pageNo, pageSize);
        if (orders == null || orders.getRowCount() <= 0) {
            return new ResponseMessage<>(1, "无订单信息");
        }
        OrderListResp resp = transformParam(orders);
        return new ResponseMessage<>(0, "success", resp);
    }

    @PostMapping("/detail")
    public Object getOrderDetail(String orderNo, SessionUserInfo user) {
        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        if (StringUtils.isBlank(orderNo)) {
            return new ResponseMessage<>(1, "订单号为空");
        }
        Order order = orderService.getOrderDetail(userId, orderNo);
        if (order == null) {
            return new ResponseMessage<>(1, orderNo + "订单号为空");
        }
        OrderResp resp = transformParam(order);
        return new ResponseMessage<>(0, "success", resp);
    }

    @PostMapping("/cancel")
    public Object cancelOrder(String orderNo, SessionUserInfo user) {
        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        if (StringUtils.isBlank(orderNo)) {
            return new ResponseMessage<>(1, "订单号为空");
        }
        try {
            final Order order = orderService.getOrder(userId, orderNo);
            if (order == null) {
                return new ResponseMessage<>(1, orderNo + "订单号为空");
            }
            boolean success = orderService.cancelOrder(userId, orderNo);
            if (success) {
                return new ResponseMessage<>(1, "订单取消成功");
            } else {
                return new ResponseMessage<>(1, "取消订单");
            }
        } catch (Exception e) {
            logger.error(orderNo + "订单取消失败：", e);
            return new ResponseMessage<>(1, "取消订单");
        }
    }

    @PostMapping("/recieve")
    public Object recieve(String orderNo, SessionUserInfo user) {
        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        if (StringUtils.isBlank(orderNo)) {
            return new ResponseMessage<>(1, "订单号为空");
        }
        try {
            final Order order = orderService.getOrder(userId, orderNo);
            if (order == null) {
                return new ResponseMessage<>(1, orderNo + "订单号为空");
            }
            boolean success = orderService.checkRecieveGoods(userId, orderNo);
            if (success) {
                return new ResponseMessage<>(1, "确认收货成功");
            } else {
                return new ResponseMessage<>(1, "确认收货失败");
            }
        } catch (Exception e) {
            logger.error(orderNo + "确认收货失败：", e);
            return new ResponseMessage<>(1, "确认收货失败");
        }
    }

    /**
     * 去支付
     *
     * @param orderNo
     * @param user
     * @return
     */
    @PostMapping("/gopay")
    public Object gopay(String orderNo, SessionUserInfo user) {
        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        if (StringUtils.isBlank(orderNo)) {
            return new ResponseMessage<>(1, "订单号为空");
        }
        try {
            //订单校验
            final Order order = orderService.getOrder(userId, orderNo);
            if (null == order) {
                return new ResponseMessage<>(1, "订单号不存在");
            }
            final Integer payStatus = order.getPayStatus();
            if (payStatus != 1) {
                return new ResponseMessage<>(1, "订单不是未支付状态");
            }
            PayInfo payInfo = payService.gopay(orderNo, userId);
            if (payInfo != null) {
                return new ResponseMessage<>(0, "success", payInfo);
            } else {
                return new ResponseMessage<>(1, "支付失败，请稍后重试");
            }
        } catch (Exception e) {
            logger.error(orderNo + "支付失败：", e);
            return new ResponseMessage<>(1, "支付失败，请稍后重试");
        }
    }

    private OrderListResp transformParam(PageFinder<Order> orders) {
        OrderListResp resp = new OrderListResp();
        resp.setCount(orders.getRowCount());
        List<OrderResp> orderResps = new ArrayList<>(orders.getRowCount());
        List<Order> data = orders.getData();
        for (Order order : data) {
            orderResps.add(transformParam(order));
        }
        resp.setOrders(orderResps);
        return resp;
    }

    private OrderResp transformParam(Order order) {
        OrderResp resp = new OrderResp();
        resp.setDeliveryWay(order.getDeliveryWay());
        resp.setExpressName(order.getExpressName());
        resp.setExpressNo(order.getExpressNo());
        resp.setAddress(order.getAddress());
        resp.setOrderNo(order.getOrderNo());
        resp.setOrderTime(order.getCreateTime());
        resp.setPayment(order.getPayment());
        resp.setTotalAmount(order.getProdTotalAmt());
        resp.setPromotionTotalAmt(order.getPromotionTotalAmt());
        resp.setPostageAmt(order.getPostageAmt());
        resp.setBuyNum(order.getProdTotalNum());
        //详情
        final List<OrderDetail> orderDetails = order.getOrderDetails();
        List<OrderDetailResp> detailResps = new ArrayList<>(orderDetails.size());
        for (OrderDetail detail : orderDetails) {
            OrderDetailResp res = new OrderDetailResp();
            res.setCommodityNo(detail.getCommodityNo());
            res.setCommodityName(detail.getCommodityName());
            res.setCommodityPic(detail.getCommodityPic());
            res.setPropName(detail.getPropName());
            res.setSalePrice(detail.getSalePrice());
            res.setNum(detail.getProductTotalNum());
            detailResps.add(res);
        }
        resp.setDetails(detailResps);
        //订单显示状态
        String showStatusStr = "等待付款";
        final Integer orderStatus = order.getOrderStatus();
        final Integer payStatus = order.getPayStatus();
        final Integer deliveryStatus = order.getDeliveryStatus();
        if (orderStatus == 1 && payStatus == 1) {
            showStatusStr = "等待付款";
            resp.setCancelOrder(true);
            resp.setGoPay(true);
        } else if (orderStatus == 3) {
            showStatusStr = "已取消";
            resp.setBuyAgain(true);
        } else if (orderStatus == 2 && payStatus == 2 && deliveryStatus == 2) {
            showStatusStr = "已发货";
            resp.setSeeLogistics(true);
            resp.setCheckGoods(true);
        } else if (orderStatus == 5) {
            showStatusStr = "已完成";
        } else if (orderStatus == 2 && payStatus == 2 && deliveryStatus == 1) {
            showStatusStr = "已付款";
            resp.setCancelOrder(true);
        }
        resp.setShowStatusStr(showStatusStr);
        return resp;
    }

    /**
     * 组装订单参数
     *
     * @param settlementParam
     * @param shoppingCartVo
     * @return
     */
    private OrderBaseVo transOrderParam(SettlementParamReq settlementParam, ShoppingCartVo shoppingCartVo) {
        OrderBaseVo orderBaseVo = new OrderBaseVo();
        orderBaseVo.setAddressId(settlementParam.getAddressId());
        orderBaseVo.setUserId(settlementParam.getLoginId());
        orderBaseVo.setDeliveryWay(settlementParam.getDeliveryWay());
        orderBaseVo.setPayment(settlementParam.getPayment());
        orderBaseVo.setSource(settlementParam.getSource());
        orderBaseVo.setRemark(settlementParam.getRemark());
        orderBaseVo.setProdTotalAmt(shoppingCartVo.getBuyAmount());
        orderBaseVo.setProdTotalNum(shoppingCartVo.getBuyNum());
        orderBaseVo.setPostageAmt(shoppingCartVo.getFreightAmount());
        orderBaseVo.setPromotionTotalAmt(shoppingCartVo.getPreferentialAmount());
        if (StringUtils.isBlank(settlementParam.getOrderNo())) {
            orderBaseVo.setOrderNoPrefix("L");
        }
        final List<CommodityColumnVo> commodityColumnVoList = shoppingCartVo.getCommodityColumnVoList();
        List<CommodityColumnVo> buyColumnVo = new ArrayList<>();
        for (CommodityColumnVo vo : commodityColumnVoList) {
            final Integer buyStatus = vo.getBuyStatus();
            if (buyStatus == ShoppingCartConstant.BUY_STATUS_CHECKED) {
                buyColumnVo.add(vo);
            }
        }
        orderBaseVo.setProducts(buyColumnVo);
        return orderBaseVo;
    }

}

