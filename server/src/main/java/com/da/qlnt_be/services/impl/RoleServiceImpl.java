package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.RoleRequest;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.model.*;
import com.da.qlnt_be.repository.RoleRepository;
import com.da.qlnt_be.repository.UserRepository;
import com.da.qlnt_be.repository.UserRoleRepository;
import com.da.qlnt_be.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_CREATE')")
    public Long createRole(RoleRequest request) {
         String name = request.getName();
         if(roleRepository.existsByName(name)) {
             throw new AppException(ErrorCode.ROLE_EXISTED);
         }
         Role role = new Role();
         role.setName(name);
         roleRepository.save(role);
         return role.getId();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_READ')")
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_READ')")
    public Page<Role> getAllRoles(Pageable pageable) {
        return roleRepository.findAllByIsActiveTrueOrderByName(pageable);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_DELETE')")
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roleRepository.delete(role);
    }


    /**
     * Gán vai trò (Role) cho người dùng (User)
     * @param userId
     * @param roleId
     */
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ASSIGN')")
    public void assignRoleToUser(Long userId, Long roleId) {

        UserRole userRole = new UserRole();
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        if(userRoleRepository.existsByUserAndRole(user, role)) {
            throw new AppException(ErrorCode.ROLE_ALREADY_ASSIGNED_TO_USER);
        }
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);

        user.getUserRoles().add(userRole);
        role.getUserRoles().add(userRole);
        userRepository.save(user);
        roleRepository.save(role);
    }

    /**
     * Gỡ vai trò (Role) khỏi người dùng (User)
     * @param userId
     * @param roleId
     */
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ASSIGN')")
    public void removeRoleFromUser(Long userId, Long roleId) {
        UserRole userRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_ASSIGNED_TO_USER));
        userRoleRepository.delete(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        Set<RolePermission> rolePermissions = role.getRolePermissions();
        return rolePermissions.stream()
                .map(RolePermission::getPermission)
                .sorted(Comparator.comparing(Permission::getCode))
                .toList();
    }
}
