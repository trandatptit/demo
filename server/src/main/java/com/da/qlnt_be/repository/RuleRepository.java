package com.da.qlnt_be.repository;

import com.da.qlnt_be.model.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
    Page<Rule> findAll(Pageable pageable);
    boolean existsByNameIgnoreCase(String name);
}
