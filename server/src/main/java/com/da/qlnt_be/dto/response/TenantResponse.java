package com.da.qlnt_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private LocalDate dob;
    private Boolean representative;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
