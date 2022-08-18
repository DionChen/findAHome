package com.example.findahome.models.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "facilities")
@Data
public class HotelFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 設施名稱
     */
    private String name;
}
