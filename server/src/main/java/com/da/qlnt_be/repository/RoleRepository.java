package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
    Page<Role> findAllByIsActiveTrueOrderByName(Pageable pageable);
}
