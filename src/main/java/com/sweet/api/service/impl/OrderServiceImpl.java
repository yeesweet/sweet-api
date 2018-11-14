package com.sweet.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sweet.api.commons.PageFinder;
import com.sweet.api.entity.Order;
import com.sweet.api.entity.OrderDetail;
import com.sweet.api.entity.vo.CommodityColumnVo;
import com.sweet.api.entity.vo.OrderBaseVo;
import com.sweet.api.mapper.OrderMapper;
import com.sweet.api.service.IOrderDetailService;
import com.sweet.api.service.IOrderService;
import com.sweet.api.util.BeanUtils;
import com.sweet.api.util.RedisCreateOrderNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private RedisCreateOrderNoUtils redisCreateOrderNoUtils;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order createOrder(OrderBaseVo orderBaseVo) {
        if (orderBaseVo == null) {
            return null;
        }
        Order order = null;
        try {
            order = BeanUtils.copy(orderBaseVo, Order.class);
            String orderNo = orderBaseVo.getOrderNo();
            if (StringUtils.isBlank(orderNo)) {
                orderNo = redisCreateOrderNoUtils.getOrderNo(orderBaseVo.getOrderNoPrefix());
            }
            order.setOrderNo(orderNo);
            order.setOrderStatus(1);
            order.setDeliveryStatus(1);
            order.setPayStatus(1);
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            //封装订单详情数据
            final List<CommodityColumnVo> products = orderBaseVo.getProducts();
            List<OrderDetail> details = new ArrayList<>(products.size());
            for (CommodityColumnVo vo : products) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderNo(orderNo);
                detail.setProductTotalNum(vo.getNum());
                detail.setProductTotalAmt(vo.getSalePrice() * vo.getNum());
                detail.setCommodityNo(vo.getCommodityNo());
                detail.setSalePrice(vo.getSalePrice());
                detail.setMarkPrice(vo.getMarketPrice());
                detail.setCommodityName(vo.getCommodityName());
                detail.setCommodityPic(vo.getDefaultPic());
                detail.setBrandName(vo.getBrandName());
                detail.setItemNo(vo.getItemNo());
                detail.setPropNo(vo.getPropNo());
                detail.setPropName(vo.getPropName());
                detail.setCreateTime(new Date());
                detail.setUpdateTime(new Date());
                detail.setDeleteFlag(1);
                details.add(detail);
            }
            saveOrderInfo(order, details);
        } catch (Exception e) {
            logger.error("保存订单数据出错：", JSON.toJSONString(order));
        }
        return order;
    }

    @Override
    public PageFinder<Order> getMyOrders(String userId, Integer type, Integer currentPage, Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        if (type == null) {
            type = 0;
        }
        final List<Order> myOrders = orderMapper.getMyOrders(userId, type);
        PageFinder<Order> pageFinder = null;
        if (myOrders != null && myOrders.size() > 0) {
            pageFinder = new PageFinder<>(currentPage, pageSize, myOrders.size(), myOrders);
        }
        return pageFinder;
    }

    @Override
    public Order getOrderDetail(String userId, String orderNo) {
        Assert.hasText(userId,"userId为空");
        Assert.hasText(orderNo,"orderNo为空");
        return orderMapper.getOrderDetail(userId,orderNo);
    }

    @Transactional(rollbackFor = Exception.class)
    private void saveOrderInfo(Order order, List<OrderDetail> details) {
        try {
            this.insert(order);
            orderDetailService.insertBatch(details);
        } catch (Exception e) {
            logger.error("保存订单数据出错：", e);
        }
    }

}
