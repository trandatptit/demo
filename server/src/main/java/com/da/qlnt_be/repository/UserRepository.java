package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByIdAndIsActiveTrue(Long id);

    Page<User> findAllByIsActiveTrueOrderById(Pageable pageable);

}
