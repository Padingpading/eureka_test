package com.padingpading.consumer.hystrix.fallback;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author libin
 * @description
 * @date 2022-03-13
 */
@SpringBootTest
public class HystrixCommand_CircuitBreaker_Tets {
    public class Demo05_HystrixCommand_CircuitBreaker {
        @Test
        public void test_CircuitBreaker() throws InterruptedException {
            for (int i = 1; i <= 10; i++) {
                CommandCircuitBreaker command = new CommandCircuitBreaker(1500L, i);
                command.execute();
                if (i == 7) {
                    Thread.sleep(5000); // 休眠5秒
                }
            }
        }
    }
}
