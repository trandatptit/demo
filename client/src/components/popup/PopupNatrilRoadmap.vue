<template>
    <Dialog v-model:visible="visible" modal :closable="false" :style="{ width: '50vw', maxWidth: '800px' }"
        :breakpoints="{ '1199px': '70vw', '575px': '95vw' }" :pt="{
            root: { class: 'rounded-3xl shadow-2xl border border-gray-200 dark:border-gray-700' },
            header: { class: '!hidden' },
            content: { class: 'bg-white dark:bg-gray-800 !p-0 rounded-3xl relative' },
            mask: { class: 'backdrop-blur-sm bg-black/40 dark:bg-black/60' }
        }">
        <div class="p-8 md:p-10">
            <!-- Header Section with Back and Close Button -->
            <div class="relative mb-8">
                <!-- Back Button -->
                <button @click="$emit('back')"
                    class="absolute left-0 top-0 flex items-center gap-2 text-gray-600 dark:text-gray-400 hover:text-gray-800 dark:hover:text-gray-200 transition-colors"
                    type="button">
                    <i class="pi pi-arrow-left"></i>
                    <span class="font-medium">{{ roadmapData.backButtonText }}</span>
                </button>

                <!-- Custom Close Button -->
                <button @click="visible = false"
                    class="absolute right-0 top-0 w-10 h-10 rounded-full bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 transition-all duration-200 flex items-center justify-center"
                    type="button">
                    <i class="pi pi-times text-gray-600 dark:text-gray-300"></i>
                </button>
            </div>

            <!-- Icon and Title Section -->
            <div class="text-center mb-8 mt-12">
                <!-- Icon -->
                <div class="flex justify-center mb-6">
                    <div :class="['w-24 h-24 rounded-full flex items-center justify-center', roadmapData.iconBgClass]">
                        <span class="text-6xl">{{ roadmapData.icon }}</span>
                    </div>
                </div>
                <!-- Title -->
                <h2 class="text-3xl md:text-4xl font-bold text-gray-800 dark:text-white mb-3">
                    {{ roadmapData.title }}
                </h2>

                <!-- Subtitle -->
                <p class="text-lg font-semibold text-gray-700 dark:text-gray-300 mb-2">
                    {{ roadmapData.subtitle }}
                </p>
                <p class="text-sm text-gray-500 dark:text-gray-400">
                    {{ roadmapData.description }}
                </p>
            </div>

            <!-- Input Section -->
            <div class="bg-gray-50 dark:bg-gray-900/50 rounded-2xl p-6 md:p-8 mb-6">
                <!-- Height Label and Value -->
                <div class="flex justify-between items-center mb-4">
                    <label class="text-gray-700 dark:text-gray-300 font-medium">
                        {{ roadmapData.sliderLabel }}
                    </label>
                    <span class="text-2xl font-bold text-gray-800 dark:text-white">
                        {{ targetHeight }} {{ roadmapData.sliderUnit }}
                    </span>
                </div>

                <!-- Slider -->
                <Slider 
                    v-model="targetHeight" 
                    :min="roadmapData.sliderMin" 
                    :max="roadmapData.sliderMax" 
                    :step="roadmapData.sliderStep" 
                    class="w-full mb-6" 
                />

                <!-- Calcium Progress Circle -->
                <div class="flex flex-col items-center justify-center py-6">
                    <div class="relative w-40 h-40 mb-4">
                        <!-- Progress Circle using SVG -->
                        <svg class="w-full h-full transform -rotate-90" viewBox="0 0 100 100">
                            <!-- Background Circle -->
                            <circle cx="50" cy="50" r="40" stroke="currentColor"
                                class="text-gray-200 dark:text-gray-700" stroke-width="8" fill="none" />
                            <!-- Progress Circle -->
                            <circle 
                                cx="50" cy="50" r="40" 
                                stroke="currentColor"
                                :class="roadmapData.progressColorClass" 
                                stroke-width="8" 
                                fill="none"
                                :stroke-dasharray="calciumCircumference" 
                                :stroke-dashoffset="calciumDashOffset"
                                stroke-linecap="round" 
                            />
                        </svg>

                        <!-- Center Text -->
                        <div class="absolute inset-0 flex flex-col items-center justify-center">
                            <span :class="['text-3xl font-bold', roadmapData.progressTextColorClass]">
                                {{ calciumValue }}{{ roadmapData.valueUnit }}
                            </span>
                            <span class="text-xs text-gray-500 dark:text-gray-400">{{ roadmapData.valueLabel }}</span>
                        </div>
                    </div>

                    <!-- Quote Text -->
                    <p class="text-center text-sm text-gray-500 dark:text-gray-400 italic max-w-md">
                        {{ roadmapData.quote }}
                    </p>
                </div>
            </div>

            <!-- Action Button -->
            <button 
                @click="analyzeAndPredict"
                :class="roadmapData.buttonClass"
            >
                {{ roadmapData.buttonText }}
            </button>
        </div>
    </Dialog>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import Dialog from "primevue/dialog";
import Slider from "primevue/slider";

const props = defineProps({
    modelValue: Boolean
});

const emit = defineEmits(["update:modelValue", "back", "analyze"]);

const visible = ref(props.modelValue);
const targetHeight = ref(165);

// Fake data for roadmap configuration
const roadmapData = ref({
    id: 1,
    backButtonText: "Quay Lại",
    icon: "🧮",
    iconBgClass: "bg-blue-100 dark:bg-blue-900/30",
    title: "Lộ Trình Dinh Dưỡng Tối Ưu",
    subtitle: "Công Cụ Tính Toán Canxi & Tăng Trưởng",
    description: "Nhập chiều cao mục tiêu để AI phân tích nhu cầu canxi và dự phóng tử trình.",
    sliderLabel: "Chiều cao mục tiêu của bạn:",
    sliderUnit: "cm",
    sliderMin: 150,
    sliderMax: 220,
    sliderStep: 1,
    valueLabel: "Lượng Canxi",
    valueUnit: "g",
    progressColorClass: "text-blue-600 dark:text-blue-400",
    progressTextColorClass: "text-blue-600 dark:text-blue-400",
    quote: '"Mỗi gram canxi bạn nạp hôm nay là một bước tiến vững chắc đến tầm vóc mơ ước."',
    buttonText: "Phân Tích & Dự Phóng",
    buttonClass: "w-full bg-gradient-to-r from-blue-600 to-blue-700 dark:from-blue-700 dark:to-blue-800 hover:from-blue-700 hover:to-blue-800 text-white font-semibold py-4 rounded-xl shadow-lg hover:shadow-xl transition-all duration-200 text-lg",
    calciumFormula: {
        multiplier: 0.001,
        minValue: 0.1,
        maxValue: 2.2,
        decimalPlaces: 1
    }
});

// Calculate calcium value based on height (example formula)
const calciumValue = computed(() => {
    const formula = roadmapData.value.calciumFormula;
    return (targetHeight.value * formula.multiplier).toFixed(formula.decimalPlaces);
});

// SVG Circle calculations
const calciumCircumference = 2 * Math.PI * 40; // radius = 40
const calciumDashOffset = computed(() => {
    const formula = roadmapData.value.calciumFormula;
    const progress = (calciumValue.value - formula.minValue) / (formula.maxValue - formula.minValue);
    return calciumCircumference * (1 - Math.min(Math.max(progress, 0), 1));
});

watch(() => props.modelValue, (val) => (visible.value = val));
watch(visible, (val) => emit("update:modelValue", val));

const analyzeAndPredict = () => {
    emit("analyze", {
        targetHeight: targetHeight.value,
        calciumValue: calciumValue.value,
        roadmapId: roadmapData.value.id
    });
};
</script>

<style scoped>
/* Custom styles for slider if needed */
:deep(.p-slider) {
    height: 0.5rem;
}

:deep(.p-slider .p-slider-handle) {
    width: 1.5rem;
    height: 1.5rem;
}
</style>