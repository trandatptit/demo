package com.da.qlnt_be.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class NumberUtils {
    // Chuyển đổi đối tượng thành Number (Integer, Long, Double)
    public static Number convertToNumber(Object value) {
        if (value instanceof Number) {
            return (Number) value;
        } else if (value instanceof String strValue) {
            try {
                return Integer.parseInt(strValue);
            } catch (NumberFormatException e1) {
                try {
                    return Long.parseLong(strValue);
                } catch (NumberFormatException e2) {
                    try {
                        return Double.parseDouble(strValue);
                    } catch (NumberFormatException e3) {
                        return null;
                    }
                }
            }
        } else {
            return null;
        }
    }

    // Chuyển đổi đối tượng thành Long
    public static Long convertToLongV1(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number number) {
            return number.longValue();
        } else if (value instanceof String strValue) {
            try {
                return Long.parseLong(strValue);
            } catch (NumberFormatException e1) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Convert an Object to Long safely.
     *
     * - If value = null hoặc không parse được -> trả về defaultValue
     * - Hỗ trợ input: Number, String
     *
     * @param value         object cần chuyển đổi
     * @param defaultValue  giá trị mặc định nếu không thể chuyển đổi
     * @return Long
     */
    public static Long convertToLongV2(Object value, Long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof String strValue) {
            try {
                return Long.parseLong(strValue.trim());
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public static Integer convertToInteger(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number number) {
            return number.intValue();
        } else if (value instanceof String strValue) {
            try {
                return Integer.parseInt(strValue);
            } catch (NumberFormatException e1) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Convert an Object to Integer safely.
     *
     * - Nếu value = null hoặc không parse được -> trả về defaultValue
     * - Hỗ trợ input: Number, String
     *
     * @param value         object cần chuyển đổi
     * @param defaultValue  giá trị mặc định nếu không thể chuyển đổi
     * @return Integer
     */
    public static Integer convertToIntegerV2(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        if (value instanceof String strValue) {
            try {
                return Integer.parseInt(strValue.trim());
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public static Double convertToDouble(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number number) {
            return number.doubleValue();
        } else if (value instanceof String strValue) {
            try {
                return Double.parseDouble(strValue);
            } catch (NumberFormatException e1) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Convert an Object to Double safely.
     *
     * - Nếu value = null hoặc không parse được -> trả về defaultValue
     * - Hỗ trợ input: Number, String
     *
     * @param value         object cần chuyển đổi
     * @param defaultValue  giá trị mặc định nếu không thể chuyển đổi
     * @return Double
     */
    public static Double convertToDoubleV2(Object value, Double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        if (value instanceof String strValue) {
            try {
                return Double.parseDouble(strValue.trim());
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /*
        * Chia 2 số, trả về 0 nếu num2 = 0 hoặc num1 = 0
     */
    public static Double div(Double num1, Double num2) {
        if (num1 == null || num2 == null
                || num1.intValue() == 0 || num2.intValue() == 0
        ) {
            return 0.0;
        }
        return num1 / num2;
    }


    /**
     * Làm tròn lên, chặn âm
     * @param value giá trị cần làm tròn
     * @param scale số chữ số thập phân
     * @param roundingMode phương thức làm tròn
     */
    public static Double roundNonNegative(Double value, int scale, RoundingMode roundingMode) {
        if (value == null) {
            return null;
        }
        value = Math.max(0, value);
        return new BigDecimal(value)
                .setScale(scale, roundingMode)
                .doubleValue();
    }

    // Ép giá trị về trong khoảng min và max
    public static Double clamp(Double value, Double min, Double max) {
        if (value == null) {
            return null;
        }
        if (min != null && value < min) {
            return min;
        }
        if (max != null && value > max) {
            return max;
        }
        return value;
    }

    /**
     * Chuyển đổi giá trị thành phần trăm, làm tròn và chặn âm
     * @param value giá trị cần chuyển đổi - ví dụ: 0.256 -> 25.6%
     * @param scale số chữ số thập phân
     * @return giá trị phần trăm đã làm tròn và chặn âm
     */
    public static Double toPercent(Double value, int scale) {
        return Math.min(100, roundNonNegative(value * 100, scale, RoundingMode.HALF_UP));
    }

}
