import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import {
    calculateAge,
    calculateAgeDetailed,
    calculateAgeDecimal,
} from '../commons/calculateAge.js';

describe('calculateAge', () => {
    beforeEach(() => {
        // Mock current date to 2026-02-01
        vi.useFakeTimers();
        vi.setSystemTime(new Date('2026-02-01'));
    });

    afterEach(() => {
        vi.useRealTimers();
    });

    describe('calculateAge - basic age calculation', () => {
        it('should return null for null input', () => {
            expect(calculateAge(null)).toBeNull();
        });

        it('should return null for undefined input', () => {
            expect(calculateAge(undefined)).toBeNull();
        });

        it('should calculate age correctly when birthday has passed', () => {
            // Birthday: 2000-01-15, today: 2026-02-01 => 26 years old
            expect(calculateAge('2000-01-15')).toBe(26);
        });

        it('should calculate age correctly when birthday has not passed yet', () => {
            // Birthday: 2000-03-15, today: 2026-02-01 => still 25
            expect(calculateAge('2000-03-15')).toBe(25);
        });

        it('should calculate age correctly on birthday', () => {
            // Birthday: 2000-02-01, today: 2026-02-01 => 26 years old
            expect(calculateAge('2000-02-01')).toBe(26);
        });

        it('should handle Date object input', () => {
            expect(calculateAge(new Date('2010-01-01'))).toBe(16);
        });
    });

    describe('calculateAgeDetailed - detailed age breakdown', () => {
        it('should return null for null input', () => {
            expect(calculateAgeDetailed(null)).toBeNull();
        });

        it('should return years, months, days correctly', () => {
            // Birthday: 2025-01-01, today: 2026-02-01
            const result = calculateAgeDetailed('2025-01-01');
            expect(result.years).toBe(1);
            expect(result.months).toBe(1);
            expect(result.days).toBe(0);
        });

        it('should handle day overflow correctly', () => {
            // Birthday: 2026-01-15, today: 2026-02-01 => 0 years, 0 months, 17 days
            const result = calculateAgeDetailed('2026-01-15');
            expect(result.years).toBe(0);
            expect(result.months).toBe(0);
            expect(result.days).toBe(17);
        });
    });

    describe('calculateAgeDecimal - decimal age', () => {
        it('should return null for null input', () => {
            expect(calculateAgeDecimal(null)).toBeNull();
        });

        it('should return decimal age with one decimal place', () => {
            // Birthday: 2016-02-01 (exactly 10 years ago)
            const result = calculateAgeDecimal('2016-02-01');
            expect(result).toBeCloseTo(10.0, 1);
        });

        it('should calculate fractional years', () => {
            // Birthday: 2025-08-01 (about 0.5 years ago)
            const result = calculateAgeDecimal('2025-08-01');
            expect(result).toBeCloseTo(0.5, 1);
        });
    });
});
