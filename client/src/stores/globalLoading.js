import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useLoadingStore = defineStore('gloLoading', () => {
    const globalLoading = ref(false);
    function setLoading(loading) {
        globalLoading.value = loading;
    }

    // Bật loading
    function mask() {
        globalLoading.value = true;
    }

    // Tắt loading
    function unmask() {
        globalLoading.value = false;
    }

    return { globalLoading, setLoading, mask, unmask };
});
