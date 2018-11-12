package com.sweet.api.service;


import com.sweet.api.entity.PageManager;

import java.util.List;

/**
 * <p>
 * 页面表 服务类
 * </p>
 *
 * @author wang.s
 * @since 2018-08-21
 */
public interface IPageManagerService{

    /**
     * 根据条件查询页面列表
     * @param pageManager
     * @return
     */
    public List<PageManager> getPageManagerList(PageManager pageManager);
	
}
