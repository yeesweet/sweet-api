package com.sweet.api.entity.res;

import com.sweet.api.entity.UserAddress;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhang.hp
 * @date 2018/11/14.
 */
@Data
public class OrderResp {
    private String orderNo;
    private String showStatus;
    private Integer buyNum;
    private double totalAmount;
    private List<OrderDetailResp> details;

    private Integer deliveryWay;
    private String expressName;
    private String expressNo;
    private UserAddress address;
    private Date orderTime;
    private Integer payment;
    private double promotionTotalAmt;
    private double postageAmt;
}
