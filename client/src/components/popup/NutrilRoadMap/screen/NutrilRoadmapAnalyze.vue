<template>
  <section class="nutril-roadmap-analyze">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section -->
      <div class="col-span-12 survey-header">
        <div class="header-accent"></div>
        <span class="header-badge">
          <i class="pi pi-calculator"></i> AI Phân Tích
        </span>
        <h2 class="header-title">{{ roadmapData.title }}</h2>
        <p class="header-subtitle">{{ roadmapData.subtitle }}</p>
      </div>

      <!-- Description -->
      <div class="col-span-12 text-center">
        <p class="section-description">
          {{ roadmapData.description }}
        </p>
      </div>

      <!-- Main Content Section - Grid Layout -->
      <!-- Left Column - Input Section -->
      <div class="col-span-12 lg:col-span-7">
        <div class="content-card">
          <div class="card-inner">
            <!-- Height Label and Input -->
            <div class="flex flex-col gap-4">
              <label class="input-label">
                {{ roadmapData.sliderLabel }}
              </label>

              <!-- InputText synced with Slider -->
              <div class="w-full">
                <InputText
                  v-model.number="targetHeight"
                  class="w-full text-center text-2xl md:text-3xl font-bold custom-input"
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
              <div class="unit-display">
                {{ targetHeight }} {{ roadmapData.sliderUnit }}
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Column - Knob Section -->
      <div class="col-span-12 lg:col-span-5">
        <div class="content-card knob-card">
          <div class="card-inner flex flex-col items-center justify-center">
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
                  <span class="knob-value">
                    {{ calciumValue }}{{ roadmapData.valueUnit }}
                  </span>
                  <span class="knob-label">
                    {{ roadmapData.valueLabel }}
                  </span>
                </div>
              </template>
            </Knob>

            <!-- Quote Text -->
            <p class="quote-text">
              {{ roadmapData.quote }}
            </p>
          </div>
        </div>
      </div>

      <!-- Action Button Section - Full Width -->
      <div class="col-span-12 mt-4">
        <Button
          @click="analyzeAndPredict"
          :label="roadmapData.buttonText"
          icon="pi pi-chart-line"
          iconPos="right"
          class="w-full submit-btn"
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
/* ─── Design Tokens (matching NutrilRoadMapSchedule) ─── */
.nutril-roadmap-analyze {
  --primary: #2563eb;
  --primary-light: #3b82f6;
  --primary-dark: #1d4ed8;
  --primary-bg: #eff6ff;
  --primary-bg-deep: #dbeafe;
  --primary-ring: rgba(37, 99, 235, 0.15);
  --surface: #ffffff;
  --surface-alt: #f8fafc;
  --text-primary: #0f172a;
  --text-secondary: #475569;
  --text-muted: #94a3b8;
  --border: #e2e8f0;
  --border-hover: #cbd5e1;
  --radius: 12px;
  --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
  --shadow-md: 0 4px 12px rgba(0, 0, 0, 0.08);
  --shadow-lg: 0 8px 24px rgba(0, 0, 0, 0.1);

  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
}

/* ─── Header ─── */
.survey-header {
  text-align: center;
  margin-bottom: 0.5rem;
}

.header-accent {
  width: 64px;
  height: 4px;
  border-radius: 4px;
  background: linear-gradient(90deg, var(--primary), var(--primary-light));
  margin: 0 auto 1rem;
}

.header-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 14px;
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  color: var(--primary);
  background: var(--primary-bg);
  border: 1px solid var(--primary-bg-deep);
  border-radius: 999px;
  margin-bottom: 12px;
}

.header-title {
  font-size: clamp(1.35rem, 3vw, 2rem);
  font-weight: 800;
  color: var(--text-primary);
  margin: 0 0 6px;
  line-height: 1.25;
}

.header-subtitle {
  font-size: 0.95rem;
  font-weight: 500;
  color: var(--text-secondary);
  margin: 0;
}

.section-description {
  font-size: 0.85rem;
  color: var(--text-muted);
  max-width: 640px;
  margin: 0 auto;
  line-height: 1.6;
}

/* ─── Content Cards ─── */
.content-card {
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 16px;
  box-shadow: var(--shadow-md);
  overflow: hidden;
  position: relative;
  height: 100%;
}

.content-card::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(
    90deg,
    var(--primary),
    var(--primary-light),
    #818cf8
  );
  z-index: 1;
}

.card-inner {
  padding: 1.5rem;
}

.knob-card {
  background: linear-gradient(
    135deg,
    var(--primary-bg) 0%,
    var(--primary-bg-deep) 100%
  );
}

/* ─── Input ─── */
.input-label {
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--text-primary);
}

.unit-display {
  text-align: center;
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--text-secondary);
}

.knob-value {
  font-size: 2rem;
  font-weight: 800;
  color: var(--primary);
}

.knob-label {
  font-size: 0.8rem;
  color: var(--text-muted);
  margin-top: 4px;
  font-weight: 500;
}

.quote-text {
  text-align: center;
  font-size: 0.8rem;
  color: var(--text-muted);
  font-style: italic;
  line-height: 1.6;
  padding: 0 0.5rem;
}

/* ─── PrimeVue Overrides ─── */
:deep(.p-slider) {
  height: 0.5rem;
  background: var(--border);
  border-radius: 0.25rem;
}

:deep(.p-slider .p-slider-range) {
  background: linear-gradient(to right, var(--primary-light), var(--primary));
}

:deep(.p-slider .p-slider-handle) {
  width: 1.5rem;
  height: 1.5rem;
  background: white;
  border: 3px solid var(--primary);
  box-shadow: 0 2px 8px rgba(37, 99, 235, 0.3);
  transition: all 0.2s ease;
}

:deep(.p-slider .p-slider-handle:hover) {
  transform: scale(1.1);
  box-shadow: 0 4px 12px rgba(37, 99, 235, 0.4);
}

:deep(.custom-input.p-inputtext) {
  padding: 1rem 1.5rem;
  border-radius: var(--radius);
  border: 1.5px solid var(--border);
  transition: all 0.2s ease;
  background: var(--surface);
  color: var(--text-primary);
}

:deep(.custom-input.p-inputtext:focus) {
  border-color: var(--primary);
  box-shadow: 0 0 0 3px var(--primary-ring);
  outline: none;
}

:deep(.custom-input.p-inputtext::placeholder) {
  color: var(--text-muted);
}

/* ─── Submit Button ─── */
:deep(.submit-btn.p-button) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  );
  border: none;
  padding: 14px 24px;
  font-size: 1rem;
  font-weight: 700;
  border-radius: var(--radius);
  box-shadow: 0 4px 14px rgba(37, 99, 235, 0.35);
  transition: all 0.25s ease;
  letter-spacing: 0.3px;
}

:deep(.submit-btn.p-button:not(:disabled):hover) {
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  );
  box-shadow: 0 6px 20px rgba(37, 99, 235, 0.45);
  transform: translateY(-1px);
}

:deep(.submit-btn.p-button:not(:disabled):active) {
  transform: translateY(0);
  box-shadow: 0 2px 8px rgba(37, 99, 235, 0.3);
}

/* ─── Animations ─── */
.survey-header,
.content-card {
  animation: fadeInUp 0.4s ease-out both;
}

.content-card:nth-child(2) {
  animation-delay: 0.05s;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(14px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* ─── Dark Mode ─── */
@media (prefers-color-scheme: dark) {
  .nutril-roadmap-analyze {
    --surface: #1e293b;
    --surface-alt: #0f172a;
    --text-primary: #f1f5f9;
    --text-secondary: #cbd5e1;
    --text-muted: #64748b;
    --border: #334155;
    --border-hover: #475569;
    --primary-bg: rgba(37, 99, 235, 0.12);
    --primary-bg-deep: rgba(37, 99, 235, 0.2);
  }

  .header-badge {
    color: var(--primary-light);
    background: rgba(37, 99, 235, 0.15);
    border-color: rgba(37, 99, 235, 0.25);
  }
}

/* ─── Responsive ─── */
@media (max-width: 768px) {
  .nutril-roadmap-analyze {
    padding: 1.25rem 0.75rem;
  }
}

@media (min-width: 1024px) {
  .nutril-roadmap-analyze {
    padding: 2.5rem 2.5rem;
  }
}

/* ─── Smooth transitions ─── */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>