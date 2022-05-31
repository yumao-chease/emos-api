package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "添加会议表单")
public class InsertMeetingForm {

    @NotBlank(message = "title不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,30}$", message = "title内容不正确")
    @Schema(description = "主题")
    private String title;

    @NotBlank(message = "date不能为空")
    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$", message = "date内容不正确")
    @Schema(description = "日期")
    private String date;

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,20}$", message = "place内容不正确")
    @Schema(description = "会议地点")
    private String place;

    @NotBlank(message = "start不能为空")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):(00|30)$", message = "start内容不正确")
    @Schema(description = "起始时间")
    private String start;

    @NotBlank(message = "end不能为空")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):(00|30)$", message = "end内容不正确")
    @Schema(description = "结束时间")
    private String end;

    @NotNull(message = "type不能为空")
    @Range(min = 1, max = 2, message = "type内容不正确")
    @Schema(description = "会议类型")
    private Byte type;

    @NotBlank(message = "members不能为空")
    @Schema(description = "参会人")
    private String members;

    @NotBlank(message = "desc不能为空")
    @Length(min = 1, max = 200)
    @Schema(description = "内容")
    private String desc;
}
