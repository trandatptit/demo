<template>
  <section class="exercise-roadmap-form">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section - Full Width -->
      <div class="col-span-12">
        <div class="flex justify-between items-center mb-4">
          <!-- Back Button -->
          <Button
            @click="$emit('back')"
            label="Quay Lại"
            icon="pi pi-arrow-left"
            text
            class="back-btn"
          />

          <!-- Close Button -->
          <Button
            @click="$emit('close')"
            icon="pi pi-times"
            rounded
            text
            class="close-btn"
          />
        </div>
      </div>

      <!-- Title Section - Full Width -->
      <div class="col-span-12 survey-header">
        <div class="header-accent"></div>
        <span class="header-badge">
          <i class="pi pi-list-check"></i> Lộ Trình
        </span>
        <h2 class="header-title">{{ title }}</h2>
        <p class="header-subtitle">
          Chọn những bài tập phù hợp để tạo lộ trình tối ưu
        </p>
      </div>

      <!-- Options List Section - Full Width -->
      <div class="col-span-12">
        <div class="grid grid-cols-12 gap-3 md:gap-4">
          <!-- Each Option Item -->
          <div
            v-for="option in options"
            :key="option.id"
            class="col-span-12 md:col-span-6"
          >
            <div class="option-item">
              <div class="option-header">
                <i :class="option.icon" class="option-icon"></i>
                <span class="option-label">{{ option.label }}</span>
                <Checkbox
                  v-model="selectedOptions"
                  :value="option.id"
                  class="option-checkbox"
                />
              </div>

              <!-- Sub-options nếu có -->
              <div
                v-if="
                  option.hasSubOptions && selectedOptions.includes(option.id)
                "
                class="sub-options"
              >
                <p class="sub-question">Bạn đã có {{ option.subQuestion }}</p>
                <p class="sub-helper">{{ option.subHelper }}</p>

                <Button
                  :label="option.subButtonLabel"
                  icon="pi pi-shopping-cart"
                  class="buy-now-btn"
                  @click="$emit('buyNow', option.id)"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Submit Button Section - Full Width -->
      <div class="col-span-12 mt-4">
        <Button
          :label="submitButtonLabel"
          class="submit-btn w-full"
          :disabled="selectedOptions.length === 0"
          @click="handleSubmit"
        />

        <!-- Selected Count -->
        <div v-if="selectedOptions.length > 0" class="selected-count">
          <span>Đã chọn {{ selectedOptions.length }} loại hình tập luyện</span>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import Button from "primevue/button";
import Checkbox from "primevue/checkbox";

const props = defineProps({
  title: {
    type: String,
    default: "Các loại hình bài tập bạn yêu thích?",
  },
  options: {
    type: Array,
    default: () => [
      {
        id: "strength",
        label: "Tập tạ/Sức mạnh",
        icon: "pi pi-bolt",
        hasSubOptions: false,
      },
      {
        id: "cardio",
        label: "Cardio/Sức bền",
        icon: "pi pi-heart",
        hasSubOptions: true,
        subQuestion: "Day nhảy chưa?",
        subHelper: "Sở hữu dụng cụ phù hợp để có thể thúc đẩy quá trình tập!",
        subButtonLabel: "Đặt mua ngay",
      },
      {
        id: "home-daily",
        label: "Bài tập tại nhà (Daily dây, Squat.)",
        icon: "pi pi-home",
        hasSubOptions: false,
      },
      {
        id: "yoga",
        label: "Yoga/Dẻo dai",
        icon: "pi pi-heart",
        hasSubOptions: true,
        subQuestion: "Thảm tập Yoga chưa?",
        subHelper: "Sở hữu dụng cụ phù hợp để có thể thúc đẩy quá trình tập!",
        subButtonLabel: "Đặt mua ngay",
      },
      {
        id: "gym",
        label: "Bài tập tại phòng Gym",
        icon: "pi pi-chart-bar",
        hasSubOptions: false,
      },
    ],
  },
  submitButtonLabel: {
    type: String,
    default: "Xem Demo Phác Đồ",
  },
});

const emit = defineEmits(["back", "close", "submit", "buyNow"]);

const selectedOptions = ref([]);

const handleSubmit = () => {
  if (selectedOptions.value.length > 0) {
    emit("submit", selectedOptions.value);
  }
};
</script>

<style scoped>
/* ─── Design Tokens (matching NutrilRoadMapSchedule) ─── */
.exercise-roadmap-form {
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
  border-radius: 1.5rem;
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
  background: var(--surface);
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
  font-size: 0.85rem;
  font-weight: 500;
  color: var(--text-secondary);
  margin: 0;
}

/* ─── Back/Close Buttons ─── */
:deep(.back-btn) {
  color: var(--text-secondary) !important;
  font-weight: 600 !important;
  padding: 0 !important;
}

:deep(.back-btn:hover) {
  color: var(--primary) !important;
}

:deep(.close-btn) {
  color: var(--text-muted) !important;
}

:deep(.close-btn:hover) {
  color: var(--text-primary) !important;
  background: var(--surface-alt) !important;
}

/* ─── Option Item ─── */
.option-item {
  background: var(--surface);
  border: 1.5px solid var(--border);
  border-radius: 16px;
  padding: 16px 18px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  overflow: hidden;
}

.option-item::before {
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
  opacity: 0;
  transition: opacity 0.3s ease;
}

.option-item:hover {
  border-color: var(--border-hover);
  background: var(--surface-alt);
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.option-item:hover::before {
  opacity: 1;
}

.option-item:has(.p-checkbox.p-checkbox-checked) {
  border-color: var(--primary-light);
  background: linear-gradient(
    135deg,
    var(--primary-bg) 0%,
    var(--primary-bg-deep) 100%
  );
  box-shadow: 0 0 0 3px var(--primary-ring);
}

.option-item:has(.p-checkbox.p-checkbox-checked)::before {
  opacity: 1;
}

.option-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.option-icon {
  font-size: 22px;
  color: var(--text-muted);
  flex-shrink: 0;
  transition: all 0.2s ease;
}

.option-item:has(.p-checkbox.p-checkbox-checked) .option-icon {
  color: var(--primary-light);
  transform: scale(1.1);
}

.option-label {
  flex: 1;
  font-size: 0.9rem;
  color: var(--text-primary);
  font-weight: 500;
  line-height: 1.5;
}

.option-item:has(.p-checkbox.p-checkbox-checked) .option-label {
  color: var(--primary-dark);
  font-weight: 600;
}

.option-checkbox {
  flex-shrink: 0;
}

/* ─── Sub-options ─── */
.sub-options {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid var(--border);
  animation: slideDown 0.3s ease;
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.sub-question {
  font-size: 0.8rem;
  color: var(--text-primary);
  margin-bottom: 6px;
  font-weight: 600;
}

.sub-helper {
  font-size: 0.75rem;
  color: var(--text-muted);
  margin-bottom: 12px;
  line-height: 1.6;
}

:deep(.buy-now-btn.p-button) {
  width: 100%;
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  );
  border: none;
  padding: 10px 16px;
  font-size: 0.8rem;
  font-weight: 600;
  border-radius: 10px;
  transition: all 0.3s ease;
}

:deep(.buy-now-btn.p-button:hover) {
  background: linear-gradient(135deg, #1e3a8a 0%, var(--primary-dark) 100%);
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(30, 64, 175, 0.3);
}

/* ─── PrimeVue Checkbox Override ─── */
:deep(.p-checkbox .p-checkbox-box) {
  width: 24px;
  height: 24px;
  border-radius: 6px;
  border: 2px solid var(--border-hover);
  transition: all 0.2s ease;
  background: var(--surface);
}

:deep(.p-checkbox .p-checkbox-box:hover) {
  border-color: var(--primary-light);
  background: var(--primary-bg);
}

:deep(.p-checkbox.p-checkbox-checked .p-checkbox-box) {
  background: linear-gradient(
    135deg,
    var(--primary-light) 0%,
    var(--primary) 100%
  ) !important;
  border-color: var(--primary-light) !important;
  box-shadow: 0 0 0 3px var(--primary-ring);
}

:deep(.p-checkbox .p-checkbox-box .p-checkbox-icon) {
  font-size: 13px;
  color: white;
}

/* ─── Selected Count ─── */
.selected-count {
  text-align: center;
  margin-top: 10px;
  font-size: 0.8rem;
  color: var(--text-muted);
  font-weight: 500;
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

:deep(.submit-btn.p-button:disabled) {
  opacity: 0.5;
  cursor: not-allowed;
}

/* ─── Ripple ─── */
.submit-btn {
  position: relative;
  overflow: hidden;
}

.submit-btn::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  transform: translate(-50%, -50%);
  transition: width 0.6s, height 0.6s;
}

.submit-btn:hover::before {
  width: 300px;
  height: 300px;
}

/* ─── Animations ─── */
.survey-header,
.option-item {
  animation: fadeInUp 0.4s ease-out both;
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
  .exercise-roadmap-form {
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

  .option-item:has(.p-checkbox.p-checkbox-checked) .option-label {
    color: #93c5fd;
  }

  .sub-options {
    border-top-color: var(--border);
  }

  :deep(.p-checkbox .p-checkbox-box) {
    background: #374151;
    border-color: #4b5563;
  }

  :deep(.p-checkbox .p-checkbox-box:hover) {
    background: #4b5563;
  }
}

/* ─── Responsive ─── */
@media (max-width: 768px) {
  .exercise-roadmap-form {
    padding: 1.25rem 0.75rem;
  }
}

@media (min-width: 768px) {
  .option-item {
    padding: 18px 20px;
  }

  .option-icon {
    font-size: 24px;
  }

  .option-label {
    font-size: 0.95rem;
  }
}

@media (min-width: 1024px) {
  .exercise-roadmap-form {
    padding: 2rem 2.5rem;
  }
}

/* ─── Smooth transitions ─── */
.col-span-12,
.col-span-6 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>