package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.TenantRequest;
import com.da.qlnt_be.dto.response.OwnerResponse;
import com.da.qlnt_be.dto.response.TenantResponse;
import com.da.qlnt_be.dto.response.UserResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.mapper.UserMapper;
import com.da.qlnt_be.model.Owner;
import com.da.qlnt_be.model.Tenant;
import com.da.qlnt_be.repository.TenantRepository;
import com.da.qlnt_be.services.TenantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TenantServiceImpl implements TenantService {
    TenantRepository tenantRepository;

    /**
     * Tạo mới khách thuê (Tenant)
     * @param request dữ liệu gửi từ client
     */
    @Override
    public UserResponse createTenant(TenantRequest request) {
        // Khởi tạo entity Tenant
        Tenant tenant = new Tenant();
        tenant.setUsername(request.getName());
        tenant.setEmail(request.getEmail());
        tenant.setAddress(request.getAddress());
        tenant.setPhoneNumber(request.getPhone());
        tenant.setPassword(request.getPassword());
        tenant.setDob(request.getDob());
        tenant.setRepresentative(request.getRepresentative());
        tenant.setIsActive(true);
        tenant.setCreatedAt(LocalDateTime.now());

        tenantRepository.save(tenant);

        return UserMapper.toResponse(tenant);
    }

    /**
     * Cập nhật thông tin khách thuê
     * @param id id của Tenant cần cập nhật
     * @param request dữ liệu mới từ client
     */
    @Override
    public UserResponse updateTenant(Long id, TenantRequest request) {
        // Tìm thông tin khách thuê trong DB, nếu không có thì ném exception
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TENANT_NOT_EXISTED));
        tenant.setUsername(request.getName());
        tenant.setAddress(request.getAddress());
        tenant.setPhoneNumber(request.getPhone());
        tenant.setDob(request.getDob());
        tenant.setRepresentative(request.getRepresentative());
        tenant.setUpdatedAt(LocalDateTime.now());
        tenantRepository.save(tenant);
        return UserMapper.toResponse(tenant);
    }


    /**
     * Lấy thông tin chi tiết 1 khách thuê theo id
     */
    @Override
    @PreAuthorize("hasAnyAuthority('TENANT_READ')")
    public UserResponse getTenantById(Long id) {
        // Tìm thông tin khách thuê trong DB, nếu không có thì ném exception
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TENANT_NOT_EXISTED));
        return UserMapper.toResponse(tenant);
    }

    /**
     * Lấy danh sách tất cả khách thuê đang hoạt động (isActive = true)
     */
    @Override
    @PreAuthorize("hasAnyAuthority('TENANT_READ')")
    public Page<?> getAllTenants(Pageable pageable) {
        Page<Tenant> tenants = tenantRepository.findAllByIsActiveTrue(pageable);
        return tenants.map(UserMapper::toResponse);
    }

    /**
     * Xóa mềm khách thuê: chỉ set isActive = false thay vì xóa khỏi DB
     */
    @Override
    @PreAuthorize("hasAnyAuthority('TENANT_DELETE')")
    public void softDeleteTenant(Long id) {
        // Tìm thông tin khách thuê trong DB, nếu không có thì ném exception
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TENANT_NOT_EXISTED));
        tenant.setIsActive(false);
        tenantRepository.save(tenant);
    }

//    /**
//     * Hàm tiện ích để map dữ liệu từ Entity -> Response DTO
//     */
//    private TenantResponse mapToResponse(Tenant tenant) {
//        TenantResponse res = new TenantResponse();
//        res.setId(tenant.getId());
//        res.setName(tenant.getUsername());
//        res.setEmail(tenant.getEmail());
//        res.setAddress(tenant.getAddress());
//        res.setPhone(tenant.getPhoneNumber());
//        res.setDob(tenant.getDob());
//        res.setRepresentative(tenant.getRepresentative());
//        res.setActive(tenant.getIsActive());
//        res.setCreatedAt(tenant.getCreatedAt());
//        res.setUpdatedAt(tenant.getUpdatedAt());
//        return res;
//    }
}
