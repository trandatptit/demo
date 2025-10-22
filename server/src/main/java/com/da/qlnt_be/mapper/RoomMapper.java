package com.da.qlnt_be.mapper;

import com.da.qlnt_be.dto.response.RoomResponse;
import com.da.qlnt_be.model.Image;
import com.da.qlnt_be.model.Room;

import java.util.List;
import java.util.stream.Collectors;

public class RoomMapper {

    public static RoomResponse toResponse(Room room){
        if (room == null) return null;

        String avatarUrl = null;
        List<String> imageUrls = null;

        if(room.getImages() != null && !room.getImages().isEmpty()){
            avatarUrl = room.getImages().stream()
                    .filter(img -> img.getImageType() == 0) // 0 = avatar
                    .map(Image::getUrl)
                    .findFirst().orElse(null);

            imageUrls = room.getImages().stream()
                    .filter(img -> img.getImageType() == 1) // 1 = ảnh thường
                    .map(Image::getUrl)
                    .collect(Collectors.toList());
        }

        return RoomResponse.builder()
                .id(room.getId())
                .code(room.getCode())
                .floor(room.getFloor())
                .maxPeople(room.getMaxPeople())
                .area(room.getArea())
                .rent(room.getRent())
                .status(room.getStatus())
                .description(room.getDescription())
                .avatarUrl(avatarUrl)
                .images(imageUrls)
                .houseId(room.getHouse().getId())
                .build();
    }
}
