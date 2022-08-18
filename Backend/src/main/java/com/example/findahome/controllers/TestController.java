package com.example.findahome.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess(){
        return "Public Content.";
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('HOTEL_ADMIN') or hasRole('ADMIN')")
    public String userAccess(){
        return "User content.";
    }

    @GetMapping("/hotel-admin")
    @PreAuthorize("hasRole('HOTEL_ADMIN')")
    public String hotelAdminAccess() {
        return "Hotel admin content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "admin content.";
    }

}
