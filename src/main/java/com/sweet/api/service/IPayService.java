package com.sweet.api.service;

import com.sweet.api.entity.PayInfo;
import com.sweet.api.entity.pay.PayTradeInfo;
import org.springframework.stereotype.Service;

/**
 * @author zhang.hp
 * @date 2018/11/12.
 */
@Service
public interface IPayService {

    /**
     * 订单支付
     * @param payTradeInfo
     * @return
     */
    PayInfo payorder(PayTradeInfo payTradeInfo);

    PayInfo gopay(String orderNo, String userId);
}
