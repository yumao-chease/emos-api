package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "查询报销分页记录表单")
public class SearchReimByPageForm {

    @Min(value = 1, message = "deptId不能小于1")
    @Schema(description = "部门ID")
    private Integer deptId;

    @Min(value = 1, message = "typeId不能小于1")
    @Schema(description = "类型ID")
    private Byte typeId;

    @Min(value = 1, message = "status不能小于1")
    @Schema(description = "状态")
    private Byte status;

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{1,10}$", message = "name内容不正确")
    @Schema(description = "姓名")
    private String name;

    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$", message = "startDate内容不正确")
    @Schema(description = "开始日期")
    private String startDate;

    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$", message = "sendDate内容不正确")
    @Schema(description = "截止日期")
    private String endDate;

    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    @Schema(description = "页数")
    private Integer page;

    @NotNull(message = "length不能为空")
    @Range(min = 10, max = 50, message = "length必须在10~50之间")
    @Schema(description = "每页记录数")
    private Integer length;
}
