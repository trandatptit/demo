<template>
  <main class="mx-auto px-4 lg:px-8 py-12 lg:py-16">
    <Toast />
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-8 lg:gap-12">
      <!-- Left Section - Hero Content (col-span-12 on mobile, col-span-7 on desktop) -->
      <div class="col-span-12 lg:col-span-7 flex flex-col justify-center">
        <div class="">
          <h1
            class="text-4xl sm:text-5xl lg:text-6xl font-bold text-blue-900 leading-tight"
          >
            CHẠM TỚI<br />ĐỊNH CAO<br />CỦA CHÍNH MÌNH
          </h1>
          <p
            class="text-base sm:text-lg text-gray-600 leading-relaxed max-w-xl"
          >
            Nền tảng AI độc quyền từ Everest Pharma giúp bạn dự đoán chính xác
            và với ưu hóa tiềm năng tăng trưởng chiều cao. NO LIMITS!
          </p>
        </div>
      </div>

      <Stepper class="col-span-5" v-model:value="stepperValue">
        <StepList>
          <Step disabled :value="1"></Step>
          <Step disabled :value="2"></Step>
          <Step disabled :value="3"></Step>
        </StepList>
        <StepPanels>
          <StepPanel :value="1" v-slot="{ activateCallback }">
            <!-- Form Content -->
            <div class="space-y-6 p-6 bg-white rounded-md">
              <div class="title-popup col-span-12 text-center text-blue-900">
                Thông tin cá nhân
              </div>
              <!-- Họ Tên -->
              <div class="col-span-12">
                <IconField>
                  <InputIcon class="pi pi-user" />
                  <InputText
                    v-model="formData.name"
                    placeholder="Họ và tên"
                    class="w-full border-b-2 border-gray-300 focus:border-blue-600"
                  />
                </IconField>
              </div>

              <!-- Tuổi & Giới Tính -->
              <div class="space-y-3">
                <div
                  class="flex flex-col sm:flex-row items-start sm:items-center gap-4 sm:gap-6"
                >
                  <div class="w-full sm:flex-1">
                    <IconField>
                      <InputIcon class="pi pi-user" />
                      <InputNumber
                        v-model="formData.age"
                        placeholder="Tuổi"
                        :min="1"
                        :max="100"
                        :useGrouping="false"
                        class="w-full"
                      />
                    </IconField>
                  </div>
                  <div class="flex items-center gap-4 w-full sm:w-auto">
                    <div class="flex items-center">
                      <RadioButton
                        v-model="formData.gender"
                        name="gender"
                        :value="1"
                        input-id="male"
                      />
                      <label
                        for="male"
                        class="ml-2 text-gray-700 cursor-pointer"
                        >Nam</label
                      >
                    </div>
                    <div class="flex items-center">
                      <RadioButton
                        v-model="formData.gender"
                        name="gender"
                        :value="2"
                        input-id="female"
                      />
                      <label
                        for="female"
                        class="ml-2 text-gray-700 cursor-pointer"
                        >Nữ</label
                      >
                    </div>
                  </div>
                </div>
              </div>

              <!-- Chiều cao & Cân nặng - 12 Grid -->
              <div class="grid grid-cols-12 gap-4">
                <div class="col-span-12 sm:col-span-6">
                  <IconField>
                    <InputIcon class="pi pi-pen-to-square" />
                    <InputNumber
                      v-model="formData.height"
                      placeholder="Chiều cao (cm)"
                      :use-grouping="false"
                      class="w-full"
                    />
                  </IconField>
                </div>
                <div class="col-span-12 sm:col-span-6">
                  <IconField>
                    <InputIcon class="pi pi-pen-to-square" />
                    <InputNumber
                      v-model="formData.weight"
                      placeholder="Cân nặng (kg)"
                      :use-grouping="false"
                      class="w-full"
                    />
                  </IconField>
                </div>
              </div>

              <!-- Buttons - 12 Grid -->
              <div
                class="grid grid-cols-12 gap-4 pt-6 border-t border-gray-200"
              >
                <Button
                  type="button"
                  icon="pi pi-arrow-left"
                  label="Quay lại"
                  text
                  severity="secondary"
                  class="col-span-12 sm:col-span-6 font-medium"
                  pt:root="bg-[#d1d5db] hover:bg-teal-700 active:bg-teal-900 cursor-pointer py-2 px-4 rounded-[6px] border-0 flex items-center justify-center gap-2"
                  pt:label="text-[#94a3b8] font-bold text-lg"
                  pt:icon="text-[#94a3b8] text-xl"
                />
                <Button
                  type="submit"
                  icon="pi pi-arrow-right"
                  icon-pos="right"
                  label="Tiếp theo"
                  class="col-span-12 sm:col-span-6 bg-blue-600 text-white font-medium"
                  @click="handleSubmitStepOne(activateCallback)"
                />
              </div>
            </div>
          </StepPanel>
          <StepPanel :value="2" v-slot="{ activateCallback }">
            <!-- Form Content -->
            <div class="space-y-6 p-6 bg-white rounded-md">
              <div class="title-popup col-span-12 text-center text-blue-900">
                Thông tin gia đình
              </div>

              <!-- Chiều cao & Cân nặng - 12 Grid -->
              <div class="col-span-12 sm:col-span-6">
                <IconField>
                  <InputIcon class="pi pi-user-plus" />
                  <InputNumber
                    v-model="formData.fatherHeight"
                    placeholder="Chiều cao của bố (cm)"
                    :use-grouping="false"
                    class="w-full"
                  />
                </IconField>
              </div>
              <div class="col-span-12 sm:col-span-6">
                <IconField>
                  <InputIcon class="pi pi-user-plus" />
                  <InputNumber
                    v-model="formData.motherHeight"
                    placeholder="Chiều cao của mẹ (cm)"
                    :use-grouping="false"
                    class="w-full"
                  />
                </IconField>
              </div>

              <div class="w-full sm:flex-1">
                <IconField>
                  <InputIcon class="pi pi-user" />
                  <InputNumber
                    v-model="formData.pubertyAge"
                    placeholder="Tuổi dậy thì (nếu có)"
                    :min="8"
                    :max="18"
                    :useGrouping="false"
                    class="w-full"
                  />
                </IconField>
              </div>

              <!-- Buttons - 12 Grid -->
              <div
                class="grid grid-cols-12 gap-4 pt-6 border-t border-gray-200"
              >
                <Button
                  type="button"
                  icon="pi pi-arrow-left"
                  label="Quay lại"
                  text
                  severity="secondary"
                  class="col-span-12 sm:col-span-6 font-medium"
                  pt:root="bg-[#d1d5db] hover:bg-teal-700 active:bg-teal-900 cursor-pointer py-2 px-4 rounded-[6px] border-0 flex items-center justify-center gap-2"
                  pt:label="text-[#94a3b8] font-bold text-lg"
                  pt:icon="text-[#94a3b8] text-xl"
                  @click="activateCallback(1)"
                />
                <Button
                  type="submit"
                  icon="pi pi-arrow-right"
                  icon-pos="right"
                  label="Tiếp theo"
                  class="col-span-12 sm:col-span-6 bg-blue-600 text-white font-medium"
                  @click="handleSubmitStepTwo(activateCallback)"
                />
              </div>
            </div>
          </StepPanel>
          <StepPanel :value="3" v-slot="{ activateCallback }">
            <!-- Form Content -->
            <div class="space-y-6 p-6 bg-white rounded-md">
              <div class="title-popup col-span-12 text-center text-blue-900">
                Lối sống & sinh hoạt
              </div>

              <!-- Chiều cao & Cân nặng - 12 Grid -->
              <div class="col-span-12 sm:col-span-6">
                <IconField>
                  <InputIcon class="pi pi-hashtag" />
                  <InputNumber
                    v-model="formData.mealsPerDay"
                    placeholder="Số bữa ăn / ngày"
                    class="w-full"
                    showButtons
                    :min="0"
                    :max="100"
                    fluid
                  />
                </IconField>
              </div>

              <div class="w-full sm:flex-1">
                <IconField>
                  <InputIcon class="pi pi-calendar" />
                  <DatePicker
                    id="datepicker-timeonly"
                    placeholder="Giờ đi ngủ trung bình"
                    v-model="formData.timeSleep"
                    timeOnly
                    fluid
                  />
                </IconField>
              </div>

              <div class="col-span-12 sm:col-span-6">
                <IconField>
                  <InputIcon class="pi pi-user-plus" />
                  <InputNumber
                    v-model="formData.exerciseFrequency"
                    placeholder="Buổi tập thể dục / tuần"
                    :use-grouping="false"
                    :min="0"
                    class="w-full"
                  />
                </IconField>
              </div>

              <!-- Buttons - 12 Grid -->
              <div
                class="grid grid-cols-12 gap-4 pt-6 border-t border-gray-200"
              >
                <Button
                  type="button"
                  icon="pi pi-arrow-left"
                  label="Quay lại"
                  text
                  severity="secondary"
                  class="col-span-12 sm:col-span-6 font-medium"
                  pt:root="bg-[#d1d5db] hover:bg-teal-700 active:bg-teal-900 cursor-pointer py-2 px-4 rounded-[6px] border-0 flex items-center justify-center gap-2"
                  pt:label="text-[#94a3b8] font-bold text-lg"
                  pt:icon="text-[#94a3b8] text-xl"
                  @click="activateCallback(2)"
                />
                <Button
                  type="submit"
                  icon="pi pi-arrow-right"
                  icon-pos="right"
                  label="Tiếp theo"
                  class="col-span-12 sm:col-span-6 bg-blue-600 text-white font-medium"
                  @click="handleSubmitStepThree(activateCallback)"
                />
              </div>
            </div>
          </StepPanel>
        </StepPanels>
      </Stepper>

      <!-- Right Section - Form Card (col-span-12 on mobile, col-span-5 on desktop) -->
      <!-- <div class="col-span-12 lg:col-span-5"></div> -->
      <height-growth-roadmap class="col-span-12" />
      <package-month class="col-span-12" />
      <expert-and-partner class="col-span-12" />
      <home-product class="col-span-12" />
      <home-tool class="col-span-12" />
    </div>
  </main>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import Card from "primevue/card";
import InputText from "primevue/inputtext";
import InputNumber from "primevue/inputnumber";
import RadioButton from "primevue/radiobutton";
import IconField from "primevue/iconfield";
import InputIcon from "primevue/inputicon";
import DatePicker from "primevue/datepicker";
import Stepper from "primevue/stepper";
import StepList from "primevue/steplist";
import Step from "primevue/step";
import StepPanels from "primevue/steppanels";
import StepPanel from "primevue/steppanel";
import { useToast } from "primevue/usetoast";
import HeightGrowthRoadmap from "@/components/HeigthGrowthRoadmap/HeightGrowthRoadmap.vue";
import PackageMonth from "@/components/PackageComponent/PackageMonth.vue";
import ExpertAndPartner from "@/components/ExpertAndPartnerComponent/ExpertAndPartner.vue";
import HomeProduct from "./HomeProduct.vue";
import HomeTool from "./HomeTool.vue";
import { useUserInfoStore } from "@/stores/userInfo.js";

const router = useRouter();
const toast = useToast();
const userInfoStore = useUserInfoStore();

const formData = ref({
  name: "",
  age: null,
  pubertyAge: null,
  gender: null,
  height: null,
  weight: null,
  birthday: null,
  pubertyDay: null,
  fatherHeight: null,
  motherHeight: null,
  mealsPerDay: null,
  timeSleep: null,
  exerciseFrequency: null,
});

const stepperValue = ref(1);
const valueFullFormRef = ref({
  step1: false,
  step2: false,
  step3: false,
});

const handleSubmitStepOne = (activateCallback) => {
  if (
    !formData.value.name ||
    !formData.value.age ||
    !formData.value.height ||
    !formData.value.weight
  ) {
    toast.add({
      severity: "error",
      summary: "Cảnh báo",
      detail: "Vui lòng điền đầy đủ thông tin!",
      //   group: "br",
      life: 3000,
    });
    return;
  }
  console.log("Form submitted:", formData.value);
  valueFullFormRef.value.step1 = true;
  activateCallback(2);
};

const handleSubmitStepTwo = (activateCallback) => {
  if (!formData.value.fatherHeight || !formData.value.motherHeight) {
    toast.add({
      severity: "error",
      summary: "Cảnh báo",
      detail: "Vui lòng điền đầy đủ thông tin!",
      //   group: "br",
      life: 3000,
    });
    return;
  }
  console.log("Form submitted:", formData.value);
  valueFullFormRef.value.step2 = true;
  activateCallback(3);
};

const handleSubmitStepThree = (activateCallback) => {
  if (
    !formData.value.mealsPerDay ||
    !formData.value.timeSleep ||
    !formData.value.exerciseFrequency
  ) {
    toast.add({
      severity: "error",
      summary: "Cảnh báo",
      detail: "Vui lòng điền đầy đủ thông tin!",
      //   group: "br",
      life: 3000,
    });
    return;
  }
  console.log("Form submitted:", formData.value);
  valueFullFormRef.value.step3 = true;
  userInfoStore.setUserInfo(formData.value);
  router.push({ name: "HeightPrediction" });
};

const handleBack = () => {
  router.push({ name: "Home" });
};
</script>

<style scoped>
:deep(.p-card) {
  border-radius: 12px;
}

:deep(.p-inputtext),
:deep(.p-inputnumber-input) {
  border: none;
  border-bottom: 2px solid #d1d5db;
  border-radius: 0;
  padding: 8px 0;
}

:deep(.p-inputtext:focus),
:deep(.p-inputnumber-input:focus) {
  border-bottom-color: #2563eb;
  box-shadow: none;
}

:deep(.p-button-text) {
  color: #9ca3af;
}

:deep(.p-button-text:hover) {
  color: #2563eb;
}

.title-popup {
  margin-bottom: 15px;
  font-weight: 800;
  font-size: 18px;
}
</style>
