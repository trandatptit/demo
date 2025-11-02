<template>
  <div class="min-h-screen flex flex-col items-center justify-center gap-4">
    <Button label="Open PopupPackage" @click="showPopup = true" />

    <!-- PopupPackage -->
    <PopupPackage v-model="showPopup" />

    <Button label="Open PopupNatrilRoadmap" @click="showNatriRoadmap = true" />

    <!-- PopupNatriRoadmap -->
    <PopupNatriRoadmap v-model="showNatriRoadmap" />
  </div>
</template>

<script setup>
import { ref } from "vue";
import Button from "primevue/button";
import PopupPackage from "../components/popup/PopupPackage.vue";
import PopupNatriRoadmap from "../components/popup/PopupNatrilRoadmap.vue";

const showPopup = ref(false);
const showNatriRoadmap = ref(false);
</script>
