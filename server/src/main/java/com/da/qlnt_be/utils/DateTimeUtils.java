package com.da.qlnt_be.utils;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {

    // =========================
    // 1. Lấy thời gian hiện tại
    // =========================

    /**
     * Trả về LocalDateTime hiện tại theo múi giờ hệ thống
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Trả về LocalDate hiện tại theo múi giờ hệ thống
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    // =========================
    // 2. Timestamp <-> LocalDateTime
    // =========================

    /**
     * Chuyển LocalDateTime -> Timestamp (dùng khi lưu DB kiểu timestamp)
     */
    public static Timestamp toTimestamp(LocalDateTime ldt) {
        return ldt == null ? null : Timestamp.valueOf(ldt);
    }

    /**
     * Chuyển Timestamp -> LocalDateTime
     */
    public static LocalDateTime fromTimestamp(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }

    // =========================
    // 3. Epoch milli <-> LocalDateTime
    // =========================

    /**
     * Chuyển LocalDateTime -> mili giây epoch (long)
     * Thường dùng để lưu/so sánh số nguyên thay vì object
     */
    public static long toEpochMilli(LocalDateTime ldt) {
        return ldt == null ? 0 : ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Chuyển mili giây epoch -> LocalDateTime
     */
    public static LocalDateTime fromEpochMilli(long epochMilli) {
        return Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // =========================
    // 4. Format & Parse
    // =========================

    /**
     * Định dạng LocalDateTime sang chuỗi theo pattern
     * Ví dụ: format(ldt, "yyyy-MM-dd HH:mm:ss")
     */
    public static String format(LocalDateTime ldt, String pattern) {
        if (ldt == null) return null;
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Parse chuỗi -> LocalDateTime theo pattern
     * Ví dụ: parse("2025-10-04 15:30:00", "yyyy-MM-dd HH:mm:ss")
     */
    public static LocalDateTime parseToLocalDateTime(String str, String pattern) {
        if (str == null || str.isBlank()) return null;
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Parse chuỗi -> LocalDate theo pattern
     * Ví dụ: parseDate("2025-10-04", "yyyy-MM-dd")
     */
    public static LocalDate parseToLocalDate(String str, String pattern) {
        if (str == null || str.isBlank()) return null;
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));
    }

    // =========================
    // 5. Tính toán thời gian
    // =========================

    /**
     * Cộng thêm số ngày vào LocalDateTime
     */
    public static LocalDateTime addDays(LocalDateTime ldt, int days) {
        return ldt == null ? null : ldt.plusDays(days);
    }

    /**
     * Cộng thêm số tháng vào LocalDateTime
     */
    public static LocalDateTime addMonths(LocalDateTime ldt, int months) {
        return ldt == null ? null : ldt.plusMonths(months);
    }

    /**
     * Cộng thêm số phút vào LocalDateTime
     */
    public static LocalDateTime addMinutes(LocalDateTime ldt, int minutes) {
        return ldt == null ? null : ldt.plusMinutes(minutes);
    }

    /**
     * Tính khoảng cách giữa 2 mốc thời gian theo đơn vị (ngày, giờ, phút...)
     * Ví dụ: between(start, end, ChronoUnit.DAYS)
     */
    public static long between(LocalDateTime start, LocalDateTime end, ChronoUnit unit) {
        if (start == null || end == null) return 0;
        return unit.between(start, end);
    }

    // =========================
    // 6. Đầu ngày / Cuối ngày / Đầu tháng / Cuối tháng
    // =========================

    /**
     * Lấy đầu ngày (00:00:00.000)
     */
    public static LocalDateTime startOfDay(LocalDateTime ldt) {
        return ldt == null ? null : ldt.toLocalDate().atStartOfDay();
    }

    /**
     * Lấy cuối ngày (23:59:59.999...)
     */
    public static LocalDateTime endOfDay(LocalDateTime ldt) {
        return ldt == null ? null : ldt.toLocalDate().atTime(LocalTime.MAX);
    }

    /**
     * Lấy ngày đầu tiên của tháng
     */
    public static LocalDate firstDayOfMonth(LocalDate date) {
        return date == null ? null : date.withDayOfMonth(1);
    }

    /**
     * Lấy ngày cuối cùng của tháng
     */
    public static LocalDate lastDayOfMonth(LocalDate date) {
        return date == null ? null : date.withDayOfMonth(date.lengthOfMonth());
    }

    // =========================
    // 7. So sánh tiện ích
    // =========================

    /**
     * Kiểm tra 2 LocalDateTime có cùng ngày không
     */
    public static boolean isSameDay(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.toLocalDate().isEqual(d2.toLocalDate());
    }

    /**
     * Kiểm tra LocalDate có phải hôm nay không
     */
    public static boolean isToday(LocalDate date) {
        return date != null && date.isEqual(LocalDate.now());
    }
}