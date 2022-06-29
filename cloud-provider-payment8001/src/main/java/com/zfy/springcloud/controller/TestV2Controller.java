package com.zfy.springcloud.controller;

import com.zfy.springcloud.versioncontroll.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zfyy04
 * @date 2022/5/8 9:14 PM
 */
@RestController
public class TestV2Controller {
    @ApiVersion("1.0.0")
    @GetMapping(value = "/{v}/test")
    public String test() {
        return "v1";
    }
}
