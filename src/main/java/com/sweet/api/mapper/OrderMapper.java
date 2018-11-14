package com.sweet.api.mapper;

import com.sweet.api.commons.SuperMapper;
import com.sweet.api.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-12
 */
public interface OrderMapper extends SuperMapper<Order> {

    List<Order> getMyOrders(@Param("userId") String userId, @Param("type") Integer type);


    Order getOrderDetail(@Param("userId") String userId, @Param("orderNo") String orderNo);
}
