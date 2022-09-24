package com.example.findahome.util.converter;

import com.example.findahome.models.po.LandingJson;
import com.google.gson.Gson;

import javax.persistence.AttributeConverter;

public class LandingConverter implements AttributeConverter<LandingJson, String> {

    @Override
    public String convertToDatabaseColumn(LandingJson attribute) {
        Gson gson = new Gson();
        return gson.toJson(attribute);
    }

    @Override
    public LandingJson convertToEntityAttribute(String dbData) {
        Gson gson = new Gson();
        return gson.fromJson(dbData, LandingJson.class);
    }
}
