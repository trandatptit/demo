package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.AssetRequest;
import com.da.qlnt_be.dto.response.AssetResponse;
import org.springframework.data.domain.Page;

public interface AssetService {
    AssetResponse createAsset(AssetRequest request);
    AssetResponse updateAsset(Long id, AssetRequest request);
    Page<AssetResponse> getAssetsByRoom(Long roomId, int page, int size);
    AssetResponse getById(Long id);
    void softDeleteAsset(Long id);
}
