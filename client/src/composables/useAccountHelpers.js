/**
 * Helper functions for account management
 * Extracted from ManagerAccount.vue for reusability and testability
 */

/**
 * Format date to Vietnamese locale
 * @param {Date|string} date - Date to format
 * @returns {string} Formatted date string
 */
export function formatDate(date) {
    if (!date) return 'N/A';
    return new Date(date).toLocaleDateString('vi-VN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
    });
}

/**
 * Get avatar background color based on name
 * @param {string} name - User name
 * @returns {string} Hex color code
 */
export function getAvatarColor(name) {
    const colors = [
        '#3b82f6', // blue
        '#10b981', // green
        '#f59e0b', // amber
        '#ef4444', // red
        '#8b5cf6', // violet
        '#ec4899', // pink
    ];
    const index = name?.charCodeAt(0) % colors.length;
    return colors[index] || colors[0];
}

/**
 * Get role display label
 * @param {string} role - Role code
 * @returns {string} Display label
 */
export function getRoleLabel(role) {
    const labels = {
        admin: 'Admin',
        user: 'User',
        manager: 'Manager',
    };
    return labels[role?.toLowerCase()] || role || 'Unknown';
}

/**
 * Get role severity for Tag component
 * @param {string} role - Role code
 * @returns {string} Severity level
 */
export function getRoleSeverity(role) {
    const severities = {
        admin: 'danger',
        manager: 'warning',
        user: 'info',
    };
    return severities[role?.toLowerCase()] || 'secondary';
}

/**
 * Get role icon class
 * @param {string} role - Role code
 * @returns {string} PrimeIcons class
 */
export function getRoleIcon(role) {
    const icons = {
        admin: 'pi pi-shield',
        manager: 'pi pi-star',
        user: 'pi pi-user',
    };
    return icons[role?.toLowerCase()] || 'pi pi-user';
}

export default {
    formatDate,
    getAvatarColor,
    getRoleLabel,
    getRoleSeverity,
    getRoleIcon,
};
