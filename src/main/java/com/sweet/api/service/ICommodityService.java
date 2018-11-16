package com.sweet.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sweet.api.entity.Commodity;
import com.sweet.api.entity.req.CommodityListReq;
import com.sweet.api.entity.res.CommodityResp;
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
    CommodityResp getCommodityDetailJson(String commodityNo);

    /**
     * 批量查询商品信息 不包含商品主图、详情图、分类信息
     * @param nos 商品编号
     * @param shelve 是否只查询上架商品 默认true
     * @param instock 是否只查询有库存商品 默认true
     * @return
     */
    List<CommodityVo> getCommodityList(List<String> nos, boolean shelve, boolean instock);

    /**
     * 商品列表查询接口
     * @param commodityListReq
     * @return
     */
    List<CommodityVo> getCommoditySearchList(CommodityListReq commodityListReq);

    /**
     * 增加库存
     * @param commodityNo
     * @param stock
     */
    void increaseStock(String commodityNo, Integer stock);

    /**
     * 减少库存
     * @param stockMap
     */
    void decreaseStock(String commodityNo, Integer stock);
}
