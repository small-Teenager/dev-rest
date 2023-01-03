package com.redis.rest.controller.basic;

import com.redis.rest.dto.SMoveDTO;
import com.redis.rest.dto.SetSAddDTO;
import com.redis.rest.response.ApiResponse;
import com.redis.rest.service.SetRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 内部编码
 * intset:
 * hashtable
 * string 基本操作
 *
 * @author: yaodong zhang
 * @create 2022/12/17
 */
@RestController
@RequestMapping("/basic/set")
public class SetController {

    @Autowired
    private SetRedisService setRedisService;

    /**
     * 获取key对应集合的长度
     * @param key
     * @return
     */
    @GetMapping("/size/{key}")
    public ApiResponse<Long> size(@PathVariable(value = "key") @NotNull String key) {
        Long result = setRedisService.size(key);
        return ApiResponse.success(result);
    }

    /**
     * 添加元素
     * @param record
     * @return
     */
    @PostMapping("/sadd")
    public ApiResponse<Boolean> sadd(@Validated @RequestBody SetSAddDTO record) {
        Boolean result = setRedisService.sadd(record);
        return ApiResponse.success(result);
    }

    /**
     * 判断key对应的集合中是否包含元素member
     * @param key
     * @param member
     * @return
     */
    @GetMapping("/sismember/{key}/{member}")
    public ApiResponse<Boolean> sismember(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "member") @NotNull String member) {
        Boolean result = setRedisService.sismember(key,member);
        return ApiResponse.success(result);
    }

    /**
     * 获取key中的值
     * @param key
     * @return
     */
    @GetMapping("/members/{key}")
    public ApiResponse<Set> members(@PathVariable(value = "key") @NotNull String key) {
        Set result = setRedisService.members(key);
        return ApiResponse.success(result);
    }


    /**
     * 随机弹出key对应集合中的一个元素
     * @param key
     * @return
     */
    @DeleteMapping("/spop/{key}")
    public ApiResponse<Object> spop(@PathVariable(value = "key") @NotNull String key) {
        Object result = setRedisService.spop(key);
        return ApiResponse.success(result);
    }

    /**
     * 随机获取key对应集合中指定个数的元素
     * @param key
     * @param count
     * @return
     */
    @GetMapping("/srandmember/{key}/{count}")
    public ApiResponse<Object> srandmember (@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "count") @NotNull Long count) {
        Object result = setRedisService.srandmember(key,count);
        return ApiResponse.success(result);
    }

    /**
     * 移除集合 key 中的 member 元素
     * @param key
     * @param member
     * @return
     */
    @DeleteMapping("/srem/{key}/{member}")
    public ApiResponse<Long> srem (@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "member") @NotNull String member) {
        Long result = setRedisService.srem(key,member);
        return ApiResponse.success(result);
    }

    /**
     * 将key1对应集合中的值v1，转移到key2集合中
     *
     * key2不存在直接新增
     * v1不存在，转移失败返回false
     * @param record
     * @return
     */
    @PutMapping("/smove")
    public ApiResponse<Boolean> smove(@Validated @RequestBody SMoveDTO record) {
        Boolean result = setRedisService.smove(record);
        return ApiResponse.success(result);
    }

    /**
     * 获取key与另一个otherKey集合之间的差值
     * @param key
     * @param otherKey
     * @return
     */
    @GetMapping("/difference/{key}/{otherKey}")
    public ApiResponse<Set> difference(@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "otherKey") @NotNull String otherKey) {
        Set result = setRedisService.difference(key,otherKey);
        return ApiResponse.success(result);
    }

    /**
     * 获取key与另外一些otherKeys集合之间的差值，并将结果存入指定的destKey中
     *
     * destKey存在，它原本集合中的所有值会被清空并且替换为获取的差值
     * destKey不存在，直接新增
     * key与otherKeys没有获取到差值，destKey如果存在，会被删除
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    @PostMapping("/difference/{key}/{otherKey}/{destKey}")
    public ApiResponse<Boolean> differenceAndStore(@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "otherKey") @NotNull String otherKey,@PathVariable(value = "destKey") @NotNull String destKey) {
        Boolean result = setRedisService.differenceAndStore(key,otherKey,destKey);
        return ApiResponse.success(result);
    }

    /**
     * 获取两个集合中的交集元素
     * @param key
     * @param otherKey
     * @return
     */
    @GetMapping("/intersect/{key}/{otherKey}")
    public ApiResponse<Set> intersect(@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "otherKey") @NotNull String otherKey) {
        Set result = setRedisService.intersect(key,otherKey);
        return ApiResponse.success(result);
    }

    /**
     * 获取key集合与另一个otherKey集合之间的交集元素，并将其放入指定的destKey集合中
     * 没有获取到交集元素时，注意：若指定集合destKey存在，它将会被删除
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    @PostMapping("/intersect/{key}/{otherKey}/{destKey}")
    public ApiResponse<Boolean> intersectAndStore(@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "otherKey") @NotNull String otherKey,@PathVariable(value = "destKey") @NotNull String destKey) {
        Boolean result = setRedisService.intersectAndStore(key,otherKey,destKey);
        return ApiResponse.success(result);
    }

    /**
     * 获取多个集合的合集，去重
     * @param key
     * @param otherKey
     * @return
     */
    @GetMapping("/union/{key}/{otherKey}")
    public ApiResponse<Set> union(@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "otherKey") @NotNull String otherKey) {
        Set result = setRedisService.union(key,otherKey);
        return ApiResponse.success(result);
    }

    /**
     * 获取两个集合之间的合集，并放入指定key对应的新集合中
     * 若指定key对应的集合存在，会被覆盖掉
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    @PostMapping("/union/{key}/{otherKey}/{destKey}")
    public ApiResponse<Boolean> unionAndStore(@PathVariable(value = "key") @NotNull String key,@PathVariable(value = "otherKey") @NotNull String otherKey,@PathVariable(value = "destKey") @NotNull String destKey) {
        Boolean result = setRedisService.unionAndStore(key,otherKey,destKey);
        return ApiResponse.success(result);
    }
}
