package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "会议签到表单")
public class UpdateMeetingPresentForm {
    @NotNull(message = "meetingId不能为空")
    @Min(value = 1, message = "meetingId不能小于1")
    private Integer meetingId;
}
