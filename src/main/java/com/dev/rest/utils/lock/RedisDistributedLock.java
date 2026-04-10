package com.dev.rest.utils.lock;

import com.dev.rest.common.utils.AddressUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 可重入(hash结构) + 阻塞重试 + 防死锁 + 防误删 + 原子解锁
 *
 * @Description  标准 Redis 分布式锁 无Watchdog
 * @Author zhangyaodong
 */
@Component
public class RedisDistributedLock {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // ====================== 生产固定配置 ======================
    private static final String LOCK_PREFIX = "hacint:lock:";
    private static final int DEFAULT_LOCK_EXPIRE = 3000;          // 默认3秒
    private static final int LOCK_WAIT_TIME = 800;        // 重试间隔
    private static final int DEFAULT_TIMEOUT = 2000;     // 最大阻塞时间

    // ====================== LUA 脚本 ======================
    private static final DefaultRedisScript<Long> LOCK_SCRIPT;
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;

    static {
        // 加锁 LUA
        LOCK_SCRIPT = new DefaultRedisScript<>();
        LOCK_SCRIPT.setScriptText(
                "if redis.call('exists',KEYS[1])==0 then " +
                        "redis.call('hset',KEYS[1],ARGV[1],'1');" +
                        "redis.call('pexpire',KEYS[1],ARGV[2]);" +
                        "return 1;" +
                        "end;" +
                        "if redis.call('hexists',KEYS[1],ARGV[1])==1 then " +
                        "redis.call('hincrby',KEYS[1],ARGV[1],1);" +
                        "redis.call('pexpire',KEYS[1],ARGV[2]);" +
                        "return 1;" +
                        "end;" +
                        "return 0;"
        );
        LOCK_SCRIPT.setResultType(Long.class);

        // 解锁 LUA
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setScriptText(
                "if redis.call('hexists',KEYS[1],ARGV[1])==0 then return 0 end;" +
                        "local c=redis.call('hincrby',KEYS[1],ARGV[1],-1);" +
                        "if c>0 then redis.call('pexpire',KEYS[1],ARGV[2]);return 1;" +
                        "else redis.call('del',KEYS[1]);return 1;end;"
        );
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    /**
     * 加锁（支持可重入 + 自动阻塞重试）使用默认过期时间
     *
     * @param lockKey 锁key
     * @return 唯一标识（用于解锁）
     */
    public String lock(String lockKey) {
        return lock(lockKey, DEFAULT_TIMEOUT, DEFAULT_LOCK_EXPIRE);
    }

    /**
     * 加锁（带最大等待时间）使用默认过期时间
     */
    public String lock(String lockKey, long waitTime) {
        return lock(lockKey, waitTime, DEFAULT_LOCK_EXPIRE);
    }

    /**
     * 加锁（带最大等待时间 + 自定义过期时间）
     *
     * @param lockKey    锁key
     * @param waitTime   最大等待时间(毫秒)
     * @param lockExpire 锁过期时间(毫秒)
     * @return 唯一标识（用于解锁）
     */
    public String lock(String lockKey, long waitTime, long lockExpire) {
        String key = LOCK_PREFIX + lockKey;
        String requestId = generateRequestId();
        long startTime = System.currentTimeMillis();

        try {
            while (true) {
                // 执行LUA加锁
                Long success = stringRedisTemplate.execute(
                        LOCK_SCRIPT,
                        Collections.singletonList(key),
                        requestId,
                        String.valueOf(lockExpire)
                );

                if (success != null && success == 1) {
                    return requestId;
                }

                // 超时放弃
                if (System.currentTimeMillis() - startTime > waitTime) {
                    return null;
                }

                // 阻塞等待
                TimeUnit.MILLISECONDS.sleep(LOCK_WAIT_TIME);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * 解锁（标准原子解锁）使用默认过期时间
     */
    public boolean unlock(String lockKey, String requestId) {
        return unlock(lockKey, requestId, DEFAULT_LOCK_EXPIRE);
    }

    /**
     * 解锁（标准原子解锁）支持自定义过期时间（与加锁时保持一致）
     *
     * @param lockKey    锁key
     * @param requestId  加锁返回的唯一标识
     * @param lockExpire 锁过期时间(毫秒)
     * @return 是否解锁成功
     */
    public boolean unlock(String lockKey, String requestId, long lockExpire) {
        if (requestId == null) return false;

        String key = LOCK_PREFIX + lockKey;
        Long result = stringRedisTemplate.execute(
                UNLOCK_SCRIPT,
                Collections.singletonList(key),
                requestId,
                String.valueOf(lockExpire)
        );
        return result != null && result == 1;
    }

    /**
     * 生成全局唯一请求ID
     * 内部规范：UUID + 时间戳 + 机器IP
     */
    private String generateRequestId() {
        return UUID.randomUUID().toString().replace("-", "")
                + "_" + System.currentTimeMillis() + "_" + AddressUtils.getHostIp();
    }

}