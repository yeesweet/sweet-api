package com.sweet.api.interceptor;

import com.alibaba.fastjson.JSON;
import com.sweet.api.annotation.WechatAccess;
import com.sweet.api.constants.RedisConsts;
import com.sweet.api.constants.ServletConst;
import com.sweet.api.entity.res.SessionUserInfo;
import com.sweet.api.exception.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Component
public class WechatAccessInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WechatAccessInterceptor.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method m = handlerMethod.getMethod();
            Object bean = handlerMethod.getBean();

            WechatAccess wechatAccess = null;
            try {
                wechatAccess = bean.getClass().getDeclaredAnnotation(WechatAccess.class);
            } catch (Exception e) {
                logger.error("", e);
            }
            WechatAccess wechatAccessMethod = m.getDeclaredAnnotation(WechatAccess.class);
            if (wechatAccessMethod != null) {
                wechatAccess = wechatAccessMethod;
            }

            if (wechatAccess != null && wechatAccess.value()) {
                String token = request.getHeader("Token");
                if (token == null) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie c : cookies) {
                            if ("Token".equals(c.getName())) {
                                token = c.getValue();
                                break;
                            }
                        }
                    }
                }
                if (token == null) {
                    logger.error("Denied access.");
                    throw new PermissionDeniedException("Token is null.");
                }

                String userKey = RedisConsts.KEY_USER_INFO_PREFIX + token;
                String userinfo = stringRedisTemplate.opsForValue().get(userKey);
                if (userinfo == null) {
                    logger.error("Denied access,token:{}", token);
                    throw new PermissionDeniedException("User info is null,token:" + token);
                }
                SessionUserInfo sessionUserInfo = JSON.parseObject(userinfo, SessionUserInfo.class);
                request.setAttribute(ServletConst.ATTR_USER_INFO, sessionUserInfo);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
