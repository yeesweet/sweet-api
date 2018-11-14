package com.sweet.api.entity.pay;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信统一下单返回数据
 */
@XmlRootElement(name = "xml")
@Data
public class WechatOrder {
    private String return_code;
    private String return_msg;
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String result_code;
    private String err_code;
    private String err_code_des;
    private String trade_type;
    private String prepay_id;
    private String code_url;
    private String openid;
}
