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

import aiApi from '../aiApi.js';

describe('AiApi', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('should have correct API URL', () => {
        expect(aiApi.ApiURL).toBe('/api/ai');
    });

    describe('genHeightParental', () => {
        it('should call post with gen-height-parental endpoint', async () => {
            const postSpy = vi.spyOn(aiApi, 'post');
            const data = { AGE: 15, GENDER: 'Nam' };
            await aiApi.genHeightParental(data);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/gen-height-parental'),
                data,
            );
        });
    });

    describe('genHeight30k', () => {
        it('should call post with gen-height-30k endpoint', async () => {
            const postSpy = vi.spyOn(aiApi, 'post');
            const data = { AGE: 15, CURRENT_HEIGHT_CM: 165 };
            await aiApi.genHeight30k(data);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/gen-height-30k'),
                data,
            );
        });
    });

    describe('getYearlyRoadmap', () => {
        it('should call post with yearly-roadmap endpoint', async () => {
            const postSpy = vi.spyOn(aiApi, 'post');
            const data = { AGE: 15 };
            await aiApi.getYearlyRoadmap(data);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/growth/yearly-roadmap'),
                data,
            );
        });
    });

    describe('genHeightMotivation', () => {
        it('should call post with gen-height-motivation endpoint', async () => {
            const postSpy = vi.spyOn(aiApi, 'post');
            const data = { user: 'test' };
            await aiApi.genHeightMotivation(data);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/gen-height-motivation'),
                data,
            );
        });
    });

    describe('genMotivation', () => {
        it('should call post with gen-motivation endpoint', async () => {
            const postSpy = vi.spyOn(aiApi, 'post');
            const data = { type: 'daily' };
            await aiApi.genMotivation(data);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/gen-motivation'),
                data,
            );
        });
    });

    describe('getCalciumTotalRequired', () => {
        it('should call post with calcium/total-required endpoint', async () => {
            const postSpy = vi.spyOn(aiApi, 'post');
            const data = { age: 15 };
            await aiApi.getCalciumTotalRequired(data);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/calcium/total-required'),
                data,
            );
        });
    });

    describe('getMonthlyPlan', () => {
        it('should call post with monthly-plan endpoint', async () => {
            const postSpy = vi.spyOn(aiApi, 'post');
            const data = { month: 1 };
            await aiApi.getMonthlyPlan(data);
            expect(postSpy).toHaveBeenCalledWith(
                expect.stringContaining('/growth/monthly-plan'),
                data,
            );
        });
    });
});
