package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Feather
 */
@Data
@Schema(description = "登录表单类")
public class LoginForm {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$",message = "用户名内容不正确")
    @Schema(description = "用户名")
    private String username;


    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$",message = "密码内容不正确")
    @Schema(description = "密码")
    private String password;

}
