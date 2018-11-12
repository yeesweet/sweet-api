package com.sweet.api.service.impl;

import com.sweet.api.commons.Query;
import com.sweet.api.entity.TopicCommodity;
import com.sweet.api.mapper.TopicCommodityMapper;
import com.sweet.api.service.ITopicCommodityService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 专题商品表 服务实现类
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
@Service
public class TopicCommodityServiceImpl implements ITopicCommodityService {

    @Autowired
    private TopicCommodityMapper topicCommodityMapper;


    @Override
    public int getCommodityCountByTopicId(long topicId) {
        return topicCommodityMapper.getCommodityCountByTopicId(topicId);
    }

    @Override
    public List<TopicCommodity> getCommodityListByTopicId(long topicId) {
        return topicCommodityMapper.getCommodityListByTopicId(topicId);
    }

    @Override
    public List<TopicCommodity> getCommodityList(TopicCommodity topicCommodity) {
        return topicCommodityMapper.getCommodityList(topicCommodity);
    }

    @Override
    public List<TopicCommodity> getTopicCommodityByTopicIdOfPage(
            Long topicId, Query query) throws Exception {
        return topicCommodityMapper.getTopicCommodityByTopicIdOfPage(topicId,
                new RowBounds(query.getOffset(), query.getPageSize()));
    }
}
