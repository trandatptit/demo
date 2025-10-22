package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    @Query("select a from Asset a where a.room.id = :roomId and a.isActive = true")
    Page<Asset> findByRoomId(@Param("roomId") Long roomId, Pageable pageable);

    @Query("select count(a) > 0 from Asset a " +
            "where a.room.id = :roomId " +
            "and lower(a.name) = lower(:name) " +
            "and a.isActive = true")
    boolean existsByNameAndRoomId(@Param("name") String name,@Param("roomId")  Long roomId);
}
