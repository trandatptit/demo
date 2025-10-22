package com.da.qlnt_be.controller;

import com.da.qlnt_be.dto.request.HouseRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.dto.response.HouseResponse;
import com.da.qlnt_be.services.HouseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/houses")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HouseController {
    HouseService houseService;

    /**
     * API tạo mới một nhà trọ và lưu vào cơ sở dữ liệu.
     * @param request đối tượng HouseRequest chứa thông tin cần thiết để tạo mới nhà trọ
     * @return ApiResponse chứa dữ liệu HouseResponse biểu diễn thông tin nhà trọ đã được tạo thành công.
     */
    @PostMapping
    public ApiResponse<HouseResponse> saveHouse(@RequestBody HouseRequest request) {
        return ApiResponse.success(houseService.saveHouse(request));
    }

    /**
     * API lấy danh sách tất cả các căn nhà thuộc về một chủ sở hữu có hỗ trợ phân trang.
     * @param ownerId ID của chủ sở hữu (Owner) cần lấy danh sách nhà.
     * @param page số trang (mặc định 0).
     * @param size số lượng bản ghi mỗi trang (mặc định 10).
     * @return ApiResponse chứa trang dữ liệu HouseResponse.
     */
    @GetMapping("/owner/{ownerId}")
    public ApiResponse<Page<HouseResponse>> getHousesByOwner(
            @PathVariable Long ownerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(houseService.getHousesByOwner(ownerId, page, size));
    }

    /**
     * API lấy thông tin chi tiết của một căn nhà dựa trên ID.
     * @param id ID của căn nhà cần truy vấn.
     * @return ApiResponse chứa HouseResponse với thông tin chi tiết của căn nhà.
     */
    @GetMapping("/{id}")
    public ApiResponse<HouseResponse> getHouseById(@PathVariable Long id) {
        return ApiResponse.success(houseService.getHouseById(id));
    }

    /**
     * API cập nhật thông tin của một căn nhà dựa trên ID.
     * @param id ID của căn nhà cần cập nhật.
     * @param request đối tượng HouseRequest chứa thông tin cập nhật.
     * @return ApiResponse chứa dữ liệu HouseResponse của căn nhà sau khi cập nhật thành công.
     */
    @PutMapping("/{id}")
    public ApiResponse<HouseResponse> updateHouse(
            @PathVariable Long id,
            @RequestBody HouseRequest request
    ) {
        return ApiResponse.success(houseService.updateHouse(id, request));
    }

    /**
     * API xóa mềm một căn nhà (chuyển trạng thái isActive = false).
     * @param id ID của căn nhà cần xóa.
     * @return ApiResponse rỗng, phản hồi thành công (HTTP 200 OK) nếu xóa mềm thành công.
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteHouse(@PathVariable Long id) {
        houseService.softDeleteHouse(id);
        return ApiResponse.ok();
    }
}
