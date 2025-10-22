package com.da.qlnt_be.controller;


import com.da.qlnt_be.dto.request.AssetRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.model.Asset;
import com.da.qlnt_be.services.AssetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assets")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetController {
    AssetService assetService;

    /**
     * Tạo mới loại tài sản
     * @param request
     * @return
     */
    @PostMapping
    public ApiResponse<?> createAsset(@RequestBody AssetRequest request) {
        return ApiResponse.success(assetService.createAsset(request));
    }

    /**
     * Lấy chi tiết loại tài sản
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<?> getAssetById(@PathVariable("id") Long id) {
        return ApiResponse.success(assetService.getById(id));
    }

    /**
     * Lấy danh sách loại tài sản theo phòng (phân trang)
     * @param roomId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/room/{roomId}")
    public ApiResponse<?> getAssetsByRoom(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(assetService.getAssetsByRoom(roomId, page, size));
    }

    /**
     * Cập nhật loại tài sản
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/{id}")
    public ApiResponse<?> updateAsset(@PathVariable Long id, @RequestBody AssetRequest request) {
        return ApiResponse.success(assetService.updateAsset(id, request));
    }

    /**
     * Xóa mềm loại tài sản
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteAssetById(@PathVariable("id") Long id) {
        assetService.softDeleteAsset(id);
        return ApiResponse.ok();
    }
}
