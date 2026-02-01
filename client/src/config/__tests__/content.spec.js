import { describe, it, expect } from 'vitest';
import { adviceAIMessages, inspirationMessages } from '../content.js';

describe('content.js', () => {
    describe('adviceAIMessages', () => {
        it('should be an array', () => {
            expect(Array.isArray(adviceAIMessages)).toBe(true);
        });

        it('should have at least 5 messages', () => {
            expect(adviceAIMessages.length).toBeGreaterThanOrEqual(5);
        });

        it('should contain placeholders for formatting', () => {
            adviceAIMessages.forEach((msg) => {
                expect(msg).toContain('{0}');
                expect(msg).toContain('{1}');
            });
        });

        it('should have non-empty messages', () => {
            adviceAIMessages.forEach((msg) => {
                expect(msg.length).toBeGreaterThan(50);
            });
        });
    });

    describe('inspirationMessages', () => {
        it('should be an array', () => {
            expect(Array.isArray(inspirationMessages)).toBe(true);
        });

        it('should have at least 5 messages', () => {
            expect(inspirationMessages.length).toBeGreaterThanOrEqual(5);
        });

        it('should have non-empty messages', () => {
            inspirationMessages.forEach((msg) => {
                expect(msg.length).toBeGreaterThan(20);
            });
        });

        it('should contain motivational content', () => {
            const allMessages = inspirationMessages.join(' ').toLowerCase();
            expect(
                allMessages.includes('chiều cao') ||
                    allMessages.includes('phát triển') ||
                    allMessages.includes('tăng trưởng'),
            ).toBe(true);
        });
    });
});
