package com.sweet.api.service;

import com.sweet.api.entity.res.CategoryResp;

import java.util.List;

/**
 * 商品分类表 服务类
 * @author wang.s
 * @since 2018-08-06
 */
public interface ICategoryService{

    /**
     * 根据条件查询分类列表
     * @return
     */
    public List<CategoryResp> selectCategoryList();
}
