<template>
  <div class="nutril-roadmap-schedule min-h-screen bg-gray-100 p-6">
    <!-- Header Section -->
    <div class="grid grid-cols-12 gap-4 mb-6">
      <div class="col-span-12 flex flex-col items-center">
        <!-- Logo -->
        <div class="mb-4">
          <img
            src="https://www.vecteezy.com/free-vector/any-logo"
            alt="Logo"
            class="w-16 h-16 object-contain"
            @error="onLogoError"
          />
        </div>

        <!-- Title -->
        <h1 class="text-2xl font-bold text-gray-800 text-center">
          Lộ Trình Dinh Dưỡng Tối Ưu
        </h1>
      </div>
    </div>

    <!-- User Info Card -->
    <div class="grid grid-cols-12 gap-4 mb-6">
      <div class="col-span-12">
        <Card
          class="shadow-lg border-0 bg-linear-to-r from-blue-600 to-blue-500 text-white rounded-2xl"
        >
          <template #content>
            <div class="grid grid-cols-12 gap-4 items-center">
              <!-- Avatar -->
              <div class="col-span-2 flex justify-center">
                <div
                  class="w-16 h-16 bg-white rounded-full flex items-center justify-center"
                >
                  <i class="pi pi-user text-blue-600 text-2xl"></i>
                </div>
              </div>

              <!-- User Name -->
              <div class="col-span-3">
                <p class="text-sm opacity-90 mb-1">Bạn</p>
                <p class="font-semibold text-lg">{{ userName }}</p>
                <p class="text-xs opacity-80">
                  Chiều cao hiện tại: {{ currentHeight }} cm
                </p>
              </div>

              <!-- Target Height -->
              <div class="col-span-2 text-center">
                <div class="bg-white bg-opacity-20 rounded-lg p-3">
                  <p class="text-xs opacity-90 mb-1">MỤC TIÊU</p>
                  <p class="text-2xl font-bold">{{ targetHeight }} cm</p>
                </div>
              </div>

              <!-- Duration -->
              <div class="col-span-2 text-center">
                <div class="bg-white bg-opacity-20 rounded-lg p-3">
                  <p class="text-xs opacity-90 mb-1">THỜI LƯỢNG</p>
                  <p class="text-2xl font-bold">{{ duration }} tháng</p>
                </div>
              </div>

              <!-- Cost -->
              <div class="col-span-2 text-center">
                <div class="bg-white bg-opacity-20 rounded-lg p-3">
                  <p class="text-xs opacity-90 mb-1">CHI PHÍ TUẦN</p>
                  <p class="text-xl font-bold">
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
                  severity="secondary"
                  class="bg-white bg-opacity-20 hover:bg-opacity-30"
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
        <h2 class="text-xl font-bold text-gray-800 mb-2">
          Lộ Trình Dinh Dưỡng Tuần Này
        </h2>
        <p class="text-sm text-gray-500">
          Thực đơn được AI có nhiệm hoạch định riêng cho bạn.
        </p>
      </div>
    </div>

    <!-- Chart Section -->
    <div class="grid grid-cols-12 gap-4">
      <div class="col-span-12">
        <Card class="shadow-lg border-0 rounded-2xl">
          <template #content>
            <!-- Chart Header -->
            <div class="flex items-center justify-center mb-6">
              <i class="pi pi-chart-bar text-gray-600 mr-2"></i>
              <h3 class="text-lg font-semibold text-gray-700">
                Tổng quan Canxi tuần
              </h3>
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
              <div class="flex items-center bg-blue-50 px-4 py-2 rounded-lg">
                <div class="w-4 h-4 bg-blue-500 rounded mr-2"></div>
                <span class="text-sm text-gray-700 font-medium"
                  >Canxi kế hoạch</span
                >
              </div>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- Meal Plan Section with Tabs -->
    <div class="grid grid-cols-12 gap-4 mt-6">
      <div class="col-span-12">
        <Card class="shadow-lg border-0 rounded-2xl">
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
                      <Card
                        class="meal-card shadow-md hover:shadow-xl transition-shadow duration-300 rounded-xl overflow-hidden"
                      >
                        <template #header>
                          <div class="relative">
                            <img
                              :src="meal.image"
                              :alt="meal.title"
                              class="w-full h-48 object-cover"
                              @error="onMealImageError"
                            />
                            <div
                              class="absolute top-3 left-3 bg-blue-600 text-white px-3 py-1 rounded-full text-sm font-semibold"
                            >
                              {{ meal.mealType }}
                            </div>
                          </div>
                        </template>

                        <template #content>
                          <!-- Meal Title -->
                          <h4 class="text-lg font-bold text-gray-800 mb-2">
                            {{ meal.title }}
                          </h4>

                          <!-- Ingredients -->
                          <div class="mb-4">
                            <p class="text-xs text-gray-500 font-semibold mb-2">
                              Nguyên liệu:
                            </p>
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
                            <p class="text-xs text-gray-500 font-semibold mb-2">
                              Chỉ báo:
                            </p>
                            <p class="text-sm text-gray-600">
                              {{ meal.nutritionNote }}
                            </p>
                          </div>

                          <!-- Nutrition Stats -->
                          <div
                            class="flex items-center justify-between mb-4 bg-gray-50 p-3 rounded-lg"
                          >
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
                              class="flex-1"
                              @click="viewMeal(meal)"
                            />
                            <Button
                              label="Cập nhật"
                              icon="pi pi-refresh"
                              class="flex-1"
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
        <Card class="shadow-lg border-0 rounded-2xl bg-gray-50">
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
                severity="success"
                class="bg-blue-600 hover:bg-blue-700 border-0 rounded-lg px-6 py-2"
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
          class="bg-blue-600 hover:bg-blue-700 border-0 rounded-lg px-8 py-3 text-lg font-semibold"
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
.nutril-roadmap-schedule {
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
}

/* Custom card styling */
:deep(.p-card) {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

:deep(.p-card-content) {
  padding: 1.5rem;
}

/* Chart container */
.chart-container {
  position: relative;
  width: 100%;
  max-width: 100%;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .col-span-2,
  .col-span-3 {
    grid-column: span 12 / span 12;
    text-align: center;
  }

  .col-span-1 {
    grid-column: span 12 / span 12;
  }
}

/* Smooth transitions */
.p-button {
  transition: all 0.3s ease;
}

.p-button:hover {
  transform: translateY(-2px);
}

/* Custom TabView Styling */
.custom-tabview :deep(.p-tabview-nav) {
  background: transparent;
  border: none;
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.custom-tabview :deep(.p-tabview-nav-link) {
  background: white;
  border: 2px solid #e5e7eb;
  border-radius: 0.5rem;
  padding: 0.75rem 1.5rem;
  color: #6b7280;
  font-weight: 600;
  transition: all 0.3s ease;
}

.custom-tabview :deep(.p-tabview-nav-link):hover {
  background: #f3f4f6;
  border-color: #3b82f6;
}

.custom-tabview :deep(.p-highlight .p-tabview-nav-link) {
  background: #3b82f6;
  border-color: #3b82f6;
  color: white;
}

.custom-tabview :deep(.p-tabview-panels) {
  background: transparent;
  padding: 0;
}

/* Meal Card Styling */
.meal-card {
  height: 100%;
  display: flex;
  flex-direction: column;
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

.meal-card:hover {
  transform: translateY(-4px);
  transition: all 0.3s ease;
}

/* Meal image hover effect */
.meal-card img {
  transition: transform 0.3s ease;
}

.meal-card:hover img {
  transform: scale(1.05);
}

/* Custom DatePicker Styling */
.custom-datepicker :deep(.p-datepicker-input-icon-container) {
  display: flex;
  align-items: center;
}

.custom-datepicker :deep(.p-datepicker-input) {
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  border: 1px solid #d1d5db;
  font-weight: 600;
  color: #1f2937;
}

.custom-datepicker :deep(.p-datepicker-input:focus) {
  border-color: #3b82f6;
  box-shadow: 0 0 0 1px #3b82f6;
}

/* Daily Report Content Styling */
.daily-report-content {
  animation: fadeIn 0.3s ease-in;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
