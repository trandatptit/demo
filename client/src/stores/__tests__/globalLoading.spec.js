import { describe, it, expect, beforeEach } from 'vitest';
import { setActivePinia, createPinia } from 'pinia';
import { useLoadingStore } from '../globalLoading.js';

describe('globalLoading store', () => {
    beforeEach(() => {
        setActivePinia(createPinia());
    });

    it('should have initial loading state as false', () => {
        const store = useLoadingStore();
        expect(store.globalLoading).toBe(false);
    });

    it('should have default message', () => {
        const store = useLoadingStore();
        expect(store.loadingMessage).toBe('AI đang suy nghĩ...');
    });

    describe('setLoading', () => {
        it('should set loading to true', () => {
            const store = useLoadingStore();
            store.setLoading(true);
            expect(store.globalLoading).toBe(true);
        });

        it('should set loading to false', () => {
            const store = useLoadingStore();
            store.setLoading(true);
            store.setLoading(false);
            expect(store.globalLoading).toBe(false);
        });
    });

    describe('setMessage', () => {
        it('should update loading message', () => {
            const store = useLoadingStore();
            store.setMessage('Loading...');
            expect(store.loadingMessage).toBe('Loading...');
        });
    });

    describe('mask', () => {
        it('should enable loading with default message', () => {
            const store = useLoadingStore();
            store.mask();
            expect(store.globalLoading).toBe(true);
            expect(store.loadingMessage).toBe('AI đang suy nghĩ...');
        });

        it('should enable loading with custom message', () => {
            const store = useLoadingStore();
            store.mask('Đang tải dữ liệu...');
            expect(store.globalLoading).toBe(true);
            expect(store.loadingMessage).toBe('Đang tải dữ liệu...');
        });
    });

    describe('unmask', () => {
        it('should disable loading and reset message', () => {
            const store = useLoadingStore();
            store.mask('Custom message');
            store.unmask();
            expect(store.globalLoading).toBe(false);
            expect(store.loadingMessage).toBe('AI đang suy nghĩ...');
        });
    });
});
