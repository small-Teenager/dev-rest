package com.dev.rest.controller.basic;

import com.dev.rest.dto.IncrementScoreDTO;
import com.dev.rest.dto.SortSetZAddDTO;
import com.dev.rest.response.ApiResponse;
import com.dev.rest.service.SortSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 内部编码
 * ziplist:
 * skiplist
 * 有序集合
 *
 * @author: yaodong zhang
 * @create 2022/12/27
 */
@RestController
@RequestMapping("/basic/sort-set")
public class SortSetController {

    @Autowired
    private SortSetService sortSetService;


    /**
     * 向指定key中添加元素，按照score值由小到大进行排列
     * 集合中对应元素已存在，会被覆盖，包括score
     *
     * @param record
     * @return
     */
    @PostMapping("/zadd")
    public ApiResponse<Boolean> zadd(@Validated @RequestBody SortSetZAddDTO record) {
        Boolean result = sortSetService.zadd(record);
        return ApiResponse.success(result);
    }

    /**
     * 移除集合中指定的值
     * @param key
     * @param object
     * @return
     */
    @DeleteMapping("/remove/{key}/{object}")
    public ApiResponse<Boolean> remove(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "object") @NotNull String object) {
        Boolean result = sortSetService.remove(key, object);
        return ApiResponse.success(result);
    }

    /**
     * 移除指定下标的值
     * (0,-1)就是移除全部
     * @param key
     * @param start
     * @param end
     * @return
     */
    @DeleteMapping("/remove-range/{key}/{start}/{end}")
    public ApiResponse<Boolean> removeRange(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "start") @NotNull Long start, @PathVariable(value = "end") @NotNull Long end) {
        Boolean result = sortSetService.removeRange(key, start,end);
        return ApiResponse.success(result);
    }

    /**
     * 移除指定score区间内的值
     * @param key
     * @param min
     * @param max
     * @return
     */
    @DeleteMapping("/remove-range-by-score/{key}/{min}/{max}")
    public ApiResponse<Boolean> removeRangeByScore(@PathVariable(value = "key") @NotNull String key,  @PathVariable(value = "min") @NotNull Double min, @PathVariable(value = "max") @NotNull Double max) {
        Boolean result = sortSetService.removeRangeByScore(key, min,max);
        return ApiResponse.success(result);
    }

    /**
     * 增加key对应的集合中元素v1的score值，并返回增加后的值
     * v1不存在，直接新增一个元素
     *
     * @param record
     * @return
     */
    @PostMapping("/increment-score")
    public ApiResponse<Double> incrementScore(@Validated @RequestBody IncrementScoreDTO record) {
        Double result = sortSetService.incrementScore(record);
        return ApiResponse.success(result);
    }

    /**
     * 获取key对应集合中o元素的score值
     *
     * @param key
     * @param object
     * @return
     */
    @GetMapping("/score/{key}/{object}")
    public ApiResponse<Double> score(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "object") @NotNull String object) {
        Double result = sortSetService.score(key, object);
        return ApiResponse.success(result);
    }

    /**
     * 获取key对应集合的长度
     *
     * @param key
     * @return
     */
    @GetMapping("/size/{key}")
    public ApiResponse<Long> size(@PathVariable(value = "key") @NotNull String key) {
        Long result = sortSetService.size(key);
        return ApiResponse.success(result);
    }

    /**
     * 获取指定score区间里的元素个数
     * <p>
     * 包括min、max
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    @GetMapping("/count/{key}/{min}/{max}")
    public ApiResponse<Long> count(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "min") @NotNull Double min, @PathVariable(value = "max") @NotNull Double max) {
        Long result = sortSetService.count(key, min, max);
        return ApiResponse.success(result);
    }

    /**
     * 获取指定下标之间的值
     * <p>
     * (0,-1)就是获取全部
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/range/{key}/{start}/{end}")
    public ApiResponse<Set> range(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "start") @NotNull Long start, @PathVariable(value = "end") @NotNull Long end) {
        Set result = sortSetService.range(key, start, end);
        return ApiResponse.success(result);
    }

    /**
     * 获取指定score区间的值
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    @GetMapping("/range-by-score/{key}/{min}/{max}")
    public ApiResponse<Set> rangeByScore(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "min") @NotNull Double min, @PathVariable(value = "max") @NotNull Double max) {
        Set result = sortSetService.rangeByScore(key, min, max);
        return ApiResponse.success(result);
    }

    /**
     * 获取指定元素在集合中的索引，索引从0开始
     *
     * @param key
     * @param object
     * @return
     */
    @GetMapping("/rank/{key}/{object}")
    public ApiResponse<Long> rank(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "object") @NotNull String object) {
        Long result = sortSetService.rank(key, object);
        return ApiResponse.success(result);
    }

    /**
     * 获取倒序排列的索引值，索引从0开始
     *
     * @param key
     * @param object
     * @return
     */
    @GetMapping("/reverse-rank/{key}/{object}")
    public ApiResponse<Long> reverseRank(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "object") @NotNull String object) {
        Long result = sortSetService.reverseRank(key, object);
        return ApiResponse.success(result);
    }

    /**
     * 逆序获取对应下标的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/reverse-range/{key}/{start}/{end}")
    public ApiResponse<Set> reverseRange(@PathVariable(value = "key") @NotNull String key, @PathVariable(value = "start") @NotNull Long start, @PathVariable(value = "end") @NotNull Long end) {
        Set result = sortSetService.reverseRange(key, start,end);
        return ApiResponse.success(result);
    }

}
