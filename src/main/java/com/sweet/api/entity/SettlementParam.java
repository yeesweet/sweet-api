package com.sweet.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang.hp
 * @date 2018/10/25.
 */
@Data
public class SettlementParam implements Serializable {

    private String commodityNo;
    private Integer count;
    private Integer linkBuy = 1;
    private String loginId;

}
