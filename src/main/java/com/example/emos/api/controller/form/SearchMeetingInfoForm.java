package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "查询会议信息表单")
public class SearchMeetingInfoForm {
    @NotNull(message = "id不能为空")
    @Min(value = 1,message = "id不能小于1")
    @Schema(description = "会议ID")
    private Long id;

    @NotNull(message = "status不能为空")
    @Range(min = 1, max = 5,message = "status必须在1~5之间")
    @Schema(description = "状态")
    private Short status;
}
