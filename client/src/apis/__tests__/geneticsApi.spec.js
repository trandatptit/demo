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

import geneticsApi from '../geneticsApi.js';

describe('GeneticsApi', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should have correct API URL', () => {
        expect(geneticsApi.ApiURL).toBe('/api/genetics');
    });

    describe('geneticResult30k', () => {
        it('should call get with userId param', async () => {
            const getSpy = vi.spyOn(geneticsApi, 'get');
            await geneticsApi.geneticResult30k(123);
            expect(getSpy).toHaveBeenCalledWith(
                expect.stringContaining('/results?userId=123'),
            );
        });
    });

    describe('saveResult30k', () => {
        it('should call post with save-result-30k endpoint', async () => {
            const postSpy = vi.spyOn(geneticsApi, 'post');
            const formData = {
                userId: 1,
                currentHeight: 165,
                hereditaryHeight: 170,
                potentialHeight: 175,
            };
            await geneticsApi.saveResult30k(formData);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/save-result-30k'),
                formData,
            );
        });
    });
});
