package com.sweet.api.service;

import java.util.Map;

/**
 * @author zhang.hp
 * @date 2018/11/15.
 */
public interface IInventoryService {

    /**
     * 批量增加库存
     * @param stockMap key:commodityNo Value:stock
     */
    void increaseStockBatch(Map<String,Integer> stockMap);

    /**
     * 批量减少库存
     * @param stockMap key:commodityNo Value:stock
     */
    void decreaseStockBatch(Map<String,Integer> stockMap);
}
