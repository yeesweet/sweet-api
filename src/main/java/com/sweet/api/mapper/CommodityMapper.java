package com.sweet.api.mapper;

import com.sweet.api.commons.SuperMapper;
import com.sweet.api.entity.Commodity;
import com.sweet.api.entity.req.CommodityListReq;
import com.sweet.api.entity.vo.CommodityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author zhang.hp
 * @since 2018-09-17
 */
public interface CommodityMapper extends SuperMapper<Commodity> {

    Commodity getCommodityByNo(@Param("commodityNo") String commodityNo);

    List<CommodityVo> getAllCommodityByItemNo(@Param("itemNo") String itemNo);

    List<CommodityVo> getCommodityList(@Param("nos") List<String> nos,
                                       @Param("shelv") boolean shelve,
                                       @Param("instock") boolean instock);

    List<CommodityVo> getCommoditySearchList(CommodityListReq commodityListReq);

    void increaseStock(@Param("commodityNo") String commodityNo, @Param("stock") Integer stock);

    void decreaseStock(@Param("commodityNo") String commodityNo, @Param("stock") Integer stock);
}
