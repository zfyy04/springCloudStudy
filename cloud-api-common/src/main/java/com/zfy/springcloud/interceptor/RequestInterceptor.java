package com.zfy.springcloud.interceptor;

import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zfyy04
 * @date 2022/4/26 8:45 PM
 */
@Configuration
public class RequestInterceptor implements feign.RequestInterceptor {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${zfy.custHeader:default}")
    private String customerHeader;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("x-appName", appName);
        requestTemplate.header("x-custHeader", customerHeader);
    }
}
