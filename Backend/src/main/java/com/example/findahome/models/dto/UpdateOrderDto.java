package com.example.findahome.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateOrderDto {

    /**
     * 訂購者姓名
     */
    @NotBlank
    private String name;

    /**
     * 訂購者電話
     */
    @NotBlank
    private String phone;
}
