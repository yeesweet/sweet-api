package com.sweet.api.service.impl;

import com.sweet.api.service.ICommodityService;
import com.sweet.api.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author zhang.hp
 * @date 2018/11/15.
 */
@Service
public class InventoryServiceImpl implements IInventoryService{

    @Autowired
    private ICommodityService commodityService;

    @Override
    public void increaseStockBatch(Map<String, Integer> stockMap) {
        Assert.notNull(stockMap,"参数为空");
        for(Map.Entry<String, Integer> entry : stockMap.entrySet()){
            final String commodityNo = entry.getKey();
            final Integer stock = entry.getValue();
            commodityService.increaseStock(commodityNo,stock);
        }
    }

    @Override
    public void decreaseStockBatch(Map<String, Integer> stockMap) {
        Assert.notNull(stockMap,"参数为空");
        for(Map.Entry<String, Integer> entry : stockMap.entrySet()){
            final String commodityNo = entry.getKey();
            final Integer stock = entry.getValue();
            commodityService.decreaseStock(commodityNo,stock);
        }
    }
}
