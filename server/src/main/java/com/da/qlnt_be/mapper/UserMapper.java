package com.da.qlnt_be.mapper;


import com.da.qlnt_be.dto.request.UserRequest;
import com.da.qlnt_be.dto.response.UserResponse;
import com.da.qlnt_be.model.Image;
import com.da.qlnt_be.model.Owner;
import com.da.qlnt_be.model.Tenant;
import com.da.qlnt_be.model.User;

import java.util.stream.Collectors;

public class UserMapper {



    public static User toEntity(UserRequest request) {
        if (request == null) return null;

        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .dob(request.getDob())
                .build();
    }

    public static UserResponse toResponse(User user) {
        if (user == null) return null;

        // Ánh xạ thông tin chung của User
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .dob(user.getDob())
                .roles(user.getUserRoles().stream()
                        .map(ur -> ur.getRole().getName())
                        .collect(Collectors.toSet()))
                .avatarUrl(user.getImages().stream()
                        .filter(img -> img.getImageType() == 0) // giả sử 0 = avatar
                        .map(Image::getUrl)
                        .findFirst()
                        .orElse(null))
                .build();

        // Ánh xạ thông tin riêng cho từng loại user (Owner / Tenant)
        if (user instanceof Owner owner) {
            response.setUserType("OWNER");
            response.setBankAcc(owner.getBankAcc());
            response.setBusinessNo(owner.getBusinessNo());
            response.setTaxCode(owner.getTaxCode());
            response.setVerified(owner.getVerified());
        } else if (user instanceof Tenant tenant) {
            response.setUserType("TENANT");
            response.setRepresentative(tenant.getRepresentative());
        } else {
            response.setUserType("USER");
        }

        return response;
    }
}
