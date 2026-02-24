<template>
  <section
    class="natril-roadmap-analyze mx-auto px-4 lg:px-8 py-8 lg:py-12 bg-white dark:bg-gray-900"
  >
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section - Full Width -->

      <!-- Icon and Title Section - Full Width -->
      <div class="col-span-12 text-center mb-6">
        <!-- Icon -->
        <div class="flex justify-center mb-4">
          <div
            :class="[
              'w-16 h-16 md:w-20 md:h-20 rounded-full flex items-center justify-center',
              roadmapData.iconBgClass,
            ]"
          >
            <span class="text-4xl md:text-5xl">{{ roadmapData.icon }}</span>
          </div>
        </div>

        <!-- Title -->
        <h2
          class="text-2xl sm:text-3xl lg:text-4xl font-bold text-gray-800 dark:text-white mb-3"
        >
          {{ roadmapData.title }}
        </h2>

        <!-- Subtitle -->
        <p
          class="text-sm md:text-base font-semibold text-gray-700 dark:text-gray-300 mb-2"
        >
          {{ roadmapData.subtitle }}
        </p>
        <p
          class="text-xs md:text-sm text-gray-600 dark:text-gray-400 mx-auto max-w-2xl"
        >
          {{ roadmapData.description }}
        </p>
      </div>

      <!-- Main Content Section - Grid Layout -->
      <!-- Left Column - Input Section (7 columns on desktop, full width on mobile) -->
      <div class="col-span-12 lg:col-span-7">
        <div
          class="bg-gray-50 dark:bg-gray-800/50 rounded-2xl p-5 md:p-6 h-full flex flex-col justify-center border border-gray-200 dark:border-gray-700"
        >
          <!-- Height Label and Input -->
          <div class="flex flex-col gap-4">
            <label
              class="text-sm md:text-base text-gray-700 dark:text-gray-300 font-semibold"
            >
              {{ roadmapData.sliderLabel }}
            </label>

            <!-- InputText synced with Slider -->
            <div class="w-full">
              <InputText
                v-model.number="targetHeight"
                class="w-full text-center text-2xl md:text-3xl font-bold"
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
            <div
              class="text-center text-sm md:text-base text-gray-600 dark:text-gray-400 font-medium"
            >
              {{ targetHeight }} {{ roadmapData.sliderUnit }}
            </div>
          </div>
        </div>
      </div>

      <!-- Right Column - Knob Section (5 columns on desktop, full width on mobile) -->
      <div class="col-span-12 lg:col-span-5">
        <div
          class="bg-gradient-to-br from-blue-50 to-indigo-50 dark:from-gray-800 dark:to-gray-900 rounded-2xl p-5 md:p-6 h-full flex flex-col items-center justify-center border border-blue-200 dark:border-gray-700"
        >
          <!-- Calcium Progress Knob -->
          <Knob
            v-model="knobValue"
            :min="roadmapData.calciumFormula.minValue * 10"
            :max="roadmapData.calciumFormula.maxValue * 10"
            :size="knobSize"
            :strokeWidth="8"
            :valueColor="knobValueColor"
            :rangeColor="knobRangeColor"
            readonly
            class="mb-4"
          >
            <template #default>
              <div class="flex flex-col items-center">
                <span
                  :class="[
                    'text-3xl md:text-4xl font-bold',
                    roadmapData.progressTextColorClass,
                  ]"
                >
                  {{ calciumValue }}{{ roadmapData.valueUnit }}
                </span>
                <span
                  class="text-xs md:text-sm text-gray-600 dark:text-gray-400 mt-2 font-medium"
                >
                  {{ roadmapData.valueLabel }}
                </span>
              </div>
            </template>
          </Knob>

          <!-- Quote Text -->
          <p
            class="text-center text-xs md:text-sm text-gray-600 dark:text-gray-400 italic leading-relaxed px-2"
          >
            {{ roadmapData.quote }}
          </p>
        </div>
      </div>

      <!-- Action Button Section - Full Width -->
      <div class="col-span-12 mt-4">
        <Button
          @click="analyzeAndPredict"
          :label="roadmapData.buttonText"
          class="w-full"
          :pt="{
            root: {
              class:
                'bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 dark:from-blue-700 dark:to-blue-800 border-0 py-4 text-base md:text-lg font-semibold shadow-lg hover:shadow-xl transition-all duration-200',
            },
          }"
        />
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed } from "vue";
import Button from "primevue/button";
import Slider from "primevue/slider";
import InputText from "primevue/inputtext";
import Knob from "primevue/knob";

// Props
defineProps({
  initialHeight: {
    type: Number,
    default: 165,
  },
});

// Events
const emit = defineEmits(["back", "close", "analyze"]);

const targetHeight = ref(165);

// Fake data for roadmap configuration
const roadmapData = ref({
  id: 1,
  backButtonText: "Quay Lại",
  icon: "🧮",
  iconBgClass: "bg-blue-100 dark:bg-blue-900/30",
  title: "Lộ Trình Dinh Dưỡng Tối Ưu",
  subtitle: "Công Cụ Tính Toán Canxi & Tăng Trưởng",
  description:
    "Nhập chiều cao mục tiêu để AI phân tích nhu cầu canxi và dự đoán lộ trình tăng trưởng của bạn.",
  sliderLabel: "Chiều cao mục tiêu của bạn:",
  sliderUnit: "cm",
  sliderMin: 150,
  sliderMax: 220,
  sliderStep: 1,
  valueLabel: "Lượng Canxi",
  valueUnit: "g",
  progressColorClass: "text-blue-600 dark:text-blue-400",
  progressTextColorClass: "text-blue-600 dark:text-blue-400",
  quote:
    '"Mỗi gram canxi bạn nạp hôm nay là một bước tiến vững chắc đến tầm vóc mơ ước."',
  buttonText: "Phân Tích & Dự Đoán",
  calciumFormula: {
    multiplier: 0.001,
    minValue: 0.1,
    maxValue: 2.2,
    decimalPlaces: 1,
  },
});

// Calculate calcium value based on height
const calciumValue = computed(() => {
  const formula = roadmapData.value.calciumFormula;
  return (targetHeight.value * formula.multiplier).toFixed(
    formula.decimalPlaces
  );
});

// Knob value (scaled for better display)
const knobValue = computed(() => {
  return parseFloat(calciumValue.value) * 10;
});

// Responsive knob size
const knobSize = computed(() => {
  if (typeof window !== "undefined") {
    return window.innerWidth < 768 ? 120 : 140;
  }
  return 140;
});

// Knob colors based on theme
const knobValueColor = computed(() => {
  return (
    getComputedStyle(document.documentElement).getPropertyValue(
      "--primary-color"
    ) || "#3b82f6"
  );
});

const knobRangeColor = computed(() => {
  const isDark = document.documentElement.classList.contains("dark");
  return isDark ? "#374151" : "#e5e7eb";
});

const analyzeAndPredict = () => {
  emit("analyze", {
    targetHeight: targetHeight.value,
    calciumValue: calciumValue.value,
    roadmapId: roadmapData.value.id,
  });
};
</script>

<style scoped>
/* Base Section Styles */
.natril-roadmap-analyze {
  max-width: 1200px;
  border-radius: 1.5rem;
}

/* Custom styles for PrimeVue components */
:deep(.p-slider) {
  height: 0.5rem;
  background: #e5e7eb;
  border-radius: 0.25rem;
}

:deep(.p-slider .p-slider-range) {
  background: linear-gradient(to right, #3b82f6, #2563eb);
}

:deep(.p-slider .p-slider-handle) {
  width: 1.5rem;
  height: 1.5rem;
  background: white;
  border: 3px solid #3b82f6;
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
  transition: all 0.2s ease;
}

:deep(.p-slider .p-slider-handle:hover) {
  transform: scale(1.1);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
}

:deep(.p-inputtext) {
  padding: 1rem 1.5rem;
  border-radius: 1rem;
  border: 2px solid #e5e7eb;
  transition: all 0.2s ease;
  background: white;
}

:deep(.p-inputtext:focus) {
  border-color: #3b82f6;
  box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.1);
  outline: none;
}

:deep(.p-inputtext::placeholder) {
  color: #9ca3af;
}

:deep(.p-knob-text) {
  font-size: 1rem;
  font-weight: 600;
}

/* Dark mode adjustments */
@media (prefers-color-scheme: dark) {
  :deep(.p-slider) {
    background: #374151;
  }

  :deep(.p-inputtext) {
    background: #1f2937;
    border-color: #374151;
    color: white;
  }

  :deep(.p-inputtext:focus) {
    border-color: #3b82f6;
    background: #1f2937;
  }
}

/* Grid Responsive Adjustments */
@media (min-width: 768px) {
  :deep(.p-inputtext) {
    padding: 1.25rem 1.5rem;
  }
}

@media (min-width: 1024px) {
  .natril-roadmap-analyze {
    padding: 2rem 2.5rem;
  }
}

/* Smooth transitions for grid layout changes */
.col-span-12,
.col-span-7,
.col-span-5 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

/* Additional styling for cards */
.bg-gray-50 {
  transition: background-color 0.3s ease;
}

.bg-gradient-to-br {
  background-size: 200% 200%;
  animation: gradient-shift 3s ease infinite;
}

@keyframes gradient-shift {
  0%,
  100% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
}
</style>