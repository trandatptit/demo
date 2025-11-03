<template>
  <section class="bg-gradient-to-b from-gray-50 to-white py-12 lg:py-16">
    <div class="mx-auto px-4 lg:px-8">
      <!-- Header Section -->
      <div class="text-center mb-12">
        <h2 class="text-3xl lg:text-4xl font-bold text-[#002855] mb-4">
          Sản Phẩm Nổi Bật
        </h2>
        <p class="text-gray-600 text-base lg:text-lg max-w-2xl mx-auto">
          Các sản phẩm bổ sung được nghiên cứu khoa học, hỗ trợ tối đa cho quá
          trình phát triển.
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