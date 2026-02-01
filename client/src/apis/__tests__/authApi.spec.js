import { describe, it, expect, vi, beforeEach } from 'vitest';

// Mock BaseApi
vi.mock('../baseApi.js', () => {
    return {
        default: class MockBaseApi {
            constructor(pathName = '') {
                this.ApiURL = `/api/${pathName}`;
            }
            async get(url) {
                return { status: true, result: {} };
            }
            async post(url, param) {
                return { status: true, result: {} };
            }
        },
    };
});

import authApi from '../authApi.js';

describe('AuthApi', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should have correct API URL', () => {
        expect(authApi.ApiURL).toBe('/api/auth');
    });

    describe('login', () => {
        it('should call post with login endpoint', async () => {
            const postSpy = vi.spyOn(authApi, 'post');
            const userInfo = { username: 'test', password: '123456' };
            await authApi.login(userInfo);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/login'),
                userInfo,
            );
        });
    });

    describe('introspect', () => {
        it('should call post with introspect endpoint', async () => {
            const postSpy = vi.spyOn(authApi, 'post');
            const paramToken = { token: 'abc123' };
            await authApi.introspect(paramToken);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/introspect'),
                paramToken,
            );
        });
    });

    describe('forgot', () => {
        it('should call post with forgot-password endpoint', async () => {
            const postSpy = vi.spyOn(authApi, 'post');
            const userInfo = { email: 'test@example.com' };
            await authApi.forgot(userInfo);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/forgot-password'),
                userInfo,
            );
        });
    });

    describe('logout', () => {
        it('should call post with logout endpoint', async () => {
            const postSpy = vi.spyOn(authApi, 'post');
            const paramToken = { token: 'abc123' };
            await authApi.logout(paramToken);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/logout'),
                paramToken,
            );
        });
    });
});
