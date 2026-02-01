import { describe, it, expect } from 'vitest';
import getKeyLocalStorage from '../commons/getKeyLocalStorage.js';

describe('getKeyLocalStorage', () => {
    it('should add Everest_ prefix to key', () => {
        expect(getKeyLocalStorage('Token')).toBe('Everest_Token');
    });

    it('should handle empty string', () => {
        expect(getKeyLocalStorage('')).toBe('Everest_');
    });

    it('should handle various key names', () => {
        expect(getKeyLocalStorage('UserInfo')).toBe('Everest_UserInfo');
        expect(getKeyLocalStorage('IsAdmin')).toBe('Everest_IsAdmin');
        expect(getKeyLocalStorage('Settings')).toBe('Everest_Settings');
    });
});
