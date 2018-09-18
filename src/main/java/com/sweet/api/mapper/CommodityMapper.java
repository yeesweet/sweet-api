package com.sweet.api.mapper;

import com.sweet.api.commons.SuperMapper;
import com.sweet.api.entity.Commodity;
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
}
