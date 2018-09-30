package com.sweet.api.service;

import com.sweet.api.commons.Query;
import com.sweet.api.entity.bean.TopicCommodity;

import java.util.List;

/**
 * <p>
 * 专题商品表 服务类
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
public interface ITopicCommodityService{

    /**
     * 根据topicId查询专题商品数量
     * @param topicId
     * @return
     */
    public int getCommodityCountByTopicId(long topicId);

    /**
     * 根据专题id查询商品列表
     * @param topicId
     * @return
     */
    public List<TopicCommodity> getCommodityListByTopicId(long topicId);

    /**
     * 根据条件查询专题商品
     * @param topicCommodity
     * @return
     */
    public List<TopicCommodity> getCommodityList(TopicCommodity topicCommodity);

    /**
     * 分页查询该活动专题编号下的商品列表
     * @param topicId
     * @param query
     */
    public List<TopicCommodity> getTopicCommodityByTopicIdOfPage(Long topicId, Query query) throws Exception;
	
}
