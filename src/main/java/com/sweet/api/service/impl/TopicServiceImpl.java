package com.sweet.api.service.impl;

import com.sweet.api.entity.bean.Topic;
import com.sweet.api.mapper.TopicMapper;
import com.sweet.api.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 专题表 服务实现类
 * </p>
 *
 * @author wang.s2
 * @since 2018-09-07
 */
@Service
public class TopicServiceImpl implements ITopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public int checkTopicName(Long id, String name) {
        return topicMapper.checkTopicName(id, name);
    }

    @Override
    public Topic getTopicById(Long id) {
        return topicMapper.getTopicById(id);
    }

    @Override
    public List<Topic> getEffectiveTopicList() {
        return topicMapper.getEffectiveTopicList();
    }

}
