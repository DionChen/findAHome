package com.example.findahome.services.Impl;

import com.example.findahome.models.exception.ApiNotFoundException;
import com.example.findahome.models.po.Hotel;
import com.example.findahome.repository.HotelRepository;
import com.example.findahome.repository.RoomTypeRepository;
import com.example.findahome.repository.UserRepository;
import com.example.findahome.services.HotelService;
import com.example.findahome.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    UserRepository userRepository;

    @Override
    public Hotel create(Hotel hotel) {
        if (userRepository.findById(hotel.getUserId()).isPresent()) {
            return hotelRepository.save(hotel);
        } else
            throw new ApiNotFoundException("Can't find User " + hotel.getUserId());
    }

    public Hotel update(Integer hotelId, Hotel hotelData) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            hotel.get().setName(hotelData.getName());
            hotel.get().setAddress(hotelData.getAddress());
            hotel.get().setRating(hotelData.getRating());
            hotel.get().setSummary(hotelData.getSummary());
            hotel.get().setFacilities(hotelData.getFacilities());
            return hotelRepository.save(hotel.get());
        } else
            throw new ApiNotFoundException("Can't find Hotel " + hotelId);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    //可能要檢查一下進來的值
    public List<Hotel> findHotel(String keyword, String petType, Integer petNum, Instant startDate, Instant endDate) {
        return hotelRepository.findAvailableHotel(keyword, petType, petNum, startDate, endDate);
    }

    public Hotel findOneById(Integer hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            return hotel.get();
        }
        return null;
    }

}
