package com.sweet.api.wx.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WxMessage {
    private String openid;
    @JSONField(name = "session_key")
    private String sessionKey;
    private String unionid;
    private String errmsg;

}
