package com.da.qlnt_be.services.impl;


import com.da.qlnt_be.dto.request.PermissionRequest;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.model.Permission;
import com.da.qlnt_be.model.Role;
import com.da.qlnt_be.model.RolePermission;
import com.da.qlnt_be.repository.PermissionRepository;
import com.da.qlnt_be.repository.RolePermissionRepository;
import com.da.qlnt_be.repository.RoleRepository;
import com.da.qlnt_be.services.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    RolePermissionRepository rolePermissionRepository;

    @Override
    @PreAuthorize("hasAnyAuthority('PERMISSION_CREATE')") // chỉ user có permission này mới tạo/sửa
    public Long createOrUpdatePermission(PermissionRequest request) {
        Long id = request.getId();
        if(id == null){
            id = 0L;
        }
        Permission permission = permissionRepository.findById(id).orElse(new Permission());
        if(permissionRepository.existsByCodeAndIsActiveTrue(request.getCode()) && permission.getId() == null) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        permission.setCode(request.getCode());
        permission.setDescription(request.getDescription());
        permissionRepository.save(permission);
        return permission.getId();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PERMISSION_READ')")
    public Permission getPermissionById(Long id) {
        return permissionRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PERMISSION_READ')")
    public Page<Permission> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAllByIsActiveTrue(pageable);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PERMISSION_DELETE')")
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionRepository.delete(permission);
    }

    /**
     * Gán permission cho role
     * @param roleId
     * @param permissionId
     */
    @Override
    @PreAuthorize("hasAnyAuthority('PERMISSION_ASSIGN')")
    public void assignPermissionToRole(Long roleId, Long permissionId) {
        RolePermission rolePermission = new RolePermission();
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        if(rolePermissionRepository.existsByRoleAndPermission(role, permission)) {
            throw new AppException(ErrorCode.PERMISSION_ASSIGNED_TO_ROLE);
        }
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        rolePermissionRepository.save(rolePermission);
    }

    /**
     * Gỡ permission khỏi role
     * @param roleId
     * @param permissionId
     */
    @Override
    @PreAuthorize("hasAnyAuthority('PERMISSION_ASSIGN')")
    public void removePermissionFromRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        Permission permission = permissionRepository.findByIdAndIsActiveTrue(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        RolePermission rolePermission = rolePermissionRepository.findByRoleAndPermission(role, permission)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_ASSIGNED_TO_ROLE));
        rolePermissionRepository.delete(rolePermission);
    }
}

