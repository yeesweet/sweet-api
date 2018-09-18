package com.sweet.api.controller;


import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.res.CommodityRes;
import com.sweet.api.service.ICommodityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品详情页接口
 * </p>
 *
 * @author zhang.hp
 * @since 2018-09-17
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private ICommodityService commodityService;

    @RequestMapping("/detail")
    public Object getCommodityDetail(String commodityNo) {
        if (StringUtils.isBlank(commodityNo)) {
            return new ResponseMessage<>(0, "commodityNo为空");
        }
        CommodityRes res = commodityService.getCommodityDetailJson(commodityNo);
        return new ResponseMessage<>(0, "success", res);
    }

}

