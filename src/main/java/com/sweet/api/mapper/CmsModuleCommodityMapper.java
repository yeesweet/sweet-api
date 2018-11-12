package com.sweet.api.mapper;



import com.sweet.api.entity.CmsModuleCommodity;

import java.util.List;


public interface CmsModuleCommodityMapper {

	/**
	 * 查询模块商品列表
	 * @param cmsModuleCommodity
	 * @return
	 */
	public List<CmsModuleCommodity> queryCmsCommodityList(CmsModuleCommodity cmsModuleCommodity);
}
