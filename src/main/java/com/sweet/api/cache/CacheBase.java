package com.sweet.api.cache;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 缓存基类
 * </p>
 *
 * @author zhang.hp
 * @since 2018-09-17
 */
@Component
public class CacheBase {
    /**
     * logger
     */
    protected final Log logger = LogFactory.getLog(CacheBase.class);

    @Resource
    protected RedisTemplate<String, Object> redisTemplate;

    /**
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    protected Method getMethod(final JoinPoint joinPoint) throws NoSuchMethodException {
        final Signature sig = joinPoint.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new NoSuchMethodException("This annotation is only valid on a method.");
        }
        final MethodSignature msig = (MethodSignature) sig;
        final Object target = joinPoint.getTarget();
        return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
    }

    /**
     * @param method
     * @param assignCacheKey
     * @return
     * @throws IllegalArgumentException
     */
    public String getCacheKey(Object[] args, String cacheKeyExpression) throws NoSuchMethodException,
            IllegalArgumentException {
        if (cacheKeyExpression == null || cacheKeyExpression.trim().equals("")) {
            logger.error("This assignCacheKey is not valid on a method.");
            throw new IllegalArgumentException("This assignCacheKey is not valid on a method.");
        }

        // 解析assignCacheKey表达式,格式如： ${param0}+ hello + ${param1.name(key)}
        StringBuffer sbCacheKey = new StringBuffer(128);
        String[] params = cacheKeyExpression.replaceAll(" ", "").split("[+]");
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null || "".equals(params[i].trim())) {
                continue;
            }
            Pattern pattern = Pattern.compile("^([$][{]).*[}]$");
            Matcher matcher = pattern.matcher(params[i]);
            if (matcher.find()) {
                // 根据参数获取参数值：${coupon.name}
                String param = params[i].substring(2, params[i].length() - 1);
                sbCacheKey.append(this.getArguValue(args, param));
            } else {
                sbCacheKey.append(params[i]);
            }
        }
        return sbCacheKey.toString();
    }

    /**
     * 根据参数名获取参数值
     *
     * @param args
     * @param param
     * @return
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     */
    private String getArguValue(Object[] args, String params) throws NoSuchMethodException, IllegalArgumentException {
        String[] arrParam = params.split("[.]");
        if (arrParam[0] == null || "".equals(arrParam[0])) {
            logger.error("This assignCacheKey is not valid on a method.");
            new IllegalArgumentException("This assignCacheKey is not valid on a method.");
        }
        // 方法入参列表中匹配当前参数对象
        int index = Integer.parseInt(arrParam[0].replaceAll("param", ""));
        Object currObject = args[index];
        try {
            for (int i = 1; i < arrParam.length; i++) {
                // 根据参数获取参数值：name(key)
                String param = arrParam[i];
                Pattern pattern = Pattern.compile("([(]).*[)]$");
                Matcher matcher = pattern.matcher(param);
                if (matcher.find()) {
                    String paramName = param.substring(0, param.indexOf('('));
                    String paramKey = param.substring(param.indexOf('(') + 1, param.length() - 1);
                    currObject = BeanUtils.getMappedProperty(currObject, paramName, paramKey);
                } else {
                    currObject = BeanUtils.getProperty(currObject, param);
                }
            }
        } catch (Exception ex) {
            logger.error("This assignCacheKey is not valid on a method.");
            new IllegalArgumentException("This assignCacheKey is not valid on a method.");
        }
        return convertObject2String(currObject);
    }

    /**
     * 支持集合类型入参，不支持嵌套集合
     *
     * @param object
     * @return
     */
    private String convertObject2String(Object object) {
        if (object == null) {
            return "";
        }
        StringBuffer resultStringBuffer = new StringBuffer();
        if (object instanceof Collection) {
            for (Object o : (Collection) object) {
                if (o != null) {
                    resultStringBuffer.append(o.toString()).append("_");
                }
            }
        } else {
            resultStringBuffer.append(object.toString()).append("_");
        }
        return resultStringBuffer.toString();
    }
}
