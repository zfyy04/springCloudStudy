package com.zfy.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableTransactionManagement
public class PaymentMain8001 {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PaymentMain8001.class, args);
        //获取加载的bean
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
//            System.out.println("==============>"+beanName);
        }

//        Scanner scanner = new Scanner(System.in);
//        String port = scanner.nextLine();
//        if(StringUtils.isEmpty(port)){
//            port="8001";
//        }
//        new SpringApplicationBuilder(PaymentMain8001.class).properties("server.port="+port).run(args);
    }
}
