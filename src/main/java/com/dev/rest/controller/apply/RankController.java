package com.dev.rest.controller.apply;

import com.dev.rest.dto.AddScoreDTO;
import com.dev.rest.response.ApiResponse;
import com.dev.rest.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Set;

/** zset
 * @author: yaodong zhang
 * @create 2022/12/30
 */
@RestController
@RequestMapping("/apply/rank")
public class RankController {

    @Autowired
    private RankService rankService;


    /**
     * 加入排行榜
     * @param record
     * @return
     */
    @PostMapping("/score")
    public ApiResponse<Double> addScore(@Validated @RequestBody AddScoreDTO record) {
        Double result = rankService.addScore(record);
        return ApiResponse.success(result);
    }

    /**
     *
     * @param page 页码
     * @param size 步长
     * @return
     */
    @GetMapping("/range/{page}/{size}")
    public ApiResponse<Set> range(@PathVariable(value = "page") @NotNull Long page, @PathVariable(value = "size") @NotNull Long size) {
        Set result = rankService.reverseRangeWithScores(page, size);
        return ApiResponse.success(result);
    }


}
