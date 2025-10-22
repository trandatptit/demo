package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Permission;
import com.da.qlnt_be.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    boolean existsByCodeAndIsActiveTrue(String code);
    Optional<Permission> findByCodeAndIsActiveTrue(String code);
    Page<Permission> findAllByIsActiveTrue(Pageable pageable);
    List<Permission> findAllByIsActiveTrue();
    Optional<Permission> findByIdAndIsActiveTrue(Long id);
    Optional<Permission> findByCode(String code);
}
