<template>
  <div
    class="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex flex-col"
  >
    <!-- Header -->
    <HomeHeader />

    <!-- Body -->
    <HomeBody class="flex-1" />

    <!-- Footer -->
    <HomeFooter />
  </div>
</template>

<script setup>
import HomeHeader from "./components/HomeHeader.vue";
import HomeBody from "./components/HomeBody.vue";
import HomeFooter from "./components/HomeFooter.vue";
</script>

<style scoped>
</style>
