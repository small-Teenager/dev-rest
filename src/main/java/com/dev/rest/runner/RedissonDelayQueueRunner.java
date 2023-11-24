package com.dev.rest.runner;

import com.dev.rest.delayqueue.RedissonDelayQueueHandle;
import com.dev.rest.enums.RedissonDelayQueueEnum;
import com.dev.rest.utils.RedissonDelayQueueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class RedissonDelayQueueRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RedissonDelayQueueRunner.class);
    @Autowired
    private RedissonDelayQueueUtil redisDelayQueueUtil;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;

    ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));

    @Override
    public void run(String... args) throws Exception {
        poolTaskExecutor.execute(() -> {
            while (true) {
//                RunTest.getSystemInfo();
                try {
                    RedissonDelayQueueEnum[] queueEnums = RedissonDelayQueueEnum.values();
                    for (RedissonDelayQueueEnum queueEnum : queueEnums) {
                        Object value = redisDelayQueueUtil.getDelayQueue(queueEnum.getCode());
                        if (value != null) {
                            RedissonDelayQueueHandle<Object> redissonDelayQueueHandle = (RedissonDelayQueueHandle<Object>) context.getBean(queueEnum.getBeanId());
                            executorService.execute(() -> {
                                redissonDelayQueueHandle.execute(value);
                            });
                        }
                    }
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    log.error("Redission 延迟队列监测异常中断 {}", e.getMessage());
                }
            }
        });
        log.info("Redission 延迟队列监测启动成功");
    }
}
