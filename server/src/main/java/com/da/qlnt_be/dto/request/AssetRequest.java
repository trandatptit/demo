package com.da.qlnt_be.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetRequest {
    private String name;
    private Long roomId;
}
