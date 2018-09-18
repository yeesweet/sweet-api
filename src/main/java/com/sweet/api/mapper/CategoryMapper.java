package com.sweet.api.mapper;


import com.sweet.api.entity.Category;

import java.util.List;

/**
 * <p>
  * 商品分类表 Mapper 接口
 * </p>
 *
 * @author wang.s
 * @since 2018-08-06
 */
public interface CategoryMapper{

    public List<Category> selectCategoryList();
}