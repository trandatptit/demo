package com.da.qlnt_be.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseRequest {
    private String name;
    private String province;
    private String district;
    private String address;
    private Double area;
    private Long ownerId; // id chủ trọ
    private List<Long> ruleIds; // Danh sách ID nội quy được chọn
}
