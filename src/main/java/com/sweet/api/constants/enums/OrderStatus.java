package com.sweet.api.constants.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum OrderStatus implements IEnum<Integer> {
    UNPAID(1, "待付款"),
    PAID(2,"已支付"),
    STATUS_CANCELED(3, "订单取消"),
    STATUS_SEND(4, "订单已发货"),
    STATUS_FINISHED(5, "订单完成");


    private Integer value;
    private String status;

    OrderStatus(Integer value, String status) {
        this.value = value;
        this.status = status;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
