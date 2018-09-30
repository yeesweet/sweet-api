package com.sweet.api.service.impl;

import com.sweet.api.entity.bean.PageManager;
import com.sweet.api.mapper.PageManagerMapper;
import com.sweet.api.service.IPageManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 页面表 服务实现类
 * </p>
 *
 * @author wang.s
 * @since 2018-08-21
 */
@Service
public class PageManagerServiceImpl implements IPageManagerService {

    @Autowired
    private PageManagerMapper pageManagerMapper;

    @Override
    public List<PageManager> getPageManagerList(PageManager pageManager) {
        return pageManagerMapper.getPageManagerList(pageManager);
    }
}
