package com.sweet.api.entity.res;

import lombok.Data;

/**
 * @author zhang.hp
 * @date 2018/11/15.
 */
@Data
public class OrderDetailResp {
    private String commodityNo;
    private String commodityName;
    private String commodityPic;
    private Integer num;
    private double salePrice;
    private String propName;
}
