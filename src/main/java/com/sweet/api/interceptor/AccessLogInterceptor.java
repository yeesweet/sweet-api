package com.sweet.api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessLogInterceptor implements HandlerInterceptor {
  private static final Logger logger = LoggerFactory.getLogger("access");

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    logger.info("Access - method:{},uri:{},query:{},Token:{}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), httpServletRequest.getQueryString(), httpServletRequest.getHeader("Token"));
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }
}
