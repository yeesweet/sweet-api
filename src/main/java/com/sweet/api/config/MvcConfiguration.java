package com.sweet.api.config;

import com.sweet.api.interceptor.AccessLogInterceptor;
import com.sweet.api.interceptor.WechatAccessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {
  private static final Logger logger = LoggerFactory.getLogger(MvcConfiguration.class);

  @Autowired
  private AccessLogInterceptor accessLogInterceptor;

  @Autowired
  private WechatAccessInterceptor wechatAccessInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

//    preHandle execute seq:from top to bottom,postHandle from bottom to top.
    registry.addInterceptor(accessLogInterceptor).addPathPatterns("/**");
    registry.addInterceptor(wechatAccessInterceptor).addPathPatterns("/**");
    super.addInterceptors(registry);
  }

}
