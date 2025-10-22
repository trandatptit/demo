package com.da.qlnt_be.controller;

import com.da.qlnt_be.dto.request.AssetItemRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.services.AssetItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/asset-items")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetItemController {
    AssetItemService assetItemService;

    /**
     * Tạo tài sản cụ thể
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> createAssetItem(@ModelAttribute AssetItemRequest request) throws IOException {
        return ApiResponse.success(assetItemService.createAssetItem(request));
    }

    /**
     * Cập nhật thông tin tài sản cụ thể
     * @param id
     * @param request
     * @return
     * @throws IOException
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?>  updateAssetItem(
            @PathVariable Long id,
            @ModelAttribute AssetItemRequest request
    ) throws IOException {
        return ApiResponse.success(assetItemService.updateAssetItem(id, request));
    }

    /**
     * Cập nhật trạng thái tài sản (tốt, hư, đang sửa)
     * @param id
     * @param status
     * @return
     */
    @PatchMapping("/{id}/status")
    public ApiResponse<?>  updateAssetItemStatus(@PathVariable Long id, @RequestParam int status) {
        return ApiResponse.success(assetItemService.updateStatusAssetItem(id, status));
    }

    /**
     * Lấy chi tiết tài sản cụ thể
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<?>  getAssetItemById(@PathVariable Long id) {
        return ApiResponse.success(assetItemService.getAssetItemById(id));
    }

    /**
     * Lấy danh sách tài sản cụ thể theo loại tài sản
     * @param assetId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/asset/{assetId}")
    public ApiResponse<?>  getAssetItemsByAsset(
            @PathVariable Long assetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(assetItemService.getAssetItemsByAssetId(assetId, page, size));
    }

    /**
     * Lấy danh sách tài sản cụ thể theo loại tài sản và trạng thái
     * @param assetId
     * @param status
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/asset/{assetId}/status/{status}")
    public ApiResponse<?>  getAssetItemsByAssetAndStatus(
            @PathVariable Long assetId,
            @PathVariable int status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(assetItemService.getAssetItemsByAssetIdAndStatus(assetId,status, page, size));
    }

    /**
     * Xóa mềm tài sản cụ thể
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiResponse<?>  deleteAssetItem(@PathVariable Long id) {
        assetItemService.softDeleteAssetItem(id);
        return ApiResponse.ok();
    }

}
