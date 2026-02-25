<template>
  <section class="nutril-roadmap-survey">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section -->
      <div class="col-span-12 survey-header">
        <div class="header-accent"></div>
        <span class="header-badge">
          <i class="pi pi-sparkles"></i> AI Nutrition
        </span>
        <h2 class="header-title">Lộ Trình Dinh Dưỡng Tối Ưu</h2>
        <p class="header-subtitle">Khảo Sát Dinh Dưỡng Thông Minh</p>
      </div>

      <!-- Survey Form -->
      <div class="col-span-12">
        <div class="survey-card">
          <!-- Question 1: Budget -->
          <div class="survey-question mb-8">
            <label class="question-label">
              <span class="question-number">1</span>
              Mức tài chính hằng tháng cho dinh dưỡng?
            </label>
            <p class="question-hint">
              AI sẽ tối ưu thực đơn dựa trên ngân sách của bạn.
            </p>
            <div class="radio-group">
              <div
                v-for="option in budgetOptions"
                :key="option.value"
                class="radio-item"
                :class="{ 'is-selected': surveyData.budget === option.value }"
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
              <span class="question-number">2</span>
              Bạn có đang dùng canxi không?
            </label>
            <div class="radio-group">
              <div
                v-for="option in yesNoOptions"
                :key="option.value"
                class="radio-item"
                :class="{
                  'is-selected': surveyData.useCalcium === option.value,
                }"
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
            <label class="question-label">
              <span class="question-number">3</span>
              Sở thích món ăn của bạn?
            </label>
            <div class="checkbox-grid">
              <div
                v-for="food in foodPreferences"
                :key="food.value"
                class="checkbox-item"
                :class="{
                  'is-selected': surveyData.foodPreferences.includes(
                    food.value
                  ),
                }"
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
            <label class="question-label">
              <span class="question-number">4</span>
              Bạn có dị ứng với?
            </label>
            <div class="checkbox-grid">
              <div
                v-for="allergy in allergyOptions"
                :key="allergy.value"
                class="checkbox-item"
                :class="{
                  'is-selected': surveyData.allergies.includes(allergy.value),
                }"
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
              <span class="question-number">5</span>
              Bạn có thường nấu ăn không?
            </label>
            <div class="radio-group">
              <div
                v-for="option in cookingFrequencyOptions"
                :key="option.value"
                class="radio-item"
                :class="{
                  'is-selected': surveyData.cookingFrequency === option.value,
                }"
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

      <!-- Submit Button Section -->
      <div class="col-span-12 mt-4">
        <Button
          @click="submitSurvey"
          label="Xem Gợi Ý Từ AI"
          icon="pi pi-arrow-right"
          iconPos="right"
          class="w-full submit-btn"
          :disabled="!isFormValid"
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
/* ─── Design Tokens ─── */
.nutril-roadmap-survey {
  --primary: #2563eb;
  --primary-light: #3b82f6;
  --primary-dark: #1d4ed8;
  --primary-bg: #eff6ff;
  --primary-bg-deep: #dbeafe;
  --primary-ring: rgba(37, 99, 235, 0.15);
  --primary-ring-strong: rgba(37, 99, 235, 0.25);
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
  --shadow-focus: 0 0 0 3px var(--primary-ring);

  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
  border-radius: 1.5rem;
}

/* ─── Header ─── */
.survey-header {
  text-align: center;
  margin-bottom: 1.5rem;
  position: relative;
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

/* ─── Survey Card ─── */
.survey-card {
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 2rem;
  box-shadow: var(--shadow-sm);
  position: relative;
  overflow: hidden;
}

.survey-card::before {
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

/* ─── Question ─── */
.survey-question {
  animation: fadeInUp 0.4s ease-out both;
}

.survey-question:nth-child(2) {
  animation-delay: 0.05s;
}
.survey-question:nth-child(3) {
  animation-delay: 0.1s;
}
.survey-question:nth-child(4) {
  animation-delay: 0.15s;
}
.survey-question:nth-child(5) {
  animation-delay: 0.2s;
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

.question-label {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 15px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 6px;
}

.question-number {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 26px;
  height: 26px;
  font-size: 13px;
  font-weight: 700;
  color: #fff;
  background: var(--primary);
  border-radius: 8px;
  flex-shrink: 0;
}

.question-hint {
  font-size: 13px;
  color: var(--text-muted);
  margin: 0 0 14px 36px;
  font-style: italic;
}

/* ─── Radio Group ─── */
.radio-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.radio-item,
.checkbox-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  background: var(--surface-alt);
  border: 1.5px solid var(--border);
  border-radius: var(--radius);
  cursor: pointer;
  transition: all 0.2s ease;
}

.radio-item:hover,
.checkbox-item:hover {
  border-color: var(--primary-light);
  background: var(--primary-bg);
}

.radio-item.is-selected,
.checkbox-item.is-selected {
  border-color: var(--primary);
  background: var(--primary-bg);
  box-shadow: var(--shadow-focus);
}

.radio-label,
.checkbox-label {
  flex: 1;
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
  cursor: pointer;
  user-select: none;
}

.radio-item.is-selected .radio-label,
.checkbox-item.is-selected .checkbox-label {
  color: var(--primary-dark);
  font-weight: 600;
}

/* ─── Checkbox Grid ─── */
.checkbox-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 10px;
}

/* ─── PrimeVue Overrides (using design tokens) ─── */
:deep(.p-radiobutton) {
  --p-radiobutton-width: 20px;
  --p-radiobutton-height: 20px;
  --p-radiobutton-border-color: var(--border-hover);
  --p-radiobutton-hover-border-color: var(--primary-light);
  --p-radiobutton-checked-background: var(--primary);
  --p-radiobutton-checked-border-color: var(--primary);
  --p-radiobutton-checked-hover-background: var(--primary-dark);
  --p-radiobutton-checked-hover-border-color: var(--primary-dark);
  --p-radiobutton-focus-ring-color: var(--primary-ring);
  --p-radiobutton-icon-checked-color: #ffffff;
  --p-radiobutton-icon-checked-hover-color: #ffffff;
}

:deep(.p-checkbox) {
  --p-checkbox-width: 20px;
  --p-checkbox-height: 20px;
  --p-checkbox-border-color: var(--border-hover);
  --p-checkbox-hover-border-color: var(--primary-light);
  --p-checkbox-checked-background: var(--primary);
  --p-checkbox-checked-border-color: var(--primary);
  --p-checkbox-checked-hover-background: var(--primary-dark);
  --p-checkbox-checked-hover-border-color: var(--primary-dark);
  --p-checkbox-focus-ring-color: var(--primary-ring);
  --p-checkbox-icon-checked-color: #ffffff;
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

:deep(.submit-btn.p-button:disabled) {
  opacity: 0.5;
  cursor: not-allowed;
  box-shadow: none;
}

/* ─── Dark Mode ─── */
@media (prefers-color-scheme: dark) {
  .nutril-roadmap-survey {
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

  .question-number {
    box-shadow: 0 0 12px rgba(37, 99, 235, 0.3);
  }

  .radio-item.is-selected .radio-label,
  .checkbox-item.is-selected .checkbox-label {
    color: var(--primary-light);
  }
}

/* ─── Responsive ─── */
@media (max-width: 768px) {
  .nutril-roadmap-survey {
    padding: 1.25rem 0.75rem;
  }

  .survey-card {
    padding: 1.25rem;
  }

  .checkbox-grid {
    grid-template-columns: 1fr;
  }

  .question-label {
    font-size: 14px;
  }

  .question-hint {
    font-size: 12px;
    margin-left: 36px;
  }
}

@media (min-width: 1024px) {
  .nutril-roadmap-survey {
    padding: 2.5rem 2.5rem;
  }
}

/* ─── Smooth transitions ─── */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>
