package com.example.findahome.models.po;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "orders", indexes = @Index(name = "ri_index", columnList = "roomId"))
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 使用者ID
     */
    @NotNull
    private Long userId;

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
    @NotNull
    private Instant startDate;

    /**
     * 訂購日期（迄）
     */
    @NotNull
    private Instant endDate;

    /**
     * 訂單狀態
     */
    @NotNull
    private Integer status;

    /**
     * 訂單費用
     */
    @NotNull
    private Integer cost;

    /**
     * 寵物種類
     */
    @NotBlank
    private String petType;

    /**
     * 寵物數量
     */
    @NotNull
    private Integer petNum;

    /**
     * 訂單建立日期
     */
    @NotNull
    private Date createdTime;
    /**
     * 更新日期
     */
    @NotNull
    private Date updatedTime;

    public Order(Long userId, Integer roomId, String name, String phone, Instant startDate, Instant endDate, Integer status, Integer cost,String petType, Integer petNum, Date createdTime, Date updatedTime) {
        this.userId = userId;
        this.roomId = roomId;
        this.name = name;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.cost = cost;
        this.petType = petType;
        this.petNum = petNum;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
    public Order() {}
}
