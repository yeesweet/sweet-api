package com.sweet.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sweet.api.entity.Commodity;
import com.sweet.api.entity.res.CommodityRes;
import com.sweet.api.entity.vo.CommodityVo;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-09-17
 */
public interface ICommodityService extends IService<Commodity> {
    /**
     * 根据商品编号获取商品信息
     * @param commodityNo
     * @return
     */
    Commodity getCommodityByNo(String commodityNo);

    /**
     * 获取同一货号下的所有商品
     * @param itemNo
     * @return
     */
    List<CommodityVo> getAllCommodityByItemNo(String itemNo);

    /**
     * 获取前端商品详情接口
     * @param commodityNo
     * @return
     */
    CommodityRes getCommodityDetailJson(String commodityNo);
}
