<template>
  <div class="min-h-screen flex flex-col items-center justify-center gap-4">
    <Button label="Open PopupPackage" @click="showPopup = true" />

    <!-- PopupPackage -->
    <PopupPackage v-model="showPopup" />

    <Button label="Open PopupNatrilRoadmap" @click="showNatriRoadmap = true" />

    <!-- PopupNatriRoadmap -->
    <PopupNatriRoadmap v-model="showNatriRoadmap" />

    <Button label="View HomeProduct" @click="showHomeProduct = true" />

    <Button label="View HomeTool" @click="showHomeTool = true" />

    <Button label="View Feature30k" @click="openFeature30k" />

    <Button label="View Feature30kFinal" @click="openFeature30kFinal" />

    <!-- HomeProduct Section -->
    <div v-if="showHomeProduct" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button icon="pi pi-times" text rounded severity="danger" @click="showHomeProduct = false" />
      </div>
      <HomeProduct />
    </div>

    <!-- HomeTool Section -->
    <div v-if="showHomeTool" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button icon="pi pi-times" text rounded severity="danger" @click="showHomeTool = false" />
      </div>
      <HomeTool />
    </div>

    <!-- Feature30k Section - Truyền props -->
    <div v-if="showFeature30k" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button icon="pi pi-times" text rounded severity="danger" @click="closeFeature30k" />
      </div>
      <Feature30k 
        :userId="customUserId"
        :currentHeight="customCurrentHeight"
        :inheritedHeight="customInheritedHeight"
        :potentialHeight="customPotentialHeight"
      />
    </div>

    <!-- Feature30kFinal Section - Truyền props -->
    <div v-if="showFeature30kFinal" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button icon="pi pi-times" text rounded severity="danger" @click="closeFeature30kFinal" />
      </div>
      <Feature30kFinal 
        :userId="finalUserId"
        :ageData="finalAgeData"
        :aiRecommendation="aiRecommendationData"
        :inspirationMessage="inspirationMessageData"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import Button from "primevue/button";
import PopupPackage from "../components/popup/PopupPackage.vue";
import PopupNatriRoadmap from "../components/popup/PopupNatrilRoadmap.vue";
import HomeProduct from "./HomePage/components/HomeProduct.vue";
import HomeTool from "./HomePage/components/HomeTool.vue";
import Feature30k from "./Feature30k/Feature30k.vue";
import Feature30kFinal from "./Feature30k/Feature30kFinal.vue";

const showPopup = ref(false);
const showNatriRoadmap = ref(false);
const showHomeProduct = ref(false);
const showHomeTool = ref(false);
const showFeature30k = ref(false);
const showFeature30kFinal = ref(false);

// Custom data for Feature30k
const customUserId = ref('User123');
const customCurrentHeight = ref(165);
const customInheritedHeight = ref(169);
const customPotentialHeight = ref(172);

// Custom data for Feature30kFinal
const finalUserId = ref('12121');
const finalAgeData = ref([
  { age: '16t', height: 163 },
  { age: '17t', height: 168 },
  { age: '18t', height: 171, locked: true },
  { age: '19t', height: 175 },
  { age: '20t', height: 179 }
]);

// AI Recommendation data
const aiRecommendationData = ref(
  'Nam, 17 tuổi - Nếu duy trì ngủ trước 10 giờ và bổ sung 40 Canxi, bạn có thể đạt 179.0cm 😊'
)



// Open Feature30k with custom data
const openFeature30k = () => {
  showFeature30k.value = true;
};

// Close Feature30k
const closeFeature30k = () => {
  showFeature30k.value = false;
};

// Open Feature30kFinal with custom data
const openFeature30kFinal = () => {
  showFeature30kFinal.value = true;
};

// Close Feature30kFinal
const closeFeature30kFinal = () => {
  showFeature30kFinal.value = false;
};
</script>