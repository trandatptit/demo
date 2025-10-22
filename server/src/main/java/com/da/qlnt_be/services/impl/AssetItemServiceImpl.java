package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.AssetItemRequest;
import com.da.qlnt_be.dto.response.AssetItemResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.mapper.AssetItemMapper;
import com.da.qlnt_be.model.Asset;
import com.da.qlnt_be.model.AssetItem;
import com.da.qlnt_be.model.Image;
import com.da.qlnt_be.repository.AssetItemRepository;
import com.da.qlnt_be.repository.AssetRepository;
import com.da.qlnt_be.repository.ImageRepository;
import com.da.qlnt_be.services.AssetItemService;
import com.da.qlnt_be.services.CommonService;
import com.da.qlnt_be.utils.Constants;
import com.da.qlnt_be.utils.NumberUtils;
import com.da.qlnt_be.utils.TableConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetItemServiceImpl implements AssetItemService {
    AssetItemRepository assetItemRepository;
    AssetRepository assetRepository;
    CommonService commonService;
    ImageRepository imageRepository;

    /**
     * Tạo mới tài sản
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    @Transactional
    public AssetItemResponse createAssetItem(AssetItemRequest request) throws IOException {
        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_NOT_EXIST));

        // ============ Tạo AssetItem =============
        AssetItem item = AssetItem.builder()
                .serialNumber(request.getSerialNumber())
                .boughtAt(request.getBoughtAt())
                .price(request.getPrice())
                .description(request.getDescription())
                .status(Constants.ASSET_ITEM_STATUS.GOOD)
                .asset(asset)
                .build();

        assetItemRepository.save(item);

        List<Image> images = new ArrayList<>();
        // ============ Upload ảnh image =============
        if(request.getImage() != null && !request.getImage().isEmpty()) {
            Map<String, Object> avatarMap = (Map<String, Object>) commonService.uploadImage(request.getImage(), Map.of());

            Image image = Image.builder()
                    .entityType(TableConstants.TABLE_NAME.ASSET_ITEM)
                    .entityId(item.getId())
                    .url((String) avatarMap.get("url"))
                    .contentType((String) avatarMap.get("format"))
                    .displayName((String) avatarMap.get("display_name"))
                    .publicId((String) avatarMap.get("public_id"))
                    .imageType(0) // avatar type
                    .bytes(NumberUtils.convertToLongV1(avatarMap.get("bytes")))
                    .build();
            imageRepository.save(image);
            images.add(image);
        }

        item.setImages(images);
        return AssetItemMapper.toResponse(item);
    }

    /**
     * Cập nhật các thông tin cơ bản của tài sản
     * @param id
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    @Transactional
    public AssetItemResponse updateAssetItem(Long id, AssetItemRequest request) throws IOException {
        AssetItem item = assetItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_ITEM_NOT_EXIST));

        if (request.getSerialNumber() != null)
            item.setSerialNumber(request.getSerialNumber());

        if (request.getBoughtAt() != null)
            item.setBoughtAt(request.getBoughtAt());

        if (request.getPrice() != null)
            item.setPrice(request.getPrice());

        if (request.getDescription() != null)
            item.setDescription(request.getDescription());

        assetItemRepository.save(item);

        List<Image> images = item.getImages();
        // Xử lý ảnh avatar (nếu người dùng upload ảnh mới)
        if(request.getImage() != null && !request.getImage().isEmpty()) {
            // lấy danh sách image cũ
            List<Image> oldAvatars = imageRepository.findImageActive(item.getId(),
                    TableConstants.TABLE_NAME.ASSET_ITEM, 0L);
            for (Image img : oldAvatars) {
                // Xóa trên Cloudinary
                commonService.deleteImage(img.getPublicId());
                // Xóa mềm trong DB
                img.setIsActive(false);

                // xoa trong list anh
                images.remove(img);
            }
            imageRepository.saveAll(oldAvatars);

            // upload avatar mới lên Cloadinary
            Map<String, Object> avatarMap = (Map<String, Object>) commonService.uploadImage(request.getImage(), Map.of());
            Image image = Image.builder()
                    .entityType(TableConstants.TABLE_NAME.ASSET_ITEM)
                    .entityId(item.getId())
                    .url((String) avatarMap.get("url"))
                    .contentType((String) avatarMap.get("format"))
                    .displayName((String) avatarMap.get("display_name"))
                    .publicId((String) avatarMap.get("public_id"))
                    .imageType(0) // avatar type
                    .bytes(NumberUtils.convertToLongV1(avatarMap.get("bytes")))
                    .build();
            imageRepository.save(image); // lưu vào db
            images.add(image);
        }

        // cập nhật lại ảnh
        item.setImages(images);

        return AssetItemMapper.toResponse(item);
    }

    /**
     * Tìm theo id
     * @param id
     * @return
     */
    @Override
    public AssetItemResponse getAssetItemById(Long id) {
        AssetItem item = assetItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_ITEM_NOT_EXIST));

        return AssetItemMapper.toResponse(item);
    }

    /**
     * Lấy danh sách tài sản theo loại tài sản
     * @param assetId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<AssetItemResponse> getAssetItemsByAssetId(Long assetId, int page, int size) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_NOT_EXIST));

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<AssetItem> items = assetItemRepository.findByAssetId(assetId, pageable);
        return items.map(AssetItemMapper::toResponse);
    }

    /**
     * Lấy danh sách tài sản theo loại tài sản theo trạng thái
     * @param assetId
     * @param status
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<AssetItemResponse> getAssetItemsByAssetIdAndStatus(Long assetId, int status, int page, int size) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_NOT_EXIST));

        // check status
        if (status < Constants.ASSET_ITEM_STATUS.GOOD || status > Constants.ASSET_ITEM_STATUS.REPAIR) {
            throw new AppException(ErrorCode.INVALID_ASSET_ITEM_STATUS);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<AssetItem> items = assetItemRepository.getAssetItemsByAssetIdAndStatus(assetId, status, pageable);
        return items.map(AssetItemMapper::toResponse);
    }

    /**
     * Xóa mềm tài sản
     * @param id
     */
    @Override
    public void softDeleteAssetItem(Long id) {
        AssetItem item = assetItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_ITEM_NOT_EXIST));
        assetItemRepository.delete(item);
    }

    /**
     * Cập nhật trạng thái tài sản
     * @param id
     * @param status
     * @return
     */
    @Override
    public AssetItemResponse updateStatusAssetItem(Long id, int status) {
        AssetItem item = assetItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSET_ITEM_NOT_EXIST));

        // check status
        if (status < Constants.ASSET_ITEM_STATUS.GOOD || status > Constants.ASSET_ITEM_STATUS.REPAIR) {
            throw new AppException(ErrorCode.INVALID_ASSET_ITEM_STATUS);
        }

        item.setStatus(status);
        assetItemRepository.save(item);

        return AssetItemMapper.toResponse(item);
    }
}
