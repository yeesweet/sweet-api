package com.sweet.api.service.impl;

import com.sweet.api.entity.CmsModuleCommodity;
import com.sweet.api.mapper.CmsModuleCommodityMapper;
import com.sweet.api.service.ICmsModuleCommodityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CmsModuleCommodityServiceImpl implements ICmsModuleCommodityService {

	@Resource
	private CmsModuleCommodityMapper cmsModuleCommodityMapper;
	
	@Override
	public List<CmsModuleCommodity> queryCmsCommodityList(
			CmsModuleCommodity cmsModuleCommodity) {
		return this.cmsModuleCommodityMapper.queryCmsCommodityList(cmsModuleCommodity);
	}
}
