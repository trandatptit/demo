package com.da.qlnt_be.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

@Slf4j
public class StringUtils {
    // =========================
    // 1. Kiểm tra chuỗi
    // =========================

    /**
     * Kiểm tra chuỗi có null hoặc rỗng ("") không
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Kiểm tra chuỗi có null, rỗng hoặc chỉ toàn khoảng trắng không
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Ngược lại với isBlank: chỉ true khi chuỗi có ký tự không phải khoảng trắng
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    // =========================
    // 2. Xử lý khoảng trắng & default
    // =========================

    /**
     * Xóa khoảng trắng 2 đầu, nếu sau khi trim rỗng thì trả về null
     * Ví dụ: "   " -> null
     */
    public static String trimToNull(String str) {
        if (str == null) return null;
        String trimmed = str.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    /**
     * Trả về defaultValue nếu chuỗi null hoặc blank
     * Ví dụ: defaultIfBlank("   ", "N/A") -> "N/A"
     */
    public static String defaultIfBlank(String str, String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }

    // =========================
    // 3. Chuyển đổi chữ
    // =========================

    /**
     * Viết hoa chữ cái đầu tiên của chuỗi
     * Ví dụ: "hello" -> "Hello"
     */
    public static String capitalize(String str) {
        if (isBlank(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Viết thường chữ cái đầu tiên của chuỗi
     * Ví dụ: "Hello" -> "hello"
     */
    public static String uncapitalize(String str) {
        if (isBlank(str)) return str;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    // =========================
    // 4. Convert case
    // =========================

    /**
     * Chuyển camelCase -> snake_case
     * Ví dụ: "userName" -> "user_name"
     */
    public static String camelToSnake(String fieldName) {
        if (isBlank(fieldName)) return fieldName;
        return fieldName.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Chuyển snake_case -> camelCase
     * Ví dụ: "user_name" -> "userName"
     */
    public static String snakeToCamel(String snake) {
        if (isBlank(snake)) return snake;
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;
        for (char c : snake.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    // =========================
    // 5. Join / Split
    // =========================

    /**
     * Ghép các phần tử iterable thành chuỗi với delimiter
     * Ví dụ: join(List.of("A", "B", "C"), ",") -> "A,B,C"
     */
    public static String join(Iterable<?> items, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (Object item : items) {
            if (sb.length() > 0) sb.append(delimiter);
            sb.append(item);
        }
        return sb.toString();
    }

    /**
     * Tách chuỗi theo delimiter thành List<String>
     * Ví dụ: split("A,B,C", ",") -> ["A","B","C"]
     */
    public static List<String> split(String str, String delimiter) {
        if (isEmpty(str)) return List.of();
        return Arrays.asList(str.split(Pattern.quote(delimiter)));
    }

    // =========================
    // 6. Xử lý đặc biệt
    // =========================

    /**
     * Xóa dấu tiếng Việt (dùng cho search, index, so sánh)
     * Ví dụ: "Nguyễn Văn A" -> "Nguyen Van A"
     */
    public static String removeAccents(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }

    /**
     * Sinh chuỗi ngẫu nhiên chỉ gồm chữ cái và số
     * Ví dụ: randomAlphanumeric(8) -> "aZ3xT9qP"
     */
    public static String randomAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
