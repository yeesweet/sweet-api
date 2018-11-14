package com.sweet.api.entity.pay;

import lombok.Data;

/**
 * @author zhang.hp
 * @date 2018/11/12.
 */
@Data
public class PayInfo {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String prepayId;
    private String signType;
    private String paySign;
    private String orderNo;
}
