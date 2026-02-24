<template>
  <section
    class="exercise-roadmap-assess mx-auto px-4 lg:px-8 py-8 lg:py-12 bg-white dark:bg-gray-900"
  >
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section - Full Width -->
      <div class="col-span-12">
        <div class="flex justify-between items-center mb-6">
          <!-- Back Button -->
          <Button
            @click="$emit('back')"
            label="Quay Lại"
            icon="pi pi-arrow-left"
            text
            severity="secondary"
            size="small"
            class="!p-0"
          />

          <!-- Close Button -->
          <Button
            @click="$emit('close')"
            icon="pi pi-times"
            rounded
            text
            severity="secondary"
            size="small"
          />
        </div>
      </div>

      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-4">
        <i class="pi pi-heart-fill text-5xl text-blue-600 mb-4"></i>
        <h2
          class="text-2xl sm:text-3xl lg:text-4xl font-bold text-gray-800 dark:text-white"
        >
          {{ title }}
        </h2>
      </div>

      <!-- BMI Info Section - Full Width -->
      <div class="col-span-12 mb-4">
        <div class="bmi-info">
          <span class="bmi-label">Chỉ số BMI của bạn: </span>
          <span class="bmi-value">{{ bmiValue }}</span>
          <span class="bmi-status">({{ bmiStatus }})</span>
        </div>
      </div>

      <!-- Survey Title - Full Width -->
      <div class="col-span-12 mb-2">
        <h3 class="survey-title">{{ surveyTitle }}</h3>
      </div>

      <!-- Form Section - Full Width -->
      <div class="col-span-12">
        <div class="form-container">
          <!-- Question: Các loại hình bài tập -->
          <div class="question-group">
            <label class="question-label">
              <i class="pi pi-heart text-blue-600 mr-2"></i>
              {{ questionLabel }}
            </label>
            <div class="exercise-options">
              <div
                v-for="option in exerciseOptions"
                :key="option.value"
                class="exercise-option-item"
              >
                <div class="exercise-option-content">
                  <i :class="option.icon" class="exercise-icon"></i>
                  <span class="exercise-label">{{ option.label }}</span>
                </div>
                <Checkbox
                  v-model="selectedExercises"
                  :value="option.value"
                  :inputId="`exercise-${option.value}`"
                  class="exercise-checkbox"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Submit Button Section - Full Width -->
      <div class="col-span-12 mt-6">
        <Button
          :label="submitButtonLabel"
          class="setp2-button submit-btn w-full"
          :disabled="!isFormValid"
          @click="handleSubmit"
          :pt="{
            root: {
              class:
                'bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 dark:from-blue-700 dark:to-blue-800 border-0 py-4 text-base md:text-lg font-semibold shadow-lg hover:shadow-xl transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed',
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
import Checkbox from "primevue/checkbox";

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
    default: "Sở Thích Luyện Tập (Bước 2/2)",
  },
  questionLabel: {
    type: String,
    default: "Các loại hình bài tập bạn yêu thích?",
  },
  submitButtonLabel: {
    type: String,
    default: "Xem Demo Phác Đồ",
  },
  exerciseOptions: {
    type: Array,
    default: () => [
      {
        value: "tap-ta",
        label: "Tập tạ/Sức mạnh",
        icon: "pi pi-box",
      },
      {
        value: "cardio",
        label: "Cardio/Sức bền",
        icon: "pi pi-bolt",
      },
      {
        value: "yoga",
        label: "Yoga/Dẻo dai",
        icon: "pi pi-heart",
      },
    ],
  },
});

const emit = defineEmits(["back", "close", "submit"]);

// Form data
const selectedExercises = ref([]);

// Validate form
const isFormValid = computed(() => {
  return selectedExercises.value.length > 0;
});

// Handle submit
const handleSubmit = () => {
  if (isFormValid.value) {
    emit("submit", {
      exercises: selectedExercises.value,
    });
  }
};
</script>

<style scoped>
/* Base Section Styles */
.exercise-roadmap-assess {
  max-width: 1000px;
  border-radius: 1.5rem;
}

/* BMI Info Styles */
.bmi-info {
  background: linear-gradient(135deg, #eff6ff 0%, #dbeafe 100%);
  border: 2px solid #93c5fd;
  border-radius: 12px;
  padding: 16px 20px;
  text-align: center;
  font-size: 16px;
  line-height: 1.6;
}

.bmi-label {
  color: #475569;
  font-weight: 500;
}

.bmi-value {
  color: #1e40af;
  font-weight: 700;
  font-size: 20px;
  margin: 0 6px;
}

.bmi-status {
  color: #059669;
  font-weight: 600;
}

/* Survey Title */
.survey-title {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  text-align: center;
  padding: 12px;
  background: #f8fafc;
  border-radius: 8px;
  border: 1px solid #e2e8f0;
}

/* Form Container */
.form-container {
  background: #ffffff;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  padding: 24px;
}

/* Question Group */
.question-group {
  margin-bottom: 0;
}

.question-label {
  display: flex;
  align-items: center;
  font-size: 15px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 18px;
}

/* Exercise Options */
.exercise-options {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.exercise-option-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  background: #f8fafc;
  border: 2px solid #e2e8f0;
  border-radius: 10px;
  transition: all 0.2s ease;
  cursor: pointer;
}

.exercise-option-item:hover {
  background: #eff6ff;
  border-color: #93c5fd;
  transform: translateX(4px);
}

.exercise-option-item:has(.p-checkbox-checked) {
  background: linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%);
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.12);
}

.exercise-option-content {
  display: flex;
  align-items: center;
  gap: 14px;
}

.exercise-icon {
  font-size: 24px;
  color: #64748b;
  transition: all 0.2s ease;
}

.exercise-option-item:has(.p-checkbox-checked) .exercise-icon {
  color: #3b82f6;
  transform: scale(1.1);
}

.exercise-label {
  font-size: 15px;
  color: #334155;
  font-weight: 500;
}

.exercise-option-item:has(.p-checkbox-checked) .exercise-label {
  color: #1e40af;
  font-weight: 600;
}

.exercise-checkbox {
  flex-shrink: 0;
}

/* Checkbox Styling */
:deep(.p-checkbox .p-checkbox-box) {
  width: 22px;
  height: 22px;
  border-radius: 6px;
  border: 2px solid #cbd5e1;
  transition: all 0.2s ease;
  background: white;
}

:deep(.p-checkbox .p-checkbox-box:hover) {
  border-color: #3b82f6;
  background: #eff6ff;
}

:deep(.p-checkbox.p-checkbox-checked .p-checkbox-box) {
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%) !important;
  border-color: #3b82f6 !important;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
}

:deep(.p-checkbox .p-checkbox-box .p-checkbox-icon) {
  font-size: 13px;
  color: white;
}

/* Dark mode adjustments */
@media (prefers-color-scheme: dark) {
  .bmi-info {
    background: linear-gradient(135deg, #1e3a8a 0%, #1e293b 100%);
    border-color: #3b82f6;
  }

  .bmi-label,
  .bmi-status {
    color: #d1d5db;
  }

  .bmi-value {
    color: #93c5fd;
  }

  .survey-title {
    background: #1f2937;
    color: #e5e7eb;
    border-color: #374151;
  }

  .form-container {
    background: #1f2937;
    border-color: #374151;
  }

  .question-label {
    color: #e5e7eb;
  }

  .exercise-option-item {
    background: #374151;
    border-color: #4b5563;
  }

  .exercise-option-item:hover {
    background: #4b5563;
  }

  .exercise-icon {
    color: #9ca3af;
  }

  .exercise-label {
    color: #d1d5db;
  }

  :deep(.p-checkbox .p-checkbox-box) {
    background: #374151;
    border-color: #4b5563;
  }
}

/* Responsive */
@media (max-width: 768px) {
  .exercise-option-content {
    gap: 12px;
  }

  .exercise-icon {
    font-size: 20px;
  }

  .exercise-label {
    font-size: 14px;
  }
}

/* Smooth transitions */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

/* Submit button animation */
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

:deep(.setp2-button) {
  background-color: #2563eb;
}
</style>