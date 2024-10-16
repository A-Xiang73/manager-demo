package com.panduoma.pdmadmin.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.panduoma.pdmadmin.response.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 潘多码(微信 : panduoma888)
 * @version 1.0.0
 * @description  统一异常处理
 * @website www.panduoma.com
 * @copyright 公众号: 潘多码
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    /**
     * 业务异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public R businessExceptionHandler(BusinessException e) {
        return R.fail(e.getCode(), e.getMessage());
    }

    /**
     * 其他异常
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public R exceptionHandler(Exception e) {
        return R.fail(e.getMessage());
    }

    /**
     * 未登录异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotLoginException.class)
    public R loginExceptionHandler(NotLoginException e) {
        return R.fail(e.getCode(), e.getMessage());
    }
}