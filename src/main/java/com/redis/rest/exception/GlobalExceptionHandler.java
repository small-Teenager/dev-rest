package com.redis.rest.exception;

import com.redis.rest.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yaodong zhang
 * @create 2022/12/19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截valid参数校验返回的异常，并转化成基本的返回样式
     * @param exception
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse exceptionHandler(MethodArgumentNotValidException exception) {
        log.error("exception:{}",exception.getMessage());
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
        return ApiResponse.error(404, message);
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