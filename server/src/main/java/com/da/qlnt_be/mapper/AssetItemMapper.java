package com.da.qlnt_be.mapper;

import com.da.qlnt_be.dto.response.AssetItemResponse;
import com.da.qlnt_be.model.AssetItem;
import com.da.qlnt_be.model.Image;

public class AssetItemMapper {

    public static AssetItemResponse toResponse(AssetItem assetItem) {
        if (assetItem == null) return null;
        String imageUrl = null;

        if(assetItem.getImages() != null && !assetItem.getImages().isEmpty()){
            imageUrl = assetItem.getImages().stream()
                    .filter(img -> img.getImageType() == 0) // 0 = avatar
                    .map(Image::getUrl)
                    .findFirst().orElse(null);
        }

        return AssetItemResponse.builder()
                .id(assetItem.getId())
                .serialNumber(assetItem.getSerialNumber())
                .boughtAt(assetItem.getBoughtAt())
                .price(assetItem.getPrice())
                .description(assetItem.getDescription())
                .status(assetItem.getStatus())
                .imageUrl(imageUrl)
                .assetId(assetItem.getAsset().getId())
                .build();
    }
}
