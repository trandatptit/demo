import { describe, it, expect } from 'vitest';
import {
    formatDate,
    getAvatarColor,
    getRoleLabel,
    getRoleSeverity,
    getRoleIcon,
} from '../useAccountHelpers.js';

describe('useAccountHelpers', () => {
    describe('formatDate', () => {
        it('should return N/A for null', () => {
            expect(formatDate(null)).toBe('N/A');
        });

        it('should return N/A for undefined', () => {
            expect(formatDate(undefined)).toBe('N/A');
        });

        it('should format date to Vietnamese locale', () => {
            const result = formatDate('2024-12-25T10:30:00');
            expect(result).toContain('25');
            expect(result).toContain('12');
            expect(result).toContain('2024');
        });
    });

    describe('getAvatarColor', () => {
        it('should return a hex color', () => {
            const result = getAvatarColor('John');
            expect(result).toMatch(/^#[0-9a-f]{6}$/i);
        });

        it('should return consistent color for same name', () => {
            expect(getAvatarColor('Alice')).toBe(getAvatarColor('Alice'));
        });

        it('should return default color for undefined name', () => {
            const result = getAvatarColor(undefined);
            expect(result).toMatch(/^#[0-9a-f]{6}$/i);
        });
    });

    describe('getRoleLabel', () => {
        it('should return Admin for admin role', () => {
            expect(getRoleLabel('admin')).toBe('Admin');
        });

        it('should return User for user role', () => {
            expect(getRoleLabel('user')).toBe('User');
        });

        it('should return Manager for manager role', () => {
            expect(getRoleLabel('manager')).toBe('Manager');
        });

        it('should handle uppercase roles', () => {
            expect(getRoleLabel('ADMIN')).toBe('Admin');
        });

        it('should return Unknown for null', () => {
            expect(getRoleLabel(null)).toBe('Unknown');
        });
    });

    describe('getRoleSeverity', () => {
        it('should return danger for admin', () => {
            expect(getRoleSeverity('admin')).toBe('danger');
        });

        it('should return warning for manager', () => {
            expect(getRoleSeverity('manager')).toBe('warning');
        });

        it('should return info for user', () => {
            expect(getRoleSeverity('user')).toBe('info');
        });

        it('should return secondary for unknown role', () => {
            expect(getRoleSeverity('unknown')).toBe('secondary');
        });
    });

    describe('getRoleIcon', () => {
        it('should return shield icon for admin', () => {
            expect(getRoleIcon('admin')).toBe('pi pi-shield');
        });

        it('should return star icon for manager', () => {
            expect(getRoleIcon('manager')).toBe('pi pi-star');
        });

        it('should return user icon for user', () => {
            expect(getRoleIcon('user')).toBe('pi pi-user');
        });

        it('should return user icon for unknown role', () => {
            expect(getRoleIcon('unknown')).toBe('pi pi-user');
        });
    });
});
