package com.padingpading.consumer.hystrix.fallback;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author libin
 * @description
 * @date 2022-03-13
 */
public class CommandCircuitBreaker extends HystrixCommand<String> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Long timeoutMill;
    private final Integer id;

    protected CommandCircuitBreaker(Long timeoutMill, Integer id) {
        super(
                Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                // 超时时间1000毫秒
                                .withExecutionTimeoutInMilliseconds(1000)
                                // 启用断路器
                                .withCircuitBreakerEnabled(true)
                                // 限流阈值，超过这个值后才会去判断是否限流，默认20
                                .withCircuitBreakerRequestVolumeThreshold(4)
                                // 请求失败百分比阈值，默认50
                                .withCircuitBreakerErrorThresholdPercentage(50)
                                // 断路器打开后休眠多久，默认5000毫秒
                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                        )
        );
        this.timeoutMill = timeoutMill;
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        logger.info("[{}] execute command", id);
        if (timeoutMill != null) {
            Thread.sleep(timeoutMill);
        }
        return "number-" + id;
    }

    @Override
    protected String getFallback() {
        logger.info("[{}] execute fallback", id);
        return "error-" + id;
    }
}
