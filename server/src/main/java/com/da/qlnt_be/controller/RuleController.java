package com.da.qlnt_be.controller;

import com.da.qlnt_be.dto.request.RuleRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.services.RuleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RuleController {
    RuleService ruleService;

    @PostMapping
    public ApiResponse<?> createRule(@RequestBody RuleRequest request) {
        return ApiResponse.success(ruleService.createRule(request));
    }

    @GetMapping
    public ApiResponse<?> getRules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(ruleService.getAllRules(page, size));
    }
}
