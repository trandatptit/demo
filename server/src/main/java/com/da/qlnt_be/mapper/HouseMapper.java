package com.da.qlnt_be.mapper;

import com.da.qlnt_be.dto.response.HouseResponse;
import com.da.qlnt_be.dto.response.RuleResponse;
import com.da.qlnt_be.model.House;

import java.util.Collections;

public class HouseMapper {
    public static HouseResponse toReponse(House house) {
        if (house == null) return null;

        return HouseResponse.builder()
                .id(house.getId())
                .code(house.getCode())
                .name(house.getName())
                .province(house.getProvince())
                .district(house.getDistrict())
                .address(house.getAddress())
                .area(house.getArea())
                .ownerId(house.getOwner() != null ? house.getOwner().getId() : null)
                .rules(house.getRules() != null ?
                        house.getRules().stream()
                                .map(r -> new RuleResponse(r.getId(), r.getName(), r.getDescription()))
                                .toList()
                        : Collections.emptyList())
                .build();
    }
}
