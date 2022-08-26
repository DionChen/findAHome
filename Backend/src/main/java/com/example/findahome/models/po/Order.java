package com.example.findahome.models.po;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 訂單序號
     */
    @NotBlank
    private String seqNum;

    /**
     * 使用者ID
     */
    @NotBlank
    private Long userId;

    /**
     * 房型ID
     */
    @NotBlank
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
    private Instant startDate;

    /**
     * 訂購日期（迄）
     */
    @NotBlank
    private Instant endDate;

    /**
     * 訂單狀態
     */
    @NotBlank
    private Integer status;

    /**
     * 訂單費用
     */
    @NotBlank
    private Integer cost;

    /**
     * 訂單建立日期
     */
    @NotBlank
    private Date createdTime;

    /**
     * 更新日期
     */
    @NotBlank
    private Date updatedTime;

    public Order(String seqNum, Long userId, Integer roomId, String name, String phone, Instant startDate, Instant endDate, Integer status, Integer cost, Date createdTime, Date updatedTime) {
        this.seqNum = seqNum;
        this.userId = userId;
        this.roomId = roomId;
        this.name = name;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.cost = cost;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
    public Order() {}
}
