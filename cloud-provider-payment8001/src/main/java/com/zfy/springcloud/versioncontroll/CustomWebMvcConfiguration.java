package com.zfy.springcloud.versioncontroll;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author zfyy04
 * @date 2022/4/27 10:10 PM
 */
@Configuration
public class CustomWebMvcConfiguration extends WebMvcConfigurationSupport {
    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ApiVersionRequestMappingHandlerMapping();
    }
}
