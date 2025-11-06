import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { fileURLToPath, URL } from 'node:url';

// https://vite.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url)),
        },
    },
    build: {
        // Minify bằng esbuild (mặc định, nhanh nhất)
        minify: 'esbuild',

        // Hoặc dùng terser (minify tốt hơn nhưng chậm hơn)
        // minify: 'terser',

        // Tùy chọn cho terser
        terserOptions: {
            compress: {
                drop_console: true, // Xóa console.log
                drop_debugger: true, // Xóa debugger
            },
        },

        // Tối ưu CSS
        cssMinify: true,

        // Tạo source map (tắt đi cho production)
        sourcemap: false,

        // Chunk size warnings
        chunkSizeWarningLimit: 500,

        // Tối ưu chunk splitting
        rollupOptions: {
            output: {
                manualChunks: {
                    vendor: ['vue', 'vue-router'], // Tách vendor riêng
                },
            },
        },
    },
});
