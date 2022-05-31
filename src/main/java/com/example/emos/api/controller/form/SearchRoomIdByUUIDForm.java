package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "查询在线会议室房间ID")
public class SearchRoomIdByUUIDForm {

    @NotBlank(message = "uuid不能为空")
    @Schema(description = "uuid")
    private String uuid;
}

