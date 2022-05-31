package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "查询任务分页列表表单")
public class SearchTaskByPageForm {

    @Pattern(regexp = "^[\\e4e00-\\u9fa5]{2,20}$", message = "creatorName内容不正确")
    private String creatorName;

    @Pattern(regexp = "^员工请假$|^会议申请$", message = "type内容不正确")
    private String type;

    @Pattern(regexp = "^[0-9a-zA-Z\\-]{36}$", message = "instanceId内容不正确")
    private String instanceId;

    @NotBlank(message = "status不能为空")
    @Pattern(regexp = "^待审批$|^已审批$|^已结束$", message = "status内容不正确")
    private String status;


    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    private Integer page;

    @NotNull(message = "length不能为空")
    @Range(min = 10, max = 100, message = "length必须在10~100之间")
    private Integer length;

}

