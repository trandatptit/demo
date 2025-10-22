package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.TenantRequest;
import com.da.qlnt_be.dto.response.TenantResponse;
import com.da.qlnt_be.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TenantService {
    UserResponse createTenant(TenantRequest request);
    UserResponse updateTenant(Long id, TenantRequest request);
    UserResponse getTenantById(Long id);
    Page<?> getAllTenants(Pageable pageable);
    void softDeleteTenant(Long id);
}
