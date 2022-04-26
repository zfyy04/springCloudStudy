package com.zfy.springcloud.controller;

import com.zfy.springcloud.api.OpenFeignAPI;
import com.zfy.springcloud.entities.CommonResult;
import com.zfy.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author zfyy04
 * @date 2021/2/6 3:16 PM
 */

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OpenFeignAPI openFeignAPI;

    @Resource
    private RestTemplate restTemplate;

//    public static final String PAYMENT_URL = "http://localhost:8001/";
    public static final String PAYMENT_URL = "http://CLOUD-PROVIDER-PAYMENT/";

    @PostMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        log.info("order:传入参数"+payment);
        return restTemplate.postForObject(PAYMENT_URL+"payment/create", payment, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult getPamentInfoById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"payment/get/"+id, CommonResult.class);
    }

    @GetMapping(value = "/consumer/helloFeign")
    public String helloFeign(){
        return openFeignAPI.helloFeign();
    }

    @GetMapping(value = "/consumer/ping")
    public String ping(){
        return String.valueOf(System.currentTimeMillis());
    }
}
