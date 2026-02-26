<template>
  <section class="nutril-roadmap-optimal">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section -->
      <div class="col-span-12 survey-header">
        <div class="header-accent"></div>
        <span class="header-badge">
          <i class="pi pi-sparkles"></i> AI Đề Xuất
        </span>
        <h2 class="header-title">{{ title }}</h2>
        <p class="header-subtitle">{{ subtitle }}</p>
      </div>

      <!-- Chart Section -->
      <div class="col-span-12">
        <div class="content-card">
          <div class="card-inner">
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
      </div>

      <!-- Action Section -->
      <div class="col-span-12 mt-4">
        <div class="action-section action-section--cta">
          <div class="action-header">
            <span class="action-title">{{ actionTitle }}</span>
          </div>

          <div class="action-note">
            <p class="action-description">{{ actionDescription }}</p>
          </div>

          <Button
            :label="actionButtonLabel"
            icon="pi pi-shopping-cart"
            class="action-btn w-full"
            @click="handleAction"
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
        backgroundColor: "rgba(37, 99, 235, 0.08)",
        borderColor: "#2563eb",
        tension: 0.4,
        pointRadius: 5,
        pointHoverRadius: 7,
        pointBackgroundColor: "#2563eb",
        pointBorderColor: "#ffffff",
        pointBorderWidth: 2,
        pointHoverBackgroundColor: "#2563eb",
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
/* ─── Design Tokens (matching NutrilRoadMapSchedule) ─── */
.nutril-roadmap-optimal {
  --primary: #2563eb;
  --primary-light: #3b82f6;
  --primary-dark: #1d4ed8;
  --primary-bg: #eff6ff;
  --primary-bg-deep: #dbeafe;
  --primary-ring: rgba(37, 99, 235, 0.15);
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
  --shadow-lg: 0 8px 24px rgba(0, 0, 0, 0.1);

  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
}

/* ─── Header ─── */
.survey-header {
  text-align: center;
  margin-bottom: 0.5rem;
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

/* ─── Content Cards ─── */
.content-card {
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 16px;
  box-shadow: var(--shadow-md);
  overflow: hidden;
  position: relative;
}

.content-card::before {
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
  z-index: 1;
}

.card-inner {
  padding: 1.5rem;
}

/* ─── Chart ─── */
.chart-description {
  text-align: center;
  font-size: 0.9rem;
  color: var(--text-secondary);
  margin-bottom: 1rem;
  line-height: 1.6;
}

.highlight-height {
  color: var(--primary);
  font-size: 1.1rem;
  font-weight: 700;
}

.chart-container {
  background: var(--surface-alt);
  border-radius: 10px;
  padding: 1rem;
  border: 1px solid var(--border);
}

/* ─── Action Section (CTA) ─── */
.action-section {
  background: var(--primary-bg);
  border-radius: 16px;
  padding: 1.5rem;
  border: 1px solid var(--primary-bg-deep);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.action-section::before {
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

.action-section:hover {
  border-color: var(--primary-light);
  box-shadow: 0 4px 16px rgba(37, 99, 235, 0.12);
}

.action-header {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
}

.action-title {
  font-size: 1rem;
  font-weight: 700;
  color: var(--primary-dark);
  text-align: center;
}

.action-note {
  background: rgba(37, 99, 235, 0.06);
  padding: 12px 16px;
  border-radius: 10px;
  border-left: 3px solid var(--primary);
  margin-bottom: 1rem;
}

.action-description {
  font-size: 0.85rem;
  color: var(--text-secondary);
  line-height: 1.6;
  text-align: center;
  margin: 0;
}

.action-footer {
  font-size: 0.75rem;
  color: var(--text-muted);
  text-align: center;
  margin-top: 12px;
  font-style: italic;
}

/* ─── Buttons ─── */
:deep(.action-btn.p-button) {
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

:deep(.action-btn.p-button:hover) {
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  );
  box-shadow: 0 6px 20px rgba(37, 99, 235, 0.45);
  transform: translateY(-1px);
}

/* ─── Animations ─── */
.survey-header,
.content-card,
.action-section {
  animation: fadeInUp 0.4s ease-out both;
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

/* ─── Dark Mode ─── */
@media (prefers-color-scheme: dark) {
  .nutril-roadmap-optimal {
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

  .action-title {
    color: var(--primary-light);
  }

  .action-note {
    background: rgba(37, 99, 235, 0.1);
  }
}

/* ─── Responsive ─── */
@media (max-width: 768px) {
  .nutril-roadmap-optimal {
    padding: 1.25rem 0.75rem;
  }
}

@media (min-width: 1024px) {
  .nutril-roadmap-optimal {
    padding: 2.5rem 2.5rem;
  }
}

/* ─── Smooth transitions ─── */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>