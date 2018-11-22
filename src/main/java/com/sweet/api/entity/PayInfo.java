package com.sweet.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_pay_info")
public class PayInfo extends Model<PayInfo> {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String id;

    /**
     * 微信支付号
     */
    @TableField("prepay_id")
    @JsonIgnore
    private String prepayId;

    @TableField("user_id")
    @JsonIgnore
    private String userId;

    /**
     * 内部订单号
     */
    @TableField("out_trade_no")
    private String outTradeNo;

    /**
     * 微信支付号
     */
    @TableField("app_id")
    private String appId;
    /**
     * 统一下单返回nonceStr
     */
    @TableField("nonce_str")
    private String nonceStr;
    /**
     * 失效时间
     */
    @TableField("expire_time")
    @JsonIgnore
    private Date expireTime;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonIgnore
    private Date createTime;

    private String paySign;

    private String signType;

    private String timeStamp;

    private String packageStr;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
