package com.da.qlnt_be.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleResponse {
    private Long id;
    private String name;
    private String description;
}
