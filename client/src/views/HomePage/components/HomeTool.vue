<template>
  <section class="bg-gradient-to-b from-gray-50 to-white py-12 lg:py-16">
    <div class="mx-auto px-4 lg:px-8">
      <!-- Header Section -->
      <div class="text-center mb-12">
        <h2 class="text-3xl lg:text-4xl font-bold text-[#002855] mb-4">
          Dụng Cụ Nổi Bật
        </h2>
        <p class="text-gray-600 text-base lg:text-lg max-w-2xl mx-auto">
          Tối ưu hóa quá trình luyện tập của bạn với các dụng cụ được chuyên gia
          khuyên dùng.
        </p>
      </div>

      <!-- Products Grid -->
      <div
        class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 lg:gap-12 max-w-7xl mx-auto"
      >
        <CardProduct
          v-for="product in products"
          :key="product.id"
          :product="product"
          @add-to-cart="handleAddToCart"
        />
      </div>

      <!-- View All Button -->
      <!-- <div class="text-center mt-12">
        <Button
          label="Xem Tất Cả Sản Phẩm"
          icon="pi pi-arrow-right"
          iconPos="right"
          class="bg-[#002855] text-white font-medium px-8 py-3 hover:bg-[#003D7A] transition"
          @click="viewAllProducts"
        />
      </div> -->
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