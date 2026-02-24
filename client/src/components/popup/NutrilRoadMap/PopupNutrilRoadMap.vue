<template>
  <Dialog
    v-model:visible="isVisible"
    modal
    :closable="false"
    :style="{ width: '1200px', maxWidth: '1200px' }"
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

    <!-- Analyze Screen -->
    <NutrilRoadMapAnalyze
      v-if="currentScreen === 'analyze'"
      @analyze="handleAnalyze"
    />

    <!-- Survey Screen -->
    <NutrilRoadMapSurvey
      v-if="currentScreen === 'survey'"
      @back="handleBack"
      @close="handleClose"
      @submit="handleSurveySubmit"
    />

    <!-- Schedule Screen -->
    <NutrilRoadMapSchedule v-if="currentScreen === 'schedule'" />

    <!-- Optimal Screen -->
    <NutrilRoadMapOptimal
      v-if="currentScreen === 'optimal'"
      :title="optimalTitle"
      :subtitle="optimalSubtitle"
      :targetHeight="optimalTargetHeight"
      :monthlyData="optimalMonthlyData"
      :actionTitle="optimalActionTitle"
      :actionDescription="optimalActionDescription"
      :actionButtonLabel="optimalActionButtonLabel"
      @back="handleBack"
      @close="handleClose"
      @openPayment="handleOpenPayment"
    />

    <!-- Optimal Propose Screen -->
    <NutrilRoadMapOptimalPropose v-if="currentScreen === 'propose'" />
  </Dialog>

  <!-- Payment QR Popup -->
  <PaymentQRPopup
    v-model="showPaymentPopup"
    :amount="paymentAmount"
    :orderId="paymentOrderId"
    bank-code="970422"
    bank-account="0123456789"
    account-name="EVEREST HEIGHT GROWTH"
    @confirm="handlePaymentConfirm"
  />
</template>

<script setup>
import { ref, watch } from "vue";
import Dialog from "primevue/dialog";
import NutrilRoadMapOptimal from "./screen/NutrilRoadMapOptimal.vue";
import NutrilRoadMapAnalyze from "@/components/Popup/NutrilRoadMap/screen/NutrilRoadMapAnalyze.vue";
import NutrilRoadMapSurvey from "@/components/Popup/NutrilRoadMap/screen/NutrilRoadMapSurvey.vue";
import NutrilRoadMapSchedule from "@/screen/PopupNutrilRoadMap_PhaseTwo/NutrilRoadMapSchedule.vue";
import NutrilRoadMapOptimalPropose from "./screen/NutrilRoadMapOptimalPropose.vue";
import PaymentQRPopup from "@/components/popup/PaymentQRPopup/PaymentQRPopup.vue";

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["update:modelValue", "action"]);

const isVisible = ref(props.modelValue);
const currentScreen = ref("analyze"); // 'analyze', 'survey', 'schedule', 'optimal', 'propose'

// Survey data
const surveyResults = ref(null);

// Payment popup state
const showPaymentPopup = ref(false);
const paymentOrderId = ref("");
const paymentAmount = ref(30000);

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
  // Navigate back based on current screen
  if (currentScreen.value === "survey") {
    currentScreen.value = "analyze";
  } else if (currentScreen.value === "schedule") {
    currentScreen.value = "survey";
  } else if (currentScreen.value === "optimal") {
    currentScreen.value = "schedule";
  } else {
    currentScreen.value = "analyze";
  }
};

const handleClose = () => {
  isVisible.value = false;
  // Reset về màn hình đầu tiên khi đóng popup
  setTimeout(() => {
    currentScreen.value = "analyze";
  }, 300);
};

const handleOpenPayment = () => {
  console.log("Opening payment popup");
  // Generate order ID
  paymentOrderId.value = `NR${Date.now().toString().slice(-8)}`;
  showPaymentPopup.value = true;
};

const handlePaymentConfirm = (paymentData) => {
  console.log("Payment confirmed", paymentData);
  showPaymentPopup.value = false;

  // Emit action sau khi thanh toán thành công
  emit("action", {
    paymentData,
    targetHeight: optimalTargetHeight.value,
    orderId: paymentOrderId.value,
  });

  // Chuyển sang màn hình propose hoặc đóng popup
  currentScreen.value = "propose";
};

const handleAnalyze = (data) => {
  console.log("Analyze completed", data);
  // Cập nhật dữ liệu từ analyze
  if (data) {
    optimalTargetHeight.value = data.targetHeight;
    // Có thể cập nhật thêm các dữ liệu khác nếu cần
  }
  // Chuyển sang màn hình survey
  currentScreen.value = "survey";
};

const handleSurveySubmit = (data) => {
  console.log("Survey submitted", data);
  surveyResults.value = data;
  // Chuyển sang màn hình schedule sau khi hoàn thành survey
  currentScreen.value = "schedule";
};
</script>