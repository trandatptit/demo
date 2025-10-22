package com.da.qlnt_be.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetResponse {
    private Long id;
    private String name;
    private Long roomId;
}
