package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "查询某个会议室一周会议表单")
public class SearchOfflineMeetingInWeekForm {

    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$", message = "date内容不正确")
    @Schema(description = "日期")
    private String date;

    @NotBlank(message = "mold不能空")
    @Pattern(regexp = "^全部会议$|^我的会议$", message = "mold内容不正确")
    @Schema(description = "模式")
    private String mold;

    @NotBlank(message = "name不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,20}$", message = "name内容不正确")
    @Schema(description = "会议室名称")
    private String name;

}
