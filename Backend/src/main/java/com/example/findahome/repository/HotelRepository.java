package com.example.findahome.repository;

import com.example.findahome.models.po.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    List<Hotel> findByName(String name);

    List<Hotel> findByAddress(String address);

    @Query(nativeQuery = true, value = "select * from hotels h where h.rating > ?1")
    List<Hotel> findByGreaterRating(double rating);

    @Query(nativeQuery = true, value = "select * from hotels h where h.name = ?1 and h.address = ?2 and h.rating > ?3")
    List<Hotel> findByName(String name, String address, double rating);
}
