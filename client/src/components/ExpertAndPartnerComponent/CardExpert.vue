<template>
  <Card
    class="expert-card shadow-lg hover:shadow-xl transition-all duration-300"
  >
    <template #header>
      <div class="relative overflow-hidden rounded-t-lg">
        <img
          :src="image"
          :alt="name"
          class="w-full h-64 object-cover transition-transform duration-300 hover:scale-110"
        />
        <div
          class="absolute inset-0 bg-linear-to-t from-black/60 to-transparent"
        ></div>
        <div class="absolute bottom-4 left-4 text-white">
          <h3 class="text-xl font-bold">{{ name }}</h3>
          <p class="text-sm opacity-90">{{ title }}</p>
        </div>
      </div>
    </template>
    <template #content>
      <div class="space-y-3">
        <p class="text-gray-700 text-sm leading-relaxed">
          {{ description }}
        </p>

        <!-- Stats or additional info if needed -->
        <div
          v-if="stats"
          class="flex items-center gap-4 pt-3 border-t border-gray-200"
        >
          <div
            v-for="(stat, index) in stats"
            :key="index"
            class="flex items-center gap-2"
          >
            <i :class="stat.icon" class="text-blue-600"></i>
            <span class="text-sm text-gray-600">{{ stat.value }}</span>
          </div>
        </div>
      </div>
    </template>
    <template #footer>
      <div class="flex items-center justify-between pt-2">
        <Button
          label="Xem Chi Tiết"
          text
          class="text-blue-600 font-semibold"
          icon="pi pi-arrow-right"
          iconPos="right"
          @click="handleViewDetail"
        />
      </div>
    </template>
  </Card>
</template>

<script setup>
import Card from "primevue/card";
import Button from "primevue/button";

const props = defineProps({
  name: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
  image: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  stats: {
    type: Array,
    default: null,
  },
});

const emit = defineEmits(["view-detail"]);

const handleViewDetail = () => {
  emit("view-detail", {
    name: props.name,
    title: props.title,
  });
};
</script>

<style scoped>
.expert-card {
  border-radius: 12px;
  border: 1px solid #e5e7eb;
  transition: all 0.3s ease;
  height: 100%;
  overflow: hidden;
}

.expert-card:hover {
  transform: translateY(-8px);
  border-color: #3b82f6;
}

:deep(.p-card-body) {
  padding: 0;
}

:deep(.p-card-content) {
  padding: 1.5rem;
}

:deep(.p-card-footer) {
  padding: 0 1.5rem 1.5rem 1.5rem;
}
</style>
