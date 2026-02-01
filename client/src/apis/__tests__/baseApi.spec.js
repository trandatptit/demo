import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import axios from 'axios';

// Mock axios
vi.mock('axios', () => ({
    default: {
        get: vi.fn(),
        post: vi.fn(),
        put: vi.fn(),
        delete: vi.fn(),
        interceptors: {
            request: { use: vi.fn() },
            response: { use: vi.fn() },
        },
    },
}));

// Mock getKeyLocalStorage
vi.mock('@/utilities/commons/getKeyLocalStorage.js', () => ({
    default: vi.fn((key) => `Everest_${key}`),
}));

// Mock localStorage
const localStorageMock = {
    store: {},
    getItem: vi.fn((key) => localStorageMock.store[key] || null),
    setItem: vi.fn((key, value) => {
        localStorageMock.store[key] = value;
    }),
    removeItem: vi.fn(),
    clear: vi.fn(() => {
        localStorageMock.store = {};
    }),
};
Object.defineProperty(global, 'localStorage', { value: localStorageMock });

// Import after mocking
import BaseApi from '../baseApi.js';

describe('BaseApi', () => {
    let api;

    beforeEach(() => {
        api = new BaseApi('test');
        vi.clearAllMocks();
        localStorageMock.clear();
    });

    afterEach(() => {
        vi.clearAllMocks();
    });

    describe('constructor', () => {
        it('should set correct API URL', () => {
            expect(api.ApiURL).toContain('/api/test');
        });

        it('should set default headers', () => {
            expect(api.config.headers['Content-Type']).toBe('application/json');
        });

        it('should handle empty pathName', () => {
            const emptyApi = new BaseApi();
            expect(emptyApi.ApiURL).toContain('/api/');
        });
    });

    describe('get', () => {
        it('should return data on successful request with status 200', async () => {
            const mockData = { status: true, result: { id: 1 } };
            axios.get.mockResolvedValue({ status: 200, data: mockData });

            const result = await api.get('http://test.com/api');
            expect(result).toEqual(mockData);
        });

        it('should return empty object when status is not 200', async () => {
            axios.get.mockResolvedValue({ status: 404, data: null });

            const result = await api.get('http://test.com/api');
            expect(result).toEqual({});
        });

        it('should return empty object when data is null', async () => {
            axios.get.mockResolvedValue({ status: 200, data: null });

            const result = await api.get('http://test.com/api');
            expect(result).toEqual({});
        });

        it('should return empty object when response is undefined', async () => {
            axios.get.mockResolvedValue(undefined);

            const result = await api.get('http://test.com/api');
            expect(result).toEqual({});
        });

        it('should handle error and return error object', async () => {
            axios.get.mockRejectedValue(new Error('Network error'));

            const result = await api.get('http://test.com/api');
            expect(result.success).toBe(false);
            expect(result.userMsg).toBeDefined();
        });
    });

    describe('post', () => {
        it('should return data on successful request with status 200', async () => {
            const mockData = { status: true, result: { created: true } };
            axios.post.mockResolvedValue({ status: 200, data: mockData });

            const result = await api.post('http://test.com/api', {
                name: 'test',
            });
            expect(result).toEqual(mockData);
        });

        it('should return empty object when status is not 200', async () => {
            axios.post.mockResolvedValue({ status: 500, data: {} });

            const result = await api.post('http://test.com/api', {});
            expect(result).toEqual({});
        });

        it('should return empty object when data is undefined', async () => {
            axios.post.mockResolvedValue({ status: 200, data: undefined });

            const result = await api.post('http://test.com/api', {});
            expect(result).toEqual({});
        });

        it('should handle error', async () => {
            axios.post.mockRejectedValue(new Error('Server error'));

            const result = await api.post('http://test.com/api', {});
            expect(result.success).toBe(false);
        });
    });

    describe('put', () => {
        it('should return data on successful request with status 200', async () => {
            const mockData = { status: true, result: { updated: true } };
            axios.put.mockResolvedValue({ status: 200, data: mockData });

            const result = await api.put('http://test.com/api', { id: 1 });
            expect(result).toEqual(mockData);
        });

        it('should return empty object when status is not 200', async () => {
            axios.put.mockResolvedValue({ status: 400, data: {} });

            const result = await api.put('http://test.com/api', {});
            expect(result).toEqual({});
        });

        it('should return empty object when data is missing', async () => {
            axios.put.mockResolvedValue({ status: 200 });

            const result = await api.put('http://test.com/api', {});
            expect(result).toEqual({});
        });

        it('should handle error', async () => {
            axios.put.mockRejectedValue(new Error('Failed'));

            const result = await api.put('http://test.com/api', {});
            expect(result.success).toBe(false);
        });
    });

    describe('delete', () => {
        it('should return data on successful request with status 200', async () => {
            const mockData = { status: true };
            axios.delete.mockResolvedValue({ status: 200, data: mockData });

            const result = await api.delete('http://test.com/api/1');
            expect(result).toEqual(mockData);
        });

        it('should return empty object when status is not 200', async () => {
            axios.delete.mockResolvedValue({ status: 403, data: {} });

            const result = await api.delete('http://test.com/api/1');
            expect(result).toEqual({});
        });

        it('should return empty object when data is null', async () => {
            axios.delete.mockResolvedValue({ status: 200, data: null });

            const result = await api.delete('http://test.com/api/1');
            expect(result).toEqual({});
        });

        it('should handle error', async () => {
            axios.delete.mockRejectedValue(new Error('Forbidden'));

            const result = await api.delete('http://test.com/api/1');
            expect(result.success).toBe(false);
        });
    });

    describe('getById', () => {
        it('should call get with correct URL', async () => {
            const mockData = { status: true, result: { id: 123 } };
            axios.get.mockResolvedValue({ status: 200, data: mockData });

            const result = await api.getById(123);
            expect(axios.get).toHaveBeenCalledWith(
                expect.stringContaining('/123'),
                expect.any(Object),
            );
            expect(result).toEqual(mockData);
        });
    });

    describe('getFile', () => {
        it('should return response on success', async () => {
            const mockBlob = new Blob(['test']);
            const mockResponse = { status: 200, data: mockBlob };
            axios.get.mockResolvedValue(mockResponse);

            const result = await api.getFile('http://test.com/file.pdf');
            expect(result).toEqual(mockResponse);
            expect(axios.get).toHaveBeenCalledWith('http://test.com/file.pdf', {
                responseType: 'blob',
            });
        });

        it('should handle error in getFile', async () => {
            axios.get.mockRejectedValue(new Error('File not found'));

            const result = await api.getFile('http://test.com/missing.pdf');
            expect(result.success).toBe(false);
            expect(result.userMsg).toBeDefined();
        });
    });

    describe('handleError', () => {
        it('should return error object with success false', () => {
            const error = new Error('Test error');
            const result = api.handleError(error);
            expect(result.success).toBe(false);
            expect(result.userMsg).toBe(error);
        });

        it('should handle string error', () => {
            const result = api.handleError('String error message');
            expect(result.success).toBe(false);
            expect(result.userMsg).toBe('String error message');
        });

        it('should handle object error', () => {
            const errorObj = { code: 500, message: 'Server error' };
            const result = api.handleError(errorObj);
            expect(result.success).toBe(false);
            expect(result.userMsg).toEqual(errorObj);
        });
    });
});
