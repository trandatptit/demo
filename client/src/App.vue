<template>
  <HomeHeader />
  <router-view :key="$route.fullPath" />
</template>

<script setup>
import HomeHeader from "./views/Header/HomeHeader.vue";
// Main App component
</script>
