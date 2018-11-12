package com.sweet.api.controller;

import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.entity.req.LoginReq;
import com.sweet.api.entity.res.LoginResp;
import com.sweet.api.service.ILoginService;
import com.sweet.api.wx.entity.AccessToken;
import com.sweet.api.wx.service.WechatCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("")
@Slf4j
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
  public ResponseMessage login(@RequestBody LoginReq loginReq) throws IOException {
    LoginResp loginResp = loginService.login(loginReq.getCode());
    if(loginResp == null){
      return new ResponseMessage(-1,"登录异常");
    }
    return new ResponseMessage(loginResp);
  }

}
