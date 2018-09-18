package com.sweet.api.service.impl;

import com.sweet.api.entity.Category;
import com.sweet.api.entity.Resp.CategoryResp;
import com.sweet.api.mapper.CategoryMapper;
import com.sweet.api.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author wang.s
 * @since 2018-08-06
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResp> selectCategoryList() {
        List<Category> categoryList = categoryMapper.selectCategoryList();
        List<CategoryResp> categoryRespList = new ArrayList<>();
        if(categoryList != null && categoryList.size()>0){
            List<CategoryResp> childCategory = null;
            Map<Long,List<CategoryResp>> childMap = new HashMap<>();
            for(int i=0;i<categoryList.size();i++){
                Category category = categoryList.get(i);
                if(category != null){
                    int level = category.getLevel();
                    CategoryResp categoryResp = new CategoryResp();
                    categoryResp.setId(category.getId());
                    categoryResp.setName(category.getName());
                    categoryResp.setImage(category.getImage());
                    categoryResp.setPid(category.getPid());
                    categoryResp.setSortNo(category.getSortNo());
                    if(level == 1){
                        categoryRespList.add(categoryResp);
                    }
                    if(level == 2){
                        childCategory = new ArrayList<>();
                        if(childMap.get(category.getPid()) != null && childMap.get(category.getPid()).size()>0){
                            childCategory = childMap.get(category.getPid());
                        }
                        childCategory.add(categoryResp);
                        childMap.put(category.getPid(),childCategory);
                    }
                }
            }

            if(categoryRespList != null && categoryRespList.size()>0){
                for(int i=0;i<categoryRespList.size();i++){
                    CategoryResp categoryResp = categoryRespList.get(i);
                    childCategory = childMap.get(categoryResp.getId());
                    if(childCategory!=null && childCategory.size()>0){
                        categoryResp.setChildCategory(childCategory);
                    }
                }
            }

        }
        return categoryRespList;
    }
}
