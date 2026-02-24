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

  <section ref="sectionResult" class="bg-gray-100 py-8 px-4">
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
                    :key="keyChart"
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
                      Chiều cao dự kiến năng (Di truyền)
                    </p>
                    <p
                      class="text-7xl md:text-8xl font-bold text-gray-800 mb-1"
                    >
                      {{ predictedHeightDisplay }}
                      <span class="text-4xl">cm</span>
                    </p>
                  </div>

                  <!-- Unlock Section -->
                  <div
                    v-if="!isShowPotentialHeight"
                    class="bg-gray-50 rounded-2xl p-8 text-center w-full"
                  >
                    <p class="text-lg font-semibold text-gray-800 mb-4">
                      Mở khóa tiềm năng thật sự!
                    </p>
                    <Button
                      label="Chỉ với 30.000đ"
                      class="bg-blue-800 hover:bg-blue-900 text-white font-bold text-base px-8 py-3 rounded-xl shadow-md hover:shadow-lg transition-all duration-300"
                      @click="unlockFeature"
                    />
                  </div>

                  <div class="text-center" v-if="isShowPotentialHeight">
                    <p class="text-sm text-gray-500 mb-2">
                      Chiều cao tiềm năng (CDC)
                    </p>
                    <p
                      class="text-7xl md:text-8xl font-bold text-gray-800 mb-1"
                    >
                      {{ heightUser.potential }}
                      <span class="text-4xl">cm</span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <section
    ref="sectionTimeline"
    v-if="isShowPotentialHeight"
    class="bg-gray-100 py-8 px-4"
  >
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
                  Biểu Đồ Tăng Trưởng Dự Kiến (đến {{ maxAge }} tuổi)
                </h1>
              </div>

              <!-- Chart Section - Full Width -->
              <div class="col-span-12 mb-2">
                <div class="chart-wrapper">
                  <Chart
                    type="bar"
                    :data="chartDataTimeline"
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
import PaymentQRPopup from "@/components/popup/PaymentQRPopup/PaymentQRPopup.vue";
import randomElement from "@/utilities/randomElement/randomeElement.js";
import { adviceAIMessages, inspirationMessages } from "@/config/content.js";
import formatStr from "@/utilities/formatString/formatString.js";
import GeneticsApi from "@/apis/geneticsApi.js";
import AiApi from "@/apis/aiApi.js";
import getKeyLocalStorage from "@/utilities/commons/getKeyLocalStorage.js";
import { useLoadingStore } from "@/stores/globalLoading.js";
import { calculateAge } from "@/utilities/commons/calculateAge.js";

const router = useRouter();
const toast = useToast();
const userInfoStore = useUserInfoStore();
const loadingStore = useLoadingStore();
// Payment popup state
const showPaymentPopup = ref(false);
const orderId = ref("");
const aiRecommendation = ref("");
const inspirationMessageDisplay = ref("");
const keyChart = ref(0);
const maxAge = ref(0);
const sectionResult = ref(null);
const sectionTimeline = ref(null);

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

onBeforeMount(async () => {
  loadingStore.mask("AI đang dự đoán chiều cao di truyền của bạn...");

  var userInfo = userInfoStore.getUserInfo();
  if (userInfo?.name) {
    userIdDisplay.value = userInfo?.name || "";
  }
  if (userInfo?.height) {
    predictedHeightDisplay.value = userInfo?.height || "";
    heightUser.value.current = userInfo?.height || 0;
  }
  if (userInfo?.fatherHeight && userInfo?.motherHeight && userInfo?.gender) {
    var responseAI = await AiApi.genHeightParental({
      AGE: userInfo?.age,
      CURRENT_HEIGHT_CM: userInfo?.height,
      EXERCISE_PER_WEEK: userInfo?.exerciseFrequency,
      FATHER_HEIGHT_CM: userInfo?.fatherHeight,
      GENDER: userInfo?.gender === 1 ? "Nam" : "Nữ",
      MEALS_PER_DAY: userInfo?.mealsPerDay,
      MOTHER_HEIGHT_CM: userInfo?.motherHeight,
      NAME: userInfo?.name,
      PUBERTY_AGE: userInfo?.pubertyAge,
      SLEEP_TIME: userInfo?.timeSleep?.getHours() || 8,
      WEIGHT_KG: userInfo?.weight,
    });
    // responseAI
    // const inheritedHeight = findHeightGen(userInfo);
    const inheritedHeight = responseAI?.result?.predicted_genetic_height_cm;
    heightUser.value.inherited = inheritedHeight;
    predictedHeightDisplay.value = inheritedHeight;
  }

  aiRecommendation.value = formatStr(
    randomElement(adviceAIMessages),
    userInfo?.gender === 1 ? "Nam" : "Nữ",
    userInfo?.age
  );
  inspirationMessageDisplay.value = formatStr(
    randomElement(inspirationMessages)
  );
  chartData.value = setChartData();
  loadingStore.unmask();
});

// Chart refs - theo pattern PrimeVue
const chartData = ref();
const chartDataTimeline = ref();
const chartOptions = ref();

// Initialize chart data on mount
onMounted(() => {
  chartData.value = setChartData();
  // chartDataTimeline.value = setChartDataTimeline();
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

const setChartDataTimeline = (dataTimeline) => {
  var lbs = dataTimeline.map((item) => `${item?.age}t`);
  var datas = dataTimeline.map((item) => item?.projected_height_cm);
  datas[datas.length - 1] =
    heightUser.value.potential < datas[datas.length - 1]
      ? datas[datas.length - 1]
      : heightUser.value.potential;
  var bgCols = dataTimeline.map((item) => "rgba(79, 134, 198, 0.9)");
  var borderCols = dataTimeline.map((item) => "rgba(79, 134, 198, 1)");
  var maxAge = Math.max(...dataTimeline.map((item) => item.age));
  return {
    labels: lbs,
    datasets: [
      {
        label: "Chiều cao (cm)",
        data: datas,
        backgroundColor: bgCols,
        borderColor: borderCols,
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
const handlePaymentConfirm = async (paymentData) => {
  showPaymentPopup.value = false;

  toast.add({
    severity: "success",
    summary: "Đã nhận thanh toán",
    detail: "Chúng tôi đang xác nhận giao dịch của bạn...",
    life: 3000,
  });

  // TODO: Call API to verify payment
  console.log("Payment data:", paymentData);
  // Phần xử lý gọi AI
  loadingStore.mask("AI đang dự đoán chiều cao tiềm năng của bạn...");
  isShowPotentialHeight.value = true;

  // Thông tin mặc định lấy từ form nhập thông tin cá nhân
  let userInfo = userInfoStore.getUserInfo();
  // Thông tin mặc định lấy từ local storage
  var localStorageUserInfo = JSON.parse(
    localStorage.getItem(getKeyLocalStorage("UserInfo"))
  );
  if (localStorageUserInfo) {
    userInfo = {
      ...userInfo,
      ...localStorageUserInfo,
    };
    userInfo.age = calculateAge(localStorageUserInfo.birthday);
  }

  if (!userInfo?.age) {
    userInfo.age = calculateAge(userInfo.birthday);
  }

  try {
    await GeneticsApi.geneticResult30k(userInfo.id);
    // Lấy ra Chiều cao tiềm năng
    var responseAI = await AiApi.genHeight30k({
      AGE: userInfo.age,
      CURRENT_HEIGHT_CM: heightUser.value.current,
      GENDER: userInfo.gender === 1 ? "Nam" : "Nữ",
      GENETIC_HEIGHT_CM: heightUser.value.inherited,
    });
    heightUser.value.potential =
      responseAI?.result?.cdc_potential_height_cm ?? 0;
    GeneticsApi.saveResult30k({
      userId: userInfo.id,
      currentHeight: heightUser.value.current,
      hereditaryHeight: heightUser.value.inherited,
      potentialHeight: heightUser.value.potential,
    }).then((res) => {
      console.log("Save result 30k response:", res);
    });

    // Lấy ra biểu đồ tăng trưởng chiều cao tiềm năng:
    var reportDataAI = await AiApi.getYearlyRoadmap({
      AGE: userInfo.age,
      CDC_POTENTIAL_HEIGHT_CM: heightUser.value.potential,
      CURRENT_HEIGHT_CM: heightUser.value.current,
      GENDER: userInfo.gender === 1 ? "Nam" : "Nữ",
      GENETIC_HEIGHT_CM: heightUser.value.inherited,
    });
    chartDataTimeline.value = setChartDataTimeline(
      reportDataAI?.result?.yearly_growth_table ?? []
    );
    maxAge.value = Math.max(
      ...reportDataAI?.result?.yearly_growth_table?.map((item) => item.age)
    );
    // var maxAge = Math.max(...dataTimeline.map((item) => item.age));
  } catch (error) {
    console.log("HandlePaymentConfirm error:", error);
  } finally {
    chartData.value = setChartData();
    keyChart.value++;
    loadingStore.unmask();
  }
};

// Download PDF function
const downloadPDF = async () => {
  loadingStore.mask("Đang tạo file PDF...");

  try {
    // Import động html2canvas và jsPDF
    const html2canvas = (await import("html2canvas")).default;
    const { jsPDF } = await import("jspdf");

    // Tạo style tag tạm để override oklch colors
    const styleOverride = document.createElement("style");
    styleOverride.id = "pdf-color-override";
    styleOverride.textContent = `
      .pdf-export-container * {
        color: #333333 !important;
        border-color: #d1d5db !important;
      }
      .pdf-export-container .bg-white { background-color: #ffffff !important; }
      .pdf-export-container .bg-gray-100 { background-color: #f3f4f6 !important; }
      .pdf-export-container .bg-gray-50 { background-color: #f9fafb !important; }
      .pdf-export-container .text-gray-800 { color: #1f2937 !important; }
      .pdf-export-container .text-gray-600 { color: #4b5563 !important; }
      .pdf-export-container .text-gray-500 { color: #6b7280 !important; }
      .pdf-export-container .text-blue-600 { color: #2563eb !important; }
      .pdf-export-container .text-blue-800 { color: #1e40af !important; }
      .pdf-export-container .text-blue-900 { color: #1e3a8a !important; }
      .pdf-export-container .border-gray-200 { border-color: #e5e7eb !important; }
    `;
    document.head.appendChild(styleOverride);

    // Helper function để copy canvas content
    const copyCanvasContent = (sourceSection, clonedSection) => {
      const sourceCanvases = sourceSection.querySelectorAll("canvas");
      const clonedCanvases = clonedSection.querySelectorAll("canvas");

      sourceCanvases.forEach((sourceCanvas, index) => {
        if (clonedCanvases[index]) {
          const clonedCanvas = clonedCanvases[index];
          // Set kích thước giống nhau
          clonedCanvas.width = sourceCanvas.width;
          clonedCanvas.height = sourceCanvas.height;
          clonedCanvas.style.width = sourceCanvas.style.width;
          clonedCanvas.style.height = sourceCanvas.style.height;

          // Copy nội dung canvas
          const ctx = clonedCanvas.getContext("2d");
          ctx.drawImage(sourceCanvas, 0, 0);
        }
      });
    };

    // Tạo container tạm để chứa cả 2 section
    const container = document.createElement("div");
    container.className = "pdf-export-container";
    container.style.cssText =
      "background: #f3f4f6; position: absolute; left: -9999px; top: 0;";

    // Clone section 1
    if (sectionResult.value) {
      const section1Clone = sectionResult.value.cloneNode(true);
      section1Clone.style.background = "#f3f4f6";
      // Copy canvas content cho section 1 (Chart đầu tiên)
      copyCanvasContent(sectionResult.value, section1Clone);
      container.appendChild(section1Clone);
    }

    // Clone section 2
    if (sectionTimeline.value) {
      const section2Clone = sectionTimeline.value.cloneNode(true);
      section2Clone.style.background = "#f3f4f6";
      // Copy canvas content cho section 2 (Chart thứ hai)
      copyCanvasContent(sectionTimeline.value, section2Clone);
      // Xóa nút download trong clone
      const buttons = section2Clone.querySelectorAll("button");
      buttons.forEach((btn) => {
        if (btn.closest(".col-span-12.text-center")) {
          btn.closest(".col-span-12.text-center").remove();
        }
      });
      container.appendChild(section2Clone);
    }

    // Thêm vào body để render
    document.body.appendChild(container);

    // Chờ một chút để styles được áp dụng
    await new Promise((resolve) => setTimeout(resolve, 200));

    // Capture bằng html2canvas
    const canvas = await html2canvas(container, {
      scale: 2,
      useCORS: true,
      logging: false,
      backgroundColor: "#f3f4f6",
      allowTaint: true,
      foreignObjectRendering: false,
    });

    // Tạo PDF
    const imgData = canvas.toDataURL("image/jpeg", 0.95);
    const pdf = new jsPDF("p", "mm", "a4");

    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = pdf.internal.pageSize.getHeight();
    const imgWidth = pdfWidth - 20; // margin 10mm mỗi bên
    const imgHeight = (canvas.height * imgWidth) / canvas.width;

    // Xử lý nhiều trang nếu nội dung dài
    let heightLeft = imgHeight;
    let position = 10; // margin top
    const pageHeight = pdfHeight - 20; // trừ margin

    // Trang đầu tiên
    pdf.addImage(imgData, "JPEG", 10, position, imgWidth, imgHeight);
    heightLeft -= pageHeight;

    // Thêm trang nếu cần
    while (heightLeft > 0) {
      position = heightLeft - imgHeight + 10;
      pdf.addPage();
      pdf.addImage(imgData, "JPEG", 10, position, imgWidth, imgHeight);
      heightLeft -= pageHeight;
    }

    // Lưu file
    const today = new Date();
    const dateStr = `${String(today.getDate()).padStart(2, "0")}-${String(
      today.getMonth() + 1
    ).padStart(2, "0")}-${today.getFullYear()}`;
    pdf.save(
      `ket-qua-chieu-cao-${userIdDisplay.value || "user"}-${dateStr}.pdf`
    );

    // Cleanup
    document.body.removeChild(container);
    document.head.removeChild(styleOverride);

    toast.add({
      severity: "success",
      summary: "Thành công",
      detail: "Đã tải xuống file PDF!",
      life: 3000,
    });
  } catch (error) {
    console.error("Error generating PDF:", error);
    // Cleanup nếu có lỗi
    const styleEl = document.getElementById("pdf-color-override");
    if (styleEl) styleEl.remove();
    const containerEl = document.querySelector(".pdf-export-container");
    if (containerEl) containerEl.remove();

    toast.add({
      severity: "error",
      summary: "Lỗi",
      detail: "Không thể tạo file PDF. Vui lòng thử lại!",
      life: 3000,
    });
  } finally {
    loadingStore.unmask();
  }
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