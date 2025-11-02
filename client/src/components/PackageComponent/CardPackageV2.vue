<template>
  <Card
    class="package-card shadow-lg hover:shadow-xl transition-all duration-300"
    :class="{ 'popular-card': isPopular }"
  >
    <template #header>
      <div v-if="isPopular" class="popular-badge">
        <span class="text-white text-sm font-bold">LỰA CHỌN TỐT NHẤT</span>
      </div>
    </template>
    <template #title>
      <div class="text-center">
        <h3 class="text-xl font-bold text-gray-800 mb-2">{{ title }}</h3>
      </div>
    </template>
    <template #content>
      <div class="text-center mb-6">
        <div class="justify-center gap-2">
          <span class="text-4xl font-bold text-blue-900">{{
            formatPrice(price)
          }}</span>
          <span class="text-gray-500 line-through text-lg">{{
            formatPrice(originalPrice)
          }}</span>
        </div>
      </div>

      <!-- Features List -->
      <div class="space-y-3 mb-6">
        <div
          v-for="(feature, index) in features"
          :key="index"
          class="flex items-start gap-3"
        >
          <i class="pi pi-check-circle text-blue-800/55 mt-1 shrink-0"></i>
          <span class="text-gray-700 text-sm">{{ feature }}</span>
        </div>
      </div>
    </template>
    <template #footer>
      <Button
        :unstyled="false"
        :label="buttonLabel"
        :class="buttonClass"
        class="w-full font-bold"
        @click="handleSelectPackage"
      />
    </template>
  </Card>
</template>

<script setup>
import { computed } from "vue";
import Card from "primevue/card";
import Button from "primevue/button";

const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  price: {
    type: Number,
    required: true,
  },
  originalPrice: {
    type: Number,
    required: true,
  },
  features: {
    type: Array,
    required: true,
  },
  isPopular: {
    type: Boolean,
    default: false,
  },
  buttonLabel: {
    type: String,
    default: "Chọn Gói Này",
  },
});

const emit = defineEmits(["select-package"]);

const buttonClass = computed(() => {
  return props.isPopular
    ? "bg-blue-600 hover:bg-blue-700 text-white border-0"
    : "bg-white hover:bg-gray-50 text-blue-600 border-2 border-blue-600";
});

const formatPrice = (price) => {
  return (
    new Intl.NumberFormat("vi-VN", {
      style: "decimal",
    }).format(price) + "đ"
  );
};

const handleSelectPackage = () => {
  emit("select-package", {
    title: props.title,
    price: props.price,
  });
};
</script>

<style scoped>
.package-card {
  border-radius: 12px;
  border: 2px solid #e5e7eb;
  transition: all 0.3s ease;
  position: relative;
  height: 100%;
}

.package-card:hover {
  transform: translateY(-8px);
  border-color: #3b82f6;
}

.popular-card {
  border-color: #2563eb;
  border-width: 3px;
}

.popular-badge {
  background: linear-gradient(135deg, #2563eb 0%, #1d4ed8 100%);
  padding: 8px 16px;
  border-radius: 8px 8px 0 0;
  text-align: center;
}

:deep(.p-card-body) {
  padding: 1.5rem;
}

:deep(.p-card-content) {
  padding: 0;
}

:deep(.p-card-footer) {
  padding: 0;
  padding-top: 1rem;
}
</style>
