package com.sweet.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang.hp
 * @date 2018/10/17.
 */
@Data
public class ShoppingCartBaseInfo implements Serializable {
    /**
     * 用户登录id
     */
    private String loginId;
    /**
     * 立即购买  1：正常加入购物车，0：立即购买
     */
    private Integer linkBuy = 1;
}
