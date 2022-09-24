package com.example.findahome.repository;

import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    List<RoomType> findByRoomName(String roomName);

    List<RoomType> findByHotel(Hotel hotel);

    @Query(nativeQuery = true, value = "select * from room_types r where r.id = ?1 and not exists " +
            "(select 1 from orders o where o.room_id = ?1 " +
            "and ( ?2 between o.start_date and o.end_date or ?3 between o.start_date and o.end_date) for update)")
    RoomType findAvailableByRoom(Integer roomId, Instant bookingStart, Instant bookingEnd);

    @Query(nativeQuery = true, value = "select * from room_types r where r.hotel_id = ?1 and " +
    "r.id in (select room_id from orders o where ?2 not between o.start_date and o.end_date or ?3 not between o.start_date and o.end_date)")
    List<RoomType> findAvailableByHotelId(Integer hotelId, Instant startDate, Instant endDate);
}
