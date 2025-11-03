<template>
  <Card 
    :class="[
      'card-package hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-2',
      featured ? 'border-4 border-blue-500 dark:border-blue-600 relative' : 'border-2 border-gray-200 dark:border-gray-700'
    ]"
    style="overflow: hidden"
  >
    <!-- Featured Badge -->
    <div v-if="featured && badge" class="absolute -top-4 left-1/2 transform -translate-x-1/2 z-10">
      <span class="bg-gradient-to-r from-blue-600 to-purple-600 text-white text-sm font-bold px-6 py-2 rounded-full shadow-lg">
        {{ badge }}
      </span>
    </div>

    <!-- Icon Slot -->
    <template #icon>
      <div :class="{ 'mt-2': featured }">
        <div 
          :class="[
            'w-24 h-24 rounded-full flex items-center justify-center mx-auto',
            iconBgClass
          ]"
        >
          <i :class="`pi ${icon} text-5xl ${iconColorClass}`"></i>
        </div>
      </div>
    </template>

    <!-- Title Slot -->
    <template #title>
      <h3 class="text-2xl font-bold text-gray-800 dark:text-gray-100 text-center">
        {{ title }}
      </h3>
    </template>

    <!-- Subtitle Slot -->
    <template #subtitle>
      <p class="text-gray-600 dark:text-gray-400 text-center text-base">
        {{ description }}
      </p>
      <p 
        v-if="subtitle" 
        :class="`text-center text-base font-semibold mt-2 ${subtitleColorClass}`"
      >
        {{ subtitle }}
      </p>
    </template>

    <!-- Content Slot -->
    <template #content>
      <slot name="content" :props="props">
        <!-- Original Price -->
        <div v-if="originalPrice" class="text-center mb-2">
          <p class="text-gray-400 dark:text-gray-500 line-through text-xl">
            {{ originalPrice }}
          </p>
        </div>

        <!-- Warning Message -->
        <div v-if="warning" class="text-center mb-4">
          <div class="inline-block bg-red-100 dark:bg-red-900/30 px-5 py-3 rounded-lg">
            <p class="text-red-600 dark:text-red-300 font-bold text-sm">
              {{ warning }}
            </p>
          </div>
        </div>

        <!-- Price -->
        <div v-if="!comingSoon && price" class="text-center mb-4">
          <p class="text-4xl font-bold text-gray-800 dark:text-gray-100">
            {{ price }}<span class="text-xl font-normal text-gray-600 dark:text-gray-400">{{ priceUnit }}</span>
          </p>
        </div>

        <!-- Custom Content Slot -->
        <slot name="custom-content"></slot>
      </slot>
    </template>

    <!-- Footer Slot -->
    <template #footer>
      <div class="flex gap-4 mt-1">
        <Button 
          :label="buttonText"
          :disabled="comingSoon"
          :class="[
            'w-full font-semibold py-3',
            comingSoon 
              ? 'opacity-60'
              : ''
          ]"
          :severity="comingSoon ? 'secondary' : 'primary'"
          @click="!comingSoon && handleClick()"
        />
      </div>
    </template>
  </Card>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';
import Card from 'primevue/card';
import Button from 'primevue/button';

const props = defineProps({
  // Package info
  title: {
    type: String,
    required: true
  },
  description: {
    type: String,
    required: true
  },
  subtitle: {
    type: String,
    default: ''
  },
  subtitleColorClass: {
    type: String,
    default: 'text-gray-700 dark:text-gray-300'
  },
  
  // Icon
  icon: {
    type: String,
    default: 'pi-box'
  },
  iconBgClass: {
    type: String,
    default: 'bg-blue-100 dark:bg-blue-900/30'
  },
  iconColorClass: {
    type: String,
    default: 'text-blue-600 dark:text-blue-400'
  },
  
  // Featured
  featured: {
    type: Boolean,
    default: false
  },
  badge: {
    type: String,
    default: ''
  },
  
  // Pricing
  price: {
    type: String,
    required: true
  },
  priceUnit: {
    type: String,
    default: '/tháng'
  },
  originalPrice: {
    type: String,
    default: ''
  },
  
  // Warning
  warning: {
    type: String,
    default: ''
  },
  
  // Button
  buttonText: {
    type: String,
    default: 'Chọn gói'
  },
  comingSoon: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['select']);

const handleClick = () => {
  emit('select', props);
};
</script>

<style scoped>
.card-package {
  border-radius: 12px;
}

:deep(.p-card) {
  height: 100%;
  display: flex;
  flex-direction: column;
}

:deep(.p-card-body) {
  display: flex;
  flex-direction: column;
  flex: 1;
  padding: 2rem;
}

:deep(.p-card-icon) {
  margin-bottom: 1.5rem;
}

:deep(.p-card-title) {
  margin-bottom: 1rem;
}

:deep(.p-card-subtitle) {
  margin-bottom: 1.5rem;
}

:deep(.p-card-content) {
  flex: 1;
  padding-top: 0;
}

:deep(.p-card-footer) {
  padding-top: 1rem;
}

:deep(.p-button) {
  border-radius: 0.75rem;
  font-weight: 600;
}

:deep(.p-button:not(:disabled):hover) {
  transform: translateY(-2px);
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
}
</style>