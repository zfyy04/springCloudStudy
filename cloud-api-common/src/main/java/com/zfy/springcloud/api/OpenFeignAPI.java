package com.zfy.springcloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zfyy04
 * @date 2022/4/26 8:23 PM
 */
@Component
@FeignClient(value = "cloud-gateway-gateway")
public interface OpenFeignAPI {

    @GetMapping(value = "/gg/helloFeign")
    public String helloFeign();
}
