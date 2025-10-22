package com.da.qlnt_be.controller;


import com.da.qlnt_be.dto.request.RoleRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    /**
     * Tạo vai trò
     * @param request
     * @return
     */
    @PostMapping("/create")
    public ApiResponse<?> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.success(roleService.createRole(request));
    }

    /**
     * Lấy thông tin vai trò theo id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<?> getRoleById(@PathVariable Long id) {
        return ApiResponse.success(roleService.getRoleById(id));
    }

    /**
     * Lấy danh sách tất cả vai trò
     * @param pageable
     * @return
     */
    @GetMapping("/getAll")
    public ApiResponse<?> getAllRoles(org.springframework.data.domain.Pageable pageable) {
        return ApiResponse.success(roleService.getAllRoles(pageable));
    }

    /**
     * Xóa vai trò
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResponse.success("Role deleted successfully");
    }

    /**
     * Phân vai trò cho người dùng
     * @param userId
     * @param roleId
     * @return
     */
    @PostMapping("/assign")
    public ApiResponse<?> assignRoleToUser(@RequestParam Long userId, @RequestParam Long roleId) {
        roleService.assignRoleToUser(userId, roleId);
        return ApiResponse.success("Role assigned to user successfully");
    }

    /**
     * Gỡ vai trò khỏi người dùng
     * @param userId
     * @param roleId
     * @return
     */
    @PostMapping("/remove")
    public ApiResponse<?> removeRoleFromUser(@RequestParam Long userId, @RequestParam Long roleId) {
        roleService.removeRoleFromUser(userId, roleId);
        return ApiResponse.success("Role removed from user successfully");
    }
    @GetMapping("/role-permissions/{roleId}")
    public ApiResponse<?> getPermissionsByRoleId(@PathVariable Long roleId) {
        return ApiResponse.success(roleService.getPermissionsByRoleId(roleId));
    }
}
