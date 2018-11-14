package com.sweet.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sweet.api.commons.PageFinder;
import com.sweet.api.entity.Order;
import com.sweet.api.entity.vo.OrderBaseVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-12
 */
public interface IOrderService extends IService<Order> {
    /**
     * 创建订单
     *
     * @param orderBaseVo
     * @return
     */
    Order createOrder(OrderBaseVo orderBaseVo);

    /**
     * 查询订单列表
     *
     * @param userId
     * @param type
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageFinder<Order> getMyOrders(String userId, Integer type, Integer currentPage, Integer pageSize);

    /**
     * 查询订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    Order getOrderDetail(String userId, String orderNo);
}
