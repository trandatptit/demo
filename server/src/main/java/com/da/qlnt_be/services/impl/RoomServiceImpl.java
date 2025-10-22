package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.RoomRequest;
import com.da.qlnt_be.dto.response.RoomResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.mapper.RoomMapper;
import com.da.qlnt_be.model.House;
import com.da.qlnt_be.model.Image;
import com.da.qlnt_be.model.Room;
import com.da.qlnt_be.repository.HouseRepository;
import com.da.qlnt_be.repository.ImageRepository;
import com.da.qlnt_be.repository.RoomRepository;
import com.da.qlnt_be.services.CommonService;
import com.da.qlnt_be.services.RoomService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;
    HouseRepository houseRepository;
    CommonService commonService;
    ImageRepository imageRepository;

    /**
     * Tạo mới một phòng (Room)
     * @param request Đối tượng RoomRequest chứa thông tin phòng cần tạo.
     * @return RoomResponse chứa thông tin chi tiết của phòng vừa được tạo
     * @throws AppException nếu không tìm thấy ngôi nhà tương ứng với houseId
     */
    @Override
    @Transactional
    public RoomResponse createRoom(RoomRequest request) throws IOException {
        // ============ Validate đầu vào =============
        if(request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_ROOM_CODE);
        }
        if(request.getHouseId() == null) {
            throw new AppException(ErrorCode.HOUSE_NOT_EXISTED);
        }
        // ============ Kiểm tra house tồn tại =============
        House house = houseRepository.findById(request.getHouseId())
                .orElseThrow(() -> new AppException(ErrorCode.HOUSE_NOT_EXISTED));

        // ============ Check trùng code trong cùng house ============
        boolean exists = roomRepository.existsByCodeAndHouseId(request.getCode(), request.getHouseId());
        if (exists) {
            throw new AppException(ErrorCode.ROOM_CODE_ALREADY_EXIST);
        }

        // ============ Tạo room =============
        Room room = new Room();
        room.setCode(request.getCode().trim());
        room.setArea(request.getArea());
        room.setFloor(request.getFloor());
        room.setMaxPeople(request.getMaxPeople());
        room.setRent(request.getRent());
        room.setDescription(request.getDescription());
        room.setStatus(Constants.ROOM_STATUS.EMPTY);
        room.setHouse(house);

        roomRepository.save(room);

        List<Image> images = new ArrayList<>();
        // ============ Upload ảnh avatar =============
        if(request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            Map<String, Object> avatarMap = (Map<String, Object>) commonService.uploadImage(request.getAvatar(), Map.of());

            Image avatar = Image.builder()
                    .entityType(TableConstants.TABLE_NAME.ROOM)
                    .entityId(room.getId())
                    .url((String) avatarMap.get("url"))
                    .contentType((String) avatarMap.get("format"))
                    .displayName((String) avatarMap.get("display_name"))
                    .publicId((String) avatarMap.get("public_id"))
                    .imageType(0) // avatar type
                    .bytes(NumberUtils.convertToLongV1(avatarMap.get("bytes")))
                    .build();
            imageRepository.save(avatar);
            images.add(avatar);
        }

        // ============ Upload gallery ảnh (nếu có) ============
        if(request.getGallery() != null && !request.getGallery().isEmpty()) {
            for(MultipartFile file : request.getGallery()) {
                if(file != null && !file.isEmpty()) {
                    Map<String, Object> imageMap = (Map<String, Object>) commonService.uploadImage(file, Map.of());
                    Image galleryImage = Image.builder()
                            .entityType(TableConstants.TABLE_NAME.ROOM)
                            .entityId(room.getId())
                            .url((String) imageMap.get("url"))
                            .contentType((String) imageMap.get("format"))
                            .displayName((String) imageMap.get("display_name"))
                            .publicId((String) imageMap.get("public_id"))
                            .imageType(1) // gallery type
                            .bytes(NumberUtils.convertToLongV1(imageMap.get("bytes")))
                            .build();
                    imageRepository.save(galleryImage);
                    images.add(galleryImage);
                }
            }
        }

        // gan danh sach anh vao room
        room.setImages(images);
        log.info("Created new room successfully: code={}, houseId={}", room.getCode(), house.getId());
        return RoomMapper.toResponse(room);
    }

    /**
     * Lấy danh sách tất cả các phòng (Room) thuộc về một ngôi nhà (House) cụ thể
     * @param houseId ID của ngôi nhà cần lấy danh sách phòng
     * @param page Số trang hiện tại (bắt đầu từ 0)
     * @param size Số lượng phần tử trong mỗi trang
     * @return Trang dữ liệu chứa danh sách các RoomResponse
     * @throws AppException nếu ngôi nhà không tồn tại trong hệ thống
     */
    @Override
    public Page<RoomResponse> getRoomsByHouse(Long houseId, int page, int size) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new AppException(ErrorCode.HOUSE_NOT_EXISTED));

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Room> rooms = roomRepository.findByHouseAndIsActiveTrue(house, pageable);

        log.info("Retrieved {} rooms for houseId={}", rooms.getContent().size(), houseId);
        return rooms.map(RoomMapper::toResponse);
    }

    /**
     * Lấy danh sách các phòng thuộc về một ngôi nhà cụ thể được lọc theo trạng thái phòng (status)
     * và chỉ lấy các phòng đang hoạt động
     * @param houseId ID của ngôi nhà cần lấy danh sách phòng
     * @param status Trạng thái phòng cần lọc (ví dụ: 0 = EMPTY, 1 = RENTED, 2 = MAINT, 3 = DEPOSITED)
     * @param page Số trang hiện tại (bắt đầu từ 0)
     * @param size Số lượng phần tử trong mỗi trang
     * @return Trang dữ liệu chứa danh sách các RoomResponse
     * @throws AppException nếu ngôi nhà không tồn tại trong hệ thống
     */
    @Override
    public Page<RoomResponse> getRoomsByHouseAndStatus(Long houseId, int status, int page, int size) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new AppException(ErrorCode.HOUSE_NOT_EXISTED));

        // check status
        if (status < Constants.ROOM_STATUS.EMPTY || status > Constants.ROOM_STATUS.DEPOSITED) {
            throw new AppException(ErrorCode.INVALID_ROOM_STATUS);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Room> rooms = roomRepository.findByHouseAndStatusAndIsActiveTrue(house, status, pageable);
        log.info("Retrieved {} rooms for houseId={} with status={}", rooms.getContent().size(), houseId, Constants.ROOM_STATUS.getStatusName(status));
        return rooms.map(RoomMapper::toResponse);
    }

    /**
     * Lấy thông tin chi tiết của một phòng dựa trên ID.
     * @param id ID của phòng cần cập nhật.
     * @return RoomResponse chứa thông tin chi tiết của phòng.
     * @throws AppException nếu phòng không tồn tại trong hệ thống.
     */
    @Override
    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
        log.info("Retrieved room successfully: id={}, code={}", room.getId(), room.getCode());

        return RoomMapper.toResponse(room);
    }

    /**
     * Cập nhật thông tin của một phòng dựa trên ID.
     * @param id ID của phòng cần cập nhật.
     * @param request đối tượng RoomRequest chứa thông tin cập nhật
     * @return RoomResponse chứa thông tin chi tiết của phòng sau khi cập nhật.
     * @throws AppException nếu phòng không tồn tại trong hệ thống.
     */
    @Override
    @Transactional
    public RoomResponse updateRoom(Long id, RoomRequest request) throws IOException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        if(!room.getCode().equalsIgnoreCase(request.getCode())) { // check code
            boolean exits = roomRepository.existsByCodeAndHouseId(request.getCode(), room.getHouse().getId());
            if(exits) {
                throw new AppException(ErrorCode.ROOM_CODE_ALREADY_EXIST);
            }
            room.setCode(request.getCode());
        }
        room.setArea(request.getArea());
        room.setRent(request.getRent());
        room.setDescription(request.getDescription());

        roomRepository.save(room);

        List<Image> images = room.getImages();
        // Xử lý ảnh avatar (nếu người dùng upload ảnh mới)
        if(request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            // lấy danh sách avatar cũ
            List<Image> oldAvatars = imageRepository.findImageActive(room.getId(),
                    TableConstants.TABLE_NAME.ROOM, 0L);
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
            Map<String, Object> avatarMap = (Map<String, Object>) commonService.uploadImage(request.getAvatar(), Map.of());
            Image avatar = Image.builder()
                    .entityType(TableConstants.TABLE_NAME.ROOM)
                    .entityId(room.getId())
                    .url((String) avatarMap.get("url"))
                    .contentType((String) avatarMap.get("format"))
                    .displayName((String) avatarMap.get("display_name"))
                    .publicId((String) avatarMap.get("public_id"))
                    .imageType(0) // avatar type
                    .bytes(NumberUtils.convertToLongV1(avatarMap.get("bytes")))
                    .build();
            imageRepository.save(avatar); // lưu vào db
            images.add(avatar);
        }

        // Xử lý gallery (nếu upload mới)
        if(request.getGallery() != null && !request.getGallery().isEmpty()) {
            // lấy danh sách gallery cũ
            List<Image> oldGallery = imageRepository.findImageActive(room.getId(),
                    TableConstants.TABLE_NAME.ROOM, 1L);
            for (Image img : oldGallery) {
                // Xóa trên Cloudinary
                commonService.deleteImage(img.getPublicId());
                // Xóa mềm trong DB
                img.setIsActive(false);

                // xoa trong list anh
                images.remove(img);
            }
            imageRepository.saveAll(oldGallery);

            // upload gallery mới lên Cloadinary
            for(MultipartFile file : request.getGallery()) {
                if(file != null && !file.isEmpty()) {
                    Map<String, Object> imageMap = (Map<String, Object>) commonService.uploadImage(file, Map.of());
                    Image galleryImage = Image.builder()
                            .entityType(TableConstants.TABLE_NAME.ROOM)
                            .entityId(room.getId())
                            .url((String) imageMap.get("url"))
                            .contentType((String) imageMap.get("format"))
                            .displayName((String) imageMap.get("display_name"))
                            .publicId((String) imageMap.get("public_id"))
                            .imageType(1) // gallery type
                            .bytes(NumberUtils.convertToLongV1(imageMap.get("bytes")))
                            .build();
                    imageRepository.save(galleryImage);
                    images.add(galleryImage);
                }
            }
        }

        // gan danh sach anh vao room
        room.setImages(images);
        log.info("Updated room successfully: id={}, code={}", room.getId(), room.getCode());
        return RoomMapper.toResponse(room);
    }


    /**
     * cập nhật status phòng
     * @param id
     * @param status
     * @return
     */
    @Override
    public RoomResponse updateRoomStatus(Long id, int status) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        // check status
        if (status < Constants.ROOM_STATUS.EMPTY || status > Constants.ROOM_STATUS.DEPOSITED) {
            throw new AppException(ErrorCode.INVALID_ROOM_STATUS);
        }

        room.setStatus(status);
        roomRepository.save(room);

        return RoomMapper.toResponse(room);
    }

    /**
     * Đánh dấu phòng không còn hoạt động (xóa mềm).
     * @param id ID của phòng cần xóa mềm
     * @throws AppException nếu không tìm thấy phòng với ID tương ứng
     */
    @Override
    public void softDeleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        roomRepository.delete(room);
        log.info("Soft deleted room successfully: id={}, code={}", room.getId(), room.getCode());
    }
}
