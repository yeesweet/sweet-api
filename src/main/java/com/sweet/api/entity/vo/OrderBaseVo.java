package com.sweet.api.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zhang.hp
 * @date 2018/11/13.
 */
@Data
public class OrderBaseVo {
    /**
     * 用户id
     */
    private String userId;
    /**
     *订单号前缀
     */
    private String orderNoPrefix;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单总金额
     */
    private Double prodTotalAmt;
    /**
     * 优惠总金额
     */
    private Double promotionTotalAmt;
    /**
     * 运费
     */
    private Double postageAmt;
    /**
     * 订单总数量
     */
    private Integer prodTotalNum;

    private List<CommodityColumnVo> products;
    /**
     * 配送方式 1 普通快递 2 自提
     */
    private Integer deliveryWay;
    /**
     * 收货人地址ID
     */
    private String addressId;
    /**
     * 支付方式 1 微信支付
     */
    private Integer payment;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 订单渠道 1 小程序
     */
    private Integer source;

}
