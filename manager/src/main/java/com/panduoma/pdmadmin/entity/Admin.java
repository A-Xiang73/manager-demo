package com.panduoma.pdmadmin.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * @author 潘多码(微信 : panduoma888)
 * @version 1.0.0
 * @description
 * @website www.panduoma.com
 * @copyright 公众号: 潘多码
 */
@Data
@TableName("admin")
@Schema(description = "管理员实体类")
public class Admin {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;
    /**
     * 密码
     */
    @Schema(description = "密码")
    private String userpwd;
    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;
    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;
    /**
     * 电话
     */
    @Schema(description = "电话")
    private String tel;
    /**
     * 头像
     */
    @Schema(description = "头像")
    private String headurl;

    /**
     * 登录token
     */
    @Schema(description = "登录token")
    @TableField(exist = false)
    private String token;

    /**
     * 验证码id
     */
    @Schema(description = "验证码ID")
    @TableField(exist = false)
    private String captchaId;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @TableField(exist = false)
    private String captchaCode;
}
