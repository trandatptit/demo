package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.RuleRequest;
import com.da.qlnt_be.dto.response.RuleResponse;
import org.springframework.data.domain.Page;

public interface RuleService {
    RuleResponse createRule(RuleRequest request);
    Page<RuleResponse> getAllRules(int page, int size);
}
