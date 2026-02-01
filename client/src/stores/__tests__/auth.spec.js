import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { setActivePinia, createPinia } from 'pinia';

// Mock localStorage
const localStorageMock = {
    store: {},
    getItem: vi.fn((key) => localStorageMock.store[key] || null),
    setItem: vi.fn((key, value) => {
        localStorageMock.store[key] = value;
    }),
    removeItem: vi.fn((key) => {
        delete localStorageMock.store[key];
    }),
    clear: vi.fn(() => {
        localStorageMock.store = {};
    }),
};
Object.defineProperty(global, 'localStorage', { value: localStorageMock });

// Mock APIs
vi.mock('@/apis/authApi', () => ({
    default: {
        login: vi.fn(() =>
            Promise.resolve({
                status: true,
                result: { token: 'test-token' },
            }),
        ),
        introspect: vi.fn(() =>
            Promise.resolve({
                status: true,
                result: { val: true },
            }),
        ),
    },
}));

vi.mock('@/apis/userApi', () => ({
    default: {
        getUserInfo: vi.fn(() =>
            Promise.resolve({
                status: true,
                result: { id: 1, name: 'Test User', isAdmin: false },
            }),
        ),
        createFullUserInfo: vi.fn(() =>
            Promise.resolve({
                status: true,
                result: { id: 1, name: 'New User', admin: false },
            }),
        ),
    },
}));

import { useAuthStore } from '../auth.js';
import authApi from '@/apis/authApi';
import userApi from '@/apis/userApi';

describe('auth store', () => {
    beforeEach(() => {
        setActivePinia(createPinia());
        localStorageMock.clear();
        vi.clearAllMocks();
    });

    afterEach(() => {
        vi.clearAllMocks();
    });

    describe('initial state', () => {
        it('should have loggedIn as false when no token', () => {
            const store = useAuthStore();
            expect(store.loggedIn).toBe(false);
        });

        it('should have null userInfo initially', () => {
            const store = useAuthStore();
            expect(store.userInfo).toBeNull();
        });

        it('should have isAdmin as false initially', () => {
            const store = useAuthStore();
            expect(store.isAdmin).toBe(false);
        });
    });

    describe('login', () => {
        it('should login successfully and set token', async () => {
            const store = useAuthStore();
            const result = await store.login({
                username: 'test',
                password: '123456',
            });

            expect(authApi.login).toHaveBeenCalled();
            expect(userApi.getUserInfo).toHaveBeenCalled();
            expect(store.loggedIn).toBe(true);
            expect(store.userInfo).toBeDefined();
        });

        it('should return undefined if login fails', async () => {
            authApi.login.mockResolvedValueOnce({ status: false });
            const store = useAuthStore();
            const result = await store.login({
                username: 'test',
                password: 'wrong',
            });

            expect(result).toBeUndefined();
        });
    });

    describe('logout', () => {
        it('should clear all auth data', async () => {
            const store = useAuthStore();
            await store.login({ username: 'test', password: '123' });
            store.logout();

            expect(store.loggedIn).toBe(false);
            expect(store.userInfo).toEqual({});
            expect(store.isAdmin).toBeNull();
            expect(localStorageMock.removeItem).toHaveBeenCalled();
        });
    });

    describe('checkToken', () => {
        it('should return true for valid token', async () => {
            localStorageMock.store['Everest_Token'] = 'valid-token';
            const store = useAuthStore();
            const isValid = await store.checkToken();
            expect(isValid).toBe(true);
        });

        it('should return true when no token exists', async () => {
            const store = useAuthStore();
            const isValid = await store.checkToken();
            expect(isValid).toBe(true);
        });

        it('should return false for invalid token', async () => {
            localStorageMock.store['Everest_Token'] = 'invalid-token';
            authApi.introspect.mockResolvedValueOnce({
                status: true,
                result: { val: false },
            });
            const store = useAuthStore();
            const isValid = await store.checkToken();
            expect(isValid).toBe(false);
        });
    });

    describe('signUp', () => {
        it('should create user and login', async () => {
            const store = useAuthStore();
            const result = await store.signUp({
                username: 'newuser',
                password: '123456',
                email: 'new@test.com',
            });

            expect(userApi.createFullUserInfo).toHaveBeenCalled();
            expect(store.loggedIn).toBe(true);
        });

        it('should return undefined if signup fails', async () => {
            userApi.createFullUserInfo.mockResolvedValueOnce({ status: false });
            const store = useAuthStore();
            const result = await store.signUp({
                username: 'newuser',
                password: '123',
            });

            expect(result).toBeUndefined();
        });
    });

    describe('getUserInfoCurrent', () => {
        it('should return cached userInfo', async () => {
            const store = useAuthStore();
            await store.login({ username: 'test', password: '123' });
            const result = await store.getUserInfoCurrent();
            expect(result).toBeDefined();
        });

        it('should fetch userInfo if not cached', async () => {
            const store = useAuthStore();
            const result = await store.getUserInfoCurrent();
            expect(userApi.getUserInfo).toHaveBeenCalled();
        });

        it('should return null if fetch fails', async () => {
            userApi.getUserInfo.mockResolvedValueOnce({ status: false });
            const store = useAuthStore();
            const result = await store.getUserInfoCurrent();
            expect(result).toBeFalsy();
        });
    });

    describe('login edge cases', () => {
        it('should return undefined if getUserInfo fails', async () => {
            userApi.getUserInfo.mockResolvedValueOnce({ status: false });
            const store = useAuthStore();
            const result = await store.login({
                username: 'test',
                password: '123456',
            });
            expect(result).toBeUndefined();
        });

        it('should handle missing result in login response', async () => {
            authApi.login.mockResolvedValueOnce({ status: true, result: null });
            const store = useAuthStore();
            const result = await store.login({
                username: 'test',
                password: '123456',
            });
            expect(result).toBeUndefined();
        });
    });

    describe('checkToken edge cases', () => {
        it('should return false when introspect status is false', async () => {
            localStorageMock.store['Everest_Token'] = 'some-token';
            authApi.introspect.mockResolvedValueOnce({
                status: false,
                result: { val: true },
            });
            const store = useAuthStore();
            const isValid = await store.checkToken();
            expect(isValid).toBe(false);
        });

        it('should return false when introspect result is null', async () => {
            localStorageMock.store['Everest_Token'] = 'some-token';
            authApi.introspect.mockResolvedValueOnce({
                status: true,
                result: null,
            });
            const store = useAuthStore();
            const isValid = await store.checkToken();
            expect(isValid).toBe(false);
        });
    });

    describe('signUp edge cases', () => {
        it('should handle missing result in signUp response', async () => {
            userApi.createFullUserInfo.mockResolvedValueOnce({
                status: true,
                result: null,
            });
            const store = useAuthStore();
            const result = await store.signUp({
                username: 'newuser',
                password: '123',
            });
            expect(result).toBeUndefined();
        });

        it('should handle failed login after signUp', async () => {
            authApi.login.mockResolvedValueOnce(null);
            const store = useAuthStore();
            const result = await store.signUp({
                username: 'newuser',
                password: '123456',
                email: 'new@test.com',
            });
            // Still returns userInfoResponse even if login fails
            expect(result).toBeUndefined();
        });
    });
});
