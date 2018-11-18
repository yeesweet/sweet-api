package com.sweet.api.controller;


import com.sweet.api.annotation.WechatAccess;
import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.Order;
import com.sweet.api.entity.OrderDetail;
import com.sweet.api.entity.ShoppingCartBaseInfo;
import com.sweet.api.entity.req.ShoppingCartReq;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.entity.res.ShoppingCartResp;
import com.sweet.api.entity.vo.ShoppingCartResultVo;
import com.sweet.api.service.IOrderService;
import com.sweet.api.service.IShoppingcartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author zhang.hp
 * @since 2018-10-15
 */
@RestController
@RequestMapping("cart")
@WechatAccess
public class ShoppingcartController {

    @Autowired
    private IShoppingcartService shoppingcartService;

    @Autowired
    private IOrderService orderService;

    /**
     * 查询购物车
     *
     * @param user
     * @return
     */
    @RequestMapping("/list")
    public Object carList(SessionUserInfo user) {

        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLoginId(userId);

        ShoppingCartResp resp = shoppingcartService.getCartListResp(info);
        return new ResponseMessage<>(0, "success", resp);
    }

    /**
     * 添加商品到购物车
     *
     * @param cartReqs
     * @param user
     * @param linkBuy
     * @param orderNo  重新购买
     * @return
     */
    @PostMapping("/add")
    public Object addCommodityToCart(@RequestBody List<ShoppingCartReq> cartReqs,
                                     @RequestParam(defaultValue = "1") Integer linkBuy,
                                     String orderNo,
                                     SessionUserInfo user) {

        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        if (StringUtils.isBlank(orderNo)) {
            if (null == cartReqs) {
                return new ResponseMessage<>(1, "商品为空");
            }

            if (cartReqs.size() <= 0) {
                return new ResponseMessage<>(1, "商品为空");
            }
        }
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLinkBuy(linkBuy);
        info.setLoginId(userId);
        Map<String, Integer> map = new HashMap<>(cartReqs.size());
        if (StringUtils.isBlank(orderNo)) {
            for (ShoppingCartReq req : cartReqs) {
                final String commodityNo = req.getCommodityNo();
                final Integer count = req.getCount();
                if (StringUtils.isNoneBlank(commodityNo) && count != null && count > 0) {
                    map.put(commodityNo, count);
                }
            }
        } else {
            //查询订单信息
            final Order order = orderService.getOrderDetail(userId, orderNo);
            if (order == null || order.getOrderDetails() == null || order.getOrderDetails().size() <= 0) {
                return new ResponseMessage<>(1, "重新购买失败");
            }
            final List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail detail : orderDetails) {
                map.put(detail.getCommodityNo(), detail.getProductTotalNum());
            }

        }
        ShoppingCartResultVo resultVo = shoppingcartService.addProductToCart(map, info);
        final boolean successFlag = resultVo.getSuccessFlag();
        final String resultMsg = resultVo.getResultMsg();
        int code = 1;
        if (successFlag) {
            code = 0;
        }
        return new ResponseMessage<>(code, resultMsg);
    }

    /**
     * 从购物车中删除商品
     *
     * @param rowIds
     * @param user
     * @return
     */
    @PostMapping("/remove")
    public Object removeFromCart(@RequestBody String[] rowIds, SessionUserInfo user) {

        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLoginId(userId);
        final ShoppingCartResultVo resultVo = shoppingcartService.removeProductFromCart(rowIds, info);
        final boolean successFlag = resultVo.getSuccessFlag();
        final String resultMsg = resultVo.getResultMsg();
        int code = 1;
        if (successFlag) {
            code = 0;
        }
        return new ResponseMessage<>(code, resultMsg);
    }

    /**
     *修改购物车数量
     * @param cartReqs
     * @param user
     * @return
     */
    @PostMapping("/editcommodity")
    public Object updateFromCart(@RequestBody List<ShoppingCartReq> cartReqs, SessionUserInfo user) {

        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        if (null == cartReqs) {
            return new ResponseMessage<>(1, "商品为空");
        }
        if (cartReqs.size() <= 0) {
            return new ResponseMessage<>(1, "商品为空");
        }
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLoginId(userId);

        Map<String, Integer> map = new HashMap<>(cartReqs.size());
        for (ShoppingCartReq req : cartReqs) {
            final String rowId = req.getRowId();
            final Integer count = req.getCount();
            if (StringUtils.isNoneBlank(rowId) && count != null && count > 0) {
                map.put(rowId, count);
            }
        }
        ShoppingCartResultVo resultVo = shoppingcartService.updateProductFromCart(map, info);
        final boolean successFlag = resultVo.getSuccessFlag();
        final String resultMsg = resultVo.getResultMsg();
        int code = 1;
        if (successFlag) {
            code = 0;
        }
        return new ResponseMessage<>(code, resultMsg);
    }

    /**
     * 购物车商品勾选
     *
     * @param cartReqs
     * @param user
     * @return
     */
    @PostMapping("/modifyStatus")
    public Object modifyStatus(@RequestBody List<ShoppingCartReq> cartReqs, SessionUserInfo user) {

        if (null == user) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        final String userId = user.getUserId();
        if (StringUtils.isBlank(userId)) {
            return new ResponseMessage<>(1, "请您先登录");
        }

        if (null == cartReqs) {
            return new ResponseMessage<>(1, "商品为空");
        }

        if (cartReqs.size() <= 0) {
            return new ResponseMessage<>(1, "商品为空");
        }
        ShoppingCartBaseInfo info = new ShoppingCartBaseInfo();
        info.setLoginId(userId);

        Map<String, Integer> map = new HashMap<>(cartReqs.size());
        for (ShoppingCartReq req : cartReqs) {
            final String rowId = req.getRowId();
            final Integer status = req.getStatus();
            if (StringUtils.isNoneBlank(rowId) && status != null) {
                map.put(rowId, status);
            }
        }
        ShoppingCartResultVo resultVo = shoppingcartService.modifyProductBuyStatus(map, info);
        final boolean successFlag = resultVo.getSuccessFlag();
        final String resultMsg = resultVo.getResultMsg();
        int code = 1;
        if (successFlag) {
            code = 0;
        }
        return new ResponseMessage<>(code, resultMsg);
    }
}

