package com.sweet.api.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhang.hp
 * @date 2018/10/17.
 */
@Data
public class ShoppingCartResultVo implements Serializable{
    /**
     * 成功标识
     */
    private Boolean successFlag = false;
    /**
     * 操作购物车结果消息
     */
    private String resultMsg;
}
