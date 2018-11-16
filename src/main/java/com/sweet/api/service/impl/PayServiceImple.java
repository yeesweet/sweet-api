package com.sweet.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sweet.api.constants.MessageConst;
import com.sweet.api.constants.WXPayConstants;
import com.sweet.api.entity.Order;
import com.sweet.api.entity.pay.PayTradeInfo;
import com.sweet.api.entity.pay.UnifiedOrder;
import com.sweet.api.entity.pay.WechatOrder;
import com.sweet.api.entity.pay.PayInfo;
import com.sweet.api.exception.OrderException;
import com.sweet.api.service.IOrderService;
import com.sweet.api.service.IPayService;
import com.sweet.api.util.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zhang.hp
 * @date 2018/11/12.
 */
@Service
public class PayServiceImple implements IPayService {

    private static final Logger logger = LoggerFactory.getLogger(PayServiceImple.class);

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.mchId}")
    private String mchId;

    @Value("${wx.pay.url}")
    private String payurl;

    @Value("${wx.pay.notifyurl}")
    private String payNotifyurl;

    @Value("${wx.refund.url}")
    private String refundurl;

    @Value("${wx.refund.notifyurl}")
    private String refundNotifyurl;

    @Autowired
    private IOrderService orderService;

    @Override
    public PayInfo payorder(PayTradeInfo payTradeInfo) {
        PayInfo payInfo = null;
        final Integer payment = payTradeInfo.getPayment();
        final String tradeType = payTradeInfo.getTradeType();
        if (payment == 1) {
            if(StringUtils.equalsIgnoreCase("pay",tradeType)) {
                payInfo = this.pay4WX(payTradeInfo);
            }
        }
        return payInfo;
    }

    /**
     * 小程序统一下单
     * @param payTradeInfo
     * @return
     */
    private PayInfo pay4WX(PayTradeInfo payTradeInfo) {
        PayInfo payInfo=null;
        Order order = orderService.selectOne(new QueryWrapper<Order>().eq("order_no", payTradeInfo.getOrderNo()));
        final Integer payStatus = order.getPayStatus();
        //未支付
        if(payStatus == 1){
            //微信统一下单
            UnifiedOrder unifiedOrder = new UnifiedOrder();
            unifiedOrder.setAppid(appId);
            unifiedOrder.setMch_id(mchId);
            unifiedOrder.setOpenid(payTradeInfo.getUser().getOpenId());
            unifiedOrder.setTrade_type(WXPayConstants.TRADE_TYPE);
            unifiedOrder.setNotify_url(payNotifyurl);
            unifiedOrder.setOut_trade_no(payTradeInfo.getOrderNo());
            BigDecimal totalFee = new BigDecimal(payTradeInfo.getAmount());
            //单位分
            unifiedOrder.setTotal_fee(totalFee.multiply(new BigDecimal(100)).intValue());
            String nonceStr= StringUtil.getUUID();
            unifiedOrder.setNonce_str(nonceStr);
            unifiedOrder.setBody("十点趣动-订单支付");
            unifiedOrder.setSpbill_create_ip(WxPayUtil.getRemoteHost());
            //一次签名认证
            try {
                SignUtils.sign(unifiedOrder, appSecret);
            } catch (Exception e) {
                logger.info("统一下单一次签名报错：{}", e.getMessage());
            }
            XStream xStream = new XStream();
            xStream.alias("xml", UnifiedOrder.class);
            String orderStr = xStream.toXML(unifiedOrder).replace("__", "_");
            String returnStr = "";
            logger.info("统一下单请求参数（XML）：{}", orderStr);
            try {
                returnStr = HttpUtil.INSTANCE.postXML(payurl, orderStr);
            } catch (IOException e) {
                logger.info("微信统一下单请求失败：{}", e.getMessage());
            }
            XStream xStream2 = new XStream(new DomDriver());
            xStream2.alias("xml", WechatOrder.class);
            WechatOrder wechatOrder = (WechatOrder) xStream2.fromXML(returnStr);
            logger.info("统一下单返回数据：{},转换之后的order对象：{}", returnStr, wechatOrder.toString());
            if ("SUCCESS".equals(wechatOrder.getReturn_code()) && "SUCCESS".equals(wechatOrder.getResult_code())) {
                logger.info("微信支付统一下单请求成功，获得的Prepay_id是：" + wechatOrder.getPrepay_id());
            } else {
                logger.error("微信支付统一下单请求错误：" + wechatOrder.getReturn_msg() + wechatOrder.getErr_code());
                throw new OrderException(MessageConst.ERROR);
            }
            //二次签名认证
            String timeStamp = Long.toString(System.currentTimeMillis());
            SortedMap<String,String> map = new TreeMap<>();
            map.put("appId",appId);
            map.put("timeStamp",timeStamp);
            map.put("nonceStr",nonceStr);
            map.put("package","prepay_id="+wechatOrder.getPrepay_id());
            map.put("signType","MD5");
            String sign = "";
            try{
                sign = SignUtils.sign(map,appSecret);
            }catch (Exception e){
                logger.info("统一下单二次签名报错：{}",e.getMessage());
            }
            payInfo = new PayInfo();
            payInfo.setOrderNo(payTradeInfo.getOrderNo());
            payInfo.setAppId(appId);
            payInfo.setNonceStr(nonceStr);
            payInfo.setPaySign(sign);
            payInfo.setSignType("MD5");
            payInfo.setTimeStamp(timeStamp);
            payInfo.setPrepayId(wechatOrder.getPrepay_id());
        }
        return payInfo;
    }
}