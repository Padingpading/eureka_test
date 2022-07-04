package com.padingpading.consumer.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author libin
 * @description
 * @date 2022-03-13
 */
public class Demo03_HystrixCommand_MultiFallback {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求结果：
     * <p>
     * 22:28:46.313 [hystrix-MySqlPool-1] INFO com.lyyzoo.hystrix.CommandMySQL - get data from mysql
     * 22:28:46.319 [main] INFO com.lyyzoo.hystrix.Demo03_HystrixCommand_MultiFallback - result: mysql-number-1
     * 22:28:46.320 [hystrix-MySqlPool-2] INFO com.lyyzoo.hystrix.CommandMySQL - get data from mysql
     * 22:28:46.324 [hystrix-MySqlPool-2] DEBUG com.netflix.hystrix.AbstractCommand - Error executing HystrixCommand.run(). Proceeding to fallback logic ...
     * java.lang.RuntimeException: data not found in mysql
     * at com.lyyzoo.hystrix.CommandMySQL.run(Demo03_HystrixCommand_MultiFallback.java:50)
     * at com.lyyzoo.hystrix.CommandMySQL.run(Demo03_HystrixCommand_MultiFallback.java:29)
     * at com.netflix.hystrix.HystrixCommand$2.call(HystrixCommand.java:302)
     * ......
     * 22:28:46.332 [hystrix-MySqlPool-2] INFO com.lyyzoo.hystrix.CommandMySQL - coming mysql fallback
     * 22:28:46.344 [hystrix-RedisPool-1] INFO com.lyyzoo.hystrix.CommandRedis - get data from redis
     * 22:28:46.344 [main] INFO com.lyyzoo.hystrix.Demo03_HystrixCommand_MultiFallback - result: redis-number-2
     */
    @Test
    public void test_HystrixCommand_multi_fallback() {
        CommandMySQL command = new CommandMySQL("ExampleGroup", 1);
        logger.info("result: {}", command.execute());

        CommandMySQL command2 = new CommandMySQL("ExampleGroup", 2);
        logger.info("result: {}", command2.execute());
    }

}

class CommandMySQL extends HystrixCommand<String> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String group;
    private final Integer id;

    public CommandMySQL(String group, Integer id) {
        super(
                HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                        // 指定不同的线程池
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("MySqlPool"))
        );
        this.group = group;
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        logger.info("get data from mysql");
        if (id % 2 == 0) {
            throw new RuntimeException("data not found in mysql");
        }
        return "mysql-number-" + id;
    }

    // 快速失败的降级逻辑
    @Override
    protected String getFallback() {
        logger.info("coming mysql fallback");
        // 嵌套 Command
        HystrixCommand<String> command = new CommandRedis(group, id);
        return command.execute();
    }
}

class CommandRedis extends HystrixCommand<String> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Integer id;

    public CommandRedis(String group, Integer id) {
        super(
                HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                        // 指定不同的线程池
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RedisPool"))
        );
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        logger.info("get data from redis");
        return "redis-number-" + id;
    }

    // 快速失败的降级逻辑
    @Override
    protected String getFallback() {
        logger.info("coming redis fallback");
        return "error";
    }
}

