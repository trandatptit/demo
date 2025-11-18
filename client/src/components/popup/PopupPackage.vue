<template>
  <Dialog
    v-model:visible="visible"
    modal
    :closable="false"
    :style="{ width: '70vw', maxWidth: '1100px' }"
    :breakpoints="{ '1199px': '85vw', '768px': '90vw', '575px': '95vw' }"
    :pt="{
      root: {
        class:
          'rounded-3xl shadow-2xl border border-gray-200 dark:border-gray-700',
      },
      header: { class: '!hidden' },
      content: {
        class:
          'bg-gradient-to-br from-blue-50 via-purple-50 to-pink-50 dark:from-gray-800 dark:via-gray-900 dark:to-gray-800 !p-0 rounded-3xl relative overflow-visible',
      },
      mask: { class: 'backdrop-blur-sm bg-black/40 dark:bg-black/60' },
    }"
  >
    <div class="p-4 md:p-6">
      <!-- Header Section - Giảm margin -->
      <div class="text-center mb-6 md:mb-8 relative">
        <!-- Custom Close Button -->
        <button
          @click="visible = false"
          class="absolute -top-2 md:-top-3 right-0 w-8 h-8 rounded-full bg-white dark:bg-gray-700 shadow-xl hover:bg-gray-100 dark:hover:bg-gray-600 transition-all duration-200 border-2 border-gray-300 dark:border-gray-600 flex items-center justify-center z-50"
          type="button"
        >
          <i class="pi pi-times text-gray-600 dark:text-gray-300 text-sm"></i>
        </button>

        <h2
          class="text-xl md:text-2xl lg:text-3xl font-bold text-blue-900 dark:text-blue-100 mb-2"
        >
          Mở Khóa Lộ Trình Tăng Trưởng
        </h2>
        <p
          class="text-gray-600 dark:text-gray-400 text-xs md:text-sm lg:text-base mx-auto text-center max-w-2xl"
        >
          Chọn một chương trình được thiết kế bởi AI để tối ưu hóa tiềm năng
          phát triển của bạn.
        </p>
      </div>

      <!-- Pricing Cards Grid -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-3 md:gap-4 lg:gap-6">
        <!-- Dynamic Package Cards using CardPackage Component -->
        <CardPackage
          v-for="(pkg, index) in packages"
          :key="index"
          v-bind="pkg"
          @select="selectPackage(pkg)"
        />
      </div>
    </div>
  </Dialog>
  <PopupNutrilRoadMap v-model="visibleNutrilRoadMap" />
  <PopupExerciseRoadMap v-model="visibleExcerciseRoadMap" />
</template>

<script setup>
import { ref, watch } from "vue";
import Dialog from "primevue/dialog";
import CardPackage from "./CardPackage.vue";
import PopupNutrilRoadMap from "./NutrilRoadMap/PopupNutrilRoadMap.vue";
import PopupExerciseRoadMap from "./ExerciseRoadMap/PopupExerciseRoadMap.vue";

const props = defineProps({
  modelValue: Boolean,
});

const emit = defineEmits(["update:modelValue", "select-package"]);

const visible = ref(props.modelValue);

// Fake data for packages
const packages = ref([
  {
    id: 1,
    title: "Lộ Trình Dinh Dưỡng Tối Ưu",
    description:
      "AI xây dựng thực đơn hàng tuần, cá nhân hóa theo nhu cầu dinh dưỡng của bạn.",
    subtitle: "Carbn & Protein",
    subtitleColorClass: "text-blue-600 dark:text-blue-400",
    price: "270.000đ",
    priceUnit: "/tháng",
    icon: "pi-shopping-cart",
    iconBgClass: "bg-blue-100 dark:bg-blue-900/30",
    iconColorClass: "text-blue-600 dark:text-blue-400",
    buttonText: "Lựa chọn",
    featured: false,
    flag: false,
  },
  {
    id: 2,
    title: "Chương Trình Tăng Trưởng Toàn Diện",
    description:
      "Kết hợp Dinh Dưỡng & Luyện Tập, Theo dõi sức khỏe và phát triển của bạn Toàn diện.",
    originalPrice: "170.000đ/tháng",
    warning: "Cần mua 2 lộ trình kia trước.",
    icon: "pi-star", // Sửa từ pi-star-fill
    iconBgClass:
      "bg-gradient-to-br from-blue-100 to-purple-100 dark:from-blue-900/30 dark:to-purple-900/30",
    iconColorClass: "text-blue-600 dark:text-blue-400",
    buttonText: "Lựa chọn",
    featured: true,
    badge: "TỐI ƯU",
    flag: true,
    price: "",
    priceUnit: "",
  },
  {
    id: 3,
    title: "Phác Đồ Luyện Tập Đột Phá",
    description:
      "AI thiết kế bài tập phù hợp với thể trạng, điều chỉnh và tối ưu hóa theo mục tiêu phóng đại.",
    subtitle: "BMI",
    subtitleColorClass: "text-purple-600 dark:text-purple-400",
    price: "30.000đ",
    priceUnit: "/tháng",
    icon: "pi-chart-line", // Sửa từ pi-bolt (vì pi-bolt không tồn tại)
    iconBgClass: "bg-purple-100 dark:bg-purple-900/30",
    iconColorClass: "text-purple-600 dark:text-purple-400",
    buttonText: "Lựa chọn",
    featured: false,
    flag: false,
  },
]);

const visibleNutrilRoadMap = ref(false);
const visibleExcerciseRoadMap = ref(false);
const selectPackage = (pkg) => {
  console.log("Selected package:", pkg);
  switch (pkg.id) {
    case 1:
      visibleNutrilRoadMap.value = true;
      visibleExcerciseRoadMap.value = false;
      break;
    case 3:
      visibleExcerciseRoadMap.value = true;
      visibleNutrilRoadMap.value = false;
      break;
    default:
      break;
  }
  emit("select-package", pkg);
};

watch(
  () => props.modelValue,
  (val) => (visible.value = val)
);
watch(visible, (val) => emit("update:modelValue", val));
</script>

<style scoped>
.hover\:shadow-3xl:hover {
  box-shadow: 0 30px 60px -15px rgba(0, 0, 0, 0.3);
}

/* Tối ưu cho màn hình 16:9 */
@media screen and (min-width: 1920px) {
  :deep(.p-dialog) {
    max-width: 1200px !important;
  }
}

@media screen and (min-width: 1440px) and (max-width: 1919px) {
  :deep(.p-dialog) {
    max-width: 1100px !important;
  }
}

@media screen and (min-width: 1024px) and (max-width: 1439px) {
  :deep(.p-dialog) {
    width: 75vw !important;
  }
}
</style>