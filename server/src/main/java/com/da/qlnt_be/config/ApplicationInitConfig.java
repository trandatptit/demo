package com.da.qlnt_be.config;

import com.da.qlnt_be.model.*;
import com.da.qlnt_be.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    /**
     * Khởi tạo tài khoản admin mặc định khi ứng dụng khởi động
     * @param userRepository
     * @param roleRepository
     * @param userRoleRepository
     * @param permissionRepository
     * @param rolePermissionRepository
     * @return
     */
    @Bean
    @Transactional
    ApplicationRunner applicationRunner(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository,
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository
    ) {
        return args -> {
            // Create or get default permission
            Permission permission = permissionRepository.findByCodeAndIsActiveTrue("ALL")
                    .orElseGet(() -> permissionRepository.save(
                            Permission.builder()
                                    .code("ALL")
                                    .description("All permissions")
                                    .build()
                    ));

            // Create or get default role
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name("ADMIN")
                                    .build()
                    ));

            // Create RolePermission only if not exist
            if (!rolePermissionRepository.existsByRoleAndPermission(adminRole, permission)) {
                RolePermission rolePermission = RolePermission.builder()
                        .role(adminRole)
                        .permission(permission)
                        .build();
                rolePermissionRepository.save(rolePermission);
            }

            // Tạo user admin nếu chưa có
            User admin = userRepository.findByUsername("admin").orElseGet(() ->
                    userRepository.save(User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .build())
            );

            // Assign role to user if not yet assigned
            if (!userRoleRepository.existsByUserAndRole(admin, adminRole)) {
                UserRole userRole = UserRole.builder()
                        .user(admin)
                        .role(adminRole)
                        .build();
                userRoleRepository.save(userRole);
            }

            // Lấy tất cả permission đang active
            List<Permission> allPermissions = permissionRepository.findAllByIsActiveTrue();

            // 5. Gán tất cả permission vào role ADMIN nếu chưa gán
            allPermissions.forEach(p -> {
                if (!rolePermissionRepository.existsByRoleAndPermission(adminRole, p)) {
                    rolePermissionRepository.save(RolePermission.builder()
                            .role(adminRole)
                            .permission(p)
                            .build());
                }
            });

            log.warn("Default admin user has been created (username=admin, password=admin). Please change it.");
        };
    }
}
