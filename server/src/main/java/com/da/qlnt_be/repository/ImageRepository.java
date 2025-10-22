package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where " +
            "i.entityId = :entityId " +
            "and i.entityType = :entityType " +
            "and i.imageType = :imageType " +
            "and i.isActive = true")
    List<Image> findImageActive(@Param("entityId") Long entityId,
                                @Param("entityType") String entityType,
                                @Param("imageType") Long imageType);

}
