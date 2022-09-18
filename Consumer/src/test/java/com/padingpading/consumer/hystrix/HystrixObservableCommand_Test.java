package com.padingpading.consumer.hystrix;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import rx.Observable;
import rx.Observer;

/**
 * @author libin
 * @description
 * @date 2022-03-13
 */
@SpringBootTest
public class HystrixObservableCommand_Test {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 运行结果：
     * <p>
     * 15:22:49.306 [main] INFO com.lyyzoo.hystrix.CommandObservableHello - hystrix command execute
     * 15:22:49.316 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - last result: hello command
     */
    @Test
    public void test_HystrixObservableCommand_observe() {
        CommandObservableHello command = new CommandObservableHello("ExampleGroup");
        Observable<String> observe = command.observe();
        String result = command.observe().toBlocking().last();
        logger.info("last result: {}", result);
    }

    /**
     * 运行结果：
     * <p>
     * 15:23:08.685 [main] INFO com.lyyzoo.hystrix.CommandObservableHello - hystrix command execute
     * 15:23:08.691 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - result data: hello world
     * 15:23:08.691 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - result data: hello hystrix
     * 15:23:08.691 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - result data: hello command
     * 15:23:08.691 [main] INFO com.lyyzoo.hystrix.Demo02_HystrixObservableCommand - completed
     */
    @Test
    public void test_HystrixObservableCommand_observe_subscribe() {
        CommandObservableHello command = new CommandObservableHello("ExampleGroup");

        command.observe().subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                logger.info("completed");
            }

            @Override
            public void onError(Throwable e) {
                logger.info("error");
            }

            @Override
            public void onNext(String o) {
                logger.info("result data: {}", o);
            }
        });
    }


}
