package com.sweet.api.service;

import com.sweet.api.entity.res.LoginResp;

/**
 * <p>
 * 小程序登录 服务类
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
public interface ILoginService {

    public LoginResp login(String code);

}
