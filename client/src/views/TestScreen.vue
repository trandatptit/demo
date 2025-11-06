<template>
  <div class="min-h-screen flex flex-col items-center justify-center gap-4">
    <nutril-road-map-schedule />
  </div>
</template>

<script setup>
import { ref } from "vue";
import NutrilRoadMapSchedule from "@/screen/PopupNutrilRoadMap_PhaseTwo/NutrilRoadMapSchedule.vue";

const showPopup = ref(false);
const showNatriRoadmap = ref(false);
const showHomeProduct = ref(false);
const showHomeTool = ref(false);
</script>