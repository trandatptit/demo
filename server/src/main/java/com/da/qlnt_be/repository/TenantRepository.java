package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    // Chỉ lấy những tenant còn hoạt động (isActive = true)
//    @Query("SELECT t FROM Tenant t WHERE t.isActive = true")
//    List<Tenant> findAllTenantActive();
    Page<Tenant> findAllByIsActiveTrue(Pageable pageable);
}
