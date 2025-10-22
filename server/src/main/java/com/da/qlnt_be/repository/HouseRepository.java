package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.House;
import com.da.qlnt_be.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
    Page<House> findByOwner(Owner owner, Pageable pageable);
}
