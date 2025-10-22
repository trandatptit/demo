package com.da.qlnt_be.mapper;

import com.da.qlnt_be.dto.response.AssetResponse;
import com.da.qlnt_be.model.Asset;

public class AssetMapper {

    public static AssetResponse toResponse(Asset asset) {
        if (asset == null) return null;
        AssetResponse response = new AssetResponse();
        response.setId(asset.getId());
        response.setName(asset.getName());
        response.setRoomId(asset.getRoom().getId());
        return response;
    }
}
