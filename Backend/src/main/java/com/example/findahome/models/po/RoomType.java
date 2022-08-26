package com.example.findahome.models.po;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "roomTypes")
@Data
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Hotel Id
     */

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    /**
     * 房間名稱
     */
    private String roomName;

    /**
     * 房間價格
     */
    private Double price;

    /**
     * 房間預設折扣
     */
    private Double discount;

    /**
     * 房間大小
     */
    private Integer area;

    /**
     * 可照顧的寵物類別(Key) 數量(value)
     */
    @ElementCollection
    private Map<String, Integer> petType;

    /**
     * 窗戶數目
     */
    private Integer windows;

    private Date createdTime;

    private Date updatedTime;

    public RoomType(Hotel hotel, String roomName, Double price, Double discount, Integer area, Map<String, Integer> petType, Integer window, Date createdTime, Date updatedTime) {
        this.hotel = hotel;
        this.roomName = roomName;
        this.price = price;
        this.discount = discount;
        this.area = area;
        this.petType = petType;
        this.windows = window;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

}
