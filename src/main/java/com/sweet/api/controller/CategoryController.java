package com.sweet.api.controller;


import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.Resp.CategoryResp;
import com.sweet.api.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 分类 前端控制器
 * </p>
 *
 * @author wangsai
 * @since 2018-09-18
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("/getCategoryInfo")
    public ResponseMessage getCategoryInfo(){
        List<CategoryResp> categoryRespList = categoryService.selectCategoryList();
        return new ResponseMessage(categoryRespList);
    }
}

