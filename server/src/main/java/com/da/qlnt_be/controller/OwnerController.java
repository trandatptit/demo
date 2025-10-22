package com.da.qlnt_be.controller;

import com.da.qlnt_be.dto.request.OwnerRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.services.OwnerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OwnerController {
    OwnerService ownerService;

    @GetMapping
    public ApiResponse<?> getAllOwners(
            Pageable pageable
    ) {
        return ApiResponse.success(ownerService.getAllOwners(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getOwnerById(@PathVariable Long id) {
        return ApiResponse.success(ownerService.getOwnerById(id));
    }

    @PostMapping
    public ApiResponse<?> createOwner(@RequestBody OwnerRequest request) {
        return ApiResponse.success(ownerService.createOwner(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateOwner(@PathVariable Long id, @RequestBody OwnerRequest request) {
        return ApiResponse.success(ownerService.updateOwner(id, request));
    }
}
