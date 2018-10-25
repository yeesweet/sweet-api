package com.sweet.api.entity.req;

import lombok.Data;

/**
 * @author zhang.hp
 * @date 2018/10/23.
 */
@Data
public class ShoppingCartReq {
    private String commodityNo;
    private Integer count;
    private String rowId;
    private Integer status;
}
