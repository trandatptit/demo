import { defineConfig } from 'vitest/config';
import vue from '@vitejs/plugin-vue';
import { fileURLToPath, URL } from 'node:url';

export default defineConfig({
    plugins: [vue()],
    test: {
        environment: 'jsdom',
        globals: true,
        include: ['src/**/*.{test,spec}.{js,ts}'],
        coverage: {
            provider: 'v8',
            reporter: ['text', 'json', 'html'],
            include: [
                'src/apis/**/*.js',
                'src/stores/**/*.js',
                'src/utilities/**/*.js',
                'src/composables/**/*.js',
                'src/config/**/*.js',
                'src/constants/**/*.js',
            ],
            exclude: [
                'src/main.js',
                'src/**/*.spec.js',
                'src/**/*.test.js',
                'src/apis/attachmentApi.js', // Complex Pinia dependency
            ],
        },
    },
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url)),
        },
    },
});
