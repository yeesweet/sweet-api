package com.sweet.api.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sweet.api.entity.Order;
import com.sweet.api.entity.PayReturnInfo;
import com.sweet.api.entity.res.PaymentResp;
import com.sweet.api.service.IOrderService;
import com.sweet.api.service.IPayReturnInfoService;
import com.sweet.api.util.SignUtils;
import com.sweet.api.util.WxPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 支付相关
 * @author zhang.hp
 * @date 2018/11/18.
 */
@RestController
@RequestMapping("pay")
public class PayController {

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IPayReturnInfoService payReturnInfoService;

    @Value("${wx.appSecret}")
    private String appSecret;

    /**
     * 支付回调
     * @param request
     * @param response
     */
    @PostMapping("/notify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response){
        String resXml = "";
        try {
            Map<String, String> map = WxPayUtil.xml2Map(request);
            PaymentResp paymentResp = JSON.parseObject(JSON.toJSONString(map), PaymentResp.class);
            logger.info("支付回调参数：{}",map);
            if (paymentResp.getResult_code().equalsIgnoreCase("SUCCESS")) {
                logger.info("微信支付----返回成功");
                String orderNo = paymentResp.getOut_trade_no();
                Order order = orderService.selectOne(new QueryWrapper<Order>().eq("order_no", orderNo));
                if (order != null) {
                    double orderAmount = order.getProdTotalAmt() * 100;
                    double totalFree = Integer.parseInt(paymentResp.getTotal_fee());
                    if (totalFree == orderAmount && verifyWeixinNotify(map,appSecret)) {
                        logger.error("微信支付----验证签名成功");
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                        //修改订单状态
                        order.setOrderStatus(2);
                        order.setPayStatus(2);
                        order.setUpdateTime(new Date());
                        boolean b = orderService.updateById(order);
                        boolean s = payReturnInfoService.insert(transform(paymentResp));
                        //保存支付回调信息
                        if (b && s ) {
                            logger.error("微信支付回调：修改订单支付状态成功");
                        } else {
                            logger.error("微信支付回调：修改订单支付状态失败");
                        }
                    }
                }
                // 处理完业务返回微信结果
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            logger.error("支付回调发生异常：" + e);
            e.printStackTrace();
        }
    }

    /**
     * 退款回调
     */
    @PostMapping("/refundNotify")
    public void refundNotify(){

    }

    /**
     * 验证签名
     *
     * @param map
     * @return
     */
    public boolean verifyWeixinNotify(Map<String, String> map, String key){
        SortedMap<String, String> parameterMap = new TreeMap<>();
        String sign = map.get("sign");
        for (Object keyValue : map.keySet()) {
            if (!keyValue.toString().equals("sign")) {
                parameterMap.put(keyValue.toString(), map.get(keyValue).toString());
            }
        }
        String createSign = "";
        try {
            createSign = SignUtils.sign(parameterMap,key);
        }catch (Exception e){
            logger.info("微信支付回调参数签名报错：{}",e.getMessage());
        }
        if (createSign.equals(sign)) {
            return true;
        } else {
            logger.error("微信支付回调参数验证签名失败。");
            return false;
        }

    }

    private PayReturnInfo transform(PaymentResp paymentResp) {
        PayReturnInfo info = new PayReturnInfo();
        info.setOutTradeNo(paymentResp.getOut_trade_no());
        info.setTotalFee(Integer.parseInt(paymentResp.getTotal_fee()));
        info.setAppId(paymentResp.getAppid());
        info.setOpenId(paymentResp.getOpenid());
        info.setTradeType(paymentResp.getTrade_type());
        info.setCreateTime(new Date());
        info.setStatus("success");
        info.setBankType(paymentResp.getBank_type());
        info.setCashFee(Integer.parseInt(paymentResp.getCash_fee()));
        info.setFeeType(paymentResp.getFee_type());
        info.setIsSubscribe(paymentResp.getIs_subscribe());
        info.setMchId(paymentResp.getMch_id());
        info.setResultCode(paymentResp.getResult_code());
        info.setReturnCode(paymentResp.getReturn_code());
        info.setTimeEnd(paymentResp.getTime_end());
        info.setTransactionId(paymentResp.getTransaction_id());
        return info;
    }
}
