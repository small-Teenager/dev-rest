package com.redis.rest.controller.apply;

import com.redis.rest.response.ApiResponse;
import org.redisson.api.RLock;
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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 尽可能减小锁粒度
 * @author: yaodong zhang
 * @create 2023/1/5
 */
@RestController
@RequestMapping("/apply/lock")
public class LockController {

    private static final Logger log = LoggerFactory.getLogger(LockController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private static Integer number = 10;

    @PostMapping("/simple/{id}")
    public ApiResponse<String> simpleLock(@PathVariable(value = "id") @NotNull String id) {
        String key = "lock:simple:" + id;
        String cliendId = UUID.randomUUID().toString();

        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, cliendId, 60, TimeUnit.SECONDS);
        if (flag) {
            // 加锁成功
            try {
                if (number > 0) {
                    number--;
                    log.info("number:{}", number);
                    return ApiResponse.success("获取simple锁成功:" + number);
                } else {
                    return ApiResponse.error(404, "获取simple锁成功,此时number<=0," + number);
                }
            } catch (Exception e) {
                log.error("e:", e);
            } finally {
                //保证加锁和解锁是同一个客户端
                if (cliendId.equals(stringRedisTemplate.opsForValue().get(key))) {
                    stringRedisTemplate.delete(key);
                }
            }
        }


        return ApiResponse.error(404, "获取simple锁失败");
    }


    @PostMapping("/redisson/{id}")
    public ApiResponse<String> redissonLock(@PathVariable(value = "id") @NotNull String id) {
        String key = "lock:redisson:" + id;
        RLock rLock = redissonClient.getLock(key);
        Boolean flag = rLock.tryLock();

        if (flag) {
            try {
                // 加锁成功
                if (number > 0) {
                    number--;
                    log.info("number:{}", number);
                    return ApiResponse.success("获取redisson锁成功:" + number);
                } else {
                    return ApiResponse.error(404, "获取redisson锁成功,此时number<=0," + number);
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
}
