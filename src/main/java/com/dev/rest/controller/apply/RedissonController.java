package com.dev.rest.controller.apply;

import com.dev.rest.enums.RedissonDelayQueueEnum;
import com.dev.rest.response.ApiResponse;
import com.dev.rest.utils.RedissonDelayQueueUtil;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 锁应尽可能减小锁粒度
 *
 * @author: yaodong zhang
 * @create 2023/1/5
 */
@RestController
@RequestMapping("/apply/redisson")
public class RedissonController {

    private static final Logger log = LoggerFactory.getLogger(RedissonController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonDelayQueueUtil redissonDelayQueueUtil;

    @Autowired
    private RedissonClient redissonClient;

    private static Integer NUMBER = 10;

    private static String PRODUCT_KEY = "product:";

    @PostMapping("/lock/simple/{id}")
    public ApiResponse<String> simpleLock(@PathVariable(value = "id") @NotNull String id) {
        String lockKey = "lock:simple:" + id;
        String cliendId = UUID.randomUUID().toString();

        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, cliendId, 60, TimeUnit.SECONDS);
        if (flag) {
            String productKey = PRODUCT_KEY + id;
            // 加锁成功
            try {
                long productNum = Long.parseLong(stringRedisTemplate.opsForValue().get(productKey));
                log.info("未减库存,库存为:{}", productNum);
                if (productNum > 0) {
                    long num = stringRedisTemplate.opsForValue().decrement(productKey, 1);
                    log.info("执行减库存,此时库存为:{}", num);
                    return ApiResponse.success("获取simple锁成功,并减库存" + num);
                } else {
                    return ApiResponse.error(404, "获取simple锁成功,库存为:" + productNum);
                }
            } catch (Exception e) {
                log.error("e:", e);
            } finally {
                //保证加锁和解锁是同一个客户端
                if (cliendId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                    stringRedisTemplate.delete(lockKey);
                }
            }
        }


        return ApiResponse.error(404, "获取simple锁失败");
    }


    @PostMapping("/lock/{id}")
    public ApiResponse<String> redissonLock(@PathVariable(value = "id") @NotNull String id) {
        String key = "lock:redisson:" + id;
        RLock rLock = redissonClient.getLock(key);
        Boolean flag = rLock.tryLock();
        // 500ms拿不到锁, 就认为获取锁失败。10000ms即10s是锁失效时间。
//        flag = rLock.tryLock(500, 10000, TimeUnit.MILLISECONDS);

        if (flag) {
            String productKey = PRODUCT_KEY + id;
            try {
                // 加锁成功
                long productNum = Long.parseLong(stringRedisTemplate.opsForValue().get(productKey));
                log.info("未减库存,库存为:{}", productNum);
                if (productNum > 0) {
                    long num = stringRedisTemplate.opsForValue().decrement(productKey, 1);
                    log.info("执行减库存,此时库存为:{}", num);
                    return ApiResponse.success("获取redisson锁成功,并减库存" + num);
                } else {
                    return ApiResponse.error(404, "获取redisson锁成功,库存为:" + productNum);
                }
            } catch (Exception e) {
                log.error("e:", e);
            } finally {
                // 判断要解锁的key是否已被锁定 判断要解锁的key是否被当前线程持有。
                if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }
        }

        return ApiResponse.error(404, "获取redisson锁失败:" + flag);
    }

    @PostMapping("/lock/read/{id}")
    public ApiResponse<String> redissonReadWriteLock(@PathVariable(value = "id") @NotNull String id) {
        String key = "lock:redisson:read-write:" + id;
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock(key);
        RLock readLock = rReadWriteLock.readLock();

        Boolean readFlag = readLock.tryLock();
        if (readFlag) {
            String productKey = PRODUCT_KEY + id;
            long productNum = Long.parseLong(stringRedisTemplate.opsForValue().get(productKey));
            if (log.isDebugEnabled()) {
                log.info("#ReadWriteLock加读锁成功:{}", productNum);
            }
            try {
                if (productNum > 0) {
                    long num = stringRedisTemplate.opsForValue().decrement(productKey, 1);
                    log.info("执行减库存,此时库存为:{}", num);
                    return ApiResponse.success("获取redisson读锁成功,并减库存" + num);
                } else {
                    return ApiResponse.error(404, "获取redisson读锁成功,库存为:" + productNum);
                }

            } catch (Exception e) {
                log.error("e:", e);
            } finally {
                // 判断要解锁的key是否已被锁定 判断要解锁的key是否被当前线程持有。
                if (readLock.isLocked() && readLock.isHeldByCurrentThread()) {
                    readLock.unlock();
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.info("#ReadWriteLock加读锁失败");
        }
        return ApiResponse.error(404, "获取redisson读锁失败:" + readFlag);
    }

    @PostMapping("/init/{id}")
    public ApiResponse<String> init(@PathVariable(value = "id") @NotNull String id) {
        String key = PRODUCT_KEY + id;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(NUMBER));
        return ApiResponse.success("init");
    }


    @PostMapping("/delay-queue/add-task")
    public ApiResponse<Boolean> addDelayQueue() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("orderId", "100");
        map1.put("remark", "订单支付超时，自动取消订单");

        Map<String, String> map2 = new HashMap<>();
        map2.put("orderId", "200");
        map2.put("remark", "订单超时未评价，系统默认好评");

        // 添加订单支付超时，自动取消订单延迟队列
        redissonDelayQueueUtil.addDelayQueue(map1, 60, TimeUnit.SECONDS, RedissonDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode());
        // 订单超时未评价，系统默认好评
        redissonDelayQueueUtil.addDelayQueue(map2, 60, TimeUnit.SECONDS, RedissonDelayQueueEnum.ORDER_TIMEOUT_NOT_EVALUATED.getCode());
        return ApiResponse.success(true);
    }

    @PostMapping("/delay-queue/cancel-task")
    public ApiResponse<Boolean> cancelTaskDelayQueue() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("orderId", "100");
        map1.put("remark", "订单支付超时，自动取消订单");

        boolean res = redissonDelayQueueUtil.removeDelayedQueue(map1, RedissonDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode());
        return ApiResponse.success(res);
    }

}
