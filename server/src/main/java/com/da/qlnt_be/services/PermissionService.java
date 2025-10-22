package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.PermissionRequest;
import com.da.qlnt_be.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermissionService {
    Long createOrUpdatePermission(PermissionRequest request);
    Permission getPermissionById(Long id);
    Page<Permission> getAllPermissions(Pageable pageable);
    void deletePermission(Long id);
    void assignPermissionToRole(Long roleId, Long permissionId);
    void removePermissionFromRole(Long roleId, Long permissionId);
}
