package com.example.findahome.models.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class AvailableRoomDto {

    private Instant bookingStartDate;
    
    private Instant bookingEndDate;

}
