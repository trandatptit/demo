package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.RuleRequest;
import com.da.qlnt_be.dto.response.RuleResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.mapper.RuleMapper;
import com.da.qlnt_be.model.Rule;
import com.da.qlnt_be.repository.RuleRepository;
import com.da.qlnt_be.services.RuleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RuleServiceImpl implements RuleService {
    RuleRepository ruleRepository;

    /**
     * Tạo rule
     * @param request
     * @return
     */
    @Override
    public RuleResponse createRule(RuleRequest request) {
        // Check trùng tên (không phân biệt hoa thường)
        if (ruleRepository.existsByNameIgnoreCase(request.getName().trim())) {
            throw new AppException(ErrorCode.RULE_ALREADY_EXISTS);
        }

        Rule rule = Rule.builder()
                .name(request.getName().trim())
                .description(request.getDescription())
                .build();
        ruleRepository.save(rule);
        return RuleMapper.toResponse(rule);
    }

    /**
     * Lấy tất cả rule đã tạo
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<RuleResponse> getAllRules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Rule> rules = ruleRepository.findAll(pageable);

        return rules.map(RuleMapper::toResponse);
    }
}
