package com.sweet.api.service.impl;

import com.sweet.api.entity.bean.CategoryCommodity;
import com.sweet.api.mapper.CategoryCommodityMapper;
import com.sweet.api.service.ICategoryCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类商品关系表 服务实现类
 */
@Service
public class CategoryCommodityServiceImpl implements ICategoryCommodityService {

    @Autowired
    private CategoryCommodityMapper categoryCommodityMapper;


    @Override
    public List<CategoryCommodity> getCategoryCommodity(Long categoryId) {
        return categoryCommodityMapper.getCategoryCommodity(categoryId);
    }
}
