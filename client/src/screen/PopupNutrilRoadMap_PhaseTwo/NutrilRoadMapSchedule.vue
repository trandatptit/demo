<template>
  <div class="nutril-roadmap-schedule">
    <!-- Header Section -->
    <div class="grid grid-cols-12 gap-4 mb-6">
      <div class="col-span-12 schedule-header">
        <div class="header-accent"></div>
        <span class="header-badge">
          <i class="pi pi-calendar-clock"></i> Lộ Trình Tuần
        </span>
        <h1 class="header-title">Lộ Trình Dinh Dưỡng Tối Ưu</h1>
        <p class="header-subtitle">Thực đơn được AI hoạch định riêng cho bạn</p>
      </div>
    </div>

    <!-- User Info Card -->
    <div class="grid grid-cols-12 gap-4 mb-6">
      <div class="col-span-12">
        <Card class="user-info-card">
          <template #content>
            <div class="grid grid-cols-12 gap-4 items-center">
              <!-- Avatar -->
              <div class="col-span-2 flex justify-center">
                <div class="user-avatar">
                  <i class="pi pi-user"></i>
                </div>
              </div>

              <!-- User Name -->
              <div class="col-span-3">
                <p class="user-label">Bạn</p>
                <p class="user-name">{{ userName }}</p>
                <p class="user-detail">
                  Chiều cao hiện tại: {{ currentHeight }} cm
                </p>
              </div>

              <!-- Target Height -->
              <div class="col-span-2 text-center">
                <div class="stat-box">
                  <p class="stat-label">MỤC TIÊU</p>
                  <p class="stat-value">{{ targetHeight }} cm</p>
                </div>
              </div>

              <!-- Duration -->
              <div class="col-span-2 text-center">
                <div class="stat-box">
                  <p class="stat-label">THỜI LƯỢNG</p>
                  <p class="stat-value">{{ duration }} tháng</p>
                </div>
              </div>

              <!-- Cost -->
              <div class="col-span-2 text-center">
                <div class="stat-box">
                  <p class="stat-label">CHI PHÍ TUẦN</p>
                  <p class="stat-value-sm">
                    {{ formatCurrency(weeklyCost) }}
                  </p>
                </div>
              </div>

              <!-- Appointment Button -->
              <div class="col-span-1 flex justify-center">
                <Button
                  icon="pi pi-calendar"
                  rounded
                  text
                  class="appt-btn"
                  @click="onAppointment"
                />
              </div>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- Current Status Section -->
    <div class="grid grid-cols-12 gap-4 mb-4">
      <div class="col-span-12 text-center">
        <h2 class="section-title">Lộ Trình Dinh Dưỡng Tuần Này</h2>
        <p class="section-subtitle">
          Thực đơn được AI có nhiệm hoạch định riêng cho bạn.
        </p>
      </div>
    </div>

    <!-- Chart Section -->
    <div class="grid grid-cols-12 gap-4">
      <div class="col-span-12">
        <Card class="content-card">
          <template #content>
            <!-- Chart Header -->
            <div class="flex items-center justify-center mb-6">
              <i class="pi pi-chart-bar section-icon mr-2"></i>
              <h3 class="card-heading">Tổng quan Canxi tuần</h3>
            </div>

            <!-- Chart -->
            <div class="chart-container">
              <Chart
                type="bar"
                :data="chartData"
                :options="chartOptions"
                class="h-64"
              />
            </div>

            <!-- Legend -->
            <div class="flex justify-center mt-4">
              <div class="chart-legend">
                <div class="legend-dot"></div>
                <span class="legend-text">Canxi kế hoạch</span>
              </div>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- Meal Plan Section with Tabs -->
    <div class="grid grid-cols-12 gap-4 mt-6">
      <div class="col-span-12">
        <Card class="content-card">
          <template #content>
            <!-- Day Tabs -->
            <Tabs v-model:value="activeDay" class="custom-tabview">
              <TabList>
                <Tab
                  v-for="day in weekDays"
                  :key="day.value"
                  :value="day.value"
                  >{{ day.label }}</Tab
                >
              </TabList>
              <TabPanels>
                <TabPanel
                  v-for="day in weekDays"
                  :key="day.value"
                  :value="day.value"
                >
                  <!-- Meal Cards Grid -->
                  <div class="grid grid-cols-12 gap-4 mt-4">
                    <!-- Meal Card -->
                    <div
                      v-for="meal in currentDayMeals"
                      :key="meal.id"
                      class="col-span-12 md:col-span-6 lg:col-span-4"
                    >
                      <Card class="meal-card">
                        <template #header>
                          <div class="relative">
                            <img
                              :src="meal.image"
                              :alt="meal.title"
                              class="w-full h-48 object-cover"
                              @error="onMealImageError"
                            />
                            <div class="meal-type-badge">
                              {{ meal.mealType }}
                            </div>
                          </div>
                        </template>

                        <template #content>
                          <!-- Meal Title -->
                          <h4 class="meal-title">
                            {{ meal.title }}
                          </h4>

                          <!-- Ingredients -->
                          <div class="mb-4">
                            <p class="info-label">Nguyên liệu:</p>
                            <ul class="space-y-1">
                              <li
                                v-for="(ingredient, idx) in meal.ingredients"
                                :key="idx"
                                class="text-sm text-gray-600 flex items-start"
                              >
                                <i
                                  class="pi pi-circle-fill text-xs text-blue-400 mr-2 mt-1"
                                ></i>
                                <span>{{ ingredient }}</span>
                              </li>
                            </ul>
                          </div>

                          <!-- Nutrition Info -->
                          <div class="mb-4">
                            <p class="info-label">Chỉ báo:</p>
                            <p class="text-sm text-gray-600">
                              {{ meal.nutritionNote }}
                            </p>
                          </div>

                          <!-- Nutrition Stats -->
                          <div class="nutrition-stats">
                            <div class="flex items-center">
                              <i class="pi pi-bolt text-yellow-500 mr-2"></i>
                              <span class="text-sm font-semibold text-gray-700">
                                {{ meal.calories }} kcal
                              </span>
                            </div>
                            <div class="flex items-center">
                              <i class="pi pi-heart text-red-500 mr-2"></i>
                              <span class="text-sm font-semibold text-gray-700">
                                {{ meal.calcium }} mg Canxi
                              </span>
                            </div>
                            <div class="flex items-center">
                              <span
                                class="text-sm font-semibold text-green-600"
                              >
                                {{ formatCurrency(meal.cost) }}
                              </span>
                            </div>
                          </div>

                          <!-- Action Buttons -->
                          <div class="flex gap-2">
                            <Button
                              label="Xem"
                              icon="pi pi-eye"
                              outlined
                              class="eye-button flex-1"
                              @click="viewMeal(meal)"
                            />
                            <Button
                              label="Cập nhật"
                              icon="pi pi-refresh"
                              class="update-button flex-1"
                              @click="updateMeal(meal)"
                            />
                          </div>
                        </template>
                      </Card>
                    </div>
                  </div>
                </TabPanel>
              </TabPanels>
            </Tabs>
          </template>
        </Card>
      </div>
    </div>

    <!-- Schedule Setting Section -->
    <div class="grid grid-cols-12 gap-4 mt-6">
      <div class="col-span-12">
        <Card class="content-card schedule-settings-card">
          <template #content>
            <!-- Initial State: Sleep Time and Complete Button -->
            <div class="flex items-center justify-between flex-wrap gap-4">
              <!-- Schedule Time Input -->
              <div class="flex items-center gap-3">
                <i class="pi pi-calendar text-gray-600 text-xl"></i>
                <span class="text-gray-700 font-medium">Giờ ngủ:</span>
                <DatePicker
                  v-model="sleepTime"
                  timeOnly
                  hourFormat="12"
                  showIcon
                  iconDisplay="input"
                  placeholder="10:00 CH"
                  class="custom-datepicker"
                />
              </div>

              <!-- Mark Today Button -->
              <Button
                label="Hoàn thành ngày hôm nay"
                icon="pi pi-check-circle"
                class="primary-action-btn"
                @click="markTodayComplete"
              />
            </div>

            <!-- Daily Report State -->
            <div v-if="showDailyReport" class="daily-report-content mt-6">
              <!-- Motivation Quote -->
              <div class="text-center mb-6">
                <p class="text-sm text-gray-600 italic">
                  "Sự kiên trì sẽ luôn nhận được nỗ lực một cách chắc chắn
                  (Không gian lên)"
                </p>
              </div>

              <!-- Report Title -->
              <h3 class="text-xl font-bold text-gray-800 text-center mb-6">
                Báo cáo nhanh
              </h3>

              <!-- Stats Grid -->
              <div class="grid grid-cols-3 gap-6 mb-0">
                <div
                  v-for="stat in dailyStats"
                  :key="stat.id"
                  class="text-center"
                >
                  <Card
                    class="shadow-md border-0 rounded-xl bg-white hover:shadow-lg transition-shadow"
                  >
                    <template #content>
                      <div class="p-2">
                        <div class="mb-2">
                          <div class="flex items-baseline justify-center">
                            <span
                              class="text-2xl font-bold"
                              :class="stat.valueColor"
                            >
                              {{ stat.value }}
                            </span>
                            <span
                              v-if="stat.target"
                              class="text-lg text-gray-500"
                            >
                              /{{ stat.target }}
                            </span>
                          </div>
                          <span class="text-sm text-gray-500">{{
                            stat.unit
                          }}</span>
                        </div>
                        <p class="text-sm text-gray-600 font-medium">
                          {{ stat.label }}
                        </p>
                      </div>
                    </template>
                  </Card>
                </div>
              </div>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- Download Weekly Plan Button -->
    <div class="grid grid-cols-12 gap-4 mt-6 mb-8">
      <div class="col-span-12 flex justify-center">
        <Button
          label="Tải về Lộ Trình Tuần"
          icon="pi pi-download"
          class="download-btn"
          @click="downloadWeeklyPlan"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import Card from "primevue/card";
import Button from "primevue/button";
import Chart from "primevue/chart";
import TabView from "primevue/tabview";
import TabPanel from "primevue/tabpanel";
import DatePicker from "primevue/datepicker";
import Tabs from "primevue/tabs";
import TabList from "primevue/tablist";
import Tab from "primevue/tab";
import TabPanels from "primevue/tabpanels";

// User Data
const userName = ref("Bạn");
const currentHeight = ref(160);
const targetHeight = ref(170);
const duration = ref(12);
const weeklyCost = ref(946000);

// Week Days for Tabs
const weekDays = ref([
  { label: "Thứ 2", value: 1 },
  { label: "Thứ 3", value: 2 },
  { label: "Thứ 4", value: 3 },
  { label: "Thứ 5", value: 4 },
  { label: "Thứ 6", value: 5 },
  { label: "Thứ 7", value: 6 },
  { label: "Chủ Nhật", value: 7 },
]);

const activeDay = ref(1);

// Sleep time
const sleepTime = ref(new Date());
sleepTime.value.setHours(22, 0, 0, 0); // Default: 10:00 PM

// Daily report state
const showDailyReport = ref(false);

// Daily stats data
const dailyStats = ref([
  {
    id: 1,
    label: "Canxi",
    value: "900",
    target: "1200",
    unit: "mg",
    valueColor: "text-gray-800",
  },
  {
    id: 2,
    label: "Calo",
    value: "2/2",
    target: "2000",
    unit: "cal",
    valueColor: "text-blue-600",
  },
  {
    id: 3,
    label: "Bổ Sinh Lượng",
    value: "+0.03",
    target: null,
    unit: "cm",
    valueColor: "text-green-600",
  },
]);

// Meal Data
const mealPlans = ref({
  1: [
    // Monday
    {
      id: 1,
      mealType: "SÁNG",
      title: "Lê ổn",
      ingredients: ["Lê (30 000đ)"],
      nutritionNote: "Rất tốt cho chỉ báo nhỏ lên",
      calories: 8,
      calcium: 0,
      cost: 0,
      image: "/meals/breakfast.jpg",
    },
    {
      id: 2,
      mealType: "TRƯA",
      title: "Cá hồi áp chảo & bông cải xanh",
      ingredients: [
        "Phi lê cá hồi (50 000đ)",
        "Bông cải xanh (30 000đ)",
        "Dầu ô liu (20 000đ)",
      ],
      nutritionNote:
        "Hợp với bé có thể ăn cùng. Ấp chảo hoặc nướng để làm này bé cho để ăn được. Giúp bé làm trưởng chiều cao & cung cấp omega-3",
      calories: 930,
      calcium: 350,
      cost: 70000,
      image: "/meals/lunch.jpg",
    },
    {
      id: 3,
      mealType: "TỐI",
      title: "Ức gà luộc và salad rau xanh & Súp bí đỏ 6m",
      ingredients: [
        "Ức gà (30 000đ)",
        "Rau xà lách (08 000đ)",
        "Súp bí đỏ (tăng cường (7 000đ)",
        "Phô mai (8 000đ)",
      ],
      nutritionNote:
        "Luộc ức gà cho đến khi chín. Cắt thành miếng nhỏ khi ăn để bé dễ tiêu hóa. Trước các thức ăn này trước khi ăn. Bé sẽ nhận ăn nhiều hơn",
      calories: 450,
      calcium: 430,
      cost: 57000,
      image: "/meals/dinner.jpg",
    },
  ],
  2: [
    // Tuesday
    {
      id: 4,
      mealType: "SÁNG",
      title: "Yến mạch với sữa",
      ingredients: [
        "Yến mạch (20 000đ)",
        "Sữa tươi (15 000đ)",
        "Mật ong (10 000đ)",
      ],
      nutritionNote: "Giàu chất xơ và protein",
      calories: 350,
      calcium: 200,
      cost: 45000,
      image: "/meals/breakfast.jpg",
    },
    {
      id: 5,
      mealType: "TRƯA",
      title: "Thịt bò xào rau củ",
      ingredients: ["Thịt bò (60 000đ)", "Rau củ hỗn hợp (25 000đ)"],
      nutritionNote: "Bổ sung sắt và protein",
      calories: 800,
      calcium: 280,
      cost: 85000,
      image: "/meals/lunch.jpg",
    },
    {
      id: 6,
      mealType: "TỐI",
      title: "Cháo cá lóc dinh dưỡng",
      ingredients: ["Cá lóc (40 000đ)", "Gạo (5 000đ)", "Rau ngót (8 000đ)"],
      nutritionNote: "Dễ tiêu hóa, giàu canxi",
      calories: 400,
      calcium: 380,
      cost: 53000,
      image: "/meals/dinner.jpg",
    },
  ],
  // Clone data for other days
  3: [],
  4: [],
  5: [],
  6: [],
  0: [],
});

// Initialize other days with Monday's data for demo
onMounted(() => {
  [3, 4, 5, 6, 7].forEach((day) => {
    mealPlans.value[day] = [...mealPlans.value[1]];
  });
});

// Computed property for current day meals
const currentDayMeals = computed(() => {
  const dayValue = weekDays.value.find(
    (day) => day.value === activeDay.value
  )?.value;
  return mealPlans.value[dayValue] || [];
});

// Chart Data
const chartData = ref({
  labels: ["Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"],
  datasets: [
    {
      label: "Canxi kế hoạch",
      backgroundColor: "#3B82F6",
      borderColor: "#3B82F6",
      borderWidth: 1,
      borderRadius: 8,
      data: [800, 750, 820, 810, 800, 830, 780],
    },
  ],
});

// Chart Options
const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      display: false,
    },
    tooltip: {
      callbacks: {
        label: function (context) {
          return `Canxi: ${context.parsed.y} mg`;
        },
      },
    },
  },
  scales: {
    y: {
      beginAtZero: true,
      max: 1000,
      ticks: {
        stepSize: 250,
        callback: function (value) {
          return value;
        },
      },
      grid: {
        display: true,
        drawBorder: false,
      },
    },
    x: {
      grid: {
        display: false,
      },
      ticks: {
        font: {
          size: 12,
        },
      },
    },
  },
});

// Methods
const formatCurrency = (value) => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(value);
};

const formatSleepTime = computed(() => {
  if (!sleepTime.value) return "10:00 CH";
  const hours = sleepTime.value.getHours();
  const minutes = sleepTime.value.getMinutes();
  const period = hours >= 12 ? "CH" : "SA";
  const displayHours = hours % 12 || 12;
  const displayMinutes = minutes.toString().padStart(2, "0");
  return `${displayHours}:${displayMinutes} ${period}`;
});

const onAppointment = () => {
  console.log("Hẹn hiệu clicked");
  // Add your appointment logic here
};

const onLogoError = (e) => {
  // Fallback if logo not found
  e.target.style.display = "none";
};

const onMealImageError = (e) => {
  // Fallback placeholder image
  e.target.src =
    "https://via.placeholder.com/400x300/E5E7EB/6B7280?text=Meal+Image";
};

const viewMeal = (meal) => {
  console.log("View meal:", meal);
  // Open meal detail popup/modal
};

const updateMeal = (meal) => {
  console.log("Update meal:", meal);
  // Open meal update popup/modal
};

const markTodayComplete = () => {
  console.log("Mark today as complete");
  console.log("Sleep time:", sleepTime.value);
  showDailyReport.value = true;
};

const editSleepTime = () => {
  console.log("Edit sleep time");
  showDailyReport.value = false;
};

const editDailyRecord = () => {
  console.log("Edit daily record");
  showDailyReport.value = false;
};

const downloadWeeklyPlan = () => {
  console.log("Download weekly plan");
  // Generate and download PDF or other format
};

// Lifecycle
onMounted(() => {
  // Initialize any data or fetch from API
  console.log("NutrilRoadMapSchedule mounted");
});
</script>

<style scoped>
/* ─── Design Tokens (matching NutrilRoadMapSurvey) ─── */
.nutril-roadmap-schedule {
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
.schedule-header {
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

/* ─── Section Titles ─── */
.section-title {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.section-subtitle {
  font-size: 0.875rem;
  color: var(--text-muted);
}

.section-icon {
  color: var(--primary);
  font-size: 1.1rem;
}

.card-heading {
  font-size: 1.05rem;
  font-weight: 600;
  color: var(--text-primary);
}

/* ─── User Info Card ─── */
:deep(.user-info-card) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  );
  border: none;
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(37, 99, 235, 0.3);
  color: #fff;
}

.user-avatar {
  width: 56px;
  height: 56px;
  background: rgba(255, 255, 255, 0.2);
  border: 2px solid rgba(255, 255, 255, 0.35);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-avatar i {
  font-size: 1.4rem;
  color: #fff;
}

.user-label {
  font-size: 0.8rem;
  opacity: 0.8;
  margin-bottom: 2px;
}

.user-name {
  font-size: 1.1rem;
  font-weight: 700;
}

.user-detail {
  font-size: 0.75rem;
  opacity: 0.75;
  margin-top: 2px;
}

.stat-box {
  background: rgba(255, 255, 255, 0.15);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  padding: 12px;
  backdrop-filter: blur(4px);
}

.stat-label {
  font-size: 0.65rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  opacity: 0.85;
  margin-bottom: 4px;
  font-weight: 600;
}

.stat-value {
  font-size: 1.45rem;
  font-weight: 800;
}

.stat-value-sm {
  font-size: 1.15rem;
  font-weight: 800;
}

:deep(.appt-btn) {
  color: #fff !important;
  background: rgba(255, 255, 255, 0.15) !important;
  border: 1px solid rgba(255, 255, 255, 0.25) !important;
}

:deep(.appt-btn:hover) {
  background: rgba(255, 255, 255, 0.25) !important;
}

/* ─── Content Cards ─── */
:deep(.content-card) {
  --p-card-background: var(--surface);
  --p-card-border-radius: 16px;
  --p-card-shadow: var(--shadow-md);
  border: 1px solid var(--border);
  overflow: hidden;
  position: relative;
}

:deep(.content-card)::before {
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

:deep(.p-card-content) {
  padding: 1.5rem;
}

/* ─── Chart ─── */
.chart-container {
  position: relative;
  width: 100%;
  max-width: 100%;
}

.chart-legend {
  display: flex;
  align-items: center;
  background: var(--primary-bg);
  padding: 8px 16px;
  border-radius: 10px;
  border: 1px solid var(--primary-bg-deep);
}

.legend-dot {
  width: 14px;
  height: 14px;
  background: var(--primary);
  border-radius: 4px;
  margin-right: 8px;
}

.legend-text {
  font-size: 0.85rem;
  font-weight: 600;
  color: var(--text-secondary);
}

/* ─── Tabs ─── */
.custom-tabview :deep(.p-tabs-nav),
.custom-tabview :deep(.p-tablist-content) {
  background: transparent;
  border: none;
}

.custom-tabview :deep(.p-tab) {
  background: var(--surface-alt);
  border: 1.5px solid var(--border);
  border-radius: 10px;
  padding: 0.6rem 1.2rem;
  color: var(--text-secondary);
  font-weight: 600;
  font-size: 0.85rem;
  transition: all 0.2s ease;
}

.custom-tabview :deep(.p-tab:hover) {
  border-color: var(--primary-light);
  color: var(--primary);
  background: var(--primary-bg);
}

.custom-tabview :deep(.p-tab-active) {
  background: var(--primary) !important;
  border-color: var(--primary) !important;
  color: #fff !important;
  box-shadow: 0 4px 12px rgba(37, 99, 235, 0.3);
}

.custom-tabview :deep(.p-tabpanels) {
  background: transparent;
  padding: 0;
}

.custom-tabview :deep(.p-tablist-active-bar) {
  display: none;
}

/* ─── Meal Cards ─── */
.meal-card {
  height: 100%;
  display: flex;
  flex-direction: column;
  border-radius: 14px !important;
  overflow: hidden;
  border: 1px solid var(--border) !important;
  box-shadow: var(--shadow-sm) !important;
  transition: all 0.3s ease;
}

.meal-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg) !important;
}

.meal-card :deep(.p-card-body) {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.meal-card :deep(.p-card-content) {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 1rem;
}

.meal-card img {
  transition: transform 0.3s ease;
}

.meal-card:hover img {
  transform: scale(1.05);
}

.meal-type-badge {
  position: absolute;
  top: 12px;
  left: 12px;
  background: var(--primary);
  color: #fff;
  padding: 4px 14px;
  border-radius: 999px;
  font-size: 0.8rem;
  font-weight: 700;
  letter-spacing: 0.3px;
  box-shadow: 0 2px 8px rgba(37, 99, 235, 0.4);
}

.meal-title {
  font-size: 1.05rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.info-label {
  font-size: 0.75rem;
  color: var(--text-muted);
  font-weight: 600;
  margin-bottom: 0.5rem;
  text-transform: uppercase;
  letter-spacing: 0.3px;
}

.nutrition-stats {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
  background: var(--surface-alt);
  padding: 10px 14px;
  border-radius: 10px;
  border: 1px solid var(--border);
}

/* ─── Buttons ─── */
:deep(.eye-button) {
  color: var(--primary) !important;
  border-color: var(--primary-bg-deep) !important;
  border-radius: 10px !important;
  font-weight: 600 !important;
  transition: all 0.2s ease !important;
}

:deep(.eye-button:hover) {
  background: var(--primary-bg) !important;
  border-color: var(--primary) !important;
}

:deep(.update-button) {
  background: var(--primary) !important;
  border-color: var(--primary) !important;
  border-radius: 10px !important;
  font-weight: 600 !important;
  transition: all 0.2s ease !important;
}

:deep(.update-button:hover) {
  background: var(--primary-dark) !important;
  border-color: var(--primary-dark) !important;
  box-shadow: 0 4px 12px rgba(37, 99, 235, 0.3);
}

:deep(.primary-action-btn) {
  background: var(--primary) !important;
  border: none !important;
  border-radius: 10px !important;
  padding: 10px 24px !important;
  font-weight: 600 !important;
  box-shadow: 0 4px 12px rgba(37, 99, 235, 0.3);
  transition: all 0.25s ease !important;
}

:deep(.primary-action-btn:hover) {
  background: var(--primary-dark) !important;
  box-shadow: 0 6px 18px rgba(37, 99, 235, 0.4);
  transform: translateY(-1px);
}

:deep(.download-btn) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  ) !important;
  border: none !important;
  border-radius: var(--radius) !important;
  padding: 14px 32px !important;
  font-size: 1rem !important;
  font-weight: 700 !important;
  box-shadow: 0 4px 14px rgba(37, 99, 235, 0.35);
  transition: all 0.25s ease !important;
  letter-spacing: 0.3px;
}

:deep(.download-btn:hover) {
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  ) !important;
  box-shadow: 0 6px 20px rgba(37, 99, 235, 0.45);
  transform: translateY(-1px);
}

/* ─── Schedule Settings ─── */
:deep(.schedule-settings-card) {
  background: var(--surface-alt) !important;
}

.custom-datepicker :deep(.p-datepicker-input-icon-container) {
  display: flex;
  align-items: center;
}

.custom-datepicker :deep(.p-datepicker-input) {
  padding: 0.5rem 1rem;
  border-radius: 10px;
  border: 1.5px solid var(--border);
  font-weight: 600;
  color: var(--text-primary);
  transition: all 0.2s ease;
}

.custom-datepicker :deep(.p-datepicker-input:focus) {
  border-color: var(--primary);
  box-shadow: 0 0 0 3px var(--primary-ring);
}

/* ─── Daily Report ─── */
.daily-report-content {
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
  .nutril-roadmap-schedule {
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

  :deep(.user-info-card) {
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.3);
  }
}

/* ─── Responsive ─── */
@media (max-width: 768px) {
  .nutril-roadmap-schedule {
    padding: 1.25rem 0.75rem;
  }

  .col-span-2,
  .col-span-3 {
    grid-column: span 12 / span 12;
    text-align: center;
  }

  .col-span-1 {
    grid-column: span 12 / span 12;
  }

  .user-avatar {
    width: 48px;
    height: 48px;
  }
}

@media (min-width: 1024px) {
  .nutril-roadmap-schedule {
    padding: 2.5rem 2.5rem;
  }
}
</style>
