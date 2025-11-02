<template>
  <section class="mx-auto px-4 lg:px-8 py-12 lg:py-16 bg-gray-50">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-8 lg:gap-12">
      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-8">
        <h2
          class="text-3xl sm:text-4xl lg:text-5xl font-bold text-gray-800 mb-4"
        >
          Các Gói Dịch Vụ Theo Tháng
        </h2>
        <p class="text-base sm:text-lg text-gray-600 mx-auto max-w-3xl">
          Đầu tư vào tương lai của con bạn với các gói hành trình được thiết kế
          khoa học và toàn diện.
        </p>
      </div>

      <!-- Package Cards - 3 columns on desktop, full width on mobile -->
      <div
        class="col-span-12 lg:col-span-4"
        v-for="pkg in packages"
        :key="pkg.id"
      >
        <CardPackageV2
          :title="pkg.title"
          :price="pkg.price"
          :original-price="pkg.originalPrice"
          :features="pkg.features"
          :is-popular="pkg.isPopular"
          :button-label="pkg.buttonLabel"
          @select-package="handleSelectPackage"
        />
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import CardPackageV2 from "./CardPackageV2.vue";

const packages = ref([
  {
    id: 1,
    title: "Hành Trình Everest 3 Tháng",
    price: 1200000,
    originalPrice: 1500000,
    isPopular: false,
    buttonLabel: "Chọn Gói Này",
    features: [
      "Dự đoán chiều cao ban đầu",
      "Lộ trình dinh dưỡng 3 tháng",
      "Phác đồ luyện tập 3 tháng",
      "Chương trình tăng trưởng toàn diện",
    ],
  },
  {
    id: 2,
    title: "Hành Trình Everest 6 Tháng",
    price: 2160000,
    originalPrice: 3000000,
    isPopular: true,
    buttonLabel: "Chọn Gói Này",
    features: [
      "Tất cả quyền lợi của gói 3 tháng",
      "Cập nhật tiến trình mỗi 3 tháng",
      "Huy hiệu và phần thưởng đặc quyền",
      "Hỗ trợ chuyên gia liên tục",
    ],
  },
  {
    id: 3,
    title: "Hành Trình Everest 9 Tháng",
    price: 3000000,
    originalPrice: 4500000,
    isPopular: false,
    buttonLabel: "Chọn Gói Này",
    features: [
      "Tất cả quyền lợi của gói 6 tháng",
      "Tư vấn trực tiếp 1-1 với chuyên gia",
      "Báo cáo phân tích chuyên sâu",
      "Quà tặng từ Everest Pharma",
    ],
  },
]);

const handleSelectPackage = (packageData) => {
  console.log("Selected package:", packageData);
  // Add your package selection logic here
  // For example: router.push({ name: 'Checkout', params: { package: packageData } });
};
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
