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
            async put(url, param) {
                return { status: true, result: {} };
            }
            async delete(url) {
                return { status: true, result: {} };
            }
        },
    };
});

import userApi from '../userApi.js';

describe('UserApi', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should have correct API URL', () => {
        expect(userApi.ApiURL).toBe('/api/users');
    });

    describe('getAll', () => {
        it('should call get with pagination params', async () => {
            const getSpy = vi.spyOn(userApi, 'get');
            await userApi.getAll(0, 10);
            expect(getSpy).toHaveBeenCalledWith(
                expect.stringContaining('getAll?page=0&size=10'),
            );
        });

        it('should use default pagination values', async () => {
            const getSpy = vi.spyOn(userApi, 'get');
            await userApi.getAll();
            expect(getSpy).toHaveBeenCalledWith(
                expect.stringContaining('page=0&size=100'),
            );
        });
    });

    describe('getUserById', () => {
        it('should call get with user ID', async () => {
            const getSpy = vi.spyOn(userApi, 'get');
            await userApi.getUserById(123);
            expect(getSpy).toHaveBeenCalledWith(
                expect.stringContaining('/123'),
            );
        });
    });

    describe('getUserInfo', () => {
        it('should call get with my-info endpoint', async () => {
            const getSpy = vi.spyOn(userApi, 'get');
            await userApi.getUserInfo();
            expect(getSpy).toHaveBeenCalledWith(
                expect.stringContaining('/my-info'),
            );
        });
    });

    describe('createSimpleUserInfo', () => {
        it('should call post with create-basic endpoint', async () => {
            const postSpy = vi.spyOn(userApi, 'post');
            const userInfo = { username: 'test' };
            await userApi.createSimpleUserInfo(userInfo);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/create-basic'),
                userInfo,
            );
        });
    });

    describe('createFullUserInfo', () => {
        it('should call post with create-detail endpoint', async () => {
            const postSpy = vi.spyOn(userApi, 'post');
            const userInfo = { username: 'test', fullName: 'Test User' };
            await userApi.createFullUserInfo(userInfo);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/create-detail'),
                userInfo,
            );
        });
    });

    describe('updateUserInfo', () => {
        it('should call post with update endpoint', async () => {
            const postSpy = vi.spyOn(userApi, 'post');
            const userInfo = { id: 1, fullName: 'Updated Name' };
            await userApi.updateUserInfo(userInfo);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/update'),
                userInfo,
            );
        });
    });

    describe('deleteUser', () => {
        it('should call delete with user ID', async () => {
            const deleteSpy = vi.spyOn(userApi, 'delete');
            await userApi.deleteUser(123);
            expect(deleteSpy).toHaveBeenCalledWith(
                expect.stringContaining('/123'),
            );
        });
    });

    describe('rateUser', () => {
        it('should call put with rating params', async () => {
            const putSpy = vi.spyOn(userApi, 'put');
            await userApi.rateUser(1, 5);
            expect(putSpy).toHaveBeenCalledWith(
                expect.stringContaining('userId=1&ratePoint=5'),
            );
        });
    });
});
