package com.example.findahome.models.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "hotels")
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private Double rating;

    /**
     * 飯店簡介
     */
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
     * 在考慮可能不需要多拆出個table
     */
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hotel_facilities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private Set<HotelFacility> facilities;

    public Hotel(String name, String address, Double rating, String summary) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.summary = summary;
    }

    public Hotel() {

    }
}
