package com.sweet.api.entity.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang.hp
 * @date 2018/10/25.
 */
@Data
public class SettlementParamReq implements Serializable {

    private String commodityNo;
    private Integer count;
    private Integer linkBuy = 1;
    private String loginId;

}
