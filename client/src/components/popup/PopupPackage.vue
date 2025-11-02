<template>
  <Dialog 
    v-model:visible="visible" 
    modal 
    :closable="false"
    :style="{ width: '85vw', maxWidth: '1300px' }"
    :breakpoints="{ '1199px': '90vw', '575px': '95vw' }"
    :pt="{
      root: { class: 'rounded-3xl shadow-2xl border border-gray-200 dark:border-gray-700' },
      header: { class: '!hidden' },
      content: { class: 'bg-gradient-to-br from-blue-50 via-purple-50 to-pink-50 dark:from-gray-800 dark:via-gray-900 dark:to-gray-800 !p-0 rounded-3xl relative overflow-visible' },
      mask: { class: 'backdrop-blur-sm bg-black/40 dark:bg-black/60' }
    }"
  >
    <div class="p-10 pt-15">
      <!-- Header Section -->
      <div class="text-center mb-24 relative">
        <!-- Custom Close Button -->
        <button 
          @click="visible = false"
          class="absolute -top-8 right-0 w-10 h-10 rounded-full bg-white dark:bg-gray-700 shadow-xl hover:bg-gray-100 dark:hover:bg-gray-600 transition-all duration-200 border-2 border-gray-300 dark:border-gray-600 flex items-center justify-center z-50"
          type="button"
        >
          <i class="pi pi-times text-gray-600 dark:text-gray-300"></i>
        </button>

        <h2 class="text-4xl md:text-5xl font-bold text-blue-900 dark:text-blue-100 mb-4">
          Mở Khóa Lộ Trình Tăng Trưởng
        </h2>
        <p class="text-gray-600 dark:text-gray-400 text-lg mx-auto text-center">
          Chọn một chương trình được thiết kế bởi AI để tối ưu hóa tiềm năng phát triển của bạn.
        </p>
      </div>

      <!-- Pricing Cards Grid -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mt-8 pt-10">
        <!-- Dynamic Package Cards -->
        <div 
          v-for="(pkg, index) in packages" 
          :key="index"
          :class="[
            'bg-white dark:bg-gray-800 rounded-2xl shadow-xl p-8 hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2 flex flex-col',
            pkg.featured ? 'border-4 border-blue-500 dark:border-blue-600 relative' : 'border-2 border-gray-200 dark:border-gray-700'
          ]"
        >
          <!-- Featured Badge -->
          <div v-if="pkg.featured" class="absolute -top-4 left-1/2 transform -translate-x-1/2">
            <span class="bg-gradient-to-r from-blue-600 to-purple-600 text-white text-sm font-bold px-6 py-2 rounded-full shadow-lg">
              {{ pkg.badge }}
            </span>
          </div>

          <!-- Icon -->
          <div class="flex justify-center mb-6" :class="{ 'mt-2': pkg.featured }">
            <div 
              :class="[
                'w-24 h-24 rounded-full flex items-center justify-center',
                pkg.iconBgClass
              ]"
            >
              <i :class="`pi ${pkg.icon} text-5xl ${pkg.iconColorClass}`"></i>
            </div>
          </div>

          <!-- Title -->
          <h3 class="text-2xl font-bold text-gray-800 dark:text-gray-100 text-center mb-4">
            {{ pkg.title }}
          </h3>

          <!-- Description -->
          <div class="flex-grow">
            <p class="text-gray-600 dark:text-gray-400 text-center text-base mb-3">
              {{ pkg.description }}
            </p>
            <p 
              v-if="pkg.subtitle" 
              :class="`text-center text-base font-semibold mb-8 ${pkg.subtitleColorClass}`"
            >
              {{ pkg.subtitle }}
            </p>
          </div>

          <!-- Original Price (for featured) -->
          <div v-if="pkg.originalPrice" class="text-center mb-2">
            <p class="text-gray-400 dark:text-gray-500 line-through text-xl">
              {{ pkg.originalPrice }}
            </p>
          </div>

          <!-- Warning Message (for featured) -->
          <div v-if="pkg.warning" class="text-center mb-6">
            <div class="inline-block bg-red-100 dark:bg-red-900/30 px-5 py-3 rounded-lg">
              <p class="text-red-600 dark:text-red-300 font-bold text-sm">
                {{ pkg.warning }}
              </p>
            </div>
          </div>

          <!-- Price -->
          <div v-if="!pkg.comingSoon" class="text-center mb-6 pb-5">
            <p class="text-4xl font-bold text-gray-800 dark:text-gray-100">
              {{ pkg.price }}<span class="text-xl font-normal text-gray-600 dark:text-gray-400">{{ pkg.priceUnit }}</span>
            </p>
          </div>

          <!-- Button -->
          <button 
            :disabled="pkg.comingSoon"
            :class="[
              'w-full font-semibold py-4 rounded-xl transition-all duration-200 text-lg',
              pkg.comingSoon 
                ? 'bg-gray-300 dark:bg-gray-600 text-gray-500 dark:text-gray-400 cursor-not-allowed opacity-60'
                : 'bg-gradient-to-r from-blue-600 to-blue-700 dark:from-blue-700 dark:to-blue-800 hover:from-blue-700 hover:to-blue-800 text-white shadow-lg hover:shadow-xl'
            ]"
            @click="!pkg.comingSoon && selectPackage(pkg)"
          >
            {{ pkg.buttonText }}
          </button>
        </div>
      </div>
    </div>
  </Dialog>
</template>

<script setup>
import { ref, watch } from "vue";
import Dialog from "primevue/dialog";

const props = defineProps({
  modelValue: Boolean
});

const emit = defineEmits(["update:modelValue", "select-package"]);

const visible = ref(props.modelValue);

// Fake data for packages
const packages = ref([
  {
    id: 1,
    title: "Lộ Trình Dinh Dưỡng Tối Ưu",
    description: "AI xây dựng thực đơn hàng tuần, cá nhân hóa theo nhu cầu dinh dưỡng của bạn.",
    subtitle: "Carbn & Protein",
    subtitleColorClass: "text-blue-600 dark:text-blue-400",
    price: "270.000đ",
    priceUnit: "/tháng",
    icon: "pi-heart",
    iconBgClass: "bg-blue-100 dark:bg-blue-900/30",
    iconColorClass: "text-blue-600 dark:text-blue-400",
    buttonText: "Lựa chọn",
    featured: false,
    comingSoon: false
  },
  {
    id: 2,
    title: "Chương Trình Tăng Trưởng Toàn Diện",
    description: "Kết hợp Dinh Dưỡng & Luyện Tập, Theo dối sức khỏe và phát triển của bạn Toàn diện.",
    originalPrice: "170.000đ/tháng",
    warning: "Cần mua 2 lộ trình kia trước.",
    icon: "pi-trophy",
    iconBgClass: "bg-gradient-to-br from-blue-100 to-purple-100 dark:from-blue-900/30 dark:to-purple-900/30",
    iconColorClass: "text-blue-600 dark:text-blue-400",
    buttonText: "Lựa chọn",
    featured: true,
    badge: "TỐI ƯU",
    comingSoon: true
  },
  {
    id: 3,
    title: "Phác Đồ Luyện Tập Đột Phá",
    description: "AI thiết kế bài tập phù hợp với thể trạng, điều chỉnh và tối ưu hóa theo mục tiêu phóng đại.",
    subtitle: "BMI",
    subtitleColorClass: "text-purple-600 dark:text-purple-400",
    price: "30.000đ",
    priceUnit: "/tháng",
    icon: "pi-bolt",
    iconBgClass: "bg-purple-100 dark:bg-purple-900/30",
    iconColorClass: "text-purple-600 dark:text-purple-400",
    buttonText: "Lựa chọn",
    featured: false,
    comingSoon: false
  }
]);

const selectPackage = (pkg) => {
  emit("select-package", pkg);
};

watch(() => props.modelValue, (val) => (visible.value = val));
watch(visible, (val) => emit("update:modelValue", val));
</script>

<style scoped>
.hover\:shadow-3xl:hover {
  box-shadow: 0 30px 60px -15px rgba(0, 0, 0, 0.3);
}
</style>