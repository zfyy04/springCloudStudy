package com.zfy.springcloud.controller;

import com.zfy.springcloud.versioncontroll.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zfyy04
 * @date 2022/5/8 9:13 PM
 */
@RestController
public class TestV1Controller {
    @ApiVersion("2.0.0")
    @GetMapping(value = "/{v}/test")
    public String test() {
        return "v2";
    }
}
