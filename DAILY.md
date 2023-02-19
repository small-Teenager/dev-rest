# daily

## 统计日活用户

## 用户签到

## 黑名单

## 计数器

## 限流

## 排行榜

## 分布式锁 及引入 redisson 实现分布式锁

## redis pipeline 减少网络请求

## 优雅停机

## redis 调试lua
     -- 需要更redis config 中loglevel 级别一致
     redis.log(redis.LOG_DEBUG, "log debug cnt : " .. cnt)

## redis 序列化
* 使用Snappy 序列化
## 接入 Spring Boot Admin

[redis-rest-admin](https://github.com/small-Teenager/redis-rest-admin)

## Redis 禁用高风险指令
redis.conf，在SECURITY这一项中 新增
    
    rename-command FLUSHALL ""
    rename-command FLUSHDB  ""
    rename-command CONFIG   ""
    rename-command KEYS     ""

如果还想保留命令，只是想改名

    rename-command FLUSHALL joYAPNXRPmcarcR4ZDgC81TbdkSmLAzRPmcarcR
    rename-command FLUSHDB  qf69aZbLAX3cf3ednHM3SOlbpH71yEXLAX3cf3e
    rename-command CONFIG   FRaqbC8wSA1XvpFVjCRGryWtIIZS2TRvpFVjCRG
    rename-command KEYS     eIiGXix4A2DreBBsQwY6YHkidcDjoYA2DreBBsQ

