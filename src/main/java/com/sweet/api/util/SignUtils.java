package com.sweet.api.util;

import com.sweet.api.entity.pay.UnifiedOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SignUtils {

    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);

    /**
     * 生成签名（class对象）
     * @param order
     * @throws Exception
     */
    public static void sign(UnifiedOrder order, String key) throws Exception{
        Class<? extends UnifiedOrder> clazz = order.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> list = new ArrayList<>();

        for (int i=0,j=fields.length; i<j; i++){
            Field field = fields[i];
            list.add(field.getName());
        }
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        StringBuffer buff = new StringBuffer();
        for (int i=0,j=list.size(); i<j; i++){
            PropertyDescriptor pd = new PropertyDescriptor(list.get(i),clazz);
            Method method = pd.getReadMethod();
            Object value = method.invoke(order);
            if(value != null && !"".equals(value.toString())){
                buff.append(list.get(i))
                        .append("=")
                        .append(value)
                        .append("&");
            }
        }
        buff.append("key=").append(key);//KEY
        logger.info("需要加密字段：{}",buff.toString());
        String sign = MD5(buff.toString()).toUpperCase();
        order.setSign(sign);
    }

    /**
     * 生成签名（map）
     * @param map
     * @return
     * @throws Exception
     */
    public static String sign(Map<String,String> map, String key) throws Exception{
        StringBuffer buff = new StringBuffer();
        map.forEach((k,v) -> {
            buff.append(k)
                    .append("=")
                    .append(v)
                    .append("&");
        });
        buff.append("key=").append(key);//KEY
        logger.info("需要加密字段：{}",buff.toString());
        String sign = MD5(buff.toString()).toUpperCase();
        return sign;
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}
