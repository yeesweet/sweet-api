package com.sweet.api.controller;

import com.sweet.api.entity.req.LoginReq;
import com.sweet.api.entity.res.LoginResp;
import com.sweet.api.service.ILoginService;
import com.sweet.api.wx.entity.AccessToken;
import com.sweet.api.wx.service.WechatCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LoginController {

  @Autowired
  private ILoginService loginService;

  /**
   * 登录
   * @param loginReq
   * @return
   * @throws IOException
   */
  @PostMapping("/login")
  public LoginResp loginMiniprogram(@RequestBody LoginReq loginReq){
    LoginResp loginResp = loginService.login(loginReq.getCode());
    return loginResp;
  }

}
