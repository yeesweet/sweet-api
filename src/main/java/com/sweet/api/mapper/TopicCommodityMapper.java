package com.sweet.api.mapper;

import com.sweet.api.entity.TopicCommodity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
  * 专题商品表 Mapper 接口
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
public interface TopicCommodityMapper {

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
     * 查询该活动专题编号下的商品 集合
     *
     * @param topicId
     */
    public List<TopicCommodity> getTopicCommodityByTopicIdOfPage(@Param("topicId") Long topicId, RowBounds rowBounds);
}