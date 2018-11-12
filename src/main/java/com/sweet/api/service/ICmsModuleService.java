package com.sweet.api.service;

import com.sweet.api.entity.CmsModule;

import java.util.List;

/**
 * 页面管理模块service
 * @author wang.s
 *
 */
public interface ICmsModuleService{

	/**
	 * 查询模块列表详情
	 * @param cmsModule
	 * @return
	 */
	public List<CmsModule> queryCmsModuleDetailsForClient(CmsModule cmsModule);

	/**
	 * 根据条件查询模块列表
	 * @param cmsModule
	 * @return
	 */
	public List<CmsModule>  queryCmsModuleList(CmsModule cmsModule);
}
