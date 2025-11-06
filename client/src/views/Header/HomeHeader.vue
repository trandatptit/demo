<template>
  <div class="bg-white shadow-sm sticky top-0 z-50">
    <div class="mx-auto px-4 lg:px-8 py-4">
      <!-- Container 12 Grid -->
      <div class="grid grid-cols-12 gap-4 items-center">
        <!-- Logo Section (col-span-6 on mobile, col-span-3 on desktop) -->
        <div class="col-span-6 md:col-span-3">
          <Menubar class="border-0 bg-transparent p-0">
            <template #start>
              <router-link to="HomePage" class="flex items-center space-x-2">
                <div
                  class="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center"
                >
                  <span class="text-white font-bold text-lg">E</span>
                </div>
                <span class="font-bold text-xl text-blue-600 hidden sm:inline"
                  >EVEREST</span
                >
              </router-link>
            </template>
          </Menubar>
        </div>

        <!-- Menu Items (hidden on mobile, col-span-6 on desktop) -->
        <!-- <div class="hidden md:col-span-6 md:flex md:justify-center md:gap-8">
        
          <router-link
            to="about"
            class="text-gray-600 hover:text-blue-600 font-medium transition"
            ><div>
              <span> Lộ Trình Của Bạn </span>
              <i class="pi pi-fw pi-angle-down"></i>
            </div>
          </router-link>
          <router-link
            to="#"
            class="text-gray-600 hover:text-blue-600 font-medium transition"
          >
            <div>
              <span> Về Everest </span>
              <i class="pi pi-fw pi-angle-down"></i>
            </div>
          </router-link>
        </div> -->

        <div class="hidden md:col-span-6 md:flex md:justify-center md:gap-8">
          <div class="text-gray-60 font-medium transition tab-feature-one">
            <div
              class="feature-one flex items-center cursor-pointer text-[16px]"
            >
              <span> Lộ trình của bạn </span>
              <i class="pi pi-fw pi-angle-down"></i>
            </div>
            <div
              class="feature-one__droplist flex bg-white p-1 rounded-[6px] shadow-lg"
            >
              <ul class="feature-two__item-list">
                <li
                  class="feature-two__item-list-item flex items-center"
                  v-for="subItem in menuItemsFeatureOne.items"
                  :key="subItem.label"
                >
                  <router-link to="#">
                    <div
                      class="feature-two__item-list-title text-stone-900 flex items-center text-[16px]"
                    >
                      {{ subItem.label }}
                    </div>
                    <div
                      class="feature-two__item-list-content ml-auto rounded bg-emphasis text-stone-600/55 text-[12px]"
                    >
                      {{ subItem.content }}
                    </div>
                  </router-link>
                </li>
              </ul>
            </div>
          </div>
          <div class="text-gray-60 font-medium transition tab-feature-two">
            <div
              class="feature-two flex items-center cursor-pointer text-[16px]"
            >
              <span> Về Everest </span>
              <i class="pi pi-fw pi-angle-down"></i>
            </div>
            <div
              class="feature-two__droplist flex bg-white p-1 rounded-[6px] shadow-lg"
            >
              <div
                class="feature-two__item flex-1"
                v-for="(itemCol, index) in menuItemsFeattureTwo.items"
                :key="index"
              >
                <div class="feature-two__item-title">{{ itemCol.label }}</div>
                <ul class="feature-two__item-list">
                  <li
                    class="feature-two__item-list-item flex items-center"
                    v-for="subItem in itemCol.items"
                    :key="subItem.label"
                  >
                    <router-link to="#">
                      <div
                        class="feature-two__item-list-title text-stone-900 flex items-center text-[16px]"
                      >
                        {{ subItem.label }}
                      </div>
                      <div
                        class="feature-two__item-list-content ml-auto rounded bg-emphasis text-stone-600/55 text-[12px]"
                      >
                        {{ subItem.content }}
                      </div>
                    </router-link>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>

        <!-- Avatar Section (col-span-6 on mobile, col-span-3 on desktop) -->
        <div class="col-span-6 md:col-span-3 flex justify-end">
          <Avatar
            icon="pi pi-user"
            class="mr-2 bg-[#ece9fc] text-[#2a1261] cursor-pointer"
            shape="circle"
            size="large"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Menubar from "primevue/menubar";
import Avatar from "primevue/avatar";
import { ref } from "vue";

const menuItemsFeatureOne = ref({
  label: "Lộ Trình Của Bạn",
  icon: "pi pi-search",
  isParent: true,
  items: [
    {
      label: "Tăng Chiều Cao",
      icon: "pi pi-fw pi-arrow-up",
      content:
        "Dự đoán và tối ưu hóa tiềm năng chiều cao của bạn bằng công nghệ...",
    },
    {
      label: "Dành Cho Mẹ Bầu (Sắp ra mắt)",
      icon: "pi pi-fw pi-bed",
      content: "Chăm sóc sức khỏe toàn diện cho mẹ và bé trong suốt thai kỳ.",
    },
    {
      label: "Phòng Ngừa Loãng Xương (Sắp ra mắt)",
      icon: "pi pi-fw pi-apple",
      content:
        "Giải pháp khoa học giúp xương chắc khỏe, ngăn ngừa loãng xương hiệu...",
    },
  ],
});

const menuItemsFeattureTwo = ref({
  label: "Về Everest",
  icon: "pi pi-fw pi-info-circle",
  items: [
    // Cộng Đồng Everest
    {
      label: "Cộng Đồng Everest",
      items: [
        {
          label: "Câu chuyện truyền cảm hứng",
          icon: "pi pi-fw pi-heart",
          content: "Những hành trình chinh phục đỉnh cao có thật.",
        },
        {
          label: "Blog & Video hướng dẫn",
          icon: "pi pi-fw pi-video",
          content: "Kiến thức và mẹo hữu ích từ chuyên gia.",
        },
        {
          label: "Sự kiện & Chương trình đồng hành",
          icon: "pi pi-fw pi-calendar",
          content: "Tham gia các hoạt động và kết nối.",
        },
        {
          label: "Diễn đàn / Nhóm hỗ trợ",
          icon: "pi pi-fw pi-comments",
          content: "Nơi chia sẻ, hỏi đáp cùng cộng đồng.",
        },
      ],
    },
    // Khoa Học Everest
    {
      label: "Khoa Học Everest",
      items: [
        {
          label: "Nghiên cứu lâm sàng",
          icon: "pi pi-fw pi-chart-line",
          content: "Các bằng chứng khoa học về hiệu quả.",
        },
        {
          label: "Tài liệu khoa học về sản phẩm",
          icon: "pi pi-fw pi-file-pdf",
          content: "Thông tin chi tiết về từng sản phẩm.",
        },
        {
          label: "Hướng dẫn sử dụng an toàn",
          icon: "pi pi-fw pi-shield",
          content: "Đảm bảo bạn sử dụng sản phẩm đúng cách.",
        },
        {
          label: "Bài viết chuyên sâu của chuyên gia",
          icon: "pi pi-fw pi-user-edit",
          content: "Phân tích từ đội ngũ R&D hàng đầu.",
        },
        {
          label: "Thư viện kiến thức",
          icon: "pi pi-fw pi-book",
          content: "Toàn bộ kiến thức chiều cao & dinh dưỡng.",
        },
      ],
    },
    // Câu Chuyện Everest
    {
      label: "Câu Chuyện Everest",
      items: [
        {
          label: "Câu chuyện Everest",
          icon: "pi pi-fw pi-star",
          content: "Hành trình và sứ mệnh của chúng tôi.",
        },
        {
          label: "Triết lý khoa học và nhân văn",
          icon: "pi pi-fw pi-lightbulb",
          content: "Giá trị cốt lõi trong từng sản phẩm.",
        },
        {
          label: "Đội ngũ, phòng lab, chuyên gia",
          icon: "pi pi-fw pi-users",
          content: "Những con người đứng sau thành công.",
        },
        {
          label: "Giấy phép & chứng nhận",
          icon: "pi pi-fw pi-verified",
          content: "Sự công nhận và minh bạch.",
        },
        {
          label: "Liên hệ & Chính sách",
          icon: "pi pi-fw pi-phone",
          content: "Thông tin liên hệ và các điều khoản.",
        },
      ],
    },
  ],
});
</script>

<style scoped>
:deep(.p-menubar) {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

:deep(.p-menubar) {
  border: none;
}

:deep(.features .p-menubar-item-link) {
  display: block;
}

.item-feature {
  display: flex;
  align-items: center;
}

:deep(.item-feature .p-menubar-item-link) {
  display: flex;
}

.tab-feature-two {
  /* position: absolute; */
}

.feature-two__droplist,
.feature-one__droplist {
  display: none;
}

/* .feature-two__droplist::before {
  content: "";
  display: block;
  width: 100%;
  height: 25px;
  background: transparent;
  top: -20px;
  left: calc(100% / 2 - 10px);
  position: absolute;
} */

.tab-feature-two:hover {
  background: #f1f5f9;
  height: 45.6px;
  padding: 10px 15px;
}

.tab-feature-two:hover .pi-angle-down,
.tab-feature-one:hover .pi-angle-down {
  color: #155dfc;
}

.tab-feature-two:hover .feature-two__droplist,
.tab-feature-one:hover .feature-one__droplist {
  display: flex;
  position: absolute;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -2px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(0, 0, 0, 0.1);
}

.tab-feature-two .feature-two__droplist {
  position: fixed;
  left: calc(100% / 2 + -450px);
}

.tab-feature-one .feature-one__droplist {
  position: fixed;
  left: calc(100% / 2 + -400px);
}

.tab-feature-two .feature-two__item-title,
.tab-feature-one .feature-one__item-title {
  font-weight: 600;
  font-size: 1.1rem;
  margin-bottom: 0.75rem;
  padding: 10px 15px 0;
}

.feature-two__item-list {
  /* padding-left: 1rem; */
}

.feature-two__item-list-title {
  font-weight: 500;
  color: #374151;
}

.feature-two__item-list-item {
  padding: 10px 15px;
  height: 72px;
}
.feature-two__item-list-item:hover {
  background: #f1f5f9;
  border-radius: 6px;
}

.feature-two__droplist {
}

.feature-two {
  height: 100%;
}
</style>
