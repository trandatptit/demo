<template>
  <div class="min-h-screen flex flex-col items-center justify-center gap-4">
    <Button label="Open PopupPackage" @click="showPopup = true" />

    <!-- PopupPackage -->
    <PopupPackage v-model="showPopup" />

    <Button label="Open PopupNatrilRoadmap" @click="showNatriRoadmap = true" />

    <!-- PopupNatriRoadmap -->
    <PopupNatriRoadmap v-model="showNatriRoadmap" />

    <Button label="View HomeProduct" @click="showHomeProduct = true" />

    <!-- HomeProduct Section -->
    <div v-if="showHomeProduct" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button 
          icon="pi pi-times" 
          text 
          rounded 
          severity="danger"
          @click="showHomeProduct = false" 
        />
      </div>
      <HomeProduct />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import Button from "primevue/button";
import PopupPackage from "../components/popup/PopupPackage.vue";
import PopupNatriRoadmap from "../components/popup/PopupNatrilRoadmap.vue";
import HomeProduct from "./HomePage/components/HomeProduct.vue";

const showPopup = ref(false);
const showNatriRoadmap = ref(false);
const showHomeProduct = ref(false);
</script>