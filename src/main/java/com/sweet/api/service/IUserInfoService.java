package com.sweet.api.service;

import com.sweet.api.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 小程序用户信息表 服务类
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 根据openId unionId查询用户信息
     * @param openId
     * @param unionId
     * @return
     */
    public UserInfo selectOne(String openId,String unionId);

}
