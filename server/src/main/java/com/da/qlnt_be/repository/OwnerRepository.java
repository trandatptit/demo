package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Page<Owner> findAllByIsActiveTrue(Pageable pageable);
}
