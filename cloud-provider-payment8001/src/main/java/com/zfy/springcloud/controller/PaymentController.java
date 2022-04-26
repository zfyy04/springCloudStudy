package com.zfy.springcloud.controller;

import com.zfy.springcloud.entities.CommonResult;
import com.zfy.springcloud.entities.Payment;
import com.zfy.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zfyy04
 * @date 2021/2/6 2:49 PM
 */

@RestController
@Slf4j
public class PaymentController {

    //resource默认按照name进行组装，autowired按照类型装配
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * @param payment
     * @return
     */
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("访问端口"+port+"插入"+payment+"结果>>>>>>>"+result);
        if(result>0){
            return new CommonResult(200,"ServerPort:"+port+",插入成功",result);
        }else{
            return new CommonResult(500,"ServerPort:"+port+",插入失败",result);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("访问端口"+port+"查询结果>>>>>>"+payment);
        if(payment==null){
            return new CommonResult(500, "ServerPort:"+port+",查询失败");
        }
        return new CommonResult(200, "ServerPort:"+port+",查询成功", payment);

    }

    @GetMapping(value = "/payment/all")
    public CommonResult getAllPayment(){
        List<Payment> allPayment = paymentService.getAllPayment();
        log.info("访问端口"+port+"查询结果>>>>>>"+allPayment);
        if(allPayment==null){
            return new CommonResult(500, "ServerPort:"+port+",查询失败");
        }
        return new CommonResult(200, "ServerPort:"+port+",查询成功", allPayment);

    }

    @GetMapping(value="/payment/discovery")
    private Object getDiscoveryClient(){
        return this.discoveryClient;
    }

    @GetMapping(value = "/helloFeign")
    public String helloFeign(@RequestHeader("x-custHeader") String xCustHeader){
        try {
            TimeUnit.SECONDS.sleep(3);//模拟超时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "helloFeign" + new Date() + ",x-custHeader="+xCustHeader;
    }

    @GetMapping(value = "/helloFeignTimeOut")
    public String helloFeignTimeOut(@RequestHeader("x-custHeader") String xCustHeader){
        try {
            TimeUnit.SECONDS.sleep(3);//模拟超时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "helloFeignTimeOut" + new Date() + ",x-custHeader="+xCustHeader;
    }
}
