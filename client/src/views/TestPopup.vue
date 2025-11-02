<template>
  <div class="min-h-screen flex flex-col items-center justify-center bg-gradient-to-br from-gray-50 via-blue-50 to-purple-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900 p-8 transition-colors duration-300">
    <!-- Header Section -->
    <div class="text-center mb-10 animate-fade-in">
      <div class="inline-block p-4 bg-gradient-to-r from-blue-600 to-purple-600 dark:from-blue-700 dark:to-purple-700 rounded-2xl shadow-2xl mb-6">
        <i class="pi pi-sparkles text-5xl text-white"></i>
      </div>
      <h2 class="text-4xl font-bold mb-3 bg-gradient-to-r from-blue-600 to-purple-600 dark:from-blue-400 dark:to-purple-400 bg-clip-text text-transparent">
        Demo PopupBase
      </h2>
      <p class="text-gray-600 dark:text-gray-400 text-lg max-w-md mx-auto">
        Nhấn nút bên dưới để mở popup nhập thông tin người dùng
      </p>
    </div>

    <!-- Main Action Button -->
    <Button
      label="✨ Mở Popup"
      icon="pi pi-external-link"
      class="!bg-gradient-to-r !from-blue-600 !to-purple-600 dark:!from-blue-700 dark:!to-purple-700 hover:!from-blue-700 hover:!to-purple-700 dark:hover:!from-blue-800 dark:hover:!to-purple-800 !text-white !font-bold !px-8 !py-4 !rounded-2xl !shadow-2xl hover:!shadow-3xl !transition-all !duration-300 !transform hover:!scale-105 !border-0"
      @click="show = true"
    />

    <!-- Features Cards -->
    <!-- <div class="mt-16 grid grid-cols-1 md:grid-cols-3 gap-6 max-w-5xl">
      <div class="bg-white dark:bg-gray-800 p-6 rounded-2xl shadow-xl border border-gray-200 dark:border-gray-700 hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-1">
        <div class="w-12 h-12 bg-blue-100 dark:bg-blue-900/30 rounded-xl flex items-center justify-center mb-4">
          <i class="pi pi-check-circle text-2xl text-blue-600 dark:text-blue-400"></i>
        </div>
        <h3 class="text-lg font-bold text-gray-800 dark:text-gray-100 mb-2">Xác nhận thay đổi</h3>
        <p class="text-gray-600 dark:text-gray-400 text-sm">Tự động phát hiện và xác nhận trước khi đóng</p>
      </div>

      <div class="bg-white dark:bg-gray-800 p-6 rounded-2xl shadow-xl border border-gray-200 dark:border-gray-700 hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-1">
        <div class="w-12 h-12 bg-purple-100 dark:bg-purple-900/30 rounded-xl flex items-center justify-center mb-4">
          <i class="pi pi-moon text-2xl text-purple-600 dark:text-purple-400"></i>
        </div>
        <h3 class="text-lg font-bold text-gray-800 dark:text-gray-100 mb-2">Dark Mode</h3>
        <p class="text-gray-600 dark:text-gray-400 text-sm">Hỗ trợ chế độ tối mượt mà</p>
      </div>

      <div class="bg-white dark:bg-gray-800 p-6 rounded-2xl shadow-xl border border-gray-200 dark:border-gray-700 hover:shadow-2xl transition-all duration-300 transform hover:-translate-y-1">
        <div class="w-12 h-12 bg-green-100 dark:bg-green-900/30 rounded-xl flex items-center justify-center mb-4">
          <i class="pi pi-palette text-2xl text-green-600 dark:text-green-400"></i>
        </div>
        <h3 class="text-lg font-bold text-gray-800 dark:text-gray-100 mb-2">Tùy biến cao</h3>
        <p class="text-gray-600 dark:text-gray-400 text-sm">Dễ dàng tùy chỉnh giao diện</p>
      </div>
    </div> -->

    <!-- PopupBase -->
    <PopupBase
      v-model="show"
      :isCustom="true"
      :formData="form"
      title="Thêm người dùng mới"
      width="32rem"
      :dismissableMask="false"
      @savedata="onSave"
      @cancelPopup="onCancel"
    >
      <template #v-content="{ formData, updateDirty }">
        <div class="space-y-5">
          <!-- Name Field -->
          <div class="group">
            <label class="block mb-2 font-semibold text-sm text-gray-700 dark:text-gray-300 flex items-center gap-2">
              <i class="pi pi-user text-blue-600 dark:text-blue-400"></i>
              Họ tên <span class="text-red-500">*</span>
            </label>
            <InputText
              v-model="formData.name"
              placeholder="Nhập họ tên đầy đủ"
              class="w-full !rounded-xl !border-2 !border-gray-300 dark:!border-gray-600 focus:!border-blue-500 dark:focus:!border-blue-400 !bg-gray-50 dark:!bg-gray-700 !text-gray-800 dark:!text-gray-100 !px-4 !py-3 !transition-all !duration-200 placeholder:!text-gray-400 dark:placeholder:!text-gray-500"
              @input="updateDirty(true)"
            />
          </div>

          <!-- Email Field -->
          <!-- <div class="group">
            <label class="block mb-2 font-semibold text-sm text-gray-700 dark:text-gray-300 flex items-center gap-2">
              <i class="pi pi-envelope text-purple-600 dark:text-purple-400"></i>
              Email <span class="text-red-500">*</span>
            </label>
            <InputText
              v-model="formData.email"
              placeholder="example@email.com"
              type="email"
              class="w-full !rounded-xl !border-2 !border-gray-300 dark:!border-gray-600 focus:!border-purple-500 dark:focus:!border-purple-400 !bg-gray-50 dark:!bg-gray-700 !text-gray-800 dark:!text-gray-100 !px-4 !py-3 !transition-all !duration-200 placeholder:!text-gray-400 dark:placeholder:!text-gray-500"
              @input="updateDirty(true)"
            />
          </div> -->

          <!-- Phone Field -->
          <!-- <div class="group">
            <label class="block mb-2 font-semibold text-sm text-gray-700 dark:text-gray-300 flex items-center gap-2">
              <i class="pi pi-phone text-green-600 dark:text-green-400"></i>
              Số điện thoại
            </label>
            <InputText
              v-model="formData.phoneNumber"
              placeholder="0xxx xxx xxx"
              type="tel"
              class="w-full !rounded-xl !border-2 !border-gray-300 dark:!border-gray-600 focus:!border-green-500 dark:focus:!border-green-400 !bg-gray-50 dark:!bg-gray-700 !text-gray-800 dark:!text-gray-100 !px-4 !py-3 !transition-all !duration-200 placeholder:!text-gray-400 dark:placeholder:!text-gray-500"
              @input="updateDirty(true)"
            />
          </div> -->
        </div>
      </template>
    </PopupBase>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import PopupBase from '../components/popup/PopupBase.vue'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'

const show = ref(false)
const form = ref({ name: '', email: '', phoneNumber: '' })

function onSave(data) {
  alert(`✅ Đã lưu thành công!\n\n👤 Họ tên: ${data.name}\n📧 Email: ${data.email}\n📱 SĐT: ${data.phoneNumber || 'Chưa có'}`)
  show.value = false
}

function onCancel() {
  console.log('❌ Đã hủy popup')
}
</script>

<style scoped>
@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in {
  animation: fade-in 0.6s ease-out;
}
</style>