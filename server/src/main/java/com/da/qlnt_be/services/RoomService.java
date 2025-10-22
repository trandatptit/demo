package com.da.qlnt_be.services;

import com.da.qlnt_be.dto.request.RoomRequest;
import com.da.qlnt_be.dto.response.RoomResponse;
import com.da.qlnt_be.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface RoomService {
    RoomResponse createRoom(RoomRequest request) throws IOException;;
    Page<RoomResponse> getRoomsByHouse(Long houseId, int page, int size);
    Page<RoomResponse> getRoomsByHouseAndStatus(Long houseId, int status, int page, int size);
    RoomResponse getRoomById(Long id);
    RoomResponse updateRoom(Long id, RoomRequest request) throws IOException;
    RoomResponse updateRoomStatus(Long id, int status);
    void softDeleteRoom(Long id);
}
