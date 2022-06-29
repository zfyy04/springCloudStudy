package com.paic;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.paic.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zfyy04
 * @date 2022/3/5 11:32 AM
 */

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@RefreshScope
public class NacosProviderMain8003 {

    public static void main(String[] args) {
        ReadableDataSource r;
        WritableDataSource w;
        SpringApplication.run(NacosProviderMain8003.class, args);
    }

    @Value("${name:123}")
    private String name;

    @Autowired
    private TokenUtil tokenUtil;


    @GetMapping(value = "/ping")
    public String ping() {
        return System.currentTimeMillis() + "=>hello " + name;
    }

    @GetMapping(value = "/login")
    public String login(String userName) {
        return userName + "=>login sucess!token is: " + tokenUtil.getToken(userName);
    }

    @GetMapping(value = "/getUser")
    public String getUser(String token) {
        return "=>getUser sucess!userName is: " + tokenUtil.getUserNameByToken(token);
    }

}
