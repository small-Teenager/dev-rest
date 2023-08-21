package com.dev.rest.controller.base;

import com.dev.rest.dto.ProjectDTO;
import com.dev.rest.dto.TeamDTO;
import com.dev.rest.annotation.ValidGroup;
import com.dev.rest.response.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.groups.Default;

/**
 * @author: yaodong zhang
 * @create 2023/2/2
 */
@RestController
@RequestMapping("/base/valid")
public class ValidController {


    /**
     * {
     * "nowYear": "2023",
     * "futureTime": "2023-06-08 12:44:29",
     * "pastTime": "1986-06-26 07:19:40",
     * "email": "n.vomwowql@qq.com",
     * "list": [
     * {
     * "name": "张三",
     * "sex": 3
     * }
     * ]
     * }
     *
     * @param teamDTO
     * @return
     */
    //@Valid的嵌套校验
    @PostMapping("/nesting")
    public ApiResponse nesting(@Valid @RequestBody TeamDTO teamDTO) {
        return ApiResponse.success(teamDTO);
    }


    /**
     * {
     * "id": "69",
     * "strValue": "dolore",
     * "intValue": 64,
     * "negativeValue": 27,
     * "strEnum": "52",
     * "intEnum": 31,
     * "teamDTO": {
     * "nowYear": "2023",
     * "futureTime": "1985-01-09 21:57:51",
     * "pastTime": "2002-07-03 09:45:24",
     * "email": "q.hkfhdxme@qq.com",
     * "list": [
     * {
     * "name": "张三",
     * "sex": 3
     * }
     * ]
     * }
     * }
     *
     * @param testAnnotationDto
     * @return
     */
    //不同的分组不同的校验策略
    @PostMapping("/grouop")
    public ApiResponse grouop(@Validated(value = {ValidGroup.Update.class}) @RequestBody ProjectDTO testAnnotationDto) {
        return ApiResponse.success(testAnnotationDto);
    }


    /**
     * {
     *     "id": "69",
     *     "strValue": "0",
     *     "intValue": 64,
     *     "negativeValue": -27,
     *     "strEnum": "agree",
     *     "intEnum": 1983,
     *     "teamDTO": {
     *         "nowYear": "2023",
     *         "futureTime": "2025-01-09 21:57:51",
     *         "pastTime": "2002-07-03 09:45:24",
     *         "email": "q.hkfhdxme@qq.com",
     *         "list": [
     *             {
     *             "name": "张三",
     *             "sex": 3
     *         }
     *         ]
     *     }
     * }
     * @param testAnnotationDto
     * @return
     */
    // @Validated和Valid可以组合使用
    @PostMapping("")
    public ApiResponse testValidPostRequest(@Valid @Validated(value = {ValidGroup.Update.class, Default.class}) @RequestBody ProjectDTO testAnnotationDto) {
        return ApiResponse.success(testAnnotationDto);

    }
}
