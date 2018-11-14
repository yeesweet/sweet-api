package com.sweet.api.service;

import com.sweet.api.entity.pay.PayTradeInfo;
import com.sweet.api.entity.pay.PayInfo;
import org.springframework.stereotype.Service;

/**
 * @author zhang.hp
 * @date 2018/11/12.
 */
@Service
public interface IPayService {

    PayInfo payorder(PayTradeInfo payTradeInfo);
}
