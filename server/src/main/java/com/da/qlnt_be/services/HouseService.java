package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.HouseRequest;
import com.da.qlnt_be.dto.response.HouseResponse;
import org.springframework.data.domain.Page;

public interface HouseService {
    HouseResponse saveHouse(HouseRequest request);
    Page<HouseResponse> getHousesByOwner(Long ownerId, int page, int size);
    HouseResponse getHouseById(Long id);
    HouseResponse updateHouse(Long id, HouseRequest request);
    void softDeleteHouse(Long id);
}
