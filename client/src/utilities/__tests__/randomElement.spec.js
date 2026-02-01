import { describe, it, expect, vi } from 'vitest';
import randomElement from '../randomElement/randomeElement.js';

describe('randomElement', () => {
    it('should return null for null input', () => {
        expect(randomElement(null)).toBeNull();
    });

    it('should return null for undefined input', () => {
        expect(randomElement(undefined)).toBeNull();
    });

    it('should return null for empty array', () => {
        expect(randomElement([])).toBeNull();
    });

    it('should return the only element from single-element array', () => {
        expect(randomElement(['only'])).toBe('only');
    });

    it('should return an element from the array', () => {
        const array = ['a', 'b', 'c'];
        const result = randomElement(array);
        expect(array).toContain(result);
    });

    it('should work with number array', () => {
        const array = [1, 2, 3, 4, 5];
        const result = randomElement(array);
        expect(array).toContain(result);
    });

    it('should have reasonable distribution', () => {
        // Mock Math.random to test specific indices
        const array = ['a', 'b', 'c'];

        vi.spyOn(Math, 'random').mockReturnValue(0);
        expect(randomElement(array)).toBe('a');

        vi.spyOn(Math, 'random').mockReturnValue(0.5);
        expect(randomElement(array)).toBe('b');

        vi.spyOn(Math, 'random').mockReturnValue(0.99);
        expect(randomElement(array)).toBe('c');

        vi.restoreAllMocks();
    });
});
