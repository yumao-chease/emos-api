package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Feather
 */
@Data
@Schema(description = "修改密码表单类")
public class UpdatePasswordForm {

    @NotBlank(message = "原密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$",message = "原密码格式不正确")
    @Schema(description = "原密码")
    private String password;

    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$",message = "新密码格式不正确")
    @Schema(description = "新密码")
    private String newPassword;


}
