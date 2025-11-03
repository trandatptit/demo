<template>
    <Dialog 
        v-model:visible="visible" 
        modal 
        :closable="false" 
        :style="{ width: '45vw', maxWidth: '650px' }"
        :breakpoints="{ '1199px': '60vw', '768px': '75vw', '575px': '90vw' }" 
        :pt="{
            root: { class: 'rounded-3xl shadow-2xl border border-gray-200 dark:border-gray-700 relative' },
            header: { class: '!hidden' },
            content: { class: 'bg-white dark:bg-gray-800 !p-0 rounded-3xl relative overflow-visible' },
            mask: { class: 'backdrop-blur-sm bg-black/40 dark:bg-black/60' }
        }"
    >
        <div class="p-5 md:p-6">
            <!-- Header Section with Back and Close Buttons -->
            <div class="flex justify-between items-center mb-4">
                <!-- Back Button - Bên trái -->
                <Button
                    @click="$emit('back')"
                    :label="roadmapData.backButtonText"
                    icon="pi pi-arrow-left"
                    text
                    severity="secondary"
                    size="small"
                    class="!p-0"
                />

                <!-- Close Button - Bên phải -->
                <Button
                    @click="visible = false"
                    icon="pi pi-times"
                    rounded
                    text
                    severity="secondary"
                    size="small"
                />
            </div>

            <!-- Icon and Title Section -->
            <div class="text-center mb-5">
                <!-- Icon -->
                <div class="flex justify-center mb-3">
                    <div :class="['w-16 h-16 md:w-20 md:h-20 rounded-full flex items-center justify-center', roadmapData.iconBgClass]">
                        <span class="text-4xl md:text-5xl">{{ roadmapData.icon }}</span>
                    </div>
                </div>

                <!-- Title -->
                <h2 class="text-xl md:text-2xl lg:text-3xl font-bold text-gray-800 dark:text-white mb-2">
                    {{ roadmapData.title }}
                </h2>

                <!-- Subtitle -->
                <p class="text-sm md:text-base font-semibold text-gray-700 dark:text-gray-300 mb-1">
                    {{ roadmapData.subtitle }}
                </p>
                <p class="text-xs md:text-sm text-gray-500 dark:text-gray-400">
                    {{ roadmapData.description }}
                </p>
            </div>

            <!-- Input Section -->
            <div class="bg-gray-50 dark:bg-gray-900/50 rounded-2xl p-4 md:p-5 mb-4">
                <!-- Height Label and Input with Slider -->
                <div class="flex flex-col gap-3 mb-4">
                    <label class="text-sm md:text-base text-gray-700 dark:text-gray-300 font-medium">
                        {{ roadmapData.sliderLabel }}
                    </label>
                    
                    <!-- InputText synced with Slider -->
                    <div class="w-full">
                        <InputText 
                            v-model.number="targetHeight" 
                            class="w-full text-center text-xl md:text-2xl font-bold"
                            :placeholder="`${roadmapData.sliderMin} - ${roadmapData.sliderMax} ${roadmapData.sliderUnit}`"
                        />
                    </div>
                    
                    <!-- Slider synced with InputText -->
                    <Slider 
                        v-model="targetHeight" 
                        :min="roadmapData.sliderMin" 
                        :max="roadmapData.sliderMax" 
                        :step="roadmapData.sliderStep" 
                        class="w-full"
                    />
                    
                    <!-- Display unit -->
                    <div class="text-center text-xs md:text-sm text-gray-500 dark:text-gray-400">
                        {{ targetHeight }} {{ roadmapData.sliderUnit }}
                    </div>
                </div>

                <!-- Calcium Progress Knob -->
                <div class="flex flex-col items-center justify-center py-4">
                    <Knob 
                        v-model="knobValue" 
                        :min="roadmapData.calciumFormula.minValue * 10"
                        :max="roadmapData.calciumFormula.maxValue * 10"
                        :size="140"
                        :strokeWidth="8"
                        :valueColor="knobValueColor"
                        :rangeColor="knobRangeColor"
                        readonly
                        class="mb-3"
                    >
                        <template #default>
                            <div class="flex flex-col items-center">
                                <span :class="['text-2xl md:text-3xl font-bold', roadmapData.progressTextColorClass]">
                                    {{ calciumValue }}{{ roadmapData.valueUnit }}
                                </span>
                                <span class="text-xs text-gray-500 dark:text-gray-400 mt-1">
                                    {{ roadmapData.valueLabel }}
                                </span>
                            </div>
                        </template>
                    </Knob>

                    <!-- Quote Text -->
                    <p class="text-center text-xs md:text-sm text-gray-500 dark:text-gray-400 italic max-w-md px-2">
                        {{ roadmapData.quote }}
                    </p>
                </div>
            </div>

            <!-- Action Button -->
            <Button
                @click="analyzeAndPredict"
                :label="roadmapData.buttonText"
                class="w-full"
                :pt="{
                    root: { 
                        class: 'bg-gradient-to-r from-blue-600 to-blue-700 dark:from-blue-700 dark:to-blue-800 hover:from-blue-700 hover:to-blue-800 border-0 py-3 text-base font-semibold shadow-lg hover:shadow-xl transition-all duration-200'
                    }
                }"
            />
        </div>
    </Dialog>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import Dialog from "primevue/dialog";
import Button from "primevue/button";
import Slider from "primevue/slider";
import InputText from "primevue/inputtext";
import Knob from "primevue/knob";

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
    calciumFormula: {
        multiplier: 0.001,
        minValue: 0.1,
        maxValue: 2.2,
        decimalPlaces: 1
    }
});

// Calculate calcium value based on height
const calciumValue = computed(() => {
    const formula = roadmapData.value.calciumFormula;
    return (targetHeight.value * formula.multiplier).toFixed(formula.decimalPlaces);
});

// Knob value (scaled for better display)
const knobValue = computed(() => {
    return parseFloat(calciumValue.value) * 10;
});

// Knob colors based on theme
const knobValueColor = computed(() => {
    return getComputedStyle(document.documentElement).getPropertyValue('--primary-color') || '#3b82f6';
});

const knobRangeColor = computed(() => {
    const isDark = document.documentElement.classList.contains('dark');
    return isDark ? '#374151' : '#e5e7eb';
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
/* Custom styles for PrimeVue components */
:deep(.p-slider) {
    height: 0.5rem;
}

:deep(.p-slider .p-slider-handle) {
    width: 1.25rem;
    height: 1.25rem;
}

:deep(.p-inputtext) {
    padding: 0.75rem 1rem;
    border-radius: 0.75rem;
    border: 2px solid #e5e7eb;
    transition: all 0.2s;
}

:deep(.p-inputtext:focus) {
    border-color: #3b82f6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

:deep(.p-knob-text) {
    font-size: 0.875rem;
}

/* Responsive adjustments */
@media (min-width: 768px) {
    :deep(.p-inputtext) {
        padding: 1rem;
    }
}
</style>