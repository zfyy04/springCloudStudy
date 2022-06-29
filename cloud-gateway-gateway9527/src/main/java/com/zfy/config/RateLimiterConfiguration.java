package com.zfy.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 限流
 *
 * @author zfyy04
 * @date 2022/3/2 9:24 PM
 */

@Configuration
public class RateLimiterConfiguration {


    @Bean(value = "ipKeyResolver")
    public KeyResolver ipResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
