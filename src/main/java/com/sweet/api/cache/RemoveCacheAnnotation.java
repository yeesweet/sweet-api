package com.sweet.api.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 刪除缓存注解
 * @author zhang.hp
 * @since 2018-09-17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RemoveCacheAnnotation {
	/**
	 *	緩存key
	 */
	String assignCacheKey();
}
