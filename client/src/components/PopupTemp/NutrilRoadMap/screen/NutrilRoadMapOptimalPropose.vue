<template>
  <section
    class="nutril-roadmap-optimal mx-auto px-4 lg:px-8 py-8 lg:py-12 bg-white dark:bg-gray-900"
  >
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section - Full Width -->

      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-2">
        <h2
          class="text-2xl sm:text-3xl lg:text-4xl font-bold text-gray-800 dark:text-white"
        >
          {{ title }}
        </h2>

        <!-- Subtitle -->
        <div class="subtitle mt-3">
          <i class="pi pi-sparkles subtitle-icon"></i>
          <span>{{ subtitle }}</span>
        </div>
      </div>

      <!-- Chart Section - Full Width -->
      <div class="col-span-12">
        <div class="chart-section">
          <div class="chart-description">
            <span>Dựa trên phân tích, bạn có thể đạt </span>
            <strong class="highlight-height">{{ targetHeight }}cm</strong>
            <span> sau 12 tháng với lộ trình phù hợp.</span>
          </div>

          <div class="chart-container">
            <Chart
              type="line"
              :data="chartData"
              :options="chartOptions"
              class="h-[20rem]"
            />
          </div>
        </div>
      </div>

      <!-- Action Section - Full Width -->
      <div class="col-span-12 mt-4">
        <div class="action-section">
          <div class="action-header">
            <span class="action-title">{{ actionTitle }}</span>
          </div>

          <p class="action-description">{{ actionDescription }}</p>

          <Button
            :label="actionButtonLabel"
            icon="pi pi-shopping-cart"
            class="action-btn w-full"
            @click="handleAction"
            :pt="{
              root: {
                class:
                  'bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600 dark:from-orange-600 dark:to-red-600 border-0 py-4 text-base md:text-lg font-semibold shadow-lg hover:shadow-xl transition-all duration-200',
              },
            }"
          />

          <p class="action-footer">{{ actionFooter }}</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import Button from "primevue/button";
import Chart from "primevue/chart";

const props = defineProps({
  title: {
    type: String,
    default: "Lộ Trình Dinh Dưỡng Tối Ưu",
  },
  subtitle: {
    type: String,
    default: "AI Đề Xuất Dành Riêng Cho Bạn",
  },
  targetHeight: {
    type: Number,
    default: 165,
  },
  monthlyData: {
    type: Array,
    default: () => [
      160, 160.5, 161, 161.5, 162, 162.5, 163, 163.5, 164, 164.5, 165, 165.5,
      166,
    ],
  },
  actionTitle: {
    type: String,
    default: "Để xuất BẤT BUỘC cho ngân sách của bạn:",
  },
  actionDescription: {
    type: String,
    default:
      "Dễ dàng báo cáo Comsir, bạn cần kết hợp thực phẩm với sản phẩm bổ sung từ Everest nếu khỏe, dễ trình diễn khẳng định tiêu chuẩn.",
  },
  actionButtonLabel: {
    type: String,
    default: "Đặt mua Combi ngay",
  },
  actionFooter: {
    type: String,
    default: "Bạn sẽ được chuyển đến trang đặt hàng chi tiết!",
  },
});

const emit = defineEmits(["back", "close", "action"]);

const chartData = ref();
const chartOptions = ref();

onMounted(() => {
  chartData.value = setChartData();
  chartOptions.value = setChartOptions();
});

// Watch props.monthlyData để update chart khi data thay đổi
watch(
  () => props.monthlyData,
  () => {
    chartData.value = setChartData();
  },
  { deep: true }
);

const setChartData = () => {
  return {
    labels: [
      "Tháng 0",
      "Tháng 1",
      "Tháng 2",
      "Tháng 3",
      "Tháng 4",
      "Tháng 5",
      "Tháng 6",
      "Tháng 7",
      "Tháng 8",
      "Tháng 9",
      "Tháng 10",
      "Tháng 11",
      "Tháng 12",
    ],
    datasets: [
      {
        label: "Chiều cao dự kiến",
        data: props.monthlyData,
        fill: true,
        backgroundColor: "rgba(59, 130, 246, 0.1)",
        borderColor: "#3b82f6",
        tension: 0.4,
        pointRadius: 5,
        pointHoverRadius: 7,
        pointBackgroundColor: "#3b82f6",
        pointBorderColor: "#ffffff",
        pointBorderWidth: 2,
        pointHoverBackgroundColor: "#3b82f6",
        pointHoverBorderColor: "#ffffff",
        pointHoverBorderWidth: 3,
      },
    ],
  };
};

const setChartOptions = () => {
  const documentStyle = getComputedStyle(document.documentElement);
  const textColor =
    documentStyle.getPropertyValue("--p-text-color") || "#6b7280";
  const textColorSecondary =
    documentStyle.getPropertyValue("--p-text-muted-color") || "#9ca3af";
  const surfaceBorder =
    documentStyle.getPropertyValue("--p-content-border-color") || "#e5e7eb";

  return {
    maintainAspectRatio: false,
    aspectRatio: 0.6,
    responsive: true,
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        backgroundColor: "rgba(0, 0, 0, 0.8)",
        padding: 12,
        titleFont: {
          size: 13,
          weight: "600",
        },
        bodyFont: {
          size: 12,
        },
        callbacks: {
          label: function (context) {
            return `Chiều cao: ${context.parsed.y}cm`;
          },
        },
      },
    },
    scales: {
      x: {
        ticks: {
          color: textColorSecondary,
          font: {
            size: 10,
          },
          maxRotation: 45,
          minRotation: 0,
        },
        grid: {
          display: false,
          drawBorder: false,
        },
      },
      y: {
        ticks: {
          color: textColorSecondary,
          font: {
            size: 10,
          },
          padding: 8,
          callback: function (value) {
            return value + "cm";
          },
        },
        grid: {
          color: surfaceBorder,
          drawBorder: false,
        },
      },
    },
  };
};

const handleAction = () => {
  emit("action");
};
</script>

<style scoped>
/* Base Section Styles */
.nutril-roadmap-optimal {
  max-width: 1200px;
  border-radius: 1.5rem;
}

.subtitle {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  color: #64748b;
  font-size: 13px;
  font-weight: 500;
}

.subtitle-icon {
  font-size: 14px;
  color: #3b82f6;
}

.chart-section {
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 12px;
  padding: 20px;
  border: 2px solid #e2e8f0;
  transition: all 0.3s ease;
}

.chart-section:hover {
  border-color: #cbd5e1;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
}

.chart-description {
  text-align: center;
  font-size: 14px;
  color: #475569;
  margin-bottom: 16px;
  line-height: 1.6;
}

.highlight-height {
  color: #3b82f6;
  font-size: 18px;
  font-weight: 700;
  text-shadow: 0 0 20px rgba(59, 130, 246, 0.3);
}

.chart-container {
  background: white;
  border-radius: 10px;
  padding: 20px 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.action-section {
  background: linear-gradient(135deg, #fff5f5 0%, #ffe4e4 100%);
  border-radius: 12px;
  padding: 20px;
  border: 2px solid #fecaca;
  transition: all 0.3s ease;
}

.action-section:hover {
  border-color: #fca5a5;
  box-shadow: 0 4px 16px rgba(239, 68, 68, 0.15);
}

.action-header {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
}

.action-title {
  font-size: 16px;
  font-weight: 600;
  color: #dc2626;
  text-align: center;
}

.action-description {
  font-size: 13px;
  color: #64748b;
  margin-bottom: 16px;
  line-height: 1.6;
  text-align: center;
  background: rgba(59, 130, 246, 0.08);
  padding: 12px 16px;
  border-radius: 8px;
  border-left: 3px solid #3b82f6;
}

.action-footer {
  font-size: 12px;
  color: #64748b;
  text-align: center;
  margin-top: 12px;
  font-style: italic;
}

.action-btn {
  position: relative;
  overflow: hidden;
}

.action-btn::before {
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

.action-btn:hover::before {
  width: 300px;
  height: 300px;
}

/* Dark mode adjustments */
@media (prefers-color-scheme: dark) {
  .chart-section {
    background: linear-gradient(135deg, #1f2937 0%, #111827 100%);
    border-color: #374151;
  }

  .chart-container {
    background: #1f2937;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
  }

  .chart-description {
    color: #d1d5db;
  }

  .highlight-height {
    color: #60a5fa;
  }

  .action-section {
    background: linear-gradient(135deg, #7f1d1d 0%, #991b1b 100%);
    border-color: #dc2626;
  }

  .action-title {
    color: #fca5a5;
  }

  .action-description {
    color: #d1d5db;
    background: rgba(59, 130, 246, 0.15);
  }

  .action-footer {
    color: #9ca3af;
  }
}

/* Grid Responsive Adjustments */
@media (min-width: 768px) {
  .chart-section {
    padding: 24px;
  }

  .chart-container {
    padding: 24px 20px;
  }

  .chart-description {
    font-size: 15px;
  }

  .highlight-height {
    font-size: 20px;
  }

  .action-section {
    padding: 24px;
  }

  .action-title {
    font-size: 17px;
  }

  .action-description {
    font-size: 14px;
    padding: 14px 20px;
  }

  .action-footer {
    font-size: 13px;
  }
}

@media (min-width: 1024px) {
  .nutril-roadmap-optimal {
    padding: 2rem 2.5rem;
  }
}

/* Smooth transitions for grid layout changes */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>