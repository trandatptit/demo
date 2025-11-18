<template>
  <section class="result-week-popup mx-auto px-4 lg:px-8 py-6 lg:py-8 bg-gradient-to-br from-gray-50 to-blue-50 dark:from-gray-900 dark:to-gray-800">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-3 lg:gap-4">
      <!-- Header Section - Full Width -->
      <div class="col-span-12">
        <div class="flex justify-end">
          <Button
            @click="$emit('close')"
            icon="pi pi-times"
            rounded
            text
            severity="secondary"
            size="small"
          />
        </div>
      </div>

      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-2">
        <div class="success-icon mb-3">
          <i class="pi pi-check-circle"></i>
        </div>
        <h2 class="text-xl sm:text-2xl lg:text-3xl font-bold text-gray-800 dark:text-white mb-2">
          {{ title }}
        </h2>
        <p class="text-sm md:text-base text-gray-600 dark:text-gray-400">
          {{ subtitle }} <Tag :value="badgeTitle" severity="info" class="font-semibold text-sm" />
        </p>
      </div>

      <!-- Stats Cards Section - Full Width -->
      <div class="col-span-12 mb-3">
        <div class="grid grid-cols-2 lg:grid-cols-4 gap-2 md:gap-3">
          <Card 
            v-for="stat in stats" 
            :key="stat.id"
            class="stat-card"
          >
            <template #content>
              <div class="text-center">
                <!-- Chart with value overlay -->
                <div class="chart-container">
                  <Chart 
                    type="doughnut" 
                    :data="getChartData(stat)" 
                    :options="chartOptions"
                    class="chart-wrapper"
                  />
                  <div class="chart-center-text">
                    <div class="chart-value">{{ stat.value }}</div>
                    <div class="chart-max">/ {{ stat.max }}</div>
                  </div>
                </div>
                
                <!-- Label -->
                <p class="stat-label mt-2" v-html="stat.label"></p>
              </div>
            </template>
          </Card>
        </div>
      </div>

      <!-- Achievement Section - Full Width -->
      <div class="col-span-12">
        <Panel class="achievement-panel mb-3">
          <template #header>
            <div class="w-full text-center">
              <h3 class="text-base font-semibold">
                {{ achievementTitle }}
              </h3>
            </div>
          </template>
          
          <div class="text-center">
            <!-- Stars Rating -->
            <Rating 
              :modelValue="starCount" 
              :stars="5" 
              readonly 
              :cancel="false"
              class="stars-rating"
            />
            
            <Tag 
              :value="achievementLabel" 
              severity="success" 
              class="mt-3 text-base font-semibold"
            />
          </div>
        </Panel>
      </div>

      <!-- Badge Section - Full Width -->
      <div class="col-span-12 text-center mb-3">
        <p class="text-xs text-gray-600 dark:text-gray-400 mb-2">
          {{ badgeDescription }}
        </p>
        
        <Chip class="badge-chip">
          <template #icon>
            <Avatar :icon="badgeIcon" class="badge-avatar" />
          </template>
          <span class="font-semibold">{{ badgeTitle }}</span>
        </Chip>
      </div>

      <!-- Quote Section - Full Width -->
      <div class="col-span-12 mb-3">
        <Message severity="info" :closable="false" class="quote-message">
          {{ quoteText }}
        </Message>
      </div>

      <!-- Action Buttons - Full Width -->
      <div class="col-span-12">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-2">
          <Button 
            :label="shareButtonLabel" 
            :icon="shareButtonIcon"
            outlined
            severity="secondary"
            class="w-full"
            :pt="{
              root: { 
                class: 'border-2 border-gray-300 text-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:text-gray-300 py-2.5 text-sm font-semibold'
              }
            }"
            @click="$emit('share')"
          />
          
          <Button 
            :label="homeButtonLabel" 
            :icon="homeButtonIcon"
            iconPos="right"
            class="submit-btn w-full"
            :pt="{
              root: { 
                class: 'bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 dark:from-blue-700 dark:to-blue-800 border-0 py-2.5 text-sm font-semibold shadow-lg hover:shadow-xl transition-all duration-200'
              }
            }"
            @click="$emit('goHome')"
          />
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import Button from 'primevue/button';
import Card from 'primevue/card';
import Chart from 'primevue/chart';
import Tag from 'primevue/tag';
import Rating from 'primevue/rating';
import Panel from 'primevue/panel';
import Chip from 'primevue/chip';
import Avatar from 'primevue/avatar';
import Message from 'primevue/message';

const props = defineProps({
  title: {
    type: String,
    default: 'Chúc mừng bạn đã hoàn thành tuần!'
  },
  subtitle: {
    type: String,
    default: 'Bạn đã xuất sắc giành được danh hiệu'
  },
  badgeTitle: {
    type: String,
    default: 'Chiến binh Canxi'
  },
  badgeDescription: {
    type: String,
    default: 'Danh hiệu đạt được tuần này'
  },
  badgeIcon: {
    type: String,
    default: 'pi pi-trophy'
  },
  stats: {
    type: Array,
    default: () => [
      {
        id: 'calcium',
        label: 'Canxi hấp thu<br>(mg)',
        value: '8.500',
        max: '10.000',
        percentage: 85,
        colorClass: 'blue'
      },
      {
        id: 'cost',
        label: 'Chi phí thực tế (đ)',
        value: '450.000',
        max: '1.928.571.429',
        percentage: 45,
        colorClass: 'orange'
      },
      {
        id: 'growth',
        label: 'Ước tính tăng<br>trưởng (cm)',
        value: '0,5',
        max: '15',
        percentage: 3.33,
        colorClass: 'gray'
      },
      {
        id: 'completion',
        label: 'Hoàn thành (%)',
        value: '93',
        max: '100',
        percentage: 93,
        colorClass: 'purple'
      }
    ]
  },
  achievementTitle: {
    type: String,
    default: 'Mục đồ hoàn thành'
  },
  achievementLabel: {
    type: String,
    default: 'Xuất sắc!'
  },
  starCount: {
    type: Number,
    default: 5
  },
  quoteText: {
    type: String,
    default: '"Một tuần thật tuyệt vời! Hãy giữ vững phong độ này nhé."'
  },
  shareButtonLabel: {
    type: String,
    default: 'Chia sẻ'
  },
  shareButtonIcon: {
    type: String,
    default: 'pi pi-share-alt'
  },
  homeButtonLabel: {
    type: String,
    default: 'Trở về trang chủ'
  },
  homeButtonIcon: {
    type: String,
    default: 'pi pi-arrow-right'
  }
});

const emit = defineEmits(['close', 'goHome', 'share']);

// Chart color mapping
const colorMap = {
  blue: { primary: '#3b82f6', secondary: '#dbeafe' },
  orange: { primary: '#f97316', secondary: '#fed7aa' },
  gray: { primary: '#6b7280', secondary: '#e5e7eb' },
  purple: { primary: '#a855f7', secondary: '#e9d5ff' }
};

// Generate chart data for each stat
const getChartData = (stat) => {
  const colors = colorMap[stat.colorClass] || colorMap.blue;
  
  return {
    labels: [stat.value, 'Remaining'],
    datasets: [
      {
        data: [stat.percentage, 100 - stat.percentage],
        backgroundColor: [colors.primary, colors.secondary],
        borderWidth: 0,
        cutout: '75%'
      }
    ]
  };
};

// Chart options
const chartOptions = {
  responsive: true,
  maintainAspectRatio: true,
  aspectRatio: 1,
  plugins: {
    legend: {
      display: false
    },
    tooltip: {
      enabled: false
    }
  },
  animation: {
    animateRotate: true,
    animateScale: true,
    duration: 1000
  }
};
</script>

<style scoped>
/* Base Section Styles */
.result-week-popup {
  max-width: 1000px;
  border-radius: 1.5rem;
}

/* Success Icon */
.success-icon {
  font-size: 48px;
  color: #10b981;
  animation: scaleIn 0.5s ease-out;
}

.success-icon i {
  display: inline-block;
  animation: pulse 2s infinite;
}

@keyframes scaleIn {
  from {
    transform: scale(0);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
}

/* Stats Card */
.stat-card {
  transition: all 0.3s ease;
  height: 100%;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
}

:deep(.stat-card .p-card-body) {
  padding: 1rem 0.75rem;
}

:deep(.stat-card .p-card-content) {
  padding: 0;
}

/* Chart Container with overlay */
.chart-container {
  width: 100px;
  height: 100px;
  margin: 0 auto;
  position: relative;
}

.chart-wrapper {
  width: 100%;
  height: 100%;
}

.chart-center-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  pointer-events: none;
}

.chart-value {
  font-size: 18px;
  font-weight: 700;
  color: #1e293b;
  line-height: 1;
}

.chart-max {
  font-size: 9px;
  color: #94a3b8;
  margin-top: 2px;
}

.stat-label {
  font-size: 12px;
  color: #64748b;
  font-weight: 500;
  line-height: 1.3;
  min-height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Achievement Panel */
.achievement-panel {
  background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
  border: none;
}

:deep(.achievement-panel .p-panel-header) {
  background: transparent;
  border: none;
  padding: 0.75rem;
}

:deep(.achievement-panel .p-panel-content) {
  padding: 0.75rem;
  border: none;
}

/* Stars Rating */
.stars-rating {
  justify-content: center;
}

:deep(.stars-rating .p-rating-icon) {
  font-size: 24px;
  color: #fbbf24;
  animation: starPop 0.5s ease-out backwards;
}

:deep(.stars-rating .p-rating-icon:nth-child(2)) {
  animation-delay: 0.1s;
}

:deep(.stars-rating .p-rating-icon:nth-child(3)) {
  animation-delay: 0.2s;
}

:deep(.stars-rating .p-rating-icon:nth-child(4)) {
  animation-delay: 0.3s;
}

:deep(.stars-rating .p-rating-icon:nth-child(5)) {
  animation-delay: 0.4s;
}

@keyframes starPop {
  from {
    transform: scale(0) rotate(-180deg);
    opacity: 0;
  }
  to {
    transform: scale(1) rotate(0);
    opacity: 1;
  }
}

/* Badge Chip */
.badge-chip {
  padding: 8px 18px;
  font-size: 13px;
  background: white;
  border: 2px solid #3b82f6;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

:deep(.badge-chip .p-chip-text) {
  color: #3b82f6;
  font-weight: 600;
}

.badge-avatar {
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
  color: white;
  width: 24px;
  height: 24px;
  font-size: 14px;
}

/* Quote Message */
.quote-message {
  font-style: italic;
  text-align: center;
}

:deep(.quote-message .p-message-wrapper) {
  justify-content: center;
  padding: 0.75rem;
}

:deep(.quote-message .p-message-text) {
  font-size: 13px;
  color: #64748b;
}

/* Dark mode adjustments */
@media (prefers-color-scheme: dark) {
  .stat-card {
    background: #1f2937;
  }
  
  .chart-value {
    color: #e5e7eb;
  }
  
  .achievement-panel {
    background: linear-gradient(135deg, #1e3a5f 0%, #1e293b 100%);
  }
  
  .badge-chip {
    background: #1f2937;
  }
}

/* Responsive */
@media (max-width: 640px) {
  .chart-container {
    width: 80px;
    height: 80px;
  }
  
  .chart-value {
    font-size: 16px;
  }
  
  .chart-max {
    font-size: 8px;
  }
  
  .stat-label {
    font-size: 11px;
    min-height: 28px;
  }
  
  :deep(.stars-rating .p-rating-icon) {
    font-size: 20px;
  }
}

/* Smooth transitions */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

/* Submit button animation */
.submit-btn {
  position: relative;
  overflow: hidden;
}

.submit-btn::before {
  content: '';
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

.submit-btn:hover::before {
  width: 300px;
  height: 300px;
}
</style>