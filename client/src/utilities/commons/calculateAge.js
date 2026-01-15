/**
 * Tính tuổi từ ngày sinh đến ngày hiện tại
 * @param {Date|string} birthday - Ngày sinh (Date object hoặc string format)
 * @returns {number} - Tuổi (số nguyên)
 */
export function calculateAge(birthday) {
    if (!birthday) return null;

    const birthDate = new Date(birthday);
    const today = new Date();

    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    // Nếu chưa đến tháng sinh nhật hoặc đúng tháng nhưng chưa đến ngày
    if (
        monthDiff < 0 ||
        (monthDiff === 0 && today.getDate() < birthDate.getDate())
    ) {
        age--;
    }

    return age;
}

/**
 * Tính tuổi chi tiết (năm, tháng, ngày)
 * @param {Date|string} birthday - Ngày sinh (Date object hoặc string format)
 * @returns {object} - { years, months, days }
 */
export function calculateAgeDetailed(birthday) {
    if (!birthday) return null;

    const birthDate = new Date(birthday);
    const today = new Date();

    let years = today.getFullYear() - birthDate.getFullYear();
    let months = today.getMonth() - birthDate.getMonth();
    let days = today.getDate() - birthDate.getDate();

    if (days < 0) {
        months--;
        // Lấy số ngày của tháng trước
        const prevMonth = new Date(today.getFullYear(), today.getMonth(), 0);
        days += prevMonth.getDate();
    }

    if (months < 0) {
        years--;
        months += 12;
    }

    return { years, months, days };
}

/**
 * Tính tuổi dạng thập phân (ví dụ: 15.5 tuổi)
 * @param {Date|string} birthday - Ngày sinh (Date object hoặc string format)
 * @returns {number} - Tuổi dạng thập phân
 */
export function calculateAgeDecimal(birthday) {
    if (!birthday) return null;

    const birthDate = new Date(birthday);
    const today = new Date();

    const diffMs = today - birthDate;
    const ageDate = new Date(diffMs);

    // Chia cho số milliseconds trong 1 năm (365.25 ngày để tính cả năm nhuận)
    const ageDecimal = diffMs / (365.25 * 24 * 60 * 60 * 1000);

    return Math.round(ageDecimal * 10) / 10; // Làm tròn 1 chữ số thập phân
}

export default calculateAge;
