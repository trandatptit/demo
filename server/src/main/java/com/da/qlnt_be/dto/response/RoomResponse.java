package com.da.qlnt_be.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private Long id;
    private Long houseId;
    private String code;
    private Integer floor;
    private Integer maxPeople; // số người tối đa
    private Double rent;
    private Double area;
    private int status;
    private String description;

    private String avatarUrl; // ảnh đại diện
    private List<String> images; // danh sách url ảnh khác
}
