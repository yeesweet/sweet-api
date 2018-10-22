package com.sweet.api.mapper;

import com.sweet.api.entity.bean.CategoryCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类商品关系表 Mapper 接口
 */
public interface CategoryCommodityMapper{

    /**
     * 获取分类下商品编号list
     * @param categoryId
     * @return
     */
    public List<CategoryCommodity> getCategoryCommodity(@Param("categoryId") Long categoryId);
}