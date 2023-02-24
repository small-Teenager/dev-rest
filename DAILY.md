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
    
## Redis 慢日志
Redis命令执行流程

     1、发送命令
     2、命令排队
     3、命令执行
     4、返回结果

注意：慢查询只统计步骤3的时间,所以没有慢查询并不代表客户端没有超时问题。
redis.conf中
slowlog-log-slower-than=0 #记录所有命令(微秒)

slowlog-log-slower-than<0 #不记录任何命令

slowlog-max-len  #最多能保存日志条数

slow log 本身是一个 FIFO(先进先出)队列，当队列大小超过 slowlog-max-len 时，最旧的一条日志将被删除，而最新的一条日志加入到 slow log中。
虽然慢查询日志存放在Redis内存列表中,但是Redis并没有暴露这个列表的键,而是通过一组命令来实现对慢查询日志的访问和管理.


### Redis 慢日志指令
slowlog get [n] #获取慢查询队列
slowlog len #获取当前日志的数量
slowlog reset #清空慢查询日志

     在生产环境中，慢查询功能可以有效地帮助我们找到Redis可能存在的瓶颈，但在实际使用过程中要注意以下几点:

     1、slowlog-max-len：线上建议调大慢查询列表，记录慢查询时Redis会对长命令做阶段操作,并不会占用大量内存.增大慢查询列表可以减缓慢查询被剔除的可能，例如线上可设置为1000以上.

     2、slowlog-log-slower-than：默认值超过10毫秒判定为慢查询,需要根据Redis并发量调整该值.

     3、慢查询只记录命令的执行时间,并不包括命令排队和网络传输时间.因此客户端执行命令的时间会大于命令的实际执行时间.因为命令执行排队机制,慢查询会导致其他命令级联阻塞,因此客户端出现请求超时时,需要检查该时间点是否有对应的慢查询,从而分析是否为慢查询导致的命令级联阻塞.

     4、由于慢查询日志是一个先进先出的队列,也就是说如果慢查询比较多的情况下,可能会丢失部分慢查询命令,为了防止这种情况发生,可以定期执行slowlog get命令将慢查询日志持久化到其他存储中(例如:MySQL等)，然后可以通过可视化工具进行查询.

