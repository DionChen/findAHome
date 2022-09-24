package com.example.findahome.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserOrderDto {

    /**
     * 房型ID
     */
    @NotNull
    private Integer roomId;

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

    /**
     * 訂購日期（起）
     */
    @NotBlank
    private String startDate;

    /**
     * 訂購日期（迄）
     */
    @NotBlank
    private String endDate;

    /**
     * 訂單費用
     */
    @NotNull
    private Integer cost;
}
