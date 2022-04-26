package com.zfy.springcloud.config.feign;

import feign.RetryableException;
import feign.Retryer;

import java.util.concurrent.TimeUnit;

/**
 * @author zfyy04
 * @date 2022/4/26 10:30 PM
 */
public class MyRetryer implements Retryer {
    @Override
    public void continueOrPropagate(RetryableException e) {
        throw e;
    }

    @Override
    public Retryer clone() {
        // 周期，重试间隔时间
        // 最大周期，重试间隔时间按照一定的规则逐渐增大，但不能超过最大周期+最大尝试次数
        // 重试次数(含第一次)
        return new Default(100, TimeUnit.SECONDS.toMillis(1), 3);
    }
}
