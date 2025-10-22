package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.UserRequest;
import com.da.qlnt_be.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserResponse createUser(UserRequest request) throws IOException;
    UserResponse updateUser(UserRequest request) throws IOException;
    UserResponse getUserById(Long id);
    void deleteUser(Long id);
    Page<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getMyInfo();

}
