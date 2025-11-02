<template>
  <section class="mx-auto px-4 lg:px-8 py-12 lg:py-16 bg-white">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-8 lg:gap-12">
      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-8">
        <h2
          class="text-3xl sm:text-4xl lg:text-5xl font-bold text-gray-800 mb-4"
        >
          Đối Tác Chuyên Gia & Người Nổi Tiếng
        </h2>
        <p class="text-base sm:text-lg text-gray-600 mx-auto max-w-3xl">
          Sự tín tưởng từ những tên tuổi hàng đầu là bảo chứng cho chất lượng và
          uy tín của Everest Pharma.
        </p>
      </div>

      <!-- Expert Cards - 3 columns on desktop, full width on mobile -->
      <div
        class="col-span-12 md:col-span-6 lg:col-span-4"
        v-for="expert in experts"
        :key="expert.id"
      >
        <CardExpert
          :name="expert.name"
          :title="expert.title"
          :image="expert.image"
          :description="expert.description"
          :stats="expert.stats"
          @view-detail="handleViewDetail"
        />
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import CardExpert from "./CardExpert.vue";

const experts = ref([
  {
    id: 1,
    name: "GS.TS. Trần Hữu Dũng",
    title: "Chuyên gia Nội tiết - Dinh dưỡng",
    image: "/images/expert-1.jpg", // Replace with actual image path
    description:
      "Là chuyên gia hàng đầu, GS.TS. Dũng mang đến kiến thức sâu rộng và kinh nghiệm dày dặc để tạo ra các lộ trình phát triển của Everest Pharma.",
    stats: [
      { icon: "pi pi-calendar", value: "25+ năm kinh nghiệm" },
      { icon: "pi pi-users", value: "10.000+ bệnh nhân" },
    ],
  },
  {
    id: 2,
    name: "MC Trần Thành",
    title: "Đại sứ thương hiệu",
    image: "/images/expert-2.jpg", // Replace with actual image path
    description:
      "Với tầm ảnh hưởng và sự tín yêu từ công chúng, Trần Thành lan tỏa thông điệp về tầm quan trọng của việc phát triển tầm vóc Việt.",
    stats: [
      { icon: "pi pi-heart", value: "5M+ followers" },
      { icon: "pi pi-star", value: "Top influencer" },
    ],
  },
  {
    id: 3,
    name: "BS.CKII. Nguyễn Thị Hòa",
    title: "Bác sĩ Nhi khoa",
    image: "/images/expert-3.jpg", // Replace with actual image path
    description:
      "Bác sĩ Hoa đảm bảo mọi phác đồ của Everest đều được xây dựng trên nền tảng y học vững chắc, an toàn và hiệu quả.",
    stats: [
      { icon: "pi pi-check-circle", value: "15+ năm" },
      { icon: "pi pi-shield", value: "Chứng nhận quốc tế" },
    ],
  },
]);

const handleViewDetail = (expertData) => {
  console.log("View detail:", expertData);
  // Add your navigation or modal logic here
  // For example: router.push({ name: 'ExpertDetail', params: { name: expertData.name } });
};
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
