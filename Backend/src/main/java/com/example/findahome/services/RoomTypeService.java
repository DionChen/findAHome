package com.example.findahome.services;

import com.example.findahome.models.dto.RoomDto;
import com.example.findahome.models.po.RoomType;

import java.util.List;

public interface RoomTypeService {

    RoomType create(RoomDto roomDto);

    List<RoomType> findAll();
}
