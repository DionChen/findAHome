package com.example.findahome.models.po;

import com.example.findahome.models.ERole;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
