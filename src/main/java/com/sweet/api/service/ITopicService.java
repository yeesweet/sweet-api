package com.sweet.api.service;


import com.sweet.api.entity.Topic;

import java.util.List;

/**
 * <p>
 * 专题表 服务类
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
public interface ITopicService{

    /**
     * 查询名称是否存在，排除当前专题
     * @param id
     * @param name
     * @return
     */
    public int checkTopicName(Long id, String name);

    /**
     * 根据id查询专题
     * @param id
     * @return
     */
    public Topic getTopicById(Long id);

    /**
     * 获取未删除、已启用、有效期未开始或有效期内专题
     * @return
     */
    public List<Topic> getEffectiveTopicList();

}
