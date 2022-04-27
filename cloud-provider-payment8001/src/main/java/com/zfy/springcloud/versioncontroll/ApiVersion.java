package com.zfy.springcloud.versioncontroll;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author zfyy04
 * @date 2022/4/27 10:01 PM
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
    String value();
}
