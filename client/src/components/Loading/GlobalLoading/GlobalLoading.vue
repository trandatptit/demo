<template>
  <div
    v-if="show"
    class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50 backdrop-blur-sm"
  >
    <div class="relative">
      <!-- Outer ring -->
      <div
        class="w-16 h-16 rounded-full border-4 border-t-primary-600 border-r-primary-600 border-b-primary-200 border-l-primary-200 animate-spin"
      ></div>

      <!-- Inner ring -->
      <div
        class="absolute top-1/2 left-1/2 w-8 h-8 -mt-4 -ml-4 rounded-full border-4 border-t-primary-400 border-r-primary-400 border-b-transparent border-l-transparent animate-spin-reverse"
      ></div>

      <!-- Loading text -->
      <div class="mt-4 text-center">
        <span class="text-white font-medium animate-pulse">
          {{ text || "Loading..." }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  show: {
    type: Boolean,
    default: false,
  },
  text: {
    type: String,
    default: "",
  },
});
</script>

<style scoped>
@keyframes spin-reverse {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(-360deg);
  }
}

.animate-spin-reverse {
  animation: spin-reverse 1s linear infinite;
}
</style>
