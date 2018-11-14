package com.sweet.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通过redis生成订单号
 */
@Service
public class RedisCreateOrderNoUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 生成订单号，避免重复(订单号前缀+当前时间字符串+incr)
     *
     * @param orderNoPrefix
     * @return
     */
    public String getOrderNo(String orderNoPrefix) {
        StringBuffer buff = new StringBuffer();
        String str = buff.append(orderNoPrefix).append(getDateStr()).toString();
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(str, redisTemplate.getConnectionFactory());
        int incr = redisAtomicInteger.getAndIncrement() + 1;
        return str + incr;
    }

    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getDateStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String dateStr = sdf.format(date);
        return dateStr;
    }
}
