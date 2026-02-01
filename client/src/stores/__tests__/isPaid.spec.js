import { describe, it, expect, beforeEach } from 'vitest';
import { setActivePinia, createPinia } from 'pinia';
import { useIsPaidStore } from '../isPaid.js';

describe('isPaid store', () => {
    beforeEach(() => {
        setActivePinia(createPinia());
    });

    it('should have initial isPaid state as false', () => {
        const store = useIsPaidStore();
        expect(store.isPaid).toBe(false);
    });

    describe('setIsPaid', () => {
        it('should set isPaid to true', () => {
            const store = useIsPaidStore();
            store.setIsPaid(true);
            expect(store.isPaid).toBe(true);
        });

        it('should set isPaid to false', () => {
            const store = useIsPaidStore();
            store.setIsPaid(true);
            store.setIsPaid(false);
            expect(store.isPaid).toBe(false);
        });
    });

    describe('getIsPaid', () => {
        it('should return current isPaid value', () => {
            const store = useIsPaidStore();
            expect(store.getIsPaid()).toBe(false);
            store.setIsPaid(true);
            expect(store.getIsPaid()).toBe(true);
        });
    });

    describe('paid', () => {
        it('should set isPaid to true', () => {
            const store = useIsPaidStore();
            store.paid();
            expect(store.isPaid).toBe(true);
        });
    });
});
