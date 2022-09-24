package com.example.findahome.models.dto;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

@Data
public class RoomDto {

    /**
     * Hotel Id
     */
    @NotNull
    private Integer HotelId;

    /**
     * 房間名稱
     */
    @NotBlank
    private String roomName;

    /**
     * 房間價格
     */
    @NotNull
    private Double price;

    /**
     * 房間預設折扣
     */
    @NotNull
    private Double discount;

    /**
     * 房間大小
     */
    @NotNull
    private Integer area;

    /**
     * 可照顧的寵物類別(Key) 數量(value)
     */
    @NotNull
    private Map<String, Integer> petType;

    /**
     * 窗戶數目
     */
    @NotNull
    private Integer windows;

}
