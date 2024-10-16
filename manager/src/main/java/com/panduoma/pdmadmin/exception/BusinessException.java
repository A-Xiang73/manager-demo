package com.panduoma.pdmadmin.exception;

import com.panduoma.pdmadmin.response.ResponseCode;
import lombok.Data;

/**
 * @author 潘多码(微信 : panduoma888)
 * @version 1.0.0
 * @description  业务异常类
 * @website www.panduoma.com
 * @copyright 公众号: 潘多码
 */
@Data
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public BusinessException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getDesc();
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}