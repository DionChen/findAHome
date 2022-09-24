package com.example.findahome.controllers;

import com.example.findahome.models.dto.AvailableRoomDto;
import com.example.findahome.models.dto.RoomDto;
import com.example.findahome.models.po.RoomType;
import com.example.findahome.services.Impl.RoomTypeServiceImpl;
import com.example.findahome.services.Impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/room")
public class RoomTypeController {

    @Autowired
    RoomTypeServiceImpl roomTypeService;


    //create room Type
    @PostMapping
    @PreAuthorize("hasRole('HOTEL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<RoomType> createRoomType(@Valid @RequestBody RoomDto roomDto) {
            return ResponseEntity.ok(roomTypeService.create(roomDto));
    }

    //update Room
    @PutMapping("{roomId}")
    @PreAuthorize("hasRole('HOTEL_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<RoomType> updateRoomType(@Valid @RequestBody RoomDto roomDto, @PathVariable Integer roomId) {
            return ResponseEntity.ok(roomTypeService.update(roomId, roomDto));
    }

    //get room by id
    @GetMapping("{roomId}")
    public ResponseEntity<RoomType> getRoom(@PathVariable Integer roomId) {
        return ResponseEntity.ok(roomTypeService.findById(roomId));
    }

    //get available room by hotelId
    @GetMapping("/available/{hotelId}")
    public ResponseEntity<List<RoomType>> getAvailableRoom(@PathVariable Integer hotelId, @RequestParam Instant startDate, @RequestParam Instant endDate) {

        return ResponseEntity.ok(roomTypeService.findAvailableByHotelId(hotelId, startDate, endDate));
    }

    //get room List by hotelId
    @GetMapping("/list/{hotelId}")
    public ResponseEntity<List<RoomType>> getRoomListByHotelId(@PathVariable Integer hotelId) {
        return ResponseEntity.ok(roomTypeService.findByHotel(hotelId));
    }
}
