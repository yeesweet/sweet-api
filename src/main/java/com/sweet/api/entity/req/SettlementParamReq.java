package com.sweet.api.entity.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang.hp
 * @date 2018/10/25.
 */
@Data
public class SettlementParamReq implements Serializable {

    private String commodityNo;
    private Integer count;
    private Integer linkBuy = 1;
    private String loginId;
    private String orderNo;
    /**
     * 配送方式 1 普通快递 2 自提
     */
    private Integer deliveryWay = 1;
    /**
     * 收货地址
     */
    private String addressId;
    /**
     * 支付方式 默认 1 微信支付
     */
    private Integer payment = 1;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 渠道  默认 1 小程序
     */
    private Integer source = 1;

}
