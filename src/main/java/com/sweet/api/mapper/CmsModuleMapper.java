package com.sweet.api.mapper;

import com.sweet.api.entity.bean.CmsModule;

import java.util.List;


public interface CmsModuleMapper{

	/**
	 * 根据条件查询模块列表
	 * @param cmsModule
	 * @return
	 */
	public List<CmsModule>  queryCmsModuleList(CmsModule cmsModule);
	
	/**
	 * 根据id查询模块详情
	 * @param moduleId
	 * @return
	 */
	public CmsModule getCmsModuleById(Long moduleId);
	
	/**
	 * 查询模块列表详情（为mall和touch提供）
	 * @param cmsModule
	 * @return
	 */
	public List<CmsModule> queryCmsModuleDetailsForClient(CmsModule cmsModule);

}
