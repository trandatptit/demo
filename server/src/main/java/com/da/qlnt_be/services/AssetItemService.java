package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.AssetItemRequest;
import com.da.qlnt_be.dto.response.AssetItemResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface AssetItemService {
    AssetItemResponse createAssetItem(AssetItemRequest request) throws IOException;
    AssetItemResponse updateAssetItem(Long id, AssetItemRequest request) throws IOException;
    AssetItemResponse getAssetItemById(Long id);
    Page<AssetItemResponse> getAssetItemsByAssetId(Long assetId, int page, int size);
    Page<AssetItemResponse> getAssetItemsByAssetIdAndStatus(Long assetId, int status, int page, int size);
    void softDeleteAssetItem(Long id);
    AssetItemResponse updateStatusAssetItem(Long id, int status);
}
