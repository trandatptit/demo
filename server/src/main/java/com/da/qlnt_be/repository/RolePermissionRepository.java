package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Permission;
import com.da.qlnt_be.model.Role;
import com.da.qlnt_be.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolePermissionRepository  extends JpaRepository<RolePermission, Long> {
    boolean existsByRoleAndPermission(Role role, Permission permission);
    Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission);
}
