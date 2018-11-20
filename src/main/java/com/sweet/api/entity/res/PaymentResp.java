package com.sweet.api.entity.res;

import lombok.Data;

@Data
public class PaymentResp {
    private String transaction_id;
    private String nonce_str;
    private String bank_type;
    private String openid;
    private String sign;
    private String fee_type;
    private String mch_id;
    private String cash_fee;
    private String out_trade_no;
    private String appid;
    private String total_fee;
    private String trade_type;
    private String result_code;
    private String time_end;
    private String is_subscribe;
    private String return_code;
}
