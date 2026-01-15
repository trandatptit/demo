import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useLoadingStore = defineStore('gloLoading', () => {
    const globalLoading = ref(false);
    const loadingMessage = ref('AI đang suy nghĩ...');

    function setLoading(loading) {
        globalLoading.value = loading;
    }

    function setMessage(message) {
        loadingMessage.value = message;
    }

    // Bật loading với message tùy chỉnh
    function mask(message = 'AI đang suy nghĩ...') {
        loadingMessage.value = message;
        globalLoading.value = true;
    }

    // Tắt loading
    function unmask() {
        globalLoading.value = false;
        loadingMessage.value = 'AI đang suy nghĩ...';
    }

    return {
        globalLoading,
        loadingMessage,
        setLoading,
        setMessage,
        mask,
        unmask,
    };
});
