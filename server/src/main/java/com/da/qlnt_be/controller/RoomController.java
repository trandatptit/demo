package com.da.qlnt_be.controller;

import com.da.qlnt_be.dto.request.RoomRequest;
import com.da.qlnt_be.dto.response.ApiResponse;
import com.da.qlnt_be.services.RoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/rooms")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoomController {
    RoomService roomService;

    /**
     * API tạo mới một phòng
     * @param request Thông tin phòng cần tạo
     * @return Thông tin phòng vừa được tạo
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> createRoom(@ModelAttribute RoomRequest request) throws IOException {
        return ApiResponse.success(roomService.createRoom(request));
    }

    /**
     * API lấy danh sách phòng theo houseId
     * @param houseId ID ngôi nhà
     * @param page Số trang (mặc định = 0)
     * @param size Kích thước trang (mặc định = 10)
     * @return D ApiResponse chứa trang dữ liệu RoomResponse.
     */
    @GetMapping("/house/{houseId}")
    public ApiResponse<?> getRoomsByHouse(
            @PathVariable Long houseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(roomService.getRoomsByHouse(houseId, page, size));
    }

    /**
     * API lấy danh sách phòng theo houseId và status
     * @param houseId ID ngôi nhà
     * @param status Trạng thái phòng (EMPTY, RENTED, ...)
     * @param page Số trang
     * @param size Số lượng phần tử mỗi trang
     * @return Danh sách phòng theo house và status
     */
    @GetMapping("/house/{houseId}/status/{status}")
    public ApiResponse<?> getRoomsByHouseAndStatus(
            @PathVariable Long houseId,
            @PathVariable int status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ApiResponse.success(roomService.getRoomsByHouseAndStatus(houseId, status, page, size));
    }

    /**
     * API lấy thông tin chi tiết 1 phòng
     * @param id ID phòng
     * @return Thông tin chi tiết phòng
     */
    @GetMapping("/{id}")
    public ApiResponse<?> getRoomById(@PathVariable Long id) {
        return ApiResponse.success(roomService.getRoomById(id));
    }

    /**
     * API cập nhật thông tin phòng
     * @param id ID phòng cần cập nhật
     * @param request Dữ liệu cập nhật
     * @return Thông tin phòng sau khi cập nhật
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> updateRoom(@PathVariable Long id, @ModelAttribute RoomRequest request) throws IOException {
        return ApiResponse.success(roomService.updateRoom(id, request));
    }


    /**
     * Cập nhập trạng thái phòng
     * @param id
     * @param status
     * @return
     */
    @PatchMapping("/{id}/status")
    public ApiResponse<?> updateRoomStatus(@PathVariable Long id,@RequestParam int status) {
        return ApiResponse.success(roomService.updateRoomStatus(id, status));
    }

    /**
     * API xóa mềm phòng (đánh dấu không còn hoạt động)
     * @param id ID phòng cần xóa mềm
     * @return Thông báo xóa thành công
     */
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteRoomById(@PathVariable Long id) {
        roomService.softDeleteRoom(id);
        return ApiResponse.ok();
    }
}
