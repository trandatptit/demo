package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.RoleRequest;
import com.da.qlnt_be.model.Permission;
import com.da.qlnt_be.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.relation.RoleList;
import java.util.List;

public interface RoleService {
    Long createRole(RoleRequest request);
    Role getRoleById(Long id);
    Page<Role> getAllRoles(Pageable pageable);
    void deleteRole(Long id);
    void assignRoleToUser(Long userId, Long roleId);
    void removeRoleFromUser(Long userId, Long roleId);
    List<Permission> getPermissionsByRoleId(Long roleId);
}
