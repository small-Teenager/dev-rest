package com.redis.rest.service.impl;

import com.redis.rest.service.SignService;
import com.redis.rest.util.DateUtil8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yaodong zhang
 * @create 2022/12/28
 */
@Service
public class SignServiceImpl implements SignService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Boolean signIn(String userId) {
        //获取当前日期
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 组合成key= sign:userId:年月
        String key = "sign:" + userId + keySuffix;
        // 获取当前日期是当月的第多少天
        int dayOfMonth = now.getDayOfMonth();
        // 存到redis中的bitmap中，由于bitmap从0开始，第多少天从1开始，dayOfMonth需要减1
        return redisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
    }

    @Override
    public Boolean signStatus(String userId) {
        //获取当前日期
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 组合成key= sign:userId:年月
        String key = "sign:" + userId + keySuffix;
        // 获取当前日期是当月的第多少天
        int dayOfMonth = now.getDayOfMonth();
        // 存到redis中的bitmap中，由于bitmap从0开始，第多少天从1开始，dayOfMonth需要减1
        return redisTemplate.opsForValue().getBit(key, dayOfMonth - 1);
    }

    @Override
    public Long monthCount(String userId) {
        //获取当前日期
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 组合成key= sign:userId:年月
        String key = "sign:" + userId + keySuffix;
        // 获取当前日期是当月的第多少天
        return (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    @Override
    public Boolean countersign(String userId, String signDate) {
        LocalDateTime dateOfSign = DateUtil8.formatLocalDateTime(signDate, DateUtil8.YYYY_MM_DD);
        String keySuffix = dateOfSign.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "sign:" + userId + keySuffix;
        int dayOfMonth = dateOfSign.getDayOfMonth();
        return redisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
    }

    @Override
    public Map<String, Boolean> signCalendar(String userId) {
        LocalDateTime now = LocalDateTime.now();
        // 获取当前日期是当月的第多少天
        int dayOfMonth = now.getDayOfMonth();
        Map<String, Boolean> signMap = new HashMap<>(dayOfMonth);
        //用户Uid
        //获取当前日期
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 组合成key= sign:userId:年月
        String key = "sign:" + userId + keySuffix;

        List<Long> result = redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }

        long v = result.get(0);
        for (int i = dayOfMonth; i > 0; i--) {
            LocalDateTime localDateTime = now.withDayOfMonth(i);
            signMap.put(DateUtil8.format(localDateTime, DateUtil8.YYYY_MM_DD), v >> 1 << 1 != v);
            v >>= 1;
        }
        return signMap;
    }

    @Override
    public Long conMonthCount(String userId) {
        Long result = 0L;
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = "sign:" + userId + keySuffix;

        //  而我们的redis的值是从0 到 30 , 所以 , 这里要减一
        int dayOfMonth = now.getDayOfMonth();
        // 获取本月截止今天为止的签到记录 , 返回的是一个十进制数字
        List<Long> list = redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        if (CollectionUtils.isEmpty(list)) {
            // 没有签到结果
            return result;
        }
        Long num = list.get(0);
        if (num == null || num == 0) {
            return result;
        }

        while (true) {
            // 让这个数字与1做与运算 , 得到数字的最后一个bit位
            // 判断这个bit位是否是0
            if ((num & 1) == 0) {
                // 为零说明未签到 , 结束
                break;
            } else {
                // 不为零 , 说明已签到 , 计数器 +1
                result++;
            }
            // 把数字右移一位 , 继续下一个bit位
            num >>>= 1;
        }

        return result;
    }
}
