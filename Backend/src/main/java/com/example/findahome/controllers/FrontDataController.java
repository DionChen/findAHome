package com.example.findahome.controllers;

import com.example.findahome.models.po.FrontData;
import com.example.findahome.models.po.LandingJson;
import com.example.findahome.repository.FrontDataRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/front-data")
public class FrontDataController {

    @Autowired
    FrontDataRepository frontDataRepository;

    @GetMapping("/landing")
    public String LandingData() {
        FrontData landData = frontDataRepository.findAll().get(0);
        if(landData != null) {
            Gson gson = new Gson();
           return gson.toJson(landData.getLandData());
        }else
            return "";
    }
}
