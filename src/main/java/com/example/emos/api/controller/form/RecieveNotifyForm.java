package com.example.emos.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RecieveNotifyForm {
    @NotBlank
    private String processId;
    @NotBlank
    private String uuid;
    @NotBlank
    private String result;
}

