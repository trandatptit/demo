package com.da.qlnt_be.services.impl;

import com.da.qlnt_be.dto.request.UserRequest;
import com.da.qlnt_be.dto.response.UserResponse;
import com.da.qlnt_be.exception.AppException;
import com.da.qlnt_be.exception.ErrorCode;
import com.da.qlnt_be.mapper.UserMapper;
import com.da.qlnt_be.model.*;
import com.da.qlnt_be.repository.*;
import com.da.qlnt_be.services.CommonService;
import com.da.qlnt_be.services.UserService;
import com.da.qlnt_be.utils.NumberUtils;
import com.da.qlnt_be.utils.TableConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RolePermissionRepository rolePermissionRepository;
    UserRoleRepository userRoleRepository;
    CommonService commonService;
    ImageRepository imageRepository;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) throws IOException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        } else if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        } else if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        } else {
            Role defaultRole = roleRepository.findByName("USER")
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name("USER")
                                    .build()
                    ));

            Permission defaultPermission = permissionRepository.findByCode("USER_NEW")
                    .orElseGet(() -> permissionRepository.save(
                            Permission.builder()
                                    .code("USER_NEW")
                                    .description("Permission for new user")
                                    .build()
                    ));

            if (!rolePermissionRepository.existsByRoleAndPermission(defaultRole, defaultPermission)) {
                rolePermissionRepository.save(RolePermission.builder()
                        .role(defaultRole)
                        .permission(defaultPermission)
                        .build());
            }

            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAddress(request.getAddress());
            user.setDob(request.getDob());
            userRepository.save(user);

            UserRole userRole = userRoleRepository.findByUserAndRole(user, defaultRole)
                    .orElseGet(() -> userRoleRepository.save(
                            UserRole.builder()
                                    .user(user)
                                    .role(defaultRole)
                                    .build()
                    ));
            user.getUserRoles().add(userRole);

            Image avatar = null;
            if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
                Map<String, Object> avatarMap = (Map<String, Object>) commonService.uploadImage(request.getAvatar(), Map.of());
                avatar = Image.builder()
                        .entityType(TableConstants.TABLE_NAME.USER)
                        .entityId(user.getId())
                        .url((String) avatarMap.get("url"))
                        .contentType((String) avatarMap.get("format"))
                        .displayName((String) avatarMap.get("display_name"))
                        .publicId((String) avatarMap.get("public_id"))
                        .imageType(0) // avatar type
                        .bytes(NumberUtils.convertToLongV1(avatarMap.get("bytes")))
                        .build();
                imageRepository.save(avatar);
            }

            // gán vào user luôn để mapper thấy
            user.setImages(Collections.singletonList(avatar));

            return UserMapper.toResponse(user);
        }
    }

    @Override
    public UserResponse updateUser(UserRequest request) throws IOException {
        User user = userRepository.findByIdAndIsActiveTrue(
                request.getId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        if (!user.getUsername().equals(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_CANNOT_CHANGE);

        } else if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        } else if (!user.getPhoneNumber().equals(request.getPhoneNumber())
                && userRepository.existsByPhoneNumber(request.getPhoneNumber())
        ) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        } else {
            if(request.getPassword() != null && !request.getPassword().isEmpty()){
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAddress(request.getAddress());
            user.setDob(request.getDob());
            userRepository.save(user);

            // Xử lý ảnh avatar (nếu người dùng upload ảnh mới)
            if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
                // Lấy danh sách avatar cũ (imageType = 0)
                List<Image> oldAvatars = imageRepository.findImageActive(
                        user.getId(),
                        TableConstants.TABLE_NAME.USER,
                        0L
                );

                // Xóa avatar cũ: xóa trên Cloudinary + đánh dấu inactive trong DB
                for (Image img : oldAvatars) {
                    commonService.deleteImage(img.getPublicId());
                    imageRepository.delete(img);
                }


                // Upload avatar mới lên Cloudinary
                Map<String, Object> avatarMap =
                        (Map<String, Object>) commonService.uploadImage(request.getAvatar(), Map.of());

                Image avatar = Image.builder()
                        .entityType(TableConstants.TABLE_NAME.USER)
                        .entityId(user.getId())
                        .url((String) avatarMap.get("url"))
                        .contentType((String) avatarMap.get("format"))
                        .displayName((String) avatarMap.get("display_name"))
                        .publicId((String) avatarMap.get("public_id"))
                        .imageType(0) // avatar type
                        .bytes(NumberUtils.convertToLongV1(avatarMap.get("bytes")))
                        .build();

                // Lưu avatar mới
                imageRepository.save(avatar);
            }
            return UserMapper.toResponse(user);
        }
    }

    @PreAuthorize("hasAnyAuthority('USER_READ')")
    @Override
    public UserResponse getUserById(Long id) {
        return UserMapper.toResponse(userRepository
                .findByIdAndIsActiveTrue(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED))
        );
    }

    @PreAuthorize("hasAnyAuthority('USER_DELETE')")
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
    }

    @PreAuthorize("hasAnyAuthority('USER_READ')")
    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllByIsActiveTrueOrderById(pageable);
        return users.map(UserMapper::toResponse);

    }

    @Override
    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return UserMapper.toResponse(user);
    }
}
