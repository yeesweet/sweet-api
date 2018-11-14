package com.sweet.api.service.impl;

import com.sweet.api.entity.OrderDetail;
import com.sweet.api.mapper.OrderDetailMapper;
import com.sweet.api.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情 服务实现类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-12
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
