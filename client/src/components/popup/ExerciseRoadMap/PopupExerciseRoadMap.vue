<template>
  <Dialog
    v-model:visible="isVisible"
    modal
    :closable="false"
    :style="{ width: '95vw', maxWidth: '900px' }"
    :draggable="false"
    :pt="{
      root: { class: 'rounded-3xl shadow-2xl border-0' },
      header: { class: '!hidden' },
      content: { class: '!p-0 rounded-3xl overflow-hidden' },
      mask: { class: 'backdrop-blur-sm bg-black/40' }
    }"
  >
    <!-- Exercise Assess Screen 1 -->
    <ExerciseRoadMapAssess1
      v-if="currentScreen === 'assess1'"
      :title="assessTitle"
      :bmiValue="assessBmiValue"
      :bmiStatus="assessBmiStatus"
      :surveyTitle="assess1SurveyTitle"
      :submitButtonLabel="assess1SubmitButtonLabel"
      :questions="assess1Questions"
      @back="handleBack"
      @close="handleClose"
      @submit="handleAssess1Submit"
    />

    <!-- Exercise Assess Screen 2 -->
    <ExerciseRoadMapAssess2
      v-else-if="currentScreen === 'assess2'"
      :title="assessTitle"
      :bmiValue="assessBmiValue"
      :bmiStatus="assessBmiStatus"
      :surveyTitle="assess2SurveyTitle"
      :questionLabel="assess2QuestionLabel"
      :submitButtonLabel="assess2SubmitButtonLabel"
      :exerciseOptions="assess2ExerciseOptions"
      @back="handleBackToAssess1"
      @close="handleClose"
      @submit="handleAssess2Submit"
    />
  </Dialog>
</template>

<script setup>
import { ref, watch } from 'vue';
import Dialog from 'primevue/dialog';
import ExerciseRoadMapAssess1 from './screen/ExerciseRoadMapAssess1.vue';
import ExerciseRoadMapAssess2 from './screen/ExerciseRoadMapAssess2.vue';

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  initialScreen: {
    type: String,
    default: 'assess1', // 'assess1' or 'assess2'
    validator: (value) => ['assess1', 'assess2'].includes(value)
  }
});

const emit = defineEmits(['update:modelValue', 'complete']);

const isVisible = ref(props.modelValue);
const currentScreen = ref(props.initialScreen);

// Common data
const assessTitle = ref('Phác Đồ Luyện Tập Đột Phá');
const assessBmiValue = ref(19.5);
const assessBmiStatus = ref('Bình thường');

// Assess1 Screen data
const assess1SurveyTitle = ref('Khảo Sát Luyện Tập (Bước 1/2)');
const assess1SubmitButtonLabel = ref('Tiếp Theo');
const assess1Questions = ref({
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
});

// Assess2 Screen data
const assess2SurveyTitle = ref('Sở Thích Luyện Tập (Bước 2/2)');
const assess2QuestionLabel = ref('Các loại hình bài tập bạn yêu thích?');
const assess2SubmitButtonLabel = ref('Xem Demo Phác Đồ');
const assess2ExerciseOptions = ref([
  { 
    value: 'tap-ta', 
    label: 'Tập tạ/Sức mạnh',
    icon: 'pi pi-box'
  },
  { 
    value: 'cardio', 
    label: 'Cardio/Sức bền',
    icon: 'pi pi-bolt'
  },
  { 
    value: 'yoga', 
    label: 'Yoga/Dẻo dai',
    icon: 'pi pi-heart'
  }
]);

// Store form data
const assess1Data = ref(null);
const assess2Data = ref(null);

watch(() => props.modelValue, (newVal) => {
  isVisible.value = newVal;
  if (newVal) {
    currentScreen.value = props.initialScreen;
    // Reset data when reopening
    assess1Data.value = null;
    assess2Data.value = null;
  }
});

watch(isVisible, (newVal) => {
  emit('update:modelValue', newVal);
});

const handleBack = () => {
  console.log('Back from assess1');
  handleClose();
};

const handleBackToAssess1 = () => {
  console.log('Back to assess1');
  currentScreen.value = 'assess1';
};

const handleClose = () => {
  isVisible.value = false;
};

const handleAssess1Submit = (formData) => {
  console.log('Assess1 form submitted:', formData);
  assess1Data.value = formData;
  
  // Move to assess2
  currentScreen.value = 'assess2';
};

const handleAssess2Submit = (formData) => {
  console.log('Assess2 form submitted:', formData);
  assess2Data.value = formData;
  
  // Combine all data
  const completeData = {
    ...assess1Data.value,
    ...assess2Data.value
  };
  
  console.log('Complete assessment data:', completeData);
  emit('complete', completeData);
  
  // Show summary
  const diseaseLabels = completeData.diseases.map(value => {
    const option = assess1Questions.value.disease.options.find(opt => opt.value === value);
    return option ? option.label : value;
  }).join(', ');
  
  const levelLabel = assess1Questions.value.currentLevel.options.find(opt => opt.value === completeData.currentLevel)?.label || '';
  const conditionLabel = assess1Questions.value.trainingCondition.options.find(opt => opt.value === completeData.trainingCondition)?.label || '';
  const timeLabel = assess1Questions.value.trainingTime.options.find(opt => opt.value === completeData.trainingTime)?.label || '';
  
  const exerciseLabels = completeData.exercises.map(value => {
    const option = assess2ExerciseOptions.value.find(opt => opt.value === value);
    return option ? option.label : value;
  }).join(', ');
  
  const summary = `
=== KẾT QUẢ KHẢO SÁT ===

BƯỚC 1:
Bệnh lý: ${diseaseLabels || 'Không có'}
Mức độ: ${levelLabel}
Điều kiện: ${conditionLabel}
Thời gian: ${timeLabel}

BƯỚC 2:
Loại hình yêu thích: ${exerciseLabels}
  `.trim();
  
  alert(summary);
  
  // Close popup
  handleClose();
};
</script>