package com.sweet.api.service;

import com.sweet.api.entity.bean.CategoryCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类商品关系表 服务类
 */
public interface ICategoryCommodityService{

    /**
     * 获取分类下商品编号list
     * @param categoryId
     * @return
     */
    public List<CategoryCommodity> getCategoryCommodity(@Param("categoryId") Long categoryId);

}
