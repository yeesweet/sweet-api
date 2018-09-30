package com.sweet.api.mapper;

import com.sweet.api.entity.bean.Topic;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
  * 专题表 Mapper 接口
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
public interface TopicMapper{


    /**
     * 查询名称是否存在，排除当前专题
     * @param id
     * @param name
     * @return
     */
    public int checkTopicName(@Param(value = "id") Long id, @Param(value = "name") String name);

    /**
     * 根据id查询专题
     * @param id
     * @return
     */
    public Topic getTopicById(@Param(value = "id") Long id);

    /**
     * 获取未删除、已启用、有效期未开始或有效期内专题
     * @return
     */
    public List<Topic> getEffectiveTopicList();

}