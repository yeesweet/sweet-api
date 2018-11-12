package com.sweet.api.config;

import com.sweet.api.constants.ServletConst;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.interceptor.AccessLogInterceptor;
import com.sweet.api.interceptor.WechatAccessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    HandlerMethodArgumentResolver argumentResolver = new HandlerMethodArgumentResolver() {
      @Override
      public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().isAssignableFrom(SessionUserInfo.class)) {
          return true;
        } else {
          return false;
        }
      }

      @Override
      public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object info = webRequest.getAttribute(ServletConst.ATTR_USER_INFO, NativeWebRequest.SCOPE_REQUEST);
        return info;
      }
    };

    argumentResolvers.add(argumentResolver);
  }

}
