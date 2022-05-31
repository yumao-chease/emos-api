package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "添加会议室表单")
public class InsertMeetingRoomForm {
    @NotBlank(message = "name不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,20}$",message = "name内容不正确")
    @Schema(description = "会议室名称")
    private String name;

    @NotNull(message = "max不能为空")
    @Range(min = 1, max = 99999,message = "max必须在1~99999之间")
    @Schema(description = "人数上限")
    private String max;

    @Length(max = 20,message = "desc不能超过20个字符")
    @Schema(description = "备注")
    private String desc;

    @Schema(description = "状态")
    private Boolean status;
}
