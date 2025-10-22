package com.da.qlnt_be.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetItemRequest {
    private String serialNumber;
    private LocalDate boughtAt;
    private Double price;
    private String description;
    private Long assetId;
    private MultipartFile image;
}
