package com.sweet.api.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 刪除缓存注解
 * @author zhang.hp
 * @since 2018-09-17
 */
@Aspect
@Component
public class RemoveCacheAdvice extends CacheBase {
	
	@Pointcut("@annotation(com.sweet.api.cache.RemoveCacheAnnotation)")
	public void removeCachePointcut() {
	}

	@AfterReturning(pointcut = "removeCachePointcut()", returning = "retVal")
	public Object methodCacheHold(final JoinPoint joinPoint, final Object retVal) throws Throwable {
		try {
			// 获取目标方法
			final Method method = this.getMethod(joinPoint);
			final RemoveCacheAnnotation annotation = method.getAnnotation(RemoveCacheAnnotation.class);
			String cacheKey = this.getCacheKey(joinPoint.getArgs(), annotation.assignCacheKey());
			this.redisTemplate.delete(cacheKey);
		} catch (Throwable ex) {
			logger.error("Removing caching via " + joinPoint.toShortString() + " aborted due to an error.", ex);
		}

		return retVal;
	}
}
