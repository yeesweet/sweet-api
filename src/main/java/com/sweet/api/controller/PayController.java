package com.sweet.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付相关
 * @author zhang.hp
 * @date 2018/11/18.
 */
@RestController
@RequestMapping("pay")
public class PayController {

    @PostMapping("/notify")
    public void payNotify(){

    }
    @PostMapping("/refundNotify")
    public void refundNotify(){

    }
}
