package com.da.qlnt_be.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {
    private Long houseId;
    private String code;
    private Integer floor;
    private Integer maxPeople; // số người tối đa
    private Double area;
    private Double rent;
    private String description;
    private MultipartFile avatar; // ảnh đại diện
    private List<MultipartFile> gallery; // danh sách ảnh phòng
}
