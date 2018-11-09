package com.sweet.api.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WechatAccess {
  boolean value() default true;
  int role() default 0;
}
