package com.sweet.api.entity.pay;

import com.sweet.api.entity.res.SessionUserInfo;
import lombok.Data;

/**
 * @author zhang.hp
 * @date 2018/11/12.
 */
@Data
public class PayTradeInfo {
    /**
     * 交易类型　pay: 支付　refund: 退款
     */
    private String tradeType;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 支付金额
     */
    private double amount;
    /**
     * 支付方式
     */
    private Integer payment;

    private SessionUserInfo user;
}
