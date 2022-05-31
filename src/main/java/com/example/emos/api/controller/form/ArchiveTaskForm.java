package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "归档任务表单")
public class ArchiveTaskForm {
    @NotBlank(message = "taskId不能为空")
    @Schema(description = "任务ID")
    private String taskId;

    @NotBlank(message = "files不能为空")
    @Schema(description = "归档文件JSON数组")
    private String files;

}

