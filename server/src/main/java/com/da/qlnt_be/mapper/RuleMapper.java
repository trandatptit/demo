package com.da.qlnt_be.mapper;

import com.da.qlnt_be.dto.response.RuleResponse;
import com.da.qlnt_be.model.Rule;

public class RuleMapper {

    public static RuleResponse toResponse(Rule rule) {
        if (rule == null) return null;

        return RuleResponse.builder()
                .id(rule.getId())
                .name(rule.getName())
                .description(rule.getDescription())
                .build();
    }
}
