package com.example.findahome.models.po;

import com.example.findahome.util.converter.LandingConverter;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "frontDatas")
@Data
public class FrontData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 首頁 json 資料
     */
    @Convert(converter = LandingConverter.class)
    @Column(columnDefinition = "jsonb")
    private LandingJson landData;



    public FrontData(LandingJson landJsonData) {
        this.landData = landJsonData;
    }

    public FrontData() {

    }
}
