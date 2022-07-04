package com.padingpading.consumer.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import hystrix.CommandHello;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author libin
 * @description
 * @date 2022-03-13
 */
@SpringBootTest
public class HystrixCommand_cache {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 运行结果：
     *
     * 20:55:43.759 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 20:55:43.878 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - result: hello hystrix
     * 20:55:43.879 [hystrix-ExampleGroup-2] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 20:55:43.985 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - result: hello ribbon
     * 20:55:43.989 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - result: hello hystrix
     * 20:55:43.989 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - result: hello hystrix
     */
    @Test
    public void test_HystrixCommand_cache() {
        // 先初始化上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try {
            CommandHello command1 = new CommandHello("ExampleGroup", "hystrix", 100);
            CommandHello command2 = new CommandHello("ExampleGroup", "ribbon", 100);
            CommandHello command3 = new CommandHello("ExampleGroup", "hystrix", 100);
            CommandHello command4 = new CommandHello("ExampleGroupTwo", "hystrix", 100);

            logger.info("result: {}", command1.execute());
            logger.info("result: {}", command2.execute());
            logger.info("result: {}", command3.execute());
            logger.info("result: {}", command4.execute());
        } finally {
            //HystrixRequestContext.getContextForCurrentThread().shutdown();
            context.shutdown();
        }
    }

}
