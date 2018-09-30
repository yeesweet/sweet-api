package com.sweet.api.service.impl;

import com.sweet.api.commons.Query;
import com.sweet.api.constants.PageManagerConstant;
import com.sweet.api.entity.bean.*;
import com.sweet.api.entity.vo.CommodityVo;
import com.sweet.api.mapper.CmsModuleCommodityMapper;
import com.sweet.api.mapper.CmsModuleDetailsMapper;
import com.sweet.api.mapper.CmsModuleMapper;
import com.sweet.api.service.ICmsModuleService;
import com.sweet.api.service.ICommodityService;
import com.sweet.api.service.ITopicCommodityService;
import com.sweet.api.service.ITopicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CmsModuleServiceImpl implements ICmsModuleService {

	@Autowired
	private CmsModuleMapper cmsModuleMapper;
	@Autowired
	private CmsModuleDetailsMapper cmsModuleDetailsMapper;
	@Autowired
	private CmsModuleCommodityMapper cmsModuleCommodityMapper;
	@Autowired
	private ICommodityService commodityService;
	@Autowired
	private ITopicService topicService;
	@Autowired
	private ITopicCommodityService topicCommodityService;

	@Override
	public List<CmsModule> queryCmsModuleDetailsForClient(CmsModule cmsModule) {
		Date nowDate = new Date();
		List<CmsModule> cmsModules = this.cmsModuleMapper.queryCmsModuleDetailsForClient(cmsModule);
		if(cmsModules!=null && cmsModules.size()>0){
			for(int i=0;i<cmsModules.size();i++){
				CmsModule module = cmsModules.get(i);
				//模块对应商品列表查询
				if(module.getModuleStyle() == 16){
					setGoodsModuleForClient(module);
				}else if(module.getModuleStyle() == 15 || module.getModuleStyle() == 12){
					setModuleCommodityForClient(module);
				}
			}
		}
		return cmsModules;
	}
	
	/**
	 * 模块对应商品列表查询
	 * @param cmsModule
	 */
	private void setModuleCommodityForClient(CmsModule cmsModule){
		CmsModuleCommodity cmsCommodity = new CmsModuleCommodity();
		cmsCommodity.setModuleId(cmsModule.getId());
		List<CmsModuleCommodity> cmsCommodities = cmsModuleCommodityMapper.queryCmsCommodityList(cmsCommodity);
		List<CmsModuleDetails> moduleDetails = new ArrayList<CmsModuleDetails>();
		if(cmsCommodities != null && cmsCommodities.size()>0){
			List<String> nos = new ArrayList<String>();
			for(int i=0;i<cmsCommodities.size();i++){
				nos.add(cmsCommodities.get(i).getCommodityCode());
			}
			List<CommodityVo> commodityVos = commodityService.getCommodityList(nos,true,true);
			Map<String,Object> commdityMap = new HashMap<>();
			if(commodityVos != null && commodityVos.size()>0){
				for(int i=0;i<commodityVos.size();i++){
					CommodityVo commodityVo = commodityVos.get(i);
					commdityMap.put(commodityVo.getCommodityNo(),commodityVo);
				}
			}
			//对商品进行排序 start     
			for (CmsModuleCommodity commodity : cmsCommodities) {
				CommodityVo commodityVo = (CommodityVo) commdityMap.get(commodity.getCommodityCode());
				if (commodityVo!=null) {
					CmsModuleDetails cmsDetails = new CmsModuleDetails();
					cmsDetails.setModuleId(cmsModule.getId());
					cmsDetails.setImgUrl(commodityVo.getDefaultPic());
					cmsDetails.setCommodityName(commodityVo.getCommodityName());
					cmsDetails.setMarketPrice(commodityVo.getMarketPrice());
					cmsDetails.setSalePrice(commodityVo.getSalePrice());
					cmsDetails.setCommonId(commodity.getCommodityCode());
					cmsDetails.setSubType(PageManagerConstant.COMMODITY_LIST);
					moduleDetails.add(cmsDetails);
				}
			}
		}
		cmsModule.setModuleDetails(moduleDetails);
	}
	
	/**
	 * 模块16对应商品列表查询,从促销专题查询商品
	 * @param cmsModule
	 */
	private void setGoodsModuleForClient(CmsModule cmsModule){
		List<CmsModuleDetails> moduleDetails = new ArrayList<CmsModuleDetails>();
		Map<String,String> params = new HashMap<String, String>();
		try {
			if(cmsModule != null && cmsModule.getModuleDetails() != null && cmsModule.getModuleDetails().size()>0){
				CmsModuleDetails details = cmsModule.getModuleDetails().get(0);
				if(details != null){
					String commonId = details.getCommonId();
					Long topicId = 0l;
					if(StringUtils.isNotBlank(commonId) && org.apache.commons.lang3.StringUtils.isNumeric(commonId)){
						topicId = Long.parseLong(commonId);
					}
					Topic cmsTopics = topicService.getTopicById(topicId);
					Query query = new Query();
					query.setPageSize(300);
					List<TopicCommodity> commodityList = topicCommodityService.getTopicCommodityByTopicIdOfPage(
							topicId, query);
					if(commodityList != null && commodityList.size()>0){
						List<String> nos = new ArrayList<String>();
						for(int i=0;i<commodityList.size();i++){
							nos.add(commodityList.get(i).getCommodityNo());
						}
						List<CommodityVo> commodityVos = commodityService.getCommodityList(nos,false,false);
						Map<String,Object> commdityMap = new HashMap<>();
						if(commodityVos != null && commodityVos.size()>0){
							for(int i=0;i<commodityVos.size();i++){
								CommodityVo commodityVo = commodityVos.get(i);
								commdityMap.put(commodityVo.getCommodityNo(),commodityVo);
							}
						}
						//对商品进行排序 start
						for (TopicCommodity commodity : commodityList) {
							CommodityVo commodityVo = (CommodityVo) commdityMap.get(commodity.getCommodityNo());
							if (commodityVo!=null) {
								CmsModuleDetails cmsModuleDetails = new CmsModuleDetails();
								cmsModuleDetails.setCommodityName(commodityVo.getCommodityName());
								cmsModuleDetails.setModuleId(cmsModule.getId());
								cmsModuleDetails.setSubType(PageManagerConstant.COMMODITY_LIST);
								String defaultPic = commodityVo.getDefaultPic();
								cmsModuleDetails.setImgUrl(defaultPic);
								cmsModuleDetails.setCommonId(commodityVo.getCommodityNo());
								cmsModuleDetails.setMarketPrice(commodityVo.getMarketPrice());
								cmsModuleDetails.setSalePrice(commodityVo.getSalePrice());
								moduleDetails.add(cmsModuleDetails);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		cmsModule.setModuleDetails(moduleDetails);
	}

	@Override
	public List<CmsModule> queryCmsModuleList(CmsModule cmsModule) {
		return cmsModuleMapper.queryCmsModuleList(cmsModule);
	}
}
