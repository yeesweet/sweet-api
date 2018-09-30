package com.sweet.api.controller;

import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.bean.CmsModule;
import com.sweet.api.entity.bean.PageManager;
import com.sweet.api.entity.res.PageManagerResp;
import com.sweet.api.service.ICmsModuleService;
import com.sweet.api.service.IPageManagerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 页面管理接口
 * @author wang.s
 */
@Controller
@RequestMapping("")
@Slf4j
public class PageManagerController{

	private static final Logger logger = LoggerFactory.getLogger(PageManagerController.class);

	@Autowired
	private IPageManagerService pageManagerService;
	@Autowired
	private ICmsModuleService cmsModuleService;

	/**
	 * 获取二级活动页
	 * @param pageManagerId
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPageManager")
	public ResponseMessage getPageManager(Long pageManagerId, Integer type) {
		if(type == null){
			return new ResponseMessage<>(0, "页面类型不能为空");
		}
		PageManagerResp pageManagerResp = new PageManagerResp();
		try{
			PageManager pageManager = new PageManager();
			pageManager.setType(type);
			pageManager.setId(pageManagerId);
			List<PageManager> pageManagerList = pageManagerService.getPageManagerList(pageManager);
			if(pageManagerList != null && pageManagerList.size()>0){
				PageManager pageManagerOne = pageManagerList.get(0);
				pageManagerResp.setId(pageManagerOne.getId());
				pageManagerResp.setName(pageManagerOne.getName());
				pageManagerResp.setTopName(pageManagerOne.getTopName());
				pageManagerResp.setTypeBg(pageManagerOne.getTypeBg());
				pageManagerResp.setBg(pageManagerOne.getBg());

				//获取页面下模块列表
				CmsModule cmsModule=new CmsModule();
				cmsModule.setPageManagerId(pageManagerOne.getId());
				List<CmsModule> modules = cmsModuleService.queryCmsModuleDetailsForClient(cmsModule);
				if(modules != null && modules.size()>0){
					pageManagerResp.setModuleList(modules);
				}
			}

		}catch (Exception ex) {
			logger.error("获取页面数据出错",ex);
			return new ResponseMessage<>(-1, "获取页面数据出错");
		}
		return new ResponseMessage(pageManagerResp);

	}

}
