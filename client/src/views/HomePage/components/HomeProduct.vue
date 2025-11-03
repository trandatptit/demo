<template>
  <section class="mx-auto px-4 lg:px-8 py-12 lg:py-16 bg-gray-50">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-8 lg:gap-12">
      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-8">
        <h2
          class="text-3xl sm:text-4xl lg:text-5xl font-bold text-gray-800 mb-4"
        >
          Sản Phẩm Nổi Bật
        </h2>
        <p class="text-base sm:text-lg text-gray-600 mx-auto max-w-3xl">
          Các sản phẩm bổ sung được nghiên cứu khoa học, hỗ trợ tối đa cho quá
          trình phát triển.
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
    name: "Everest Calcium D3K2",
    description:
      "Viên uống bổ sung Canxi, Vitamin D3 và K2, giúp xương chắc khỏe, tối ưu phát triển chiều cao.",
    price: 350000,
    image: "/images/expert-2.jpg",
    badge: null,
  },
  {
    id: 2,
    name: "Everest Growth Milk",
    description:
      "Sữa công thức chuyên biệt, giàu dưỡng chất, hỗ trợ tăng trưởng toàn diện cho trẻ em và thanh thiếu niên.",
    price: 520000,
    image: "/images/expert-2.jpg",
    badge: "Hot",
  },
  {
    id: 3,
    name: "Everest Smart Gummies",
    description:
      "Kẹo dẻo bổ sung đa vitamin và khoáng chất, vị trái cây thơm ngon, giúp bé ăn ngon và khỏe mạnh.",
    price: 280000,
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