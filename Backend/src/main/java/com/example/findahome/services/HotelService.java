package com.example.findahome.services;

import com.example.findahome.models.po.Hotel;

import java.util.List;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> findAll();
}
