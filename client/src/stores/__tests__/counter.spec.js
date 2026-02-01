import { describe, it, expect, beforeEach } from 'vitest';
import { setActivePinia, createPinia } from 'pinia';
import { useCounterStore } from '../counter.js';

describe('counter store', () => {
    beforeEach(() => {
        setActivePinia(createPinia());
    });

    it('should have initial count as 0', () => {
        const store = useCounterStore();
        expect(store.count).toBe(0);
    });

    describe('increment', () => {
        it('should increment count by 1', () => {
            const store = useCounterStore();
            store.increment();
            expect(store.count).toBe(1);
        });

        it('should increment multiple times', () => {
            const store = useCounterStore();
            store.increment();
            store.increment();
            store.increment();
            expect(store.count).toBe(3);
        });
    });

    describe('doubleCount', () => {
        it('should return double the count', () => {
            const store = useCounterStore();
            store.increment();
            store.increment();
            expect(store.doubleCount).toBe(4);
        });

        it('should return 0 for initial count', () => {
            const store = useCounterStore();
            expect(store.doubleCount).toBe(0);
        });
    });

    describe('decrement', () => {
        it('should decrement count by 1', () => {
            const store = useCounterStore();
            store.increment();
            store.increment();
            store.decrement();
            expect(store.count).toBe(1);
        });

        it('should allow negative counts', () => {
            const store = useCounterStore();
            store.decrement();
            expect(store.count).toBe(-1);
        });
    });

    describe('reset', () => {
        it('should reset count to 0', () => {
            const store = useCounterStore();
            store.increment();
            store.increment();
            store.reset();
            expect(store.count).toBe(0);
        });
    });
});
