import { describe, it, expect } from 'vitest';
import formatStr from '../formatString/formatString.js';

describe('formatStr', () => {
    it('should replace single placeholder', () => {
        expect(formatStr('Hello {0}!', 'World')).toBe('Hello World!');
    });

    it('should replace multiple placeholders', () => {
        expect(formatStr('{0} is {1} years old', 'John', 25)).toBe(
            'John is 25 years old',
        );
    });

    it('should replace placeholders in any order', () => {
        expect(formatStr('{1} before {0}', 'first', 'second')).toBe(
            'second before first',
        );
    });

    it('should keep placeholder if argument not provided', () => {
        expect(formatStr('Hello {0} and {1}!', 'World')).toBe(
            'Hello World and {1}!',
        );
    });

    it('should handle empty string', () => {
        expect(formatStr('')).toBe('');
    });

    it('should handle string without placeholders', () => {
        expect(formatStr('No placeholders here')).toBe('No placeholders here');
    });

    it('should handle numeric arguments', () => {
        expect(formatStr('Value: {0}', 123)).toBe('Value: 123');
    });
});
