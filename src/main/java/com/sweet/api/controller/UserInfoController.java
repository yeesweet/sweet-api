package com.sweet.api.controller;


import com.sweet.api.annotation.WechatAccess;
import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.UserInfo;
import com.sweet.api.entity.req.UserInfoReq;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.entity.res.UserInfoResp;
import com.sweet.api.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 小程序用户信息表 前端控制器
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
@RestController
@RequestMapping("/userInfo")
@Slf4j
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 我的页面
     * @param sessionUserInfo
     * @return
     */
    @RequestMapping("/myUInfo")
    @WechatAccess
    public ResponseMessage myUInfo(SessionUserInfo sessionUserInfo){
        UserInfoResp userInfoResp = new UserInfoResp();
        UserInfo userInfo = userInfoService.selectById(sessionUserInfo.getUserId());
        if(userInfo != null){
            userInfoResp.setUserId(userInfo.getId());
            userInfoResp.setUserName(userInfo.getUserName());
            userInfoResp.setAvatarUrl(userInfo.getAvatarUrl());
        }
        return new ResponseMessage(userInfoResp);
    }

    /**
     * 修改昵称和头像
     * @param userInfoReq
     * @param sessionUserInfo
     * @return
     */
    @RequestMapping("/updateUserInfo")
    @WechatAccess
    public ResponseMessage updateUserInfo(UserInfoReq userInfoReq,SessionUserInfo sessionUserInfo){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(sessionUserInfo.getUserId());
        userInfo.setUserName(userInfoReq.getUserName());
        userInfo.setAvatarUrl(userInfoReq.getAvatarUrl());
        userInfoService.updateById(userInfo);
        return new ResponseMessage();
    }



}

