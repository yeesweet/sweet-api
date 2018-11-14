package com.sweet.api.entity.pay;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 统一下单请求数据
 */
@XmlRootElement(name = "xml")
@Data
public class UnifiedOrder {
    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 附加数据(说明)
     */
//    private String attach;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 随机串
     */
    private String nonce_str;
    /**
     * 通知地址
     */
    private String notify_url;
    /**
     * 用户标识
     */
    private String openid;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 终端IP（用户）
     */
    private String spbill_create_ip;
    /**
     * 总金额
     */
    private Integer total_fee;
    /**
     * 交易类型
     */
    private String trade_type;
    /**
     * 签名
     */
    private String sign;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * WEB
     */
    private String device_info;

    /**
     * 统一下单接口
     */
    private String prepay_id;

    /**
     * 时间戳
     * @return
     */
    private String timeStamp;
}
