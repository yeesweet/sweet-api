package com.sweet.api.configuration;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sweet.api.constants.ServletConst;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.interceptor.AccessLogInterceptor;
import com.sweet.api.interceptor.WechatAccessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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
