package com.da.qlnt_be.utils;

import lombok.Getter;

public final class Constants {
    public static enum UserStatus {
        ACTIVE,
        INACTIVE,
        LOCKED
    }
    // thêm các hằng số khác nếu cần thiết

    public interface ROOM_STATUS {
        int EMPTY = 0;       // Phòng trống
        int RENTED = 1;      // Đang cho thuê
        int MAINT = 2;       // Bảo trì
        int DEPOSITED = 3;   // Đã cọc

        static String getStatusName(int status) {
            switch (status) {
                case EMPTY:
                    return "EMPTY";
                case RENTED:
                    return "RENTED";
                case MAINT:
                    return "MAINT";
                case DEPOSITED:
                    return "DEPOSITED";
                default:
                    return "UNKNOWN";
            }
        }
    }

    public interface ASSET_ITEM_STATUS {
        int GOOD = 0;       // Tốt
        int BROKEN = 1;      // hỏng
        int REPAIR = 2;       // Đang sửa

        static String getStatusName(int status) {
            switch (status) {
                case GOOD:
                    return "GOOD";
                case BROKEN:
                    return "BROKEN";
                case REPAIR:
                    return "REPAIR";
                default:
                    return "UNKNOWN";
            }
        }
    }

}
