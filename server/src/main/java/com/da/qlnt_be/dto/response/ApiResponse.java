package com.da.qlnt_be.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @Builder.Default
    int code = 1000;
    String message;
    T result;

    public static <T> ApiResponse<T> success(T result) {
        return ApiResponse.<T>builder()
                .code(1000)
                .message("Success")
                .result(result)
                .build();
    }

    public static ApiResponse<Void> ok() {
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Success")
                .result(null)
                .build();
    }

}
