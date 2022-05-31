package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Feather
 */
@Data
@Schema(description = "修改个人信息表单类")
public class UpdateUserInfoForm {

    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "电话格式不正确")
    @Schema(description = "电话")
    private String tel;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱")
    private String email;


}
