package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.OwnerRequest;
import com.da.qlnt_be.dto.response.OwnerResponse;
import com.da.qlnt_be.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OwnerService {
     UserResponse createOwner(OwnerRequest request);
    UserResponse updateOwner(Long id, OwnerRequest request);
    Page<?> getAllOwners(Pageable pageable);
    UserResponse getOwnerById(Long id);
    void softDeleteOwner(Long id);
}
