package com.example.findahome.po;

import com.example.findahome.models.po.FrontData;
import com.example.findahome.models.po.LandingJson;
import com.example.findahome.repository.FrontDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FrontDataTests {

    @Autowired
    FrontDataRepository frontDataRepository;

    /**
     * create landing data
     */
    @Test
    public void createLandingJson() {
        List<String> news = new ArrayList<>();
        news.add("news1");
        news.add("news2");
        List<String> sug = new ArrayList<>();
        sug.add("sug1");
        sug.add("sug2");
        List<String> suggestion = new ArrayList<>();
        LandingJson newJson = new LandingJson("testJson","http://test123.com.tw", "newsTitle", news,"Sug Title",sug);
        FrontData newData = new FrontData(newJson);
        frontDataRepository.save(newData);
    }
}
