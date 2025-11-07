import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useIsPaidStore = defineStore('isPaid', () => {
    const isPaid = ref(false);
    function setIsPaid(value) {
        isPaid.value = value;
    }

    function getIsPaid() {
        return isPaid.value;
    }

    function paid() {
        isPaid.value = true;
    }

    return { isPaid, setIsPaid, getIsPaid, paid };
});
