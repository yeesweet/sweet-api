package com.sweet.api.service;


import com.sweet.api.entity.bean.CmsModuleCommodity;

import java.util.List;

/**
 * 模块商品service
 * @author wang.s
 *
 */
public interface ICmsModuleCommodityService{
	/**
	 * 查询模块商品列表
	 * @param cmsModuleCommodity
	 * @return
	 */
	public List<CmsModuleCommodity> queryCmsCommodityList(CmsModuleCommodity cmsModuleCommodity);
}
