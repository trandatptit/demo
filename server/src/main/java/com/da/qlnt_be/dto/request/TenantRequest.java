package com.da.qlnt_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantRequest {
    private String name;
    private String email;
    private String address;
    private String phone;
    private String password;
    private LocalDate dob;
    private Boolean representative; // Người đại diện
}
