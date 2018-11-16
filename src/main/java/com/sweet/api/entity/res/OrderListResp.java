package com.sweet.api.entity.res;

import lombok.Data;

import java.util.List;

/**
 * @author zhang.hp
 * @date 2018/11/14.
 */
@Data
public class OrderListResp {
    private long count;
    private List<OrderResp> orders;
}
