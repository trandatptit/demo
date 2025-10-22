package com.da.qlnt_be.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private Long id;
    private String name;
}
