package com.example.findahome.models.dao;

import com.example.findahome.models.po.Hotel;
import com.example.findahome.models.po.Order;
import com.example.findahome.models.po.RoomType;
import com.example.findahome.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class HotelDao {

//    @PersistenceContext
//    private EntityManager em;
//
//    @Autowired
//    HotelRepository hotelRepository;
//
//    public  List<Hotel> filterHotelByNameOrAddressAndTimeAndPetType(String keyword, Instant startDate, Instant endDate,  Map<String, Integer> petType) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Hotel> cq = cb.createQuery(Hotel.class);
//        Root<Hotel> root = cq.from(Hotel.class);
//            // hotel.address like %keyword%  or hotel.name like %keyword%
//            //startDate between order.startDate and order.endDate or endDate between order.startDate and order.endDate
//            //room_type_pet_type.key = petType. and room_type_pet_type.pet_type = petType.get()
//            List<Predicate> list = new ArrayList<>();
//            Join<Hotel, RoomType> roomTypes = root.join("room_types");
//            Join<RoomType, Order> orders = roomTypes.join("orders");
//                cb.conjunction()
//            list.add(cb.equal(roomTypes.get("name"),keyword));
//            list.add(roomTypes.equals(roomTypes.get("petType",Map.class)))
//
//    }

}
