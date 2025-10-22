package com.da.qlnt_be.controller;


import com.da.qlnt_be.dto.request.UserRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> createUser(
            @ModelAttribute UserRequest request
    ) throws IOException {
        return ApiResponse.success(userService.createUser(request));
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> updateUser(
            @ModelAttribute UserRequest request
    ) throws IOException {
        return ApiResponse.success(userService.updateUser(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getUserById(@PathVariable Long id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @GetMapping("/getAll")
    public ApiResponse<?> getAllUsers(
            Pageable pageable
    ) {
        return ApiResponse.success(userService.getAllUsers(pageable));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.success("User deleted successfully");
    }
    @GetMapping("/me")
    public ApiResponse<?> getMyInfo() {
        return ApiResponse.success(userService.getMyInfo());
    }
}
