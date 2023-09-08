package com.example.findahome.models.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "hotels")
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 飯店建立者
     */
    @NotNull
    private Long userId;

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
    @Type()
    private String summary;

    /**
     * 飯店房型
     */
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "hotel",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<RoomType> roomTypes;

    /**
     * 飯店設施
     */
    @NotBlank
    private String facilities;


    public Hotel(String name, Long userId, String address, Double rating, String summary ,String facilities) {
        this.name = name;
        this.userId = userId;
        this.address = address;
        this.rating = rating;
        this.summary = summary;
        this.facilities = facilities;
    }

    public Hotel() {

    }
}
