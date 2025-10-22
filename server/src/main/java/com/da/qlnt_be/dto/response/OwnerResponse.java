package com.da.qlnt_be.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String bankAcc;
    private String businessNo;
    private String taxCode;
    private Boolean verified;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
