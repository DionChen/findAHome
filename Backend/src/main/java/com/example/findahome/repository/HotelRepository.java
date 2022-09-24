package com.example.findahome.repository;

import com.example.findahome.models.po.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>, JpaSpecificationExecutor<Hotel> {

    List<Hotel> findByName(String name);

    List<Hotel> findByAddress(String address);

    @Query(nativeQuery = true, value = "select * from hotels h where h.rating > ?1")
    List<Hotel> findByGreaterRating(double rating);

    @Query(nativeQuery = true, value = "select * from hotels h where h.name = ?1 and h.address = ?2 and h.rating > ?3")
    List<Hotel> findByName(String name, String address, double rating);

    //    @Query(nativeQuery = true, value = "select * from hotels h inner JOIN room_types r on h.room_types = r.id " +
//            "inner JOIN orders o on r.hotel_id  = o.id where h.name like ?1 or h.address like ?1 and " +
//            "not exists ( ?2 between o.start_date and" +
//            " o.end_date or ?3 between o.start_date and o.end_date)")
//    @Query(nativeQuery = true, value = "select * from hotels h where h.id in " +
//            "(select * from hotels h where  h.name like ?1 or h.address like ?1 " +
//            "and (select * from room_types r where r.aaa = ?2 and r.bbb <= ?3) " +
//            "and (select * from orders o where not exists ?2 between o.start_date and o.end_date or ?3 between o.start_date and o.end_date))")
    @Query(nativeQuery = true, value = "select * from hotels h where h.name like CONCAT('%', ?1, '%') or h.address like CONCAT('%', ?1, '%')" +
            " and h.id in (select hotel_id from room_types r inner join room_type_pet_type t on r.id = t.room_type_id where t.pet_type_key = ?2 and t.pet_type >= ?3 " +
            " and r.id in (select room_id from orders o where ?4 not between o.start_date and o.end_date or ?5 not between o.start_date and o.end_date ))")
    List<Hotel> findAvailableHotel(String keyword, String petType, Integer petNum, Instant startDate, Instant endDate);
}
