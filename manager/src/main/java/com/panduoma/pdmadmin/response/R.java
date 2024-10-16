package com.panduoma.pdmadmin.response;

import lombok.Data;
import lombok.Getter;

/**
 * @author 潘多码(微信 : panduoma888)
 * @version 1.0.0
 * @description
 * @website www.panduoma.com
 * @copyright 公众号: 潘多码
 */
@Getter
public class R<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 描述
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T data;

    private R(Integer code) {
        this.code = code;
    }

    private R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static <T> R<T> success() {
        return new R<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> R<T> success(String message) {
        return new R<T>(ResponseCode.SUCCESS.getCode(), message);
    }

    public static <T> R<T> data(T data) {
        return new R<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), data);
    }

    public static <T> R<T> fail() {
        return new R<T>(ResponseCode.ERROR.getCode());
    }

    public static <T> R<T> fail(String message) {
        return new R<T>(ResponseCode.ERROR.getCode(), message);
    }

    public static <T> R<T> fail(Integer code, String message) {
        return new R<T>(code, message);
    }

    public static <T> R<T> fail(ResponseCode responseCode) {
        return new R<T>(responseCode.getCode(), responseCode.getDesc());
    }

}