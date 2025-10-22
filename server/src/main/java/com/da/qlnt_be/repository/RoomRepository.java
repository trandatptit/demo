package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.House;
import com.da.qlnt_be.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("select r from Room r where r.house = :house and r.isActive = true")
    Page<Room> findByHouseAndIsActiveTrue(@Param("house") House house, Pageable pageable);

    @Query("select r from Room r where r.house = :house and r.status = :status and r.isActive = true")
    Page<Room> findByHouseAndStatusAndIsActiveTrue(@Param("house") House house, @Param("status") int status, Pageable pageable);

    @Query("select count(r) > 0 from Room r " +
            "where r.house.id = :houseId " +
            "and r.code = :code " +
            "and r.isActive = true")
    boolean existsByCodeAndHouseId(@Param("code") String code, @Param("houseId") Long houseId);
}
