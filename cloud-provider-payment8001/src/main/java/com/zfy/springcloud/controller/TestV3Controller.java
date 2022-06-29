package com.zfy.springcloud.controller;

import com.zfy.springcloud.versioncontroll.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zfyy04
 * @date 2022/5/8 9:26 PM
 */

@RestController
@RequestMapping("/{v}")
@ApiVersion("1.0.0")
public class TestV3Controller {

    @GetMapping("/t1")
    public String get1() {
        return "t1";
    }

    @GetMapping("/t2")
    public String get2() {
        return "t2";
    }
}
