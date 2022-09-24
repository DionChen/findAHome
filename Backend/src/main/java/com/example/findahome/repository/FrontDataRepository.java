package com.example.findahome.repository;

import com.example.findahome.models.po.FrontData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontDataRepository extends JpaRepository<FrontData, Integer> {

}
