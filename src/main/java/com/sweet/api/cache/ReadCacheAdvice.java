package com.sweet.api.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


/**
 * 读缓存注解
 *
 * @author zhang.hp
 * @since 2018-09-17
 */
@Aspect
@Configuration
public class ReadCacheAdvice extends CacheBase {
    @Pointcut("@annotation(com.sweet.api.cache.ReadCacheAnnotation)")
    public void methodCachePointcut() {
    }

    @Around("methodCachePointcut()")
    public Object methodCacheHold(final ProceedingJoinPoint joinPoint) throws Throwable {
        ReadCacheAnnotation annotation = null;
        Object result = null;
        String cacheKey = "";

        try {
            // 获取目标方法
            final Method method = this.getMethod(joinPoint);
            annotation = method.getAnnotation(ReadCacheAnnotation.class);
            cacheKey = this.getCacheKey(joinPoint.getArgs(), annotation.assignCacheKey());
            result = redisTemplate.opsForValue().get(cacheKey);
            if (result != null) {
                return result;
            }
        } catch (Throwable ex) {
            logger.error("Caching on cache " + joinPoint.toShortString() + " aborted due to an error. cacheKey = " + cacheKey, ex);
            return joinPoint.proceed();
        }

        // 缓存命中失败,执行方法从DB获取数据
        result = joinPoint.proceed();

        try {
            // 将数据缓存到缓存服务器
            if (result != null) {
                if (annotation.remoteExpire() > 0) {
                    redisTemplate.opsForValue().set(cacheKey, result, annotation.remoteExpire(), TimeUnit.SECONDS);
                } else {
                    redisTemplate.opsForValue().set(cacheKey, result);
                }
            }
        } catch (Throwable ex) {
            logger.error("Caching on db " + joinPoint.toShortString() + " aborted due to an error. cacheKey = " + cacheKey, ex);
        }
        return result;
    }
}
