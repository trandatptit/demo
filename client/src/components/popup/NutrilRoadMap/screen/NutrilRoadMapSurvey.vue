<template>
  <section
    class="nutril-roadmap-survey mx-auto px-4 lg:px-8 py-8 lg:py-12 bg-white dark:bg-gray-900"
  >
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section - Full Width -->
      <div class="col-span-12 text-center mb-6">
        <!-- Title -->
        <h2
          class="text-2xl sm:text-3xl lg:text-4xl font-bold text-gray-800 dark:text-white mb-3"
        >
          Lộ Trình Dinh Dưỡng Tối Ưu
        </h2>

        <!-- Subtitle -->
        <p
          class="text-base md:text-lg font-semibold text-gray-700 dark:text-gray-300"
        >
          Khảo Sát Dinh Dưỡng Thông Minh
        </p>
      </div>

      <!-- Survey Form -->
      <div class="col-span-12">
        <div
          class="bg-gray-50 dark:bg-gray-800/50 rounded-2xl p-6 md:p-8 border border-gray-200 dark:border-gray-700"
        >
          <!-- Question 1: Budget -->
          <div class="survey-question mb-8">
            <label class="question-label">
              1. Mức tài chính hằng tháng cho dinh dưỡng?
            </label>
            <p class="question-hint">
              AI sẽ tối ưu thực đơn dựa trên ngân sách của bạn.
            </p>
            <div class="radio-group">
              <div
                v-for="option in budgetOptions"
                :key="option.value"
                class="radio-item"
                @click="surveyData.budget = option.value"
              >
                <RadioButton
                  v-model="surveyData.budget"
                  :inputId="'budget-' + option.value"
                  name="budget"
                  :value="option.value"
                />
                <label :for="'budget-' + option.value" class="radio-label">
                  {{ option.label }}
                </label>
              </div>
            </div>
          </div>

          <!-- Question 2: Calcium Supplement -->
          <div class="survey-question mb-8">
            <label class="question-label">
              2. Bạn có đang dùng canxi không?
            </label>
            <div class="radio-group">
              <div
                v-for="option in yesNoOptions"
                :key="option.value"
                class="radio-item"
                @click="surveyData.useCalcium = option.value"
              >
                <RadioButton
                  v-model="surveyData.useCalcium"
                  :inputId="'calcium-' + option.value"
                  name="calcium"
                  :value="option.value"
                />
                <label :for="'calcium-' + option.value" class="radio-label">
                  {{ option.label }}
                </label>
              </div>
            </div>
          </div>

          <!-- Question 3: Food Preferences -->
          <div class="survey-question mb-8">
            <label class="question-label"> 3. Sở thích món ăn của bạn? </label>
            <div class="checkbox-grid">
              <div
                v-for="food in foodPreferences"
                :key="food.value"
                class="checkbox-item"
              >
                <Checkbox
                  v-model="surveyData.foodPreferences"
                  :inputId="'food-' + food.value"
                  name="food"
                  :value="food.value"
                />
                <label :for="'food-' + food.value" class="checkbox-label">
                  <i :class="food.icon" class="mr-2"></i>
                  {{ food.label }}
                </label>
              </div>
            </div>
          </div>

          <!-- Question 4: Allergies -->
          <div class="survey-question mb-8">
            <label class="question-label"> 4. Bạn có dị ứng với? </label>
            <div class="checkbox-grid">
              <div
                v-for="allergy in allergyOptions"
                :key="allergy.value"
                class="checkbox-item"
              >
                <Checkbox
                  v-model="surveyData.allergies"
                  :inputId="'allergy-' + allergy.value"
                  name="allergy"
                  :value="allergy.value"
                />
                <label :for="'allergy-' + allergy.value" class="checkbox-label">
                  <i :class="allergy.icon" class="mr-2"></i>
                  {{ allergy.label }}
                </label>
              </div>
            </div>
          </div>

          <!-- Question 5: Cooking Frequency -->
          <div class="survey-question mb-6">
            <label class="question-label">
              5. Bạn có thường nấu ăn không?
            </label>
            <div class="radio-group">
              <div
                v-for="option in cookingFrequencyOptions"
                :key="option.value"
                class="radio-item"
                @click="surveyData.cookingFrequency = option.value"
              >
                <RadioButton
                  v-model="surveyData.cookingFrequency"
                  :inputId="'cooking-' + option.value"
                  name="cooking"
                  :value="option.value"
                />
                <label :for="'cooking-' + option.value" class="radio-label">
                  {{ option.label }}
                </label>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Submit Button Section - Full Width -->
      <div class="col-span-12 mt-4">
        <Button
          @click="submitSurvey"
          label="Xem Gợi Ý Từ AI"
          icon="pi pi-arrow-right"
          iconPos="right"
          class="w-full"
          :disabled="!isFormValid"
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
import RadioButton from "primevue/radiobutton";
import Checkbox from "primevue/checkbox";

// Events
const emit = defineEmits(["back", "close", "submit"]);

// Survey data
const surveyData = ref({
  budget: "",
  useCalcium: "",
  foodPreferences: [],
  allergies: [],
  cookingFrequency: "",
});

// Options data
const budgetOptions = [
  { value: "low", label: "< 800k" },
  { value: "medium", label: "800k - 1.5tr" },
  { value: "high", label: "> 1.5tr" },
];

const yesNoOptions = [
  { value: "yes", label: "Có" },
  { value: "no", label: "Không" },
];

const foodPreferences = [
  { value: "seafood", label: "Hải sản", icon: "pi pi-circle-fill" },
  { value: "meat", label: "Các loại thịt", icon: "pi pi-circle-fill" },
  { value: "veggies", label: "Món chay/rau củ", icon: "pi pi-circle-fill" },
];

const allergyOptions = [
  { value: "seafood", label: "Hải sản", icon: "pi pi-circle-fill" },
  { value: "gluten", label: "Gluten/Lúa mì", icon: "pi pi-circle-fill" },
];

const cookingFrequencyOptions = [
  { value: "often", label: "Thường xuyên" },
  { value: "sometimes", label: "Thỉnh thoảng" },
  { value: "rarely", label: "Hầu như không" },
];

// Form validation
const isFormValid = computed(() => {
  return (
    surveyData.value.budget !== "" &&
    surveyData.value.useCalcium !== "" &&
    surveyData.value.cookingFrequency !== ""
  );
});

// Submit survey
const submitSurvey = () => {
  if (isFormValid.value) {
    emit("submit", surveyData.value);
  }
};
</script>

<style scoped>
/* Base Section Styles */
.nutril-roadmap-survey {
  max-width: 1200px;
  border-radius: 1.5rem;
}

/* Survey Question Styles */
.survey-question {
  animation: fadeInUp 0.5s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.question-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 15px;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 8px;
}

.question-hint {
  font-size: 13px;
  color: #6b7280;
  margin-bottom: 16px;
  font-style: italic;
}

/* Radio Group Styles */
.radio-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.radio-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  background: white;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.radio-item:hover {
  border-color: #3b82f6;
  background: #eff6ff;
}

.radio-item:has(:checked) {
  border-color: #3b82f6;
  background: #eff6ff;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.radio-label {
  flex: 1;
  font-size: 14px;
  font-weight: 500;
  color: #374151;
  cursor: pointer;
  user-select: none;
}

/* Checkbox Grid Styles */
.checkbox-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 12px;
}

.checkbox-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  background: white;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.checkbox-item:hover {
  border-color: #3b82f6;
  background: #eff6ff;
}

.checkbox-item:has(:checked) {
  border-color: #3b82f6;
  background: #eff6ff;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.checkbox-label {
  flex: 1;
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: 500;
  color: #374151;
  cursor: pointer;
  user-select: none;
}

/* PrimeVue Component Overrides */
:deep(.p-radiobutton) {
  width: 20px;
  height: 20px;
}

:deep(.p-radiobutton .p-radiobutton-box) {
  width: 20px;
  height: 20px;
  border-width: 2px;
}

:deep(.p-checkbox) {
  width: 20px;
  height: 20px;
}

:deep(.p-checkbox .p-checkbox-box) {
  width: 20px;
  height: 20px;
  border-width: 2px;
}

/* Dark mode adjustments */
@media (prefers-color-scheme: dark) {
  .question-label {
    color: #e5e7eb;
  }

  .question-hint {
    color: #9ca3af;
  }

  .radio-item,
  .checkbox-item {
    background: #1f2937;
    border-color: #374151;
  }

  .radio-item:hover,
  .checkbox-item:hover {
    border-color: #3b82f6;
    background: #1e3a8a;
  }

  .radio-item:has(:checked),
  .checkbox-item:has(:checked) {
    border-color: #3b82f6;
    background: #1e3a8a;
  }

  .radio-label,
  .checkbox-label {
    color: #d1d5db;
  }
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .checkbox-grid {
    grid-template-columns: 1fr;
  }

  .question-label {
    font-size: 14px;
  }

  .question-hint {
    font-size: 12px;
  }
}

@media (min-width: 1024px) {
  .nutril-roadmap-survey {
    padding: 2rem 2.5rem;
  }
}

/* Smooth transitions for grid layout changes */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>
