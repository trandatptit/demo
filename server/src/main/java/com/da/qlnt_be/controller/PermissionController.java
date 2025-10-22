package com.da.qlnt_be.controller;

import com.da.qlnt_be.dto.request.PermissionRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.services.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/permissions")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    /**
     * Tạo hoặc cập nhật quyền
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdate")
    public ApiResponse<?> createPermission(
            @RequestBody PermissionRequest request
    ) {
        return ApiResponse.success(permissionService.createOrUpdatePermission(request));
    }

    /**
     * Lấy thông tin quyền theo id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<?> getPermissionById(@PathVariable Long id) {
        return ApiResponse.success(permissionService.getPermissionById(id));
    }


    /**
     * Lấy danh sách tất cả quyền
     * @param pageable
     * @return
     */
    @GetMapping("/getAll")
    public ApiResponse<?> getAllPermissions(
            Pageable pageable
            ) {
        return ApiResponse.success(permissionService.getAllPermissions(pageable));
    }


    /**
     * Phân quyền cho vai trò
     * @param roleId
     * @param permissionId
     * @return
     */
    @PostMapping("/assign")
    public ApiResponse<?> assign(@RequestParam Long roleId, @RequestParam Long permissionId) {
        permissionService.assignPermissionToRole(roleId, permissionId);
        return ApiResponse.ok();
    }

    /**
     * Gỡ quyền khỏi vai trò
     * @param roleId
     * @param permissionId
     * @return
     */
    @PostMapping("/remove")
    public ApiResponse<?> remove(@RequestParam Long roleId, @RequestParam Long permissionId) {
        permissionService.removePermissionFromRole(roleId, permissionId);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ApiResponse.success("Permission deleted successfully");
    }
}
