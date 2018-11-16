package com.sweet.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sweet.api.constants.RedisConsts;
import com.sweet.api.entity.UserInfo;
import com.sweet.api.entity.res.LoginResp;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.entity.res.UserInfoSimple;
import com.sweet.api.service.ILoginService;
import com.sweet.api.service.IUserInfoService;
import com.sweet.api.util.StringUtil;
import com.sweet.api.wx.entity.WxMessage;
import com.sweet.api.wx.service.WechatCommon;
import org.bouncycastle.util.encoders.UrlBase64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 小程序登录 服务实现类
 * </p>
 *
 * @author wangsai
 * @since 2018-11-09
 */
@Service
public class LoginServiceImpl  implements ILoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);


    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Autowired
    private WechatCommon wechatCommon;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginResp login(String code) throws IOException {
        WxMessage wxMessage = wechatCommon.miniProgramLogin(code,appId,appSecret);

//        WxMessage wxMessage = new WxMessage();
//        wxMessage.setOpenid("test");
//        wxMessage.setUnionid("test");

        if(wxMessage != null && wxMessage.getOpenid() != null){
            return setSessionUserInfo(wxMessage);
        }
        return null;
    }

    /**
     * 设置登录用户信息
     * @param wxMessage
     */
    public LoginResp setSessionUserInfo(WxMessage wxMessage){
        UserInfo userInfo = userInfoService.selectOne(wxMessage.getOpenid(),wxMessage.getUnionid());
        SessionUserInfo sessionUserInfo = new SessionUserInfo();
        String userId = "";
        if (userInfo == null) {
            userInfo = new UserInfo();
            userId = StringUtil.getUUID();
            userInfo.setId(userId);
            userInfo.setUnionId(wxMessage.getUnionid());
            userInfo.setOpenId(wxMessage.getOpenid());
            userInfo.setCreateTime(new Date());
            userInfo.setUpdateTime(new Date());
            userInfoService.insert(userInfo);
            sessionUserInfo.setUserId(userId);
            sessionUserInfo.setOpenId(wxMessage.getOpenid());
            sessionUserInfo.setSessionKey(wxMessage.getSessionKey());
        }
        if (userInfo != null) {
            userId = userInfo.getId();
            sessionUserInfo.setUserId(userId);
            sessionUserInfo.setOpenId(wxMessage.getOpenid());
            sessionUserInfo.setSessionKey(wxMessage.getSessionKey());
        }

        UserInfoSimple userInfoSimple = new UserInfoSimple();
        userInfoSimple.setOpenId(userInfo.getOpenId());
        userInfoSimple.setUserId(userId);
        String tokenStr = JSON.toJSONString(userInfoSimple);
        String tokenRandom = StringUtil.getUUID();
        String prefix = null;
        try {
            prefix = new String(UrlBase64.encode(tokenStr.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            logger.info("登录token转码异常"+e);
        }
        String token = prefix+"."+tokenRandom;
        sessionUserInfo.setToken(token);

        stringRedisTemplate.opsForValue().set(RedisConsts.KEY_USER_INFO_PREFIX + token, JSONObject.toJSONString(sessionUserInfo).toString(), 1, TimeUnit.HOURS);
        LoginResp loginResp = new LoginResp();
        loginResp.setToken(token);
        return loginResp;
    }
}
