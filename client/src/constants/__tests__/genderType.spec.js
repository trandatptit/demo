import { describe, it, expect } from 'vitest';
import genderType from '../genderType.js';

describe('genderType constant', () => {
    it('should have MALE constant', () => {
        expect(genderType.MALE).toBe(1);
    });

    it('should have FEMALE constant', () => {
        expect(genderType.FEMALE).toBe(2);
    });

    it('should have OTHER constant', () => {
        expect(genderType.OTHER).toBe(3);
    });

    it('should have exactly 3 gender types', () => {
        expect(Object.keys(genderType).length).toBe(3);
    });
});
