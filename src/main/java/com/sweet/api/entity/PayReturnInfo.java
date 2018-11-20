package com.sweet.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 支付回调结果表
 * </p>
 *
 * @author zhang.hp
 * @since 2018-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_pay_return_info")
public class PayReturnInfo extends Model<PayReturnInfo> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 内部订单号
     */
    @TableField("out_trade_no")
    private String outTradeNo;
    @TableField("total_fee")
    private Integer totalFee;
    @TableField("app_id")
    private String appId;
    @TableField("open_id")
    private String openId;
    /**
     * JSAPI
     */
    @TableField("trade_type")
    private String tradeType;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * init success cancel failed
     */
    private String status;
    @TableField("bank_type")
    private String bankType;
    @TableField("cash_fee")
    private Integer cashFee;
    @TableField("fee_type")
    private String feeType;
    @TableField("is_subscribe")
    private String isSubscribe;
    @TableField("mch_id")
    private String mchId;
    @TableField("result_code")
    private String resultCode;
    @TableField("return_code")
    private String returnCode;
    @TableField("time_end")
    private String timeEnd;
    @TableField("transaction_id")
    private String transactionId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
