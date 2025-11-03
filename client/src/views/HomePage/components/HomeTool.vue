<template>
  <section class="mx-auto px-4 lg:px-8 py-12 lg:py-16 bg-gray-50">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-8 lg:gap-12">
      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-8">
        <h2
          class="text-3xl sm:text-4xl lg:text-5xl font-bold text-gray-800 mb-4"
        >
          Dụng Cụ Nổi Bật
        </h2>
        <p class="text-base sm:text-lg text-gray-600 mx-auto max-w-3xl">
          Tối ưu hóa quá trình luyện tập của bạn với các dụng cụ được chuyên gia
          khuyên dùng.
        </p>
      </div>

      <!-- Product Cards - 3 columns on desktop, full width on mobile -->
      <div
        class="col-span-12 lg:col-span-4"
        v-for="product in products"
        :key="product.id"
      >
        <CardProduct :product="product" @add-to-cart="handleAddToCart" />
      </div>
    </div>

    <!-- Toast for notifications -->
    <Toast />
  </section>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import Button from "primevue/button";
import Toast from "primevue/toast";
import { useToast } from "primevue/usetoast";
import CardProduct from "../../../components/CardProduct/CardProduct.vue";

const router = useRouter();
const toast = useToast();

const products = ref([
  {
    id: 1,
    name: "Xà Đơn Treo Tường",
    description:
      "Công cụ tuyệt vời để kéo giãn cơ sống, hỗ trợ phát triển chiều cao hiệu quả ngay tại nhà.",
    price: 450000,
    image: "/images/expert-2.jpg",
    badge: null,
  },
  {
    id: 2,
    name: "Dây Nhảy Tốc Độ",
    description:
      "Bài tập cardio toàn thân, kích thích xương phát triển với cơ thể luôn ưu tiên.",
    price: 150000,
    image: "/images/expert-2.jpg",
    badge: "Hot",
  },
  {
    id: 3,
    name: "Thảm Tập Yoga Định Tuyến",
    description:
      "Chất liệu êm ái, chống trượt, giúp thực hiện động tác kéo dãn cơ và yoga an toàn.",
    price: 320000,
    image: "/images/expert-2.jpg",
    badge: null,
  },
]);

const handleAddToCart = (product) => {
  toast.add({
    severity: "success",
    summary: "Thành công",
    detail: `Đã thêm ${product.name} vào giỏ hàng`,
    life: 3000,
  });

  // Logic thêm vào giỏ hàng
  console.log("Added to cart:", product);
};

const viewAllProducts = () => {
  router.push({ name: "Products" });
};
</script>

<style scoped>
/* Additional custom styles if needed */
</style>