package com.example.emos.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author Feather
 */
@Data
@Schema(description = "删除用户")
public class DeleteUserByIdsForm {

    @NotEmpty(message = "ids不能为空")
    @Schema(description = "用户ID")
    private Integer[] ids;

}
