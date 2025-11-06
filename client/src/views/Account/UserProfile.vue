<template>
  <div class="min-h-screen bg-linear-to-br from-gray-50 to-gray-100">
    <Toast />

    <!-- Header -->
    <div class="bg-white shadow-md">
      <div class="container mx-auto px-4 lg:px-8 py-6">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-3xl font-bold text-blue-900">Hồ Sơ Của Tôi</h1>
            <p class="text-gray-600 mt-1">
              Quản lý thông tin hồ sơ để bảo mật tài khoản
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="container mx-auto px-4 lg:px-8 py-8">
      <Card class="shadow-lg">
        <template #content>
          <form @submit.prevent="handleSubmit" class="space-y-6">
            <!-- Tên đăng nhập (Read-only) -->
            <div class="grid grid-cols-12 gap-4 items-center">
              <label
                for="username"
                class="col-span-12 md:col-span-3 text-right text-gray-700 font-medium"
              >
                Tên đăng nhập
              </label>
              <div class="col-span-12 md:col-span-6">
                <InputText
                  id="username"
                  v-model="profileForm.username"
                  :readonly="true"
                  disabled
                  class="w-full bg-gray-100 text-gray-600"
                />
              </div>
            </div>

            <!-- Tên -->
            <div class="grid grid-cols-12 gap-4 items-start">
              <label
                for="name"
                class="col-span-12 md:col-span-3 text-right text-gray-700 font-medium pt-2"
              >
                Tên
              </label>
              <div class="col-span-12 md:col-span-6">
                <InputText
                  id="name"
                  v-model="profileForm.name"
                  placeholder="Nhập tên của bạn"
                  class="w-full"
                  :class="{ 'p-invalid': submitted && !profileForm.name }"
                />
                <small
                  v-if="submitted && !profileForm.name"
                  class="p-error block mt-1"
                >
                  Tên là bắt buộc
                </small>
              </div>
            </div>

            <!-- Email -->
            <div class="grid grid-cols-12 gap-4 items-start">
              <label
                for="email"
                class="col-span-12 md:col-span-3 text-right text-gray-700 font-medium pt-2"
              >
                Email
              </label>
              <div class="col-span-12 md:col-span-6">
                <InputText
                  id="email"
                  v-model="profileForm.email"
                  type="email"
                  placeholder="Nhập email"
                  class="w-full"
                  :class="{ 'p-invalid': submitted && !isValidEmail }"
                />
                <small
                  v-if="submitted && !isValidEmail"
                  class="p-error block mt-1"
                >
                  Email không hợp lệ
                </small>
              </div>
            </div>

            <!-- Số điện thoại -->
            <div class="grid grid-cols-12 gap-4 items-start">
              <label
                for="phone"
                class="col-span-12 md:col-span-3 text-right text-gray-700 font-medium pt-2"
              >
                Số điện thoại
              </label>
              <div class="col-span-12 md:col-span-6">
                <InputText
                  id="phone"
                  v-model="profileForm.phone"
                  placeholder="Nhập số điện thoại"
                  class="w-full"
                  :class="{ 'p-invalid': submitted && !isValidPhone }"
                />
                <small
                  v-if="submitted && !isValidPhone"
                  class="p-error block mt-1"
                >
                  Số điện thoại không hợp lệ
                </small>
              </div>
            </div>

            <!-- Giới tính -->
            <div class="grid grid-cols-12 gap-4 items-center">
              <label
                class="col-span-12 md:col-span-3 text-right text-gray-700 font-medium"
              >
                Giới tính
              </label>
              <div class="col-span-12 md:col-span-6">
                <div class="flex items-center gap-6">
                  <div class="flex items-center">
                    <RadioButton
                      id="male"
                      v-model="profileForm.gender"
                      name="gender"
                      value="male"
                    />
                    <label for="male" class="ml-2 text-gray-700">Nam</label>
                  </div>
                  <div class="flex items-center">
                    <RadioButton
                      id="female"
                      v-model="profileForm.gender"
                      name="gender"
                      value="female"
                    />
                    <label for="female" class="ml-2 text-gray-700">Nữ</label>
                  </div>
                  <div class="flex items-center">
                    <RadioButton
                      id="other"
                      v-model="profileForm.gender"
                      name="gender"
                      value="other"
                    />
                    <label for="other" class="ml-2 text-gray-700">Khác</label>
                  </div>
                </div>
              </div>
            </div>

            <!-- Ngày sinh -->
            <div class="grid grid-cols-12 gap-4 items-start">
              <label
                for="birthDate"
                class="col-span-12 md:col-span-3 text-right text-gray-700 font-medium pt-2"
              >
                Ngày sinh
              </label>
              <div class="col-span-12 md:col-span-6">
                <DatePicker
                  id="birthDate"
                  v-model="profileForm.birthDate"
                  date-format="dd/mm/yy"
                  placeholder="Chọn ngày sinh"
                  show-icon
                  :max-date="maxDate"
                  class="w-full"
                  :class="{ 'p-invalid': submitted && !profileForm.birthDate }"
                />
                <small
                  v-if="submitted && !profileForm.birthDate"
                  class="p-error block mt-1"
                >
                  Ngày sinh là bắt buộc
                </small>
              </div>
            </div>

            <!-- Submit Button -->
            <div class="grid grid-cols-12 gap-4">
              <div class="col-span-12 md:col-span-9 md:col-start-4">
                <Button
                  type="submit"
                  label="Lưu"
                  icon="pi pi-save"
                  :loading="isSubmitting"
                  class="bg-blue-600 hover:bg-blue-700 text-white border-0 px-8"
                />
              </div>
            </div>
          </form>
        </template>
      </Card>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useToast } from "primevue/usetoast";
import Card from "primevue/card";
import Button from "primevue/button";
import InputText from "primevue/inputtext";
import RadioButton from "primevue/radiobutton";
import DatePicker from "primevue/datepicker";
import Toast from "primevue/toast";
// import userApi from "../../apis/userApi";

const toast = useToast();

// State
const isSubmitting = ref(false);
const submitted = ref(false);

// Max date for birth date (today)
const maxDate = ref(new Date());

// Form data
const profileForm = ref({
  username: "everest_pharma",
  name: "",
  email: "",
  phone: "",
  gender: null,
  birthDate: null,
});

// Validation
const isValidEmail = computed(() => {
  if (!profileForm.value.email) return false;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(profileForm.value.email);
});

const isValidPhone = computed(() => {
  if (!profileForm.value.phone) return true; // Phone is optional
  const phoneRegex = /^[0-9]{10,11}$/;
  return phoneRegex.test(profileForm.value.phone);
});

// Methods
const loadUserProfile = async () => {
  try {
    // Simulate API call
    // const response = await userApi.getProfile();
    // profileForm.value = response.data;

    // Mock data
    profileForm.value = {
      username: "tran_nhat_danh",
      name: "",
      email: "",
      phone: "",
      gender: null,
      birthDate: null,
    };
  } catch (error) {
    console.error("Error loading user profile:", error);
    toast.add({
      severity: "error",
      summary: "Lỗi",
      detail: "Không thể tải thông tin hồ sơ",
      life: 3000,
    });
  }
};

const handleSubmit = async () => {
  submitted.value = true;

  // Validate required fields
  if (
    !profileForm.value.name ||
    !isValidEmail.value ||
    !isValidPhone.value ||
    !profileForm.value.birthDate
  ) {
    toast.add({
      severity: "warn",
      summary: "Cảnh báo",
      detail: "Vui lòng điền đầy đủ thông tin bắt buộc",
      life: 3000,
    });
    return;
  }

  isSubmitting.value = true;

  try {
    // Simulate API call
    await new Promise((resolve) => setTimeout(resolve, 1000));

    // const response = await userApi.updateProfile(profileForm.value);

    toast.add({
      severity: "success",
      summary: "Thành công",
      detail: "Đã cập nhật thông tin hồ sơ",
      life: 3000,
    });

    submitted.value = false;

    // Reload profile to get updated data
    await loadUserProfile();
  } catch (error) {
    console.error("Error updating profile:", error);
    toast.add({
      severity: "error",
      summary: "Lỗi",
      detail: "Không thể cập nhật thông tin hồ sơ",
      life: 3000,
    });
  } finally {
    isSubmitting.value = false;
  }
};

// Lifecycle
onMounted(() => {
  loadUserProfile();
});
</script>

<style scoped>
:deep(.p-card) {
  border-radius: 12px;
}

:deep(.p-card .p-card-content) {
  padding: 2rem;
}

:deep(.p-inputtext),
:deep(.p-datepicker) {
  border: 1px solid #d1d5db;
  border-radius: 6px;
}

:deep(.p-inputtext:focus),
:deep(.p-datepicker:focus-within) {
  border-color: #2563eb;
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

:deep(.p-inputtext:disabled),
:deep(.p-inputtext[readonly]) {
  background-color: #f3f4f6;
  color: #6b7280;
  cursor: not-allowed;
}

:deep(.p-radiobutton) {
  border-color: #d1d5db;
}

:deep(.p-radiobutton.p-highlight) {
  border-color: #ef4444;
  background-color: #ef4444;
}

:deep(.p-radiobutton:not(.p-disabled):not(.p-highlight):hover) {
  border-color: #ef4444;
}

:deep(.p-datepicker-input-icon-container) {
  cursor: pointer;
}

:deep(.p-button) {
  font-weight: 600;
  padding: 0.625rem 1.5rem;
  border-radius: 6px;
  transition: all 0.2s;
}

:deep(.p-button:hover) {
  transform: translateY(-1px);
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

:deep(.p-button:active) {
  transform: translateY(0);
}

/* Custom styles for masked fields */
.bg-gray-100 {
  background-color: #f3f4f6;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  :deep(.p-card .p-card-content) {
    padding: 1rem;
  }

  .text-right {
    text-align: left;
  }
}
</style>
