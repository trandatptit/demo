package com.da.qlnt_be.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseResponse {
    private Long id;
    private String name;
    private String code;
    private String province;
    private String district;
    private String address;
    private Double area;
    private Long ownerId;
    private List<RuleResponse> rules;
}
