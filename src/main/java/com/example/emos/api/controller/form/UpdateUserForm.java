package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author Feather
 */
@Schema(description = "修改用户信息表单")
@Data
public class UpdateUserForm {

    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "用户ID不能小于1")
    @Schema(description = "用户ID")
    private Integer userId;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "用户名内容不正确")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "密码内容不正确")
    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "姓名内容不正确")
    @Schema(description = "姓名")
    private String name;

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^男$|^女$", message = "性别内容不正确")
    @Schema(description = "性别")
    private String sex;

    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "电话内容不正确")
    @Schema(description = "电话")
    private String tel;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱")
    private String email;

    @NotBlank(message = "入职日期日期不能为空")
    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$", message = "hiredate内容不正确")
    @Schema(description = "入职日期")
    private String hiredate;

    @NotEmpty(message = "角色不能为空")
    @Schema(description = "角色")
    private Integer[] role;

    @NotNull(message = "部门编号不能为空")
    @Min(value = 1, message = "部门编号不能小于1")
    @Schema(description = "部门")
    private Integer deptId;

}

