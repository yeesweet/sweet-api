//package com.sweet.api.wx.service;
//
//import com.alibaba.fastjson.JSON;
//import com.sweet.api.constants.RedisConsts;
//import com.sweet.api.util.HttpUtil;
//import com.sweet.api.wx.entity.AccessToken;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//public class WechatUtil {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
//    private static final String GRANT_TYPE_C = "client_credential";
//
//    String getToken(String appId, String appSecret) throws IOException {
//        String token = stringRedisTemplate.opsForValue().get(RedisConsts.KEY_WX_SERVER_TOKEN_PREFIX + appId);
//        if(token != null){
//            return token;
//        }
//        List<NameValuePair> list = new ArrayList<>();
//        list.add(new BasicNameValuePair("appid",appId));
//        list.add(new BasicNameValuePair("secret",appSecret));
//        list.add(new BasicNameValuePair("grant_type",GRANT_TYPE_C));
//        Map<String,String> headers = new HashMap<>();
//        headers.put("charset","utf-8");
//        String response = HttpUtil.INSTANCE.postForm(list,headers,ACCESS_TOKEN_URL);
//
//        if(response!=null) {
//            log.info("获取token返回数据：{} ",response);
//            AccessToken accessToken = JSON.parseObject(response,AccessToken.class);
//            if(accessToken.getAccess_token() != null){
//                stringRedisTemplate.opsForValue().set(RedisConsts.KEY_WX_SERVER_TOKEN_PREFIX + appId, accessToken.getAccess_token(), 7000, TimeUnit.SECONDS);
//                return accessToken.getAccess_token();
//            }
//        }
//        log.error("getToken error access wx token,response = {}",response);
//        return null;
//    }
//
//    public static String getUrl(String url) throws UnsupportedEncodingException {
//        return URLEncoder.encode(url,"utf-8");
//    }
//
//
//}
