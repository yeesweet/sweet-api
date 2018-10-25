package com.sweet.api.controller;


import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.bean.CategoryCommodity;
import com.sweet.api.entity.bean.TopicCommodity;
import com.sweet.api.entity.req.CommodityListReq;
import com.sweet.api.entity.res.CommodityListResp;
import com.sweet.api.entity.res.CommodityResp;
import com.sweet.api.entity.vo.CommodityVo;
import com.sweet.api.service.ICategoryCommodityService;
import com.sweet.api.service.ICommodityService;
import com.sweet.api.service.ITopicCommodityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品详情页接口
 * </p>
 *
 * @author zhang.hp
 * @since 2018-09-17
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private ICommodityService commodityService;
    @Autowired
    private ITopicCommodityService topicCommodityService;
    @Autowired
    private ICategoryCommodityService categoryCommodityService;

    @RequestMapping("/detail")
    public Object getCommodityDetail(String commodityNo) {
        if (StringUtils.isBlank(commodityNo)) {
            return new ResponseMessage<>(1, "commodityNo为空");
        }
        CommodityResp res = commodityService.getCommodityDetailJson(commodityNo);
        return new ResponseMessage<>(1, "success", res);
    }

    /**
     * 获取商品列表
     * @param commodityListReq
     * @return
     */
    @RequestMapping("/getCommodityList")
    public Object getCommodityList(CommodityListReq commodityListReq) {
        List<CommodityListResp> commodityList = new ArrayList<>();
        if(commodityListReq.getType() == null){
            return new ResponseMessage<>(1, "商品列表类型不能为空");
        }
        if(commodityListReq.getType() == 2 || commodityListReq.getType() == 3){
            if(commodityListReq.getParamId() == null){
                return new ResponseMessage<>(1, "商品列表参数id不能为空");
            }
        }
        if(commodityListReq.getSortOrder() == null){
            commodityListReq.setSortOrder(2);
        }
        //促销专题商品列表
        if (commodityListReq.getType() == 2) {
            List<TopicCommodity> topicCommodityList = topicCommodityService.getCommodityListByTopicId(commodityListReq.getParamId());
            if(!(topicCommodityList != null && topicCommodityList.size()>0)){
                return new ResponseMessage<>(commodityList);
            }
            List<String> noList = new ArrayList<>();
            for(int i=0;i<topicCommodityList.size();i++){
                TopicCommodity topicCommodity = topicCommodityList.get(i);
                if(topicCommodity!=null && StringUtils.isNotBlank(topicCommodity.getCommodityNo())){
                    noList.add(topicCommodity.getCommodityNo());
                }
            }
            commodityListReq.setNos(noList);
        }
        //二级分类商品列表
        if (commodityListReq.getType() == 3) {
            List<CategoryCommodity> categoryCommodityList = categoryCommodityService.getCategoryCommodity(commodityListReq.getParamId());
            if(!(categoryCommodityList != null && categoryCommodityList.size()>0)){
                return new ResponseMessage<>(commodityList);
            }
            List<String> noList = new ArrayList<>();
            for(int i=0;i<categoryCommodityList.size();i++){
                CategoryCommodity categoryCommodity = categoryCommodityList.get(i);
                if(categoryCommodity!=null && StringUtils.isNotBlank(categoryCommodity.getCommodityNo())){
                    noList.add(categoryCommodity.getCommodityNo());
                }
            }
            commodityListReq.setNos(noList);
        }
        List<CommodityVo> commodityVoList = commodityService.getCommoditySearchList(commodityListReq);
        if(commodityVoList != null && commodityVoList.size()>0){
            for(int i=0;i<commodityVoList.size();i++){
                CommodityVo commodityVo = commodityVoList.get(i);
                if(commodityVo!=null){
                    CommodityListResp commodityListResp = new CommodityListResp();
                    commodityListResp.setId(commodityVo.getId());
                    commodityListResp.setCommodityNo(commodityVo.getCommodityNo());
                    commodityListResp.setCommodityName(commodityVo.getCommodityName());
                    commodityListResp.setSellPoint(commodityVo.getSellPoint());
                    commodityListResp.setDefaultPic(commodityVo.getDefaultPic());
                    commodityListResp.setSalePrice(commodityVo.getSalePrice());
                    commodityListResp.setMarketPrice(commodityVo.getMarketPrice());
                    commodityList.add(commodityListResp);
                }
            }
        }
        return new ResponseMessage<>(0,"success",commodityList);
    }

}

