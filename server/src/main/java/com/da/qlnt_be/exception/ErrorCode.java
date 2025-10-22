package com.da.qlnt_be.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
/**
 * @author TranDat
 * @since 2025-06-16
 *
 * Enum đại diện cho các mã lỗi trong ứng dụng.
 * Mỗi mã lỗi có một mã số, thông điệp và mã trạng thái HTTP tương ứng.
 */
public enum ErrorCode {
    // --- Lỗi hệ thống chung ---
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    DATA_CONFLICT(1002, "Data conflict", HttpStatus.CONFLICT),

    // --- Lỗi xác thực / phân quyền ---
    UNAUTHENTICATED(2001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2002, "You do not have permission", HttpStatus.FORBIDDEN),

    // --- Lỗi tài nguyên ---
    FILE_NOT_FOUND(3001, "File not found", HttpStatus.NOT_FOUND),
    RESOURCE_NOT_FOUND(3002, "Resource not found", HttpStatus.NOT_FOUND),
    OWNER_NOT_EXISTED(3003, "Owner not existed", HttpStatus.NOT_FOUND),
    TENANT_NOT_EXISTED(3004, "Tenant not existed", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(3005, "User not existed", HttpStatus.NOT_FOUND),
    USER_NOT_ACTIVE(3006, "User not active", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(3005, "User not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_EXISTED(3007, "Role not existed", HttpStatus.NOT_FOUND),
    ROLE_NOT_ASSIGNED_TO_USER(3008, "Role not assigned to user", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(3009, "Permission not existed", HttpStatus.NOT_FOUND),
    PERMISSION_ASSIGNED_TO_ROLE(3010, "Permission assigned to role", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_ASSIGNED_TO_ROLE(3011, "Permission not assigned to role", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(3012, "Username existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(3013, "Email existed", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED(3014, "Phone number existed", HttpStatus.BAD_REQUEST),

    HOUSE_NOT_EXISTED(3015, "House not existed", HttpStatus.NOT_FOUND),
    ROOM_NOT_EXISTED(3016, "Room not existed", HttpStatus.NOT_FOUND),
    INVALID_ROOM_STATUS(3017, "Invalid room status", HttpStatus.BAD_REQUEST),
    INVALID_ROOM_CODE(3018, "Invalid room code", HttpStatus.BAD_REQUEST),
    ROOM_CODE_ALREADY_EXIST(3019, "Room code already exist", HttpStatus.BAD_REQUEST),
    ASSET_NAME_ALREADY_EXIST(3020, "Assets already exist in this room", HttpStatus.BAD_REQUEST),
    ASSET_NOT_EXIST(3021, "Asset not exist", HttpStatus.NOT_FOUND),
    ASSET_ITEM_NOT_EXIST(3022, "Asset item not exist", HttpStatus.NOT_FOUND),
    INVALID_ASSET_ITEM_STATUS(3023, "Invalid asset item status", HttpStatus.BAD_REQUEST),

    RULE_NOT_EXIST(3024, "Rule not existed", HttpStatus.NOT_FOUND),
    RULE_ALREADY_EXISTS(3025, "Rule already exists", HttpStatus.BAD_REQUEST),
    // --- Lỗi dữ liệu đầu vào ---
    VALIDATION_FAILED(4001, "Validation failed", HttpStatus.BAD_REQUEST),
    INVALID_INPUT(4002, "Invalid input data", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(4003, "Role existed", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(4004, "Permission existed", HttpStatus.BAD_REQUEST),
    USERNAME_CANNOT_CHANGE(4005, "Username cannot be changed", HttpStatus.BAD_REQUEST),
    ROLE_ALREADY_ASSIGNED_TO_USER(4006, "Role already assigned to user", HttpStatus.BAD_REQUEST),

    // --- Lỗi xử lý upload ---
    UPLOAD_FAILED(5001, "Upload failed", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_FAILED(5002, "Delete failed", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
