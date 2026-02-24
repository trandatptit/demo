<template>
  <section class="exercise-roadmap-assess mx-auto px-4 lg:px-8 py-8 lg:py-12 bg-white dark:bg-gray-900">
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
        <h2 class="text-2xl sm:text-3xl lg:text-4xl font-bold text-gray-800 dark:text-white">
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
                <label :for="option.value" class="checkbox-label">{{ option.label }}</label>
              </div>
            </div>
          </div>

          <!-- Question 2: Mục đô hiện tại -->
          <div class="question-group">
            <label class="question-label">{{ questions.currentLevel.label }}</label>
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
                <label :for="`level-${option.value}`" class="radio-label">{{ option.label }}</label>
              </div>
            </div>
          </div>

          <!-- Question 3: Điều kiện tập -->
          <div class="question-group">
            <label class="question-label">{{ questions.trainingCondition.label }}</label>
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
                <label :for="`condition-${option.value}`" class="radio-label">{{ option.label }}</label>
              </div>
            </div>
          </div>

          <!-- Question 4: Thời gian tập -->
          <div class="question-group">
            <label class="question-label">{{ questions.trainingTime.label }}</label>
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
                <label :for="`time-${option.value}`" class="radio-label">{{ option.label }}</label>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Submit Button Section - Full Width -->
      <div class="col-span-12 mt-6">
        <Button 
          :label="submitButtonLabel" 
          class="submit-btn w-full"
          :disabled="!isFormValid"
          @click="handleSubmit"
          :pt="{
            root: { 
              class: 'bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 dark:from-blue-700 dark:to-blue-800 border-0 py-4 text-base md:text-lg font-semibold shadow-lg hover:shadow-xl transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed'
            }
          }"
        />
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed } from 'vue';
import Button from 'primevue/button';
import Checkbox from 'primevue/checkbox';
import RadioButton from 'primevue/radiobutton';

const props = defineProps({
  title: {
    type: String,
    default: 'Phác Đồ Luyện Tập Đột Phá'
  },
  bmiValue: {
    type: [Number, String],
    default: 19.5
  },
  bmiStatus: {
    type: String,
    default: 'Bình thường'
  },
  surveyTitle: {
    type: String,
    default: 'Khảo Sát Luyện Tập (Bước 1/2)'
  },
  submitButtonLabel: {
    type: String,
    default: 'Tiếp Theo'
  },
  questions: {
    type: Object,
    default: () => ({
      disease: {
        label: 'Bệnh lý (nếu có)',
        options: [
          { value: 'tim-mach', label: 'Tim mạch' },
          { value: 'xuong-khop', label: 'Xương khớp' },
          { value: 'cap-tinh', label: 'Cấp tính' }
        ]
      },
      currentLevel: {
        label: 'Mức độ hiện tại',
        options: [
          { value: 'moi-bat-dau', label: 'Mới bắt đầu' },
          { value: 'trung-binh', label: 'Trung bình' },
          { value: 'nang-cao', label: 'Nâng cao' }
        ]
      },
      trainingCondition: {
        label: 'Điều kiện tập',
        options: [
          { value: 'tai-nha', label: 'Tại nhà' },
          { value: 'phong-gym', label: 'Phòng gym' },
          { value: 'ngoai-troi', label: 'Ngoài trời' }
        ]
      },
      trainingTime: {
        label: 'Thời gian có thể tập mỗi ngày',
        options: [
          { value: '15', label: '15 phút' },
          { value: '30', label: '30 phút' },
          { value: '45', label: '45 phút' },
          { value: '60', label: '60 phút' }
        ]
      }
    })
  }
});

const emit = defineEmits(['back', 'close', 'submit']);

// Form data
const formData = ref({
  diseases: [],
  currentLevel: '',
  trainingCondition: '',
  trainingTime: ''
});

// Validate form
const isFormValid = computed(() => {
  return formData.value.currentLevel !== '' &&
         formData.value.trainingCondition !== '' &&
         formData.value.trainingTime !== '';
});

// Handle submit
const handleSubmit = () => {
  if (isFormValid.value) {
    emit('submit', formData.value);
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
  margin-bottom: 28px;
}

.question-group:last-child {
  margin-bottom: 0;
}

.question-label {
  display: block;
  font-size: 15px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 14px;
}

/* Checkbox Group */
.checkbox-group {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.checkbox-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 16px;
  background: #f8fafc;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  transition: all 0.2s ease;
  cursor: pointer;
  min-width: 140px;
}

.checkbox-item:hover {
  background: #eff6ff;
  border-color: #93c5fd;
}

.checkbox-item:has(.p-checkbox-checked) {
  background: linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%);
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.12);
}

.checkbox-label {
  font-size: 14px;
  color: #334155;
  font-weight: 500;
  cursor: pointer;
  user-select: none;
}

/* Radio Group */
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
  background: #f8fafc;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  transition: all 0.2s ease;
  cursor: pointer;
  min-width: 120px;
}

.radio-item:hover {
  background: #eff6ff;
  border-color: #93c5fd;
}

.radio-item:has(.p-radiobutton-checked) {
  background: linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%);
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.12);
}

.radio-label {
  font-size: 14px;
  color: #334155;
  font-weight: 500;
  cursor: pointer;
  user-select: none;
}

/* Checkbox Styling */
:deep(.p-checkbox .p-checkbox-box) {
  width: 20px;
  height: 20px;
  border-radius: 5px;
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
  font-size: 12px;
  color: white;
}

/* RadioButton Styling */
:deep(.p-radiobutton .p-radiobutton-box) {
  width: 20px;
  height: 20px;
  border: 2px solid #cbd5e1;
  transition: all 0.2s ease;
  background: white;
}

:deep(.p-radiobutton .p-radiobutton-box:hover) {
  border-color: #3b82f6;
  background: #eff6ff;
}

:deep(.p-radiobutton.p-radiobutton-checked .p-radiobutton-box) {
  border-color: #3b82f6 !important;
  background: white !important;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
}

:deep(.p-radiobutton .p-radiobutton-box .p-radiobutton-icon) {
  width: 10px;
  height: 10px;
  background-color: #3b82f6;
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
  
  .checkbox-item,
  .radio-item {
    background: #374151;
    border-color: #4b5563;
  }
  
  .checkbox-item:hover,
  .radio-item:hover {
    background: #4b5563;
  }
  
  .checkbox-label,
  .radio-label {
    color: #d1d5db;
  }
  
  :deep(.p-checkbox .p-checkbox-box),
  :deep(.p-radiobutton .p-radiobutton-box) {
    background: #374151;
    border-color: #4b5563;
  }
}

/* Responsive */
@media (max-width: 768px) {
  .checkbox-group,
  .radio-group {
    flex-direction: column;
  }
  
  .checkbox-item,
  .radio-item {
    width: 100%;
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
  content: '';
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
</style>