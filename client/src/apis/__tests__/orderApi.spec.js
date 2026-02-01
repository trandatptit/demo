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

import orderApi from '../orderApi.js';

describe('OrderApi', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should have correct API URL', () => {
        expect(orderApi.ApiURL).toBe('/api/orders');
    });

    describe('getAll', () => {
        it('should call get with pagination params', async () => {
            const getSpy = vi.spyOn(orderApi, 'get');
            await orderApi.getAll({}, 0, 10);
            expect(getSpy).toHaveBeenCalledWith(
                expect.stringContaining('getAll?page=0&size=10'),
            );
        });

        it('should use default pagination values', async () => {
            const getSpy = vi.spyOn(orderApi, 'get');
            await orderApi.getAll({});
            expect(getSpy).toHaveBeenCalledWith(
                expect.stringContaining('page=0&size=10'),
            );
        });
    });

    describe('createOrder', () => {
        it('should call post with create endpoint', async () => {
            const postSpy = vi.spyOn(orderApi, 'post');
            const param = { userId: 1, amount: 30000 };
            await orderApi.createOrder(param);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/create'),
                param,
            );
        });
    });
});
