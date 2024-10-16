package com.panduoma.pdmadmin.response;

/**
 * @author 潘多码(微信 : panduoma888)
 * @version 1.0.0
 * @description
 * @website www.panduoma.com
 * @copyright 公众号: 潘多码
 */


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公用返回状态码
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    /**
     * 用户名已存在
     */
    USERNAME_EXIST(1001, "用户名已存在"),
    /**
     * 用户名密码错误
     */
    USERNAME_USERPWD_ERROR(1002, "用户名密码错误"),
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(1003, "验证码错误"),
    /**
     * 生成验证码失败
     */
    CREATE_CAPTCHA_ERROR(2001, "生成验证码失败"),

    /**
     * 成功
     */
    SUCCESS(200, "操作成功！"),
    /**
     * 错误
     */
    ERROR(500, "操作失败！");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String desc;

}