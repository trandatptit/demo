package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.OwnerRequest;
import com.da.qlnt_be.dto.response.UserResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.mapper.UserMapper;
import com.da.qlnt_be.model.Owner;
import com.da.qlnt_be.repository.OwnerRepository;
import com.da.qlnt_be.services.OwnerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OwnerServiceImpl implements OwnerService {
    OwnerRepository ownerRepository;

    /**
     * Tạo mới chủ trọ (Owner)
     * @param request dữ liệu gửi từ client
     */
    @Override
    public UserResponse createOwner(OwnerRequest request) {
        // Khởi tạo entity Owner
        Owner owner = new Owner();
        owner.setUsername(request.getName());
        owner.setEmail(request.getEmail());
        owner.setPhoneNumber(request.getPhone());
        owner.setAddress(request.getAddress());
        owner.setPassword(request.getPassword());
        owner.setIsActive(true);
        owner.setCreatedAt(LocalDateTime.now());
        owner.setVerified(false); // Mặc định chưa xác thực

        // Lưu vào DB
        ownerRepository.save(owner);
        return UserMapper.toResponse(owner);
    }

    /**
     * Cập nhật thông tin chủ trọ
     * @param id id của Owner cần cập nhật
     * @param request dữ liệu mới từ client
     */
    @Override
    @PreAuthorize("hasAnyAuthority('OWNER_CREATE')")
    public UserResponse updateOwner(Long id, OwnerRequest request) {
        // Tìm Owner trong DB, nếu không có thì ném exception
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.OWNER_NOT_EXISTED));

        // Cập nhật các thông tin cho Owner
        owner.setUsername(request.getName());
        owner.setPhoneNumber(request.getPhone());
        owner.setAddress(request.getAddress());
        owner.setBankAcc(request.getBankAcc());
        owner.setBusinessNo(request.getBusinessNo());
        owner.setTaxCode(request.getTaxCode());
        owner.setUpdatedAt(LocalDateTime.now());
        ownerRepository.save(owner);
        return UserMapper.toResponse(owner);
    }

    /**
     * Lấy danh sách tất cả chủ trọ đang hoạt động (isActive = true)
     */
    @Override
    @PreAuthorize("hasAnyAuthority('OWNER_READ')")
    public Page<?> getAllOwners(Pageable pageable) {
        Page<Owner> owners = ownerRepository.findAllByIsActiveTrue(pageable);
        return owners.map(UserMapper::toResponse);

    }

    /**
     * Lấy thông tin chi tiết 1 chủ trọ theo id
     */
    @Override
    @PreAuthorize("hasAnyAuthority('OWNER_READ')")
    public UserResponse getOwnerById(Long id) {
        // Tìm Owner trong DB, nếu không có thì ném exception
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.OWNER_NOT_EXISTED));
        return UserMapper.toResponse(owner);
    }

    /**
     * Xóa mềm (soft delete): chỉ set isActive = false thay vì xóa khỏi DB
     */
    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_READ')")
    public void softDeleteOwner(Long id) {
        // Tìm Owner trong DB, nếu không có thì ném exception
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.OWNER_NOT_EXISTED));
        owner.setIsActive(false);
        ownerRepository.save(owner);
    }

//    /**
//     * Hàm tiện ích để map dữ liệu từ Entity -> Response DTO
//     */
//    private OwnerResponse mapToResponse(Owner owner) {
//        OwnerResponse res = new OwnerResponse();
//        res.setId(owner.getId());
//        res.setName(owner.getUsername());
//        res.setEmail(owner.getEmail());
//        res.setPhone(owner.getPhoneNumber());
//        res.setAddress(owner.getAddress());
//        res.setBankAcc(owner.getBankAcc());
//        res.setBusinessNo(owner.getBusinessNo());
//        res.setTaxCode(owner.getTaxCode());
//        res.setVerified(owner.getVerified());
//        res.setActive(owner.getIsActive());
//        res.setCreatedAt(owner.getCreatedAt());
//        res.setUpdatedAt(owner.getUpdatedAt());
//        return res;
//    }
}
