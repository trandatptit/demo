import { describe, it, expect, vi, beforeEach } from 'vitest';
import findHeightGen from '../findHeightGen/findHeightGen.js';
import genderType from '@/constants/genderType.js';

describe('findHeightGen', () => {
    it('should calculate male height prediction correctly', () => {
        const params = {
            gender: genderType.MALE,
            height: 165,
            fatherHeight: 175,
            motherHeight: 160,
        };
        // Formula for male: (175 + 160 + 13) / 2 = 174
        expect(findHeightGen(params)).toBe(174);
    });

    it('should calculate female height prediction correctly', () => {
        const params = {
            gender: genderType.FEMALE,
            height: 155,
            fatherHeight: 175,
            motherHeight: 160,
        };
        // Formula for female: (175 + 160 - 13) / 2 = 161
        expect(findHeightGen(params)).toBe(161);
    });

    it('should handle tall parents for male', () => {
        const params = {
            gender: genderType.MALE,
            height: 170,
            fatherHeight: 185,
            motherHeight: 170,
        };
        // (185 + 170 + 13) / 2 = 184
        expect(findHeightGen(params)).toBe(184);
    });

    it('should handle short parents for female', () => {
        const params = {
            gender: genderType.FEMALE,
            height: 145,
            fatherHeight: 160,
            motherHeight: 150,
        };
        // (160 + 150 - 13) / 2 = 148.5
        expect(findHeightGen(params)).toBe(148.5);
    });

    it('should return NaN for undefined values', () => {
        const params = {
            gender: 999, // invalid gender
            height: undefined,
            fatherHeight: undefined,
            motherHeight: undefined,
        };
        // Will use female formula with undefined values => NaN
        const result = findHeightGen(params);
        expect(result).toBeNaN();
    });
});
