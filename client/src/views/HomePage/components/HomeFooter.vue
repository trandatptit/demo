<template>
  <footer class="bg-[#002855] text-white py-12 lg:py-16">
    <div class="mx-auto px-4 lg:px-8">
      <!-- Container 12 Grid -->
      <div class="grid grid-cols-12 gap-8 lg:gap-12 mb-8">
        <!-- Brand Section (col-span-12 lg:col-span-4) -->
        <div class="col-span-12 lg:col-span-4">
          <div class="logo flex items-center space-x-3 mb-6">
            <router-link
              :to="{ name: 'HomePage' }"
              class="w-14 h-14 bg-white rounded-lg flex items-center justify-center"
            >
              <span class="text-[#002855] font-bold text-2xl">E</span>
            </router-link>
          </div>
          <p class="text-gray-300 text-sm leading-relaxed max-w-xs">
            Cùng bạn chinh phục đỉnh cao của bạn nâng. NO LIMITS!
          </p>
          <div class="flex gap-4 mt-6">
            <a
              href="#"
              class="w-10 h-10 bg-[#003D7A] rounded-md flex items-center justify-center hover:bg-[#0052A3] transition"
            >
              <i class="pi pi-facebook text-white"></i>
            </a>
            <a
              href="#"
              class="w-10 h-10 bg-[#003D7A] rounded-md flex items-center justify-center hover:bg-[#0052A3] transition"
            >
              <i class="pi pi-instagram text-white"></i>
            </a>
            <a
              href="#"
              class="w-10 h-10 bg-[#003D7A] rounded-md flex items-center justify-center hover:bg-[#0052A3] transition"
            >
              <i class="pi pi-youtube text-white"></i>
            </a>
          </div>
        </div>

        <!-- Dịch Vụ Section (col-span-6 sm:col-span-4 lg:col-span-2) -->
        <div class="col-span-6 sm:col-span-4 lg:col-span-2">
          <h3 class="font-bold text-lg mb-4 text-white">Dịch Vụ</h3>
          <ul class="space-y-3">
            <li>
              <a
                href="#"
                class="text-gray-300 hover:text-white transition text-sm"
                >Tăng Trưởng Chiều Cao</a
              >
            </li>
            <li>
              <a
                href="#"
                class="text-gray-300 hover:text-white transition text-sm"
                >AI Timeline</a
              >
            </li>
            <li>
              <a
                href="#"
                class="text-gray-300 hover:text-white transition text-sm"
                >Sản Phẩm</a
              >
            </li>
          </ul>
        </div>

        <!-- Về Everest Section (col-span-6 sm:col-span-4 lg:col-span-2) -->
        <div class="col-span-6 sm:col-span-4 lg:col-span-2">
          <h3 class="font-bold text-lg mb-4 text-white">Về Everest</h3>
          <ul class="space-y-3">
            <li>
              <a
                href="#"
                class="text-gray-300 hover:text-white transition text-sm"
                >Câu Chuyện</a
              >
            </li>
            <li>
              <a
                href="#"
                class="text-gray-300 hover:text-white transition text-sm"
                >Khoa Học</a
              >
            </li>
            <li>
              <a
                href="#"
                class="text-gray-300 hover:text-white transition text-sm"
                >Cơ Hội</a
              >
            </li>
          </ul>
        </div>

        <!-- Liên Hệ Section (col-span-12 sm:col-span-4 lg:col-span-4) -->
        <div class="col-span-12 sm:col-span-4 lg:col-span-4">
          <h3 class="font-bold text-lg mb-4 text-white">Liên Hệ</h3>
          <ul class="space-y-3">
            <li class="flex items-start gap-2">
              <i class="pi pi-map-marker text-gray-300 text-sm mt-1"></i>
              <span class="text-gray-300 text-sm leading-relaxed"
                >123 Đường ABC, Quận 1, TP.HCM</span
              >
            </li>
            <li class="flex items-center gap-2">
              <i class="pi pi-phone text-gray-300 text-sm"></i>
              <span class="text-gray-300 text-sm">1900 xxxx</span>
            </li>
            <li class="flex items-center gap-2">
              <i class="pi pi-envelope text-gray-300 text-sm"></i>
              <span class="text-gray-300 text-sm">info@everestpharma.vn</span>
            </li>
          </ul>
        </div>
      </div>

      <!-- Divider -->
      <div class="border-t border-[#003D7A] pt-8">
        <p class="text-gray-400 text-sm text-center">
          © 2026 Everest Pharma Vietnam. All Rights Reserved.
        </p>
      </div>
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped>
.logo {
}
</style>
