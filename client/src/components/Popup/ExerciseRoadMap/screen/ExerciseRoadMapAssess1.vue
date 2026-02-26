<template>
  <section class="exercise-roadmap-assess">
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
          <i class="pi pi-heart-fill"></i> Đánh Giá
        </span>
        <h2 class="header-title">{{ title }}</h2>
      </div>

      <!-- BMI Info Section - Full Width -->
      <div class="col-span-12">
        <div class="bmi-info">
          <span class="bmi-label">Chỉ số BMI của bạn: </span>
          <span class="bmi-value">{{ bmiValue }}</span>
          <span class="bmi-status">({{ bmiStatus }})</span>
        </div>
      </div>

      <!-- Survey Title - Full Width -->
      <div class="col-span-12">
        <h3 class="survey-title">{{ surveyTitle }}</h3>
      </div>

      <!-- Form Section - Full Width -->
      <div class="col-span-12">
        <div class="form-container">
          <!-- Question 1: Bệnh lý -->
          <div class="question-group">
            <label class="question-label">{{ questions.disease.label }}</label>
            <div class="checkbox-group">
              <div
                v-for="option in questions.disease.options"
                :key="option.value"
                class="checkbox-item"
              >
                <Checkbox
                  v-model="formData.diseases"
                  :value="option.value"
                  :inputId="option.value"
                />
                <label :for="option.value" class="checkbox-label">{{
                  option.label
                }}</label>
              </div>
            </div>
          </div>

          <!-- Question 2: Mức độ hiện tại -->
          <div class="question-group">
            <label class="question-label">{{
              questions.currentLevel.label
            }}</label>
            <div class="radio-group">
              <div
                v-for="option in questions.currentLevel.options"
                :key="option.value"
                class="radio-item"
              >
                <RadioButton
                  v-model="formData.currentLevel"
                  :value="option.value"
                  :inputId="`level-${option.value}`"
                />
                <label :for="`level-${option.value}`" class="radio-label">{{
                  option.label
                }}</label>
              </div>
            </div>
          </div>

          <!-- Question 3: Điều kiện tập -->
          <div class="question-group">
            <label class="question-label">{{
              questions.trainingCondition.label
            }}</label>
            <div class="radio-group">
              <div
                v-for="option in questions.trainingCondition.options"
                :key="option.value"
                class="radio-item"
              >
                <RadioButton
                  v-model="formData.trainingCondition"
                  :value="option.value"
                  :inputId="`condition-${option.value}`"
                />
                <label :for="`condition-${option.value}`" class="radio-label">{{
                  option.label
                }}</label>
              </div>
            </div>
          </div>

          <!-- Question 4: Thời gian tập -->
          <div class="question-group">
            <label class="question-label">{{
              questions.trainingTime.label
            }}</label>
            <div class="radio-group">
              <div
                v-for="option in questions.trainingTime.options"
                :key="option.value"
                class="radio-item"
              >
                <RadioButton
                  v-model="formData.trainingTime"
                  :value="option.value"
                  :inputId="`time-${option.value}`"
                />
                <label :for="`time-${option.value}`" class="radio-label">{{
                  option.label
                }}</label>
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
          :disabled="!isFormValid"
          @click="handleSubmit"
        />
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed } from "vue";
import Button from "primevue/button";
import Checkbox from "primevue/checkbox";
import RadioButton from "primevue/radiobutton";

const props = defineProps({
  title: {
    type: String,
    default: "Phác Đồ Luyện Tập Đột Phá",
  },
  bmiValue: {
    type: [Number, String],
    default: 19.5,
  },
  bmiStatus: {
    type: String,
    default: "Bình thường",
  },
  surveyTitle: {
    type: String,
    default: "Khảo Sát Luyện Tập (Bước 1/2)",
  },
  submitButtonLabel: {
    type: String,
    default: "Tiếp Theo",
  },
  questions: {
    type: Object,
    default: () => ({
      disease: {
        label: "Bệnh lý (nếu có)",
        options: [
          { value: "tim-mach", label: "Tim mạch" },
          { value: "xuong-khop", label: "Xương khớp" },
          { value: "cap-tinh", label: "Cấp tính" },
        ],
      },
      currentLevel: {
        label: "Mức độ hiện tại",
        options: [
          { value: "moi-bat-dau", label: "Mới bắt đầu" },
          { value: "trung-binh", label: "Trung bình" },
          { value: "nang-cao", label: "Nâng cao" },
        ],
      },
      trainingCondition: {
        label: "Điều kiện tập",
        options: [
          { value: "tai-nha", label: "Tại nhà" },
          { value: "phong-gym", label: "Phòng gym" },
          { value: "ngoai-troi", label: "Ngoài trời" },
        ],
      },
      trainingTime: {
        label: "Thời gian có thể tập mỗi ngày",
        options: [
          { value: "15", label: "15 phút" },
          { value: "30", label: "30 phút" },
          { value: "45", label: "45 phút" },
          { value: "60", label: "60 phút" },
        ],
      },
    }),
  },
});

const emit = defineEmits(["back", "close", "submit"]);

// Form data
const formData = ref({
  diseases: [],
  currentLevel: "",
  trainingCondition: "",
  trainingTime: "",
});

// Validate form
const isFormValid = computed(() => {
  return (
    formData.value.currentLevel !== "" &&
    formData.value.trainingCondition !== "" &&
    formData.value.trainingTime !== ""
  );
});

// Handle submit
const handleSubmit = () => {
  if (isFormValid.value) {
    emit("submit", formData.value);
  }
};
</script>

<style scoped>
/* ─── Design Tokens (matching NutrilRoadMapSchedule) ─── */
.exercise-roadmap-assess {
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
  --success: #059669;

  max-width: 1000px;
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
  margin: 0;
  line-height: 1.25;
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

/* ─── BMI Info ─── */
.bmi-info {
  background: linear-gradient(
    135deg,
    var(--primary-bg) 0%,
    var(--primary-bg-deep) 100%
  );
  border: 1.5px solid var(--primary-bg-deep);
  border-radius: var(--radius);
  padding: 14px 20px;
  text-align: center;
  font-size: 0.95rem;
  line-height: 1.6;
}

.bmi-label {
  color: var(--text-secondary);
  font-weight: 500;
}

.bmi-value {
  color: var(--primary-dark);
  font-weight: 700;
  font-size: 1.15rem;
  margin: 0 6px;
}

.bmi-status {
  color: var(--success);
  font-weight: 600;
}

/* ─── Survey Title ─── */
.survey-title {
  font-size: 1rem;
  font-weight: 600;
  color: var(--text-primary);
  text-align: center;
  padding: 10px 16px;
  background: var(--surface-alt);
  border-radius: 10px;
  border: 1px solid var(--border);
  margin: 0;
}

/* ─── Form Container ─── */
.form-container {
  background: var(--surface);
  border: 1.5px solid var(--border);
  border-radius: 16px;
  padding: 24px;
  box-shadow: var(--shadow-sm);
  position: relative;
  overflow: hidden;
}

.form-container::before {
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
}

/* ─── Question Group ─── */
.question-group {
  margin-bottom: 24px;
}

.question-group:last-child {
  margin-bottom: 0;
}

.question-label {
  display: block;
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
}

/* ─── Checkbox Group ─── */
.checkbox-group {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.checkbox-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 16px;
  background: var(--surface-alt);
  border: 1.5px solid var(--border);
  border-radius: 10px;
  transition: all 0.2s ease;
  cursor: pointer;
  min-width: 140px;
}

.checkbox-item:hover {
  background: var(--primary-bg);
  border-color: var(--primary-light);
}

.checkbox-item:has(.p-checkbox-checked) {
  background: linear-gradient(
    135deg,
    var(--primary-bg) 0%,
    var(--primary-bg-deep) 100%
  );
  border-color: var(--primary-light);
  box-shadow: 0 0 0 3px var(--primary-ring);
}

.checkbox-label {
  font-size: 0.85rem;
  color: var(--text-secondary);
  font-weight: 500;
  cursor: pointer;
  user-select: none;
}

/* ─── Radio Group ─── */
.radio-group {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.radio-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 16px;
  background: var(--surface-alt);
  border: 1.5px solid var(--border);
  border-radius: 10px;
  transition: all 0.2s ease;
  cursor: pointer;
  min-width: 120px;
}

.radio-item:hover {
  background: var(--primary-bg);
  border-color: var(--primary-light);
}

.radio-item:has(.p-radiobutton-checked) {
  background: linear-gradient(
    135deg,
    var(--primary-bg) 0%,
    var(--primary-bg-deep) 100%
  );
  border-color: var(--primary-light);
  box-shadow: 0 0 0 3px var(--primary-ring);
}

.radio-label {
  font-size: 0.85rem;
  color: var(--text-secondary);
  font-weight: 500;
  cursor: pointer;
  user-select: none;
}

/* ─── PrimeVue Checkbox Override ─── */
:deep(.p-checkbox .p-checkbox-box) {
  width: 20px;
  height: 20px;
  border-radius: 5px;
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
  font-size: 12px;
  color: white;
}

/* ─── PrimeVue RadioButton Override ─── */
:deep(.p-radiobutton .p-radiobutton-box) {
  width: 20px;
  height: 20px;
  border: 2px solid var(--border-hover);
  transition: all 0.2s ease;
  background: var(--surface);
}

:deep(.p-radiobutton .p-radiobutton-box:hover) {
  border-color: var(--primary-light);
  background: var(--primary-bg);
}

:deep(.p-radiobutton.p-radiobutton-checked .p-radiobutton-box) {
  border-color: var(--primary-light) !important;
  background: var(--surface) !important;
  box-shadow: 0 0 0 3px var(--primary-ring);
}

:deep(.p-radiobutton .p-radiobutton-box .p-radiobutton-icon) {
  width: 10px;
  height: 10px;
  background-color: var(--primary-light);
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

/* ─── Submit button ripple animation ─── */
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
.bmi-info,
.form-container {
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
  .exercise-roadmap-assess {
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

  :deep(.p-checkbox .p-checkbox-box),
  :deep(.p-radiobutton .p-radiobutton-box) {
    background: #374151;
    border-color: #4b5563;
  }
}

/* ─── Responsive ─── */
@media (max-width: 768px) {
  .exercise-roadmap-assess {
    padding: 1.25rem 0.75rem;
  }

  .checkbox-group,
  .radio-group {
    flex-direction: column;
  }

  .checkbox-item,
  .radio-item {
    width: 100%;
  }
}

@media (min-width: 1024px) {
  .exercise-roadmap-assess {
    padding: 2rem 2.5rem;
  }
}

/* ─── Smooth transitions ─── */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>