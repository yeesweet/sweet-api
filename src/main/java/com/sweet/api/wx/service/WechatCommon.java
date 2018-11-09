package com.sweet.api.wx.service;

import com.alibaba.fastjson.JSON;
import com.sweet.api.util.HttpUtil;
import com.sweet.api.wx.entity.WxMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WechatCommon {

//    @Autowired
//    private WechatUtil wechatUtil;

    private static final String MINIPROGRAM_ACCESS_OPEN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String GRANT_TYPE_A = "authorization_code";
    private static final String ACCESS_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
    private static final String OFFICALACCOUNT_ACCESS_OPEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
    private static final String INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
    private static final String REQUEST_QR_CODE = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";


//    public String getMiniProgramQrcode(String appid,String secret,String scene,String page) throws IOException {
//        String accessToken = this.getToken(appid,secret);
//        String url  = REQUEST_QR_CODE+accessToken;
//        QrcodeReq req = new QrcodeReq();
//        req.setPage(page);
//        req.setScene(scene);
//        String result = HttpUtil.INSTANCE.postJson(JSON.toJSONString(req),null,url);
//        log.info("Request wechat mini program result: {}",result);
//        return result;
//    }
//
//
//    public OpenMessage getOfficalAccountOpenid(String code, String appId, String appSecret) throws IOException {
//        Map<String,String> headers = new HashMap<>();
//        headers.put("charset","utf-8");
//        StringBuilder query = new StringBuilder();
//        query.append(appId).append("&secret=").append(appSecret).append("&code=").append(code).append("&grant_type=authorization_code");
//        String response = HttpUtil.INSTANCE.getString(headers,OFFICALACCOUNT_ACCESS_OPEN_URL+query.toString());
//        if(response == null){
//            throw new RuntimeException("网页授权获取openid失败。");
//        }
//        OpenMessage open = JSON.parseObject(response,OpenMessage.class);
//        log.info("获取openid返回数据：{} ",open);
//        return open;
//    }
//
//    public WxInfo getOfficalAccountInfo(String openid, String appId, String appSecret) throws IOException{
//        log.info("获取weixinInfo请求参数：{}",openid);
//        Map<String,String> headers = new HashMap<>();
//        headers.put("charset","utf-8");
//        StringBuilder query = new StringBuilder();
//        query.append(getToken(appId,appSecret)).append("&openid=").append(openid).append("&lang=zh_CN");
//        String response = HttpUtil.INSTANCE.getString(headers,INFO_URL+query.toString());
//        if(response == null){
//            throw new RuntimeException("网页授权获取code失败。");
//        }
//        WxInfo info = JSON.parseObject(response,WxInfo.class);
//        log.info("获取weixinInfo返回数据：{} ",response);
//        return info;
//    }
//
//    public String getToken(String appId,String appSecret) throws IOException {
//        return wechatUtil.getToken(appId,appSecret);
//    }

    public WxMessage miniProgramLogin(String code, String appId, String appSecret) throws IOException {
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("appid",appId));
        list.add(new BasicNameValuePair("secret",appSecret));
        list.add(new BasicNameValuePair("js_code",code));
        list.add(new BasicNameValuePair("grant_type",GRANT_TYPE_A));
        Map<String,String> headers = new HashMap<>();
        headers.put("charset","utf-8");
        String response = HttpUtil.INSTANCE.postForm(list,headers,MINIPROGRAM_ACCESS_OPEN_URL);

        if(response!=null) {
            log.info("获取openid返回数据：{} ",response);
            WxMessage wx = JSON.parseObject(response,WxMessage.class);
            if(wx != null){
                return wx;
            }
        }
        return null;
    }

}
