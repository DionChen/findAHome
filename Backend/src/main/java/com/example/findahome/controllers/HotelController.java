package com.example.findahome.controllers;

import com.example.findahome.models.dto.HotelDto;
import com.example.findahome.models.po.Hotel;
import com.example.findahome.services.Impl.HotelServiceImpl;
import com.example.findahome.services.Impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelServiceImpl hotelService;

    @PostMapping
    @PreAuthorize("hasRole('HOTEL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody HotelDto hotelDto, @AuthenticationPrincipal UserDetailsImpl user) {
        Hotel newHotel = new Hotel(hotelDto.getName(), user.getId(), hotelDto.getAddress(), hotelDto.getRating(), hotelDto.getSummary(), hotelDto.getFacilities());
        return ResponseEntity.ok(hotelService.create(newHotel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HOTEL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<Hotel> updateHotelInformation(@Valid @RequestBody HotelDto hotelDto, @PathVariable Integer id, @AuthenticationPrincipal UserDetailsImpl user) {
        Hotel hotelData = new Hotel(hotelDto.getName(), user.getId(), hotelDto.getAddress(), hotelDto.getRating(), hotelDto.getSummary(), hotelDto.getFacilities());
        return ResponseEntity.ok(hotelService.update(id, hotelData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Integer id) {

        return ResponseEntity.ok(hotelService.findOneById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Hotel>> getHotelList(@RequestParam String keyword, @RequestParam String type, @RequestParam Integer num, @RequestParam Instant start, @RequestParam Instant end) {
        return ResponseEntity.ok(hotelService.findHotel(keyword, type, num, start, end));
    }
}
