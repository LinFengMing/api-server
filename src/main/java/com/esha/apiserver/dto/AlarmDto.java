package com.esha.apiserver.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlarmDto {

    @NotNull(message = "age不可為空")
    String text;

    @NotNull(message = "age不可為空")
    String numbers;

}
