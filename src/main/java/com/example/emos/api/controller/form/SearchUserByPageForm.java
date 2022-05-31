package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Feather
 */
@Data
@Schema(description = "查询用户分页记录表单")
public class SearchUserByPageForm {

    @NotNull(message = "当前页数不能为空")
    @Min(value = 1, message = "当前页数不能小于1")
    @Schema(description = "当前页数")
    private Integer pageIndex;

    @NotNull(message = "每页记录数不能为空")
    @Range(min = 10,max = 50,message = "每页记录数必须在10~50之间")
    @Schema(description = "每页记录数")
    private Integer pageSize;

    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,10}$",message = "名字内容不正确")
    @Schema(description = "姓名")
    private String name;

    @Pattern(regexp = "^男$|^女$",message = "性别内容不正确")
    @Schema(description = "性别")
    private String sex;

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,10}$",message = "角色内容不正确")
    @Schema(description = "角色")
    private String role;

    @Min(value = 1,message = "部门编号不能小于1")
    @Schema(description = "部门编号")
    private Integer deptId;

    @Min(value = 1,message = "状态不能小于1")
    @Schema(description = "状态")
    private Integer status;

}
