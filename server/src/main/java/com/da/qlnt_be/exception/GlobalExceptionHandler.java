package com.da.qlnt_be.exception;


import com.da.qlnt_be.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;


/* * @author TranDat
 * @since 2025-06-16
 *
 * Lớp xử lý ngoại lệ toàn cục cho ứng dụng.
 * Sử dụng để bắt và xử lý các ngoại lệ xảy ra trong ứng dụng,
 * bao gồm cả ngoại lệ tùy chỉnh và ngoại lệ từ các phương thức kiểm tra hợp lệ.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    // --- Ngoại lệ chưa được xử lý cụ thể ---
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<?>> handleUncategorizedException(Exception exception) {
        log.error("Uncategorized exception:", exception);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(exception.getMessage())
                        .build()
        );
    }

    // --- Ngoại lệ tùy chỉnh ---
    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<?>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.warn("Application exception: {}", errorCode);
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    // --- Lỗi validate dữ liệu ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            errorCode = ErrorCode.VALIDATION_FAILED;
        }

        log.debug("Validation failed: {}", exception.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    // --- Lỗi truy cập không đủ quyền ---
    @ExceptionHandler(AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse<?>> handleAccessDenied(AuthorizationDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        log.warn("Access denied: {}", exception.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    // --- Lỗi vi phạm ràng buộc dữ liệu (unique, foreign key, ...) ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ApiResponse<?>> handleDataConflict(DataIntegrityViolationException ex) {
        ErrorCode errorCode = ErrorCode.DATA_CONFLICT;
        log.warn("Data integrity violation: {}", ex.getMostSpecificCause().getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

}
