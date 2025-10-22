package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.AssetRequest;
import com.da.qlnt_be.dto.response.AssetResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.mapper.AssetMapper;
import com.da.qlnt_be.model.Asset;
import com.da.qlnt_be.model.Room;
import com.da.qlnt_be.repository.AssetRepository;
import com.da.qlnt_be.repository.RoomRepository;
import com.da.qlnt_be.services.AssetService;
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
public class AssetServiceImpl implements AssetService {
    AssetRepository assetRepository;
    RoomRepository roomRepository;

    /**
     * Tạo mới Asset
     * @param request
     * @return
     */
    @Override
    public AssetResponse createAsset(AssetRequest request) {
        boolean exists = assetRepository.existsByNameAndRoomId(request.getName().trim(), request.getRoomId());
        if (exists) {
            throw new AppException(ErrorCode.ASSET_NAME_ALREADY_EXIST);
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        Asset asset = new Asset();
        asset.setName(request.getName().trim());
        asset.setRoom(room);
        assetRepository.save(asset);

        return AssetMapper.toResponse(asset);
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    public AssetResponse updateAsset(Long id, AssetRequest request) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_NOT_EXIST));

        boolean exists = assetRepository.existsByNameAndRoomId(request.getName(), asset.getRoom().getId());
        if (exists) {
            throw new AppException(ErrorCode.ASSET_NAME_ALREADY_EXIST);
        }
        asset.setName(request.getName());
        assetRepository.save(asset);
        return AssetMapper.toResponse(asset);
    }

    /**
     *
     * @param roomId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<AssetResponse> getAssetsByRoom(Long roomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Asset> assets = assetRepository.findByRoomId(roomId, pageable);
        return assets.map(AssetMapper::toResponse);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public AssetResponse getById(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_NOT_EXIST));

        return AssetMapper.toResponse(asset);
    }

    /**
     *
     * @param id
     */
    @Override
    public void softDeleteAsset(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_NOT_EXIST));

        assetRepository.delete(asset);
    }
}
