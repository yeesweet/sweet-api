package com.sweet.api.config;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
        fastJsonConfig.setSerializeConfig(config);

        ParserConfig parserConfig = new ParserConfig();
        parserConfig.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
        fastJsonConfig.setParserConfig(parserConfig);
        fastConverter.setSupportedMediaTypes(new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON_UTF8);
        }});
        // 不忽略对象属性中的null值
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(new ArrayList<MediaType>() {{
            add(MediaType.TEXT_HTML);
            add(MediaType.TEXT_PLAIN);
        }});
        stringHttpMessageConverter.setDefaultCharset(Charset.forName("utf8"));
        converters.add(stringHttpMessageConverter);

        ByteArrayHttpMessageConverter bahmc = new ByteArrayHttpMessageConverter();
        bahmc.setSupportedMediaTypes(new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_OCTET_STREAM);
        }});

    }

}
