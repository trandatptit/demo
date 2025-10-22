package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Role;
import com.da.qlnt_be.model.User;
import com.da.qlnt_be.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);
    Optional<UserRole> findByUserAndRole(User userId, Role roleId);
    boolean existsByUserAndRole(User user, Role role);
}
