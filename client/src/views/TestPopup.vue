<template>
  <div class="min-h-screen flex flex-col items-center justify-center gap-4">
    <Button label="Open ResultWeekPopup" @click="showResultWeek = true" />

    <!-- ResultWeekPopup Section -->
    <div v-if="showResultWeek" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button
          icon="pi pi-times"
          text
          rounded
          severity="danger"
          @click="closeResultWeek"
        />
      </div>
      <ResultWeekPopup 
        :title="testData.title"
        :subtitle="testData.subtitle"
        :badgeTitle="testData.badgeTitle"
        :badgeDescription="testData.badgeDescription"
        :badgeIcon="testData.badgeIcon"
        :stats="testData.stats"
        :achievementTitle="testData.achievementTitle"
        :achievementLabel="testData.achievementLabel"
        :starCount="testData.starCount"
        :quoteText="testData.quoteText"
        :shareButtonLabel="testData.shareButtonLabel"
        :shareButtonIcon="testData.shareButtonIcon"
        :homeButtonLabel="testData.homeButtonLabel"
        :homeButtonIcon="testData.homeButtonIcon"
        @close="closeResultWeek"
        @goHome="handleGoHome"
        @share="handleShare"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import Button from "primevue/button";
import ResultWeekPopup from "@/components/popup/ResultWeekPopup/ResultWeekPopup.vue";

const showResultWeek = ref(false);

// Test data
const testData = ref({
  title: 'Chúc mừng bạn đã hoàn thành tuần!',
  subtitle: 'Bạn đã xuất sắc giành được danh hiệu',
  badgeTitle: 'Chiến binh Canxi',
  badgeDescription: 'Danh hiệu đạt được tuần này',
  badgeIcon: 'pi pi-trophy',
  stats: [
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
  ],
  achievementTitle: 'Mục đồ hoàn thành',
  achievementLabel: 'Xuất sắc!',
  starCount: 5,
  quoteText: '"Một tuần thật tuyệt vời! Hãy giữ vững phong độ này nhé."',
  shareButtonLabel: 'Chia sẻ',
  shareButtonIcon: 'pi pi-share-alt',
  homeButtonLabel: 'Trở về trang chủ',
  homeButtonIcon: 'pi pi-arrow-right'
});

// ResultWeekPopup handlers
const closeResultWeek = () => {
  showResultWeek.value = false;
};

const handleGoHome = () => {
  console.log("Redirecting to home...");
  showResultWeek.value = false;
  alert("Chuyển về trang chủ!");
};

const handleShare = () => {
  console.log("Sharing result...");
  alert("Chia sẻ kết quả tuần!");
};
</script>