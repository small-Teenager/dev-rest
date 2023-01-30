# Redis性能排查

## 进行排查，是否Redis是否真的慢了

在服务内部集成链路追踪，查看对应方法在调用Redis 服务时是否造成延迟。有可能是存在网络的原因导致在传输中出现了丢包等问题，或者是redis确实出现了问题。

## 进行基准测试，进行排查

在redis服务器中进行调用，测试对应实例的响应延迟情况

    redis-cli -h 127.0.0.1 -p 6379 --intrinsic-latency 60
    通过上面的命令可以知道这个实例在60秒内的最大响应延迟。

```
\Redis-x64-5.0.14.1>redis-cli -h 127.0.0.1 -p 6379 --intrinsic-latency 60
Max latency so far: 1 microseconds.
Max latency so far: 2 microseconds.
Max latency so far: 10 microseconds.
Max latency so far: 13 microseconds.
Max latency so far: 19 microseconds.
Max latency so far: 21 microseconds.
Max latency so far: 52 microseconds.
Max latency so far: 179 microseconds.
Max latency so far: 250 microseconds.
Max latency so far: 348 microseconds.
Max latency so far: 651 microseconds.
Max latency so far: 678 microseconds.
Max latency so far: 996 microseconds.
Max latency so far: 1133 microseconds.
Max latency so far: 1335 microseconds.
Max latency so far: 1746 microseconds.
Max latency so far: 1996 microseconds.
Max latency so far: 2938 microseconds.

1330646464 total runs (avg latency: 0.0451 microseconds / 45.09 nanoseconds per run).
Worst run took 65157x longer than the average latency.
```

参数中的60是测试执行的秒数，可以看到最大延迟为2938微秒（3毫秒左右），如果命令的执行远超3毫秒，此时Redis就有可能很慢了！

```
redis-cli -h 127.0.0.1 -p 6379 --latency-history -i 1
通过这个命令可以查看一段时间内redis的最大延迟，最小与平均延迟
```

通过以下几步 可以判断redis 是否变慢了

在相同配置的服务器上，测试一个正常 Redis 实例的基准性能 找到你认为可能变慢的 Redis 实例，测试这个实例的基准性能 如果你观察到，这个实例的运行延迟是正常 Redis 基准性能的 2 倍以上，即可认为这个 Redis
实例确实变慢了

## 是否存在大量复杂度高的命令

查看慢日志来了解是哪些命令耗时比较严重

首先采用以下命令来设置慢日志阈值

```
# 命令执行耗时超过 5 毫秒，记录慢日志
CONFIG SET slowlog-log-slower-than 5000
# 只保留最近 500 条慢日志
CONFIG SET slowlog-max-len 500
# 如果要Redis将配置持久化到本地配置文件，需要执行config rewrite命令
CONFIG REWRITE
```

对于时间复杂度在O(N) 的命令 会造成运行比较慢

复杂度为O(N),比如SORT、SUNION、ZUNIONSTORE 聚合类命令

N 代表的数组特别大 （可以类别 在mysql 查询中 查出来数据比较大） 针对于 第一个问题 需要考虑的是 将聚合的操作在代码层面完成，由于复杂度的提高，对于CPU的耗费会很高，

第二个问题主要是由于 一次性返回了过多的数据，导致网络的传输消耗会很高。遇到这个问题，最好采用分页的方式对于数据进行分批的返回。

## bigKey

redis 中bigKey会带来的问题,主要是由于在序列化的时候耗时比较长，会造成阻塞。

string长度大于10K，list长度大于10240认为是big bigkeys

同时在存储的时候耗时比较长，以及在删除的时候耗时也比较长。

```
redis-cli -h 127.0.0.1 -p 6379 --bigkeys -i 0.01
```

```
\Redis-x64-5.0.14.1>redis-cli -h 127.0.0.1 -p 6379 --bigkeys -i 0.01

# Scanning the entire keyspace to find biggest keys as well as
# average sizes per key type.  You can use -i 0.1 to sleep 0.1 sec
# per 100 SCAN commands (not usually needed).

[00.00%] Biggest string found so far 'pipeline:54' with 2 bytes
[15.12%] Biggest string found so far 'dolore' with 2793 bytes

-------- summary -------

Sampled 205 keys in the keyspace!
Total key length in bytes is 2032 (avg len 9.91)

Biggest string found 'dolore' has 2793 bytes

0 lists with 0 items (00.00% of keys, avg size 0.00)
0 hashs with 0 fields (00.00% of keys, avg size 0.00)
205 strings with 6356 bytes (100.00% of keys, avg size 31.00)
0 streams with 0 entries (00.00% of keys, avg size 0.00)
0 sets with 0 members (00.00% of keys, avg size 0.00)
0 zsets with 0 members (00.00% of keys, avg size 0.00)
```
可以看到命令的输入有如下3个部分

* 内存中key的数量，已经占用的总内存，每个key占用的平均内存
* 每种类型占用的最大内存，已经key的名字
* 每种数据类型的占比，以及平均大小

该名称内部使用了SCAN的原理来扫描每一个key

该命令也会有两个问题
* 该命令会造成Redis 的 OPS 会突增，需要控制扫描的效率
* 该命令在对于一些结构为 list set 会返回结构的数量多的那些ky, 但是不排除存在一个list 只有一个对象，但该对象会很多。

处理的方法有几个

* 避免big key写入
* 在删除的时候 使用 unlink 替换 del ，del 是阻塞的，但是 unlink该命令会执行命令之外的线程中执行实际的内存回收，因此它不是阻塞.主要是将 引用断开，后续在进行value 删除。

## 集中过期

redis 的删除策略有两种 ：

* 被动删除： 只有当访问某个 key 时，才判断这个 key 是否已过期，如果已过期，则从实例中删除
* 主动删除： 开一个线程 每隔若干时间，从redis中取出一部分数据，判断是否过期，如果过期数量超过了25%，那就再次进行，小于25%就不再进行。

  在第二种策略情况下，在删除执行时，如果有新的命令过来，这只能等待删除数据结束后，才能执行后续的命令。后续的命令不会被记录到慢日志的，因为删除的任务是在执行任务之前。

当然在大量key 集中过期 更严重的问题是缓存雪崩

处理方案就是在过期时间后 加一个随机时间，还有一种方法是在redis4.0之前 使用了一个lazy-free，在释放过期内存时，采用了后台线程的方式。lazyfree-lazy-eviction = yes
