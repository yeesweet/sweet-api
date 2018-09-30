package com.sweet.api.mapper;

import com.sweet.api.entity.bean.PageManager;

import java.util.List;

/**
 * <p>
  * 页面表 Mapper 接口
 * </p>
 *
 * @author wang.s
 * @since 2018-08-21
 */
public interface PageManagerMapper{
    /**
     * 根据条件查询页面列表
     * @param pageManager
     * @return
     */
    public List<PageManager> getPageManagerList(PageManager pageManager);
}