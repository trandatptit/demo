<template>
  <main class="mx-auto px-4 lg:px-8 py-12 lg:py-16">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-8 lg:gap-12">
      <!-- Left Section - Hero Content (col-span-12 on mobile, col-span-7 on desktop) -->
      <div class="col-span-12 lg:col-span-7 flex flex-col justify-center">
        <div class="space-y-6">
          <h1
            class="text-4xl sm:text-5xl lg:text-6xl font-bold text-blue-900 leading-tight"
          >
            CHẠM TỚI<br />ĐỊNH CAO<br />CỦA CHÍNH MÌNH
          </h1>
          <p
            class="text-base sm:text-lg text-gray-600 leading-relaxed max-w-xl"
          >
            Nền tảng AI độc quyền từ Everest Pharmo giúp bạn dự đoán chính xác
            và với ưu hóa tiềm năng tăng trưởng chiều cao. NO LIMITS!
          </p>
          <!-- Optional: Add CTA Button -->
          <div class="pt-4">
            <Button
              label="Khám Phá Ngay"
              icon="pi pi-arrow-right"
              icon-pos="right"
              class="bg-blue-600 text-white font-medium"
            />
          </div>
        </div>
      </div>

      <!-- Right Section - Form Card (col-span-12 on mobile, col-span-5 on desktop) -->
      <div class="col-span-12 lg:col-span-5">
        <Card class="shadow-2xl h-full">
          <!-- Form Header -->
          <template #header>
            <div class="border-b-4 border-blue-600 pb-4">
              <h2 class="text-2xl font-bold text-gray-800">
                Thông Tin Cá Nhân
              </h2>
            </div>
          </template>

          <!-- Form Content -->
          <form @submit.prevent="handleSubmit" class="space-y-6 p-6">
            <!-- Họ Tên -->
            <div class="col-span-12">
              <label class="flex items-center text-gray-700 font-medium mb-2">
                <i class="pi pi-user text-lg mr-2"></i>
                <span>Họ tên</span>
              </label>
              <InputText
                v-model="formData.name"
                placeholder="Nhập họ tên của bạn"
                class="w-full border-b-2 border-gray-300 focus:border-blue-600"
              />
            </div>

            <!-- Tuổi & Giới Tính -->
            <div class="space-y-3">
              <label class="flex items-center text-gray-700 font-medium mb-2">
                <i class="pi pi-calendar text-lg mr-2"></i>
                <span>Tuổi</span>
              </label>
              <div
                class="flex flex-col sm:flex-row items-start sm:items-center gap-4 sm:gap-6"
              >
                <div class="w-full sm:flex-1">
                  <InputNumber
                    v-model="formData.age"
                    placeholder="Tuổi"
                    :use-grouping="false"
                    class="w-full"
                  />
                </div>
                <div class="flex items-center gap-4 w-full sm:w-auto">
                  <div class="flex items-center">
                    <RadioButton
                      v-model="formData.gender"
                      name="gender"
                      value="male"
                      input-id="male"
                    />
                    <label for="male" class="ml-2 text-gray-700 cursor-pointer"
                      >Nam</label
                    >
                  </div>
                  <div class="flex items-center">
                    <RadioButton
                      v-model="formData.gender"
                      name="gender"
                      value="female"
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
                <label class="flex items-center text-gray-700 font-medium mb-2">
                  <i class="pi pi-arrow-up text-lg mr-2"></i>
                  <span>Chiều cao (cm)</span>
                </label>
                <InputNumber
                  v-model="formData.height"
                  placeholder="cm"
                  :use-grouping="false"
                  class="w-full"
                />
              </div>
              <div class="col-span-12 sm:col-span-6">
                <label class="flex items-center text-gray-700 font-medium mb-2">
                  <i class="pi pi-box text-lg mr-2"></i>
                  <span>Cân nặng (kg)</span>
                </label>
                <InputNumber
                  v-model="formData.weight"
                  placeholder="kg"
                  :use-grouping="false"
                  class="w-full"
                />
              </div>
            </div>

            <!-- Buttons - 12 Grid -->
            <div class="grid grid-cols-12 gap-4 pt-6 border-t border-gray-200">
              <Button
                type="button"
                @click="handleBack"
                icon="pi pi-arrow-left"
                label="Quay lại"
                text
                severity="secondary"
                class="col-span-12 sm:col-span-6 font-medium"
              />
              <Button
                type="submit"
                icon="pi pi-arrow-right"
                icon-pos="right"
                label="Tiếp theo"
                class="col-span-12 sm:col-span-6 bg-blue-600 text-white font-medium"
              />
            </div>
          </form>
        </Card>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import Button from "primevue/button";
import Card from "primevue/card";
import InputText from "primevue/inputtext";
import InputNumber from "primevue/inputnumber";
import RadioButton from "primevue/radiobutton";

const router = useRouter();

const formData = ref({
  name: "",
  age: null,
  gender: "male",
  height: null,
  weight: null,
});

const handleSubmit = () => {
  if (
    !formData.value.name ||
    !formData.value.age ||
    !formData.value.height ||
    !formData.value.weight
  ) {
    alert("Vui lòng điền đầy đủ thông tin!");
    return;
  }
  console.log("Form submitted:", formData.value);
  // TODO: Navigate to next step or submit to API
  alert("Thông tin đã được lưu! \n" + JSON.stringify(formData.value, null, 2));
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
</style>
