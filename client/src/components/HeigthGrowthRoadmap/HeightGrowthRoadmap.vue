<template>
  <section class="mx-auto px-4 lg:px-8 py-12 lg:py-16">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-8 lg:gap-12">
      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-8">
        <h2
          class="text-3xl sm:text-4xl lg:text-5xl font-bold text-blue-900 mb-4"
        >
          Khám Phá Lộ Trình Tăng Chiều<br />Cao Everest
        </h2>
        <p class="text-base sm:text-lg text-gray-600 mx-auto max-w-3xl">
          Chỉ từ <span class="text-blue-600 font-bold">170.000đ/tháng</span> cho
          một lộ trình phát triển toàn diện, được chuyên gia AI và y khoa thiết
          kế riêng cho bạn.
        </p>
      </div>

      <!-- CTA Button - Centered -->
      <div class="col-span-12 flex justify-center">
        <Button
          label="Tìm Hiểu Ngay"
          unstyled
          class="bg-blue-600 hover:bg-blue-700 text-white font-bold text-lg px-8 py-3 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 cursor-pointer"
          @click="handleLearnMore"
        />
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import Button from "primevue/button";

const handleLearnMore = () => {
  // Add your navigation or action logic here
  console.log("Learn more clicked");
  // For example: router.push({ name: 'Packages' });
};
</script>

<style scoped>
/* Additional custom styles if needed */
.bg-gradient-to-b {
  background: linear-gradient(to bottom, #ffffff, #eff6ff);
}
</style>
