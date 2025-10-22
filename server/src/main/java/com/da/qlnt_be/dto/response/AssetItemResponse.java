package com.da.qlnt_be.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetItemResponse {
    private Long id;
    private String serialNumber;
    private LocalDate boughtAt;
    private Double price;
    private String description;
    private int status;
    private Long assetId;

    private String imageUrl;
}
