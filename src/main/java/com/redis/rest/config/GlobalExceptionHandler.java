package com.redis.rest.config;

import com.redis.rest.exception.RedisLimitException;
import com.redis.rest.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: yaodong zhang
 * @create 2022/12/19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理接口请求验证错误的异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public ApiResponse exceptionHandler(BindException exception) {
        log.error("exception:{}",exception.getMessage());
        return ApiResponse.error(404, exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = RedisLimitException.class)
    public ApiResponse exceptionHandler(RedisLimitException exception) {
        log.error("exception:{}",exception.getMessage());
        return ApiResponse.error(404, exception.getMessage());
    }

    @ExceptionHandler(value = SerializationException.class)
    public ApiResponse exceptionHandler(SerializationException exception) {
        log.error("exception:{},{}",exception.getMessage(),exception);
        return ApiResponse.error(404, exception.toString());
    }

}