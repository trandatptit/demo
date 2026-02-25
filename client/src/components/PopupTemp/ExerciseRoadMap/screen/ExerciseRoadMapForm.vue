<template>
  <section class="nutril-roadmap-form mx-auto px-4 lg:px-8 py-8 lg:py-12 bg-white dark:bg-gray-900">
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
        <h2 class="text-2xl sm:text-3xl lg:text-4xl font-bold text-gray-800 dark:text-white">
          {{ title }}
        </h2>
        <p class="text-sm md:text-base text-gray-600 dark:text-gray-400 mt-2">
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
            class="col-span-12 md:col-span-6 lg:col-span-6"
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
              <div v-if="option.hasSubOptions && selectedOptions.includes(option.id)" class="sub-options">
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
          :pt="{
            root: { 
              class: 'bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 dark:from-blue-700 dark:to-blue-800 border-0 py-4 text-base md:text-lg font-semibold shadow-lg hover:shadow-xl transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed'
            }
          }"
        />
        
        <!-- Selected Count -->
        <div v-if="selectedOptions.length > 0" class="text-center mt-3">
          <span class="text-sm text-gray-600 dark:text-gray-400">
            Đã chọn {{ selectedOptions.length }} loại hình tập luyện
          </span>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import Button from 'primevue/button';
import Checkbox from 'primevue/checkbox';

const props = defineProps({
  title: {
    type: String,
    default: 'Các loại hình bài tập bạn yêu thích?'
  },
  options: {
    type: Array,
    default: () => [
      {
        id: 'strength',
        label: 'Tập tạ/Sức mạnh',
        icon: 'pi pi-bolt',
        hasSubOptions: false
      },
      {
        id: 'cardio',
        label: 'Cardio/Sức bền',
        icon: 'pi pi-heart',
        hasSubOptions: true,
        subQuestion: 'Day nhảy chưa?',
        subHelper: 'Sở hữu dụng cụ phù hợp để có thể thúc đẩy quá trình tập!',
        subButtonLabel: 'Đặt mua ngay'
      },
      {
        id: 'home-daily',
        label: 'Bài tập tại nhà (Daily dây, Squat.)',
        icon: 'pi pi-home',
        hasSubOptions: false
      },
      {
        id: 'yoga',
        label: 'Yoga/Dẻo dai',
        icon: 'pi pi-heart',
        hasSubOptions: true,
        subQuestion: 'Thảm tập Yoga chưa?',
        subHelper: 'Sở hữu dụng cụ phù hợp để có thể thúc đẩy quá trình tập!',
        subButtonLabel: 'Đặt mua ngay'
      },
      {
        id: 'gym',
        label: 'Bài tập tại phòng Gym',
        icon: 'pi pi-chart-bar',
        hasSubOptions: false
      }
    ]
  },
  submitButtonLabel: {
    type: String,
    default: 'Xem Demo Phác Đồ'
  }
});

const emit = defineEmits(['back', 'close', 'submit', 'buyNow']);

const selectedOptions = ref([]);

const handleSubmit = () => {
  if (selectedOptions.value.length > 0) {
    emit('submit', selectedOptions.value);
  }
};
</script>
<style scoped>
/* Base Section Styles */
.nutril-roadmap-form {
  max-width: 1200px;
  border-radius: 1.5rem;
}

/* Option Item Styles */
.option-item {
  background: #ffffff;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  padding: 16px 18px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.option-item:hover {
  border-color: #cbd5e1;
  background: #f8fafc;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

/* Fix: Đổi selector để detect checkbox checked */
.option-item:has(.p-checkbox.p-checkbox-checked) {
  border-color: #3b82f6;
  background: linear-gradient(135deg, #eff6ff 0%, #dbeafe 100%);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.12);
}

.option-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.option-icon {
  font-size: 22px;
  color: #64748b;
  flex-shrink: 0;
  transition: all 0.2s ease;
}

/* Fix: Đổi selector để đổi màu icon khi checked */
.option-item:has(.p-checkbox.p-checkbox-checked) .option-icon {
  color: #3b82f6;
  transform: scale(1.1);
}

.option-label {
  flex: 1;
  font-size: 15px;
  color: #1e293b;
  font-weight: 500;
  line-height: 1.5;
}

/* Fix: Đổi selector để đổi màu label khi checked */
.option-item:has(.p-checkbox.p-checkbox-checked) .option-label {
  color: #1e40af;
  font-weight: 600;
}

.option-checkbox {
  flex-shrink: 0;
}

/* Sub-options Styles */
.sub-options {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid #e2e8f0;
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
  font-size: 13px;
  color: #334155;
  margin-bottom: 8px;
  font-weight: 600;
}

.sub-helper {
  font-size: 12px;
  color: #64748b;
  margin-bottom: 14px;
  line-height: 1.6;
}

.buy-now-btn {
  width: 100%;
  background: linear-gradient(135deg, #1e40af 0%, #1e3a8a 100%);
  border: none;
  padding: 10px 16px;
  font-size: 13px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.buy-now-btn:hover {
  background: linear-gradient(135deg, #1e3a8a 0%, #1e293b 100%);
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(30, 64, 175, 0.3);
}

.buy-now-btn:active {
  transform: translateY(0);
}

/* Checkbox Styling */
:deep(.p-checkbox .p-checkbox-box) {
  width: 24px;
  height: 24px;
  border-radius: 6px;
  border: 2px solid #cbd5e1;
  transition: all 0.2s ease;
  background: white;
}

:deep(.p-checkbox .p-checkbox-box:hover) {
  border-color: #3b82f6;
  background: #eff6ff;
}

/* Fix: Thêm !important để override PrimeVue default */
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
  .option-item {
    background: #1f2937;
    border-color: #374151;
  }
  
  .option-item:hover {
    background: #374151;
    border-color: #4b5563;
  }
  
  /* Fix: Dark mode checked state */
  .option-item:has(.p-checkbox.p-checkbox-checked) {
    background: linear-gradient(135deg, #1e3a8a 0%, #1e293b 100%);
    border-color: #3b82f6;
  }
  
  .option-label {
    color: #e5e7eb;
  }
  
  /* Fix: Dark mode label checked */
  .option-item:has(.p-checkbox.p-checkbox-checked) .option-label {
    color: #93c5fd;
  }
  
  .sub-question {
    color: #d1d5db;
  }
  
  .sub-helper {
    color: #9ca3af;
  }
  
  .sub-options {
    border-top-color: #374151;
  }
  
  :deep(.p-checkbox .p-checkbox-box) {
    background: #374151;
    border-color: #4b5563;
  }
  
  :deep(.p-checkbox .p-checkbox-box:hover) {
    background: #4b5563;
  }
}

/* Grid Responsive Adjustments */
@media (min-width: 768px) {
  .option-item {
    padding: 18px 20px;
  }
  
  .option-icon {
    font-size: 24px;
  }
  
  .option-label {
    font-size: 16px;
  }
}

@media (min-width: 1024px) {
  .nutril-roadmap-form {
    padding: 2rem 2.5rem;
  }
}

/* Smooth transitions for grid layout changes */
.col-span-12,
.col-span-6 {
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