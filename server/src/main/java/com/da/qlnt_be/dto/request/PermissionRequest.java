package com.da.qlnt_be.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {
    private Long id;
    private String code;
    private String description;

}
