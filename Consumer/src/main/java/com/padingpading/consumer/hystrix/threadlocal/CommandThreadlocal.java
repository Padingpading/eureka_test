package com.padingpading.consumer.hystrix.threadlocal;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 单条数据
 */
public class CommandThreadlocal extends HystrixCommand<String> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String name;
    private final long timeout;

    public CommandThreadlocal(String group, String name, long timeout) {
        //指定分组
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("group"))
                //指定key
                .andCommandKey(HystrixCommandKey.Factory.asKey("key"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(" pool"))
                //设置线程池参数。
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        //核心线程
                        .withCoreSize(20)
                        //等待队列的大小
                        .withQueueSizeRejectionThreshold(15)))
                ;
        this.name = name;
        this.timeout = timeout;
    }

    // 要封装的业务请求
    @Override
    protected String run() throws Exception {
        logger.info("hystrix command execute");
        if (name == null) {
            throw new RuntimeException("data exception");
        }
        Thread.sleep(timeout); // 休眠
        return "hello " + name;
    }

    // 快速失败的降级逻辑
    @Override
    protected String getFallback() {
        logger.info("return fallback data");
        return "error";
    }
}
