package com.sweet.api.controller;


import com.sweet.api.annotation.WechatAccess;
import com.sweet.api.entity.UserInfo;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 小程序用户信息表 前端控制器
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/myUInfo")
    @WechatAccess
    public UserInfo myUInfo(SessionUserInfo sessionUserInfo){
        UserInfo userInfo = userInfoService.selectById(sessionUserInfo.getUserId());
        return userInfo;
    }

}

