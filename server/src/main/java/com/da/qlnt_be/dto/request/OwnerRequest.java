package com.da.qlnt_be.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String bankAcc;
    private String businessNo;
    private String taxCode;
}
