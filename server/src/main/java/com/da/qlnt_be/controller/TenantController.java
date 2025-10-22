package com.da.qlnt_be.controller;

import com.da.qlnt_be.dto.request.TenantRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.dto.response.TenantResponse;
import com.da.qlnt_be.services.TenantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TenantController {
    TenantService tenantService;

    @GetMapping("/{id}")
    public ApiResponse<?> tenant(@PathVariable Long id) {
        return ApiResponse.success(tenantService.getTenantById(id));
    }

    @GetMapping
    public ApiResponse<?> getAllTenants(Pageable pageable) {
        return ApiResponse.success(tenantService.getAllTenants(pageable));
    }

    @PostMapping
    public ApiResponse<?> createTenant(@RequestBody TenantRequest request) {
        return ApiResponse.success(tenantService.createTenant(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateTenant(@PathVariable Long id, @RequestBody TenantRequest request) {
        return ApiResponse.success(tenantService.updateTenant(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> softDeleteTenant(@PathVariable Long id) {
        ApiResponse<Void> response = new ApiResponse<>();
        tenantService.softDeleteTenant(id);
        return response;
    }
}
