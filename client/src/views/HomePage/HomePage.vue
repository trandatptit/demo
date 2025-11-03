<template>
  <div
    class="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex flex-col"
  >
    <!-- Body -->
    <HomeBody class="flex-1" />

    <!-- Footer -->
    <HomeFooter />
  </div>
</template>

<script setup>
import HomeBody from "./components/HomeBody.vue";
import HomeFooter from "./components/HomeFooter.vue";
</script>

<style scoped>
</style>
