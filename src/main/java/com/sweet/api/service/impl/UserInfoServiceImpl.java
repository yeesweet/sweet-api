package com.sweet.api.service.impl;

import com.sweet.api.entity.UserInfo;
import com.sweet.api.mapper.UserInfoMapper;
import com.sweet.api.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小程序用户信息表 服务实现类
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectOne(String openId, String unionId) {
        return userInfoMapper.selectOne(openId,unionId);
    }
}
