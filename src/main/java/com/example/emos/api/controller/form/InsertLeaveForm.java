package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "添加请假记录表单")
public class InsertLeaveForm {

    @NotBlank(message = "reason不能为空")
    @Length(max = 200,message = "reason不能超过200字符")
    private String reason;

    @NotBlank(message = "start不能为空")
    @Schema(description = "起始时间")
    private String start;

    @NotBlank(message = "end不能为空")
    @Schema(description = "结束时间")
    private String end;

    @NotNull(message = "type不能为空")
    @Range(min = 1, max = 2, message = "type内容不正确")
    @Schema(description = "请假类型")
    private Byte type;
}
