package com.sweet.api.controller;


import com.sweet.api.commons.ResponseMessage;
import com.sweet.api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author zhang.hp
 * @since 2018-08-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/info")
    public Object getUserInfo(){
      return new ResponseMessage<>(1,"success",userService.selectById(1));
    }
}

