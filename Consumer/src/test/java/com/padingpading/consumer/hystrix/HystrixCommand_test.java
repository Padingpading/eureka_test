package com.padingpading.consumer.hystrix;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Future;

/**
 * @author libin
 * @description
 * @date 2022-03-13
 */
@SpringBootTest
public class HystrixCommand_test {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 运行结果：
     * <p>
     * 14:56:03.699 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 14:56:04.206 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - execute result is: hello hystrix
     */
    @Test
    public void test_HystrixCommand_execute() {
        CommandHello command = new CommandHello("ExampleGroup", "com/padingpading/consumer/hystrix", 500);
        // 同步执行
        String result = command.execute();
        logger.info("execute result is: {}", result);
    }

    /**
     * 运行结果：
     * <p>
     * 14:56:19.269 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - do something...
     * 14:56:19.279 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 14:56:19.785 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - queue result is: hello hystrix
     */
    @Test
    public void test_HystrixCommand_queue() throws Exception {
        CommandHello command = new CommandHello("ExampleGroup", "com/padingpading/consumer/hystrix", 500);
        // 异步执行，返回 Future
        Future<String> future = command.queue();
        logger.info("do something...");
        logger.info("queue result is: {}", future.get());
    }

    /**
     * 运行结果
     * <p>
     * 14:59:56.748 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 14:59:57.252 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - observe result is: hello hystrix
     */
    @Test
    public void test_HystrixCommand_observe_single() {
        CommandHello command = new CommandHello("ExampleGroup", "com/padingpading/consumer/hystrix", 500);
        Observable<String> observable = command.observe();
        // 获取请求结果，toBlocking() 是为了同步执行，不加 toBlocking() 就是异步执行
        String result = observable.toBlocking().single();
        logger.info("observe result is: {}", result);
    }

    /**
     * 运行结果：
     * <p>
     * 15:00:58.921 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 15:00:59.424 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - subscribe result is: hello hystrix
     * 15:00:59.425 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - completed
     */
    @Test
    public void test_HystrixCommand_observe_subscribe() {
        CommandHello command = new CommandHello("ExampleGroup", "com/padingpading/consumer/hystrix", 500);
        Observable<String> observable = command.observe();
        // 订阅结果处理
        observable.toBlocking().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                logger.info("completed");
            }

            @Override
            public void onError(Throwable e) {
                logger.info("error", e);
            }

            @Override
            public void onNext(String s) {
                logger.info("subscribe result is: {}", s);
            }
        });
    }


    /**
     * 运行结果：
     *
     * 14:44:43.199 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - hystrix command execute
     * 14:44:43.203 [hystrix-ExampleGroup-1] DEBUG com.netflix.hystrix.AbstractCommand - Error executing HystrixCommand.run(). Proceeding to fallback logic ...
     * java.lang.RuntimeException: data exception
     * 	at com.lyyzoo.hystrix.Demo01_HystrixCommand$CommandHello.run(Demo01_HystrixCommand.java:75)
     * 	at com.lyyzoo.hystrix.Demo01_HystrixCommand$CommandHello.run(Demo01_HystrixCommand.java:61)
     * 	at com.netflix.hystrix.HystrixCommand$2.call(HystrixCommand.java:302)
     * 	........
     * 14:44:43.210 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - return fallback data
     * 14:44:43.214 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - result is: error
     */
    @Test
    public void test_HystrixCommand_exception_fallback() {
        CommandHello command = new CommandHello("ExampleGroup", null, 500);
        // 抛出异常，返回降级逻辑中的数据
        String result = command.execute();
        logger.info("result is: {}", result);
    }

    /**
     * 运行结果：
     *
     * 14:51:27.114 [hystrix-ExampleGroup-1] INFO com.lyyzoo.hystrix.CommandHello - hystrix command execute
     * 14:51:28.113 [HystrixTimer-1] INFO com.lyyzoo.hystrix.CommandHello - return fallback data
     * 14:51:28.119 [main] INFO com.lyyzoo.hystrix.Demo01_HystrixCommand - result is: error
     */
    @Test
    public void test_HystrixCommand_timeout_fallback() {
        CommandHello command = new CommandHello("ExampleGroup", "com/padingpading/consumer/hystrix", 1500);
        // 请求超时，返回降级逻辑中的数据
        String result = command.execute();
        logger.info("result is: {}", result);
    }



}
