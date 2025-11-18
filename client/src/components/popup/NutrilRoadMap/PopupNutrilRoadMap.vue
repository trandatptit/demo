<template>
  <Dialog
    v-model:visible="isVisible"
    modal
    :closable="false"
    :style="{ width: '95vw', maxWidth: '800px' }"
    :draggable="false"
    :pt="{
      root: { class: 'rounded-3xl shadow-2xl border-0' },
      header: { class: '!hidden' },
      content: { class: '!p-0 rounded-3xl overflow-hidden' },
      mask: { class: 'backdrop-blur-sm bg-black/40' },
    }"
  >
    <!-- Close Button -->
    <button
      @click="handleClose"
      class="cursor-pointer absolute top-4 right-4 z-50 w-10 h-10 flex items-center justify-center rounded-full bg-white/90 hover:bg-white shadow-lg transition-all duration-200 hover:scale-110"
      aria-label="Đóng"
    >
      <i class="pi pi-times text-gray-600 hover:text-gray-900 text-xl"></i>
    </button>

    <NutrilRoadMapAnalyze @analyze="handleAnalyze" />
    <!-- Optimal Screen -->
    <NutrilRoadMapOptimal
      :title="optimalTitle"
      :subtitle="optimalSubtitle"
      :targetHeight="optimalTargetHeight"
      :monthlyData="optimalMonthlyData"
      :actionTitle="optimalActionTitle"
      :actionDescription="optimalActionDescription"
      :actionButtonLabel="optimalActionButtonLabel"
      @back="handleBack"
      @close="handleClose"
      @action="handleOptimalAction"
    />
    <NutrilRoadMapOptimalPropose />
  </Dialog>
</template>

<script setup>
import { ref, watch } from "vue";
import Dialog from "primevue/dialog";
import NutrilRoadMapOptimal from "./screen/NutrilRoadMapOptimal.vue";
import NutrilRoadMapAnalyze from "./screen/NutrilRoadMapAnalyze.vue";
import NutrilRoadMapOptimalPropose from "./screen/NutrilRoadMapOptimalPropose.vue";

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["update:modelValue", "action"]);

const isVisible = ref(props.modelValue);

// Optimal Screen data
const optimalTitle = ref("Lộ Trình Dinh Dưỡng Tối Ưu");
const optimalSubtitle = ref("AI Đề Xuất Dành Riêng Cho Bạn");
const optimalTargetHeight = ref(170);
const optimalMonthlyData = ref([
  160, 160.5, 161, 162, 163, 164, 165, 166, 167, 168, 168.5, 169, 170,
]);
const optimalActionTitle = ref("Sẵn sàng để bắt đầu?");
const optimalActionDescription = ref(
  "Nhận thực đơn chi tiết hằng tuần, công thức nấu ăn và video hướng dẫn."
);
const optimalActionButtonLabel = ref("Thanh toán & Nhận Lộ Trình");

watch(
  () => props.modelValue,
  (newVal) => {
    isVisible.value = newVal;
  }
);

watch(isVisible, (newVal) => {
  emit("update:modelValue", newVal);
});

const handleBack = () => {
  console.log("Back clicked");
  handleClose();
};

const handleClose = () => {
  isVisible.value = false;
};

const handleOptimalAction = () => {
  console.log("Optimal action clicked");
  emit("action");
  // Có thể thêm logic chuyển sang màn hình khác hoặc xử lý thanh toán ở đây
  alert("Bạn đã chọn thanh toán & nhận lộ trình!");
};

const handleAnalyze = () => {
  console.log("Analyze completed");
  // Logic chuyển
};
</script>