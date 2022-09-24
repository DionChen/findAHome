package com.example.findahome.services.Impl;

import com.example.findahome.models.dto.RoomDto;
import com.example.findahome.models.exception.ApiBadRequestException;
import com.example.findahome.models.exception.ApiNotFoundException;
import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.RoomType;
import com.example.findahome.repository.HotelRepository;
import com.example.findahome.repository.RoomTypeRepository;
import com.example.findahome.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public RoomType create(RoomDto roomData) {
        Date now = new Date();
        Optional<Hotel> hotel = hotelRepository.findById(roomData.getHotelId());
        if (hotel.isPresent()) {
            RoomType updateRoom = new RoomType(hotel.get(), roomData.getRoomName(), roomData.getPrice(), roomData.getDiscount(), roomData.getArea(), roomData.getPetType(), roomData.getWindows(), now, now);
            return roomTypeRepository.save(updateRoom);
        } else
            throw new ApiNotFoundException("Can't find Hotel id : " + roomData.getHotelId());
    }

    @Override
    public List<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    public RoomType findAvailableByRoom(Integer roomId, Instant bookingStart, Instant bookingEnd) {
        if (roomTypeRepository.findById(roomId).isPresent()) {
            if (bookingEnd.isAfter(bookingStart)) {
                return roomTypeRepository.findAvailableByRoom(roomId, bookingStart, bookingEnd);
            } else {
                throw new ApiBadRequestException("Booking Date Error. Please check your select date.");
            }
        } else
            throw new ApiNotFoundException("Can't find Room Id: " + roomId);
    }

    public List<RoomType> findAvailableByHotelId(Integer hotelId, Instant bookingStart, Instant bookingEnd) {
        if(hotelRepository.findById(hotelId).isPresent()) {
            if(bookingEnd.isAfter(bookingStart)) {
                return roomTypeRepository.findAvailableByHotelId(hotelId, bookingStart, bookingEnd);
            }else {
                throw new ApiBadRequestException("Wrong date selected. Please check your select date.");
            }
        }else
            throw new ApiNotFoundException("Can't find hotel Id: " + hotelId);
    }


    public List<RoomType> findByHotel(Integer hotelId) {
        if (hotelRepository.findById(hotelId).isPresent()) {
            return roomTypeRepository.findByHotel(hotelRepository.findById(hotelId).get());
        } else
            throw new ApiNotFoundException("Can't find Hotel Id : " + hotelId);
    }

    public RoomType update(Integer roomId, RoomDto roomData) {
        Date now = new Date();
        Optional<RoomType> room = roomTypeRepository.findById(roomId);
        if (room.isPresent()) {
            room.get().setRoomName(roomData.getRoomName());
            room.get().setPrice(roomData.getPrice());
            room.get().setDiscount(roomData.getDiscount());
            room.get().setArea(roomData.getArea());
            room.get().setPetType(roomData.getPetType());
            room.get().setWindows(roomData.getWindows());
            room.get().setUpdatedTime(now);
            return roomTypeRepository.save(room.get());
        } else
            throw new ApiNotFoundException("Can't find Room Id: " + roomId);
    }

    public RoomType findById(Integer roomId) {
        Optional<RoomType> room = roomTypeRepository.findById(roomId);
        if (room.isPresent()) {
            return room.get();
        } else
            throw new ApiNotFoundException("Can't find Room Id: " + roomId);
    }
}
