package com.da.qlnt_be.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDate dob;
    private String avatarUrl;
    private String userType; // "OWNER" hoặc "TENANT"
    // Danh sách role mà user có (ví dụ: ["OWNER", "TENANT"])
    private Set<String> roles;


    // --- Thông tin riêng cho OWNER ---
    private String bankAcc;
    private String businessNo;
    private String taxCode;
    private Boolean verified;

    // --- Thông tin riêng cho TENANT ---
    private Boolean representative; // ví dụ nếu có mã tenant
}