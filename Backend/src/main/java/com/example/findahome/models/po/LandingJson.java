package com.example.findahome.models.po;

import lombok.Data;

import java.util.List;

@Data
public class LandingJson {

    private String title;

    private String bannerImg;

    private String newsTitle;

    private List<String> news;

    private String suggestedTitle;

    private List<Object> suggestion;

    public LandingJson(String title, String bannerImg, String newsTitle, List<String> news, String suggestedTitle, List<Object> suggestion) {
        this.title = title;
        this.bannerImg = bannerImg;
        this.newsTitle = newsTitle;
        this.news = news;
        this.suggestedTitle = suggestedTitle;
        this.suggestion = suggestion;
    }
}
