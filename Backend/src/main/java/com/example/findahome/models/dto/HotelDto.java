package com.example.findahome.models.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class HotelDto {

    /**
     * 飯店名稱
     */
    @NotBlank
    private String name;

    /**
     * 飯店地址
     */
    @NotBlank
    private String address;

    /**
     * 飯店評等
     */
    @NotNull
    private Double rating;

    /**
     * 飯店簡介
     */
    @NotBlank
    private String summary;

    /**
     * 飯店設施
     */
    @NotBlank
    private String facilities;

}
