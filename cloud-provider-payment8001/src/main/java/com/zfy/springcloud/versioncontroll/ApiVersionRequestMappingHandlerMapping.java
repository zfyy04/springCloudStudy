package com.zfy.springcloud.versioncontroll;

import lombok.NonNull;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author zfyy04
 * @date 2022/4/27 10:08 PM
 */
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    /**
     * add @ApiVersion to controller class.
     *
     * @param handlerType handlerType
     * @return RequestCondition
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(@NonNull Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return null == apiVersion ? super.getCustomTypeCondition(handlerType) : new ApiVersionCondition(apiVersion.value());
    }

    /**
     * add @ApiVersion to controller method.
     *
     * @param method method
     * @return RequestCondition
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(@NonNull Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return null == apiVersion ? super.getCustomMethodCondition(method) : new ApiVersionCondition(apiVersion.value());
    }
}
