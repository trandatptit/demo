<template>
  <!-- Toast for notifications -->
  <Toast />

  <!-- Payment QR Popup -->
  <PaymentQRPopup
    v-model="showPaymentPopup"
    :amount="30000"
    :orderId="orderId"
    bank-code="970422"
    bank-account="0123456789"
    account-name="EVEREST HEIGHT GROWTH"
    @confirm="handlePaymentConfirm"
  />

  <section class="bg-gray-100 py-8 px-4">
    <!-- Container 12 Grid Main -->
    <div class="max-w-6xl mx-auto">
      <div class="grid grid-cols-12 gap-4 lg:gap-6">
        <!-- Back Button Section - Full Width -->
        <div class="col-span-12 mb-2">
          <Button
            label="Về trang chủ"
            icon="pi pi-arrow-left"
            text
            severity="secondary"
            @click="goBack"
            class="text-gray-600 hover:text-gray-800"
          />
        </div>

        <!-- Main Content Card - Full Width -->
        <div class="col-span-12">
          <div class="bg-white rounded-3xl shadow-xl p-6 md:p-8 lg:p-12">
            <div class="grid grid-cols-12 gap-6 lg:gap-12">
              <!-- Left Side - Chart Section (6 columns on large screens) -->
              <div class="col-span-12 lg:col-span-6">
                <!-- Title -->
                <div class="text-center mb-6 lg:mb-8">
                  <h1 class="text-3xl md:text-4xl font-bold text-gray-800 mb-3">
                    Kết Quả cho {{ userIdDisplay }}
                  </h1>
                  <p class="text-base text-gray-500">
                    Hành trình vươn tới đỉnh cao của bạn bắt đầu từ đây.
                  </p>
                </div>

                <!-- Chart with Icons -->
                <div class="chart-wrapper relative">
                  <Chart
                    type="bar"
                    :data="chartData"
                    :options="chartOptions"
                    class="h-[400px]"
                  />

                  <!-- Stick Figure Icons Overlay -->
                  <div
                    class="absolute bottom-16 left-0 right-0 flex justify-around px-12"
                  >
                    <i
                      class="pi pi-user text-3xl text-white drop-shadow-lg"
                    ></i>
                    <i
                      class="pi pi-user text-3xl text-white drop-shadow-lg"
                    ></i>
                    <i
                      class="pi pi-user text-3xl text-gray-400 drop-shadow-lg"
                    ></i>
                  </div>
                </div>
              </div>

              <!-- Right Side - Result & Button Section (6 columns on large screens) -->
              <div class="col-span-12 lg:col-span-6">
                <div
                  class="flex flex-col items-center justify-center h-full space-y-8"
                >
                  <!-- Prediction Result -->
                  <div class="text-center">
                    <p class="text-sm text-gray-500 mb-2">
                      Chiều cao dự kiến năng (All transfer)
                    </p>
                    <p
                      class="text-7xl md:text-8xl font-bold text-gray-800 mb-1"
                    >
                      {{ predictedHeightDisplay }}
                      <span class="text-4xl">cm</span>
                    </p>
                  </div>

                  <!-- Unlock Section -->
                  <div class="bg-gray-50 rounded-2xl p-8 text-center w-full">
                    <p class="text-lg font-semibold text-gray-800 mb-4">
                      Mở khóa tiềm năng thật sự!
                    </p>
                    <Button
                      label="Chỉ với 30.000đ"
                      class="bg-blue-800 hover:bg-blue-900 text-white font-bold text-base px-8 py-3 rounded-xl shadow-md hover:shadow-lg transition-all duration-300"
                      @click="unlockFeature"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <section class="bg-gray-100 py-8 px-4">
    <!-- Container 12 Grid Main -->
    <div class="max-w-6xl mx-auto">
      <div class="grid grid-cols-12 gap-4 lg:gap-6">
        <!-- Main Content Card - Full Width -->
        <div class="col-span-12">
          <div class="bg-white rounded-2xl shadow-xl p-6 md:p-8">
            <div class="grid grid-cols-12 gap-4 lg:gap-6">
              <!-- Title Section - Full Width -->
              <div class="col-span-12 mb-2">
                <h1 class="text-xl md:text-2xl font-bold text-gray-800">
                  Biểu Đồ Tăng Trưởng Dự Kiến (đến 20 tuổi)
                </h1>
              </div>

              <!-- Chart Section - Full Width -->
              <div class="col-span-12 mb-2">
                <div class="chart-wrapper">
                  <Chart
                    type="bar"
                    :data="chartData"
                    :options="chartOptions"
                    class="h-[350px]"
                  />
                </div>
              </div>

              <!-- Info Cards Grid - Two columns on desktop -->
              <div class="col-span-12 md:col-span-6">
                <!-- AI Recommendation Card -->
                <div
                  class="bg-white border border-gray-200 rounded-2xl p-6 h-full"
                >
                  <div class="flex items-start gap-3 mb-3">
                    <i class="pi pi-sparkles text-2xl text-blue-600"></i>
                    <h3 class="text-lg font-semibold text-gray-800">
                      Lời khuyên từ AI
                    </h3>
                  </div>
                  <p class="text-sm text-gray-600 leading-relaxed">
                    {{ aiRecommendation }}
                  </p>
                </div>
              </div>

              <div class="col-span-12 md:col-span-6">
                <!-- Traditional Method Card -->
                <div
                  class="bg-white border border-gray-200 rounded-2xl p-6 h-full"
                >
                  <div class="flex items-start gap-2 mb-2">
                    <i class="pi pi-heart text-2xl text-blue-600"></i>
                    <h3 class="text-lg font-semibold text-gray-800">
                      Thông điệp truyền cảm hứng
                    </h3>
                  </div>
                  <p class="text-sm text-gray-600 leading-relaxed">
                    {{ inspirationMessageDisplay }}
                  </p>
                </div>
              </div>

              <!-- Download PDF Button - Full Width -->
              <div class="col-span-12 text-center mt-2">
                <Button
                  label="Tải kết quả PDF"
                  icon="pi pi-download"
                  @click="downloadPDF"
                  class="bg-blue-800 hover:bg-blue-900 text-white font-semibold px-6 py-2 rounded-lg shadow-md hover:shadow-lg transition-all duration-300"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, watch, computed, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import Chart from "primevue/chart";
import Button from "primevue/button";
import Toast from "primevue/toast";
import { useToast } from "primevue/usetoast";
import { useUserInfoStore } from "@/stores/userInfo";
import findHeightGen from "@/utilities/findHeightGen/findHeightGen.js";
import PaymentQRPopup from "@/components/Popup/PaymentQRPopup/PaymentQRPopup.vue";
import randomElement from "@/utilities/randomElement/randomeElement.js";
import { adviceAIMessages, inspirationMessages } from "@/config/content.js";
import formatStr from "@/utilities/formatString/formatString.js";

const router = useRouter();
const toast = useToast();
const userInfoStore = useUserInfoStore();

// Payment popup state
const showPaymentPopup = ref(false);
const orderId = ref("");
const aiRecommendation = ref("");
const inspirationMessageDisplay = ref("");

// Accept props
// const props = defineProps({
//   userId: {
//     type: String,
//     default: "12121",
//   },
//   currentHeight: {
//     type: Number,
//     default: 165,
//   },
//   inheritedHeight: {
//     type: Number,
//     default: 168,
//   },
//   potentialHeight: {
//     type: Number,
//     default: 171,
//   },
// });

// Display values
const userIdDisplay = ref("");
const predictedHeightDisplay = ref(0);
const heightUser = ref({
  current: 0,
  inherited: 0,
  potential: 0,
});

onBeforeMount(() => {
  var userInfo = userInfoStore.getUserInfo();
  if (userInfo?.name) {
    userIdDisplay.value = userInfo?.name || "";
  }
  if (userInfo?.height) {
    predictedHeightDisplay.value = userInfo?.height || "";
    heightUser.value.current = userInfo?.height || 0;
  }
  if (userInfo?.fatherHeight && userInfo?.motherHeight && userInfo?.gender) {
    const inheritedHeight = findHeightGen(userInfo);
    heightUser.value.inherited = inheritedHeight;
  }

  aiRecommendation.value = formatStr(
    randomElement(adviceAIMessages),
    userInfo?.gender === 1 ? "Nam" : "Nữ",
    userInfo?.age
  );
  inspirationMessageDisplay.value = formatStr(
    randomElement(inspirationMessages)
  );
});

// Chart refs - theo pattern PrimeVue
const chartData = ref();
const chartOptions = ref();

// Initialize chart data on mount
onMounted(() => {
  chartData.value = setChartData();
  chartOptions.value = setChartOptions();
});

// Watch for prop changes
// watch(
//   () => props,
//   (newProps) => {
//     userIdDisplay.value = newProps.userId;
//     predictedHeightDisplay.value = newProps.potentialHeight.toFixed(1);

//     // Update chart data when props change
//     chartData.value = setChartData();
//   },
//   { deep: true }
// );

// Set chart data - theo pattern PrimeVue
const setChartData = () => {
  return {
    labels: ["Hiện tại", "Dự truyền", "Tiềm năng (CDC)"],
    datasets: [
      {
        label: "Chiều cao (cm)",
        data: [
          heightUser.value.current,
          heightUser.value.inherited,
          heightUser.value.potential,
        ],
        backgroundColor: [
          "rgba(79, 134, 198, 0.9)", // Dark Blue
          "rgba(96, 150, 210, 0.9)", // Medium Blue
          "rgba(180, 200, 225, 0.9)", // Light Blue
        ],
        borderColor: [
          "rgba(79, 134, 198, 1)",
          "rgba(96, 150, 210, 1)",
          "rgba(180, 200, 225, 1)",
        ],
        borderWidth: 0,
        borderRadius: 8,
        barThickness: 100,
      },
    ],
  };
};

// Set chart options - theo pattern PrimeVue
const setChartOptions = () => {
  const documentStyle = getComputedStyle(document.documentElement);
  const textColor = documentStyle.getPropertyValue("--p-text-color") || "#333";
  const textColorSecondary =
    documentStyle.getPropertyValue("--p-text-muted-color") || "#666";
  const surfaceBorder =
    documentStyle.getPropertyValue("--p-content-border-color") ||
    "rgba(0, 0, 0, 0.05)";

  return {
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
        labels: {
          color: textColor,
        },
      },
      tooltip: {
        enabled: true,
        backgroundColor: "rgba(0, 0, 0, 0.8)",
        titleColor: "#fff",
        bodyColor: "#fff",
        padding: 12,
        cornerRadius: 8,
        displayColors: false,
        callbacks: {
          label: function (context) {
            return "Chiều cao: " + context.parsed.y + " cm";
          },
        },
      },
    },
    scales: {
      y: {
        beginAtZero: true,
        max: 200,
        ticks: {
          stepSize: 50,
          callback: function (value) {
            return value === 0 ? "" : value + "cm";
          },
          font: {
            size: 12,
            family: "Arial",
          },
          color: textColorSecondary,
        },
        grid: {
          color: surfaceBorder,
          drawBorder: false,
        },
        border: {
          display: false,
        },
      },
      x: {
        grid: {
          display: false,
          drawBorder: false,
          color: surfaceBorder,
        },
        ticks: {
          font: {
            size: 13,
            weight: "500",
          },
          color: textColor,
        },
        border: {
          display: false,
        },
      },
    },
    layout: {
      padding: {
        top: 30,
        bottom: 20,
      },
    },
  };
};

// Go back function
const goBack = () => {
  router.push({ name: "HomePage" });
};

// Unlock feature
const unlockFeature = () => {
  // Generate order ID
  orderId.value = `EP${Date.now().toString().slice(-5)}`;
  showPaymentPopup.value = true;
};

const isShowPotentialHeight = ref(false);
// Handle payment confirmation
const handlePaymentConfirm = (paymentData) => {
  showPaymentPopup.value = false;

  toast.add({
    severity: "success",
    summary: "Đã nhận thanh toán",
    detail: "Chúng tôi đang xác nhận giao dịch của bạn...",
    life: 3000,
  });

  // TODO: Call API to verify payment
  console.log("Payment data:", paymentData);
  isShowPotentialHeight.value = true;
  var userInfo = userInfoStore.getUserInfo();
};
</script>

<style scoped>
/* Chart wrapper for positioning */
.chart-wrapper {
  position: relative;
}

/* Custom styling for chart canvas */
:deep(.p-chart) {
  position: relative;
}

:deep(canvas) {
  position: relative;
}

/* Smooth transitions for grid layout changes */
.col-span-12,
.col-span-6 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>