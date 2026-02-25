<template>
  <div class="user-profile-page">
    <Toast />

    <!-- Header -->
    <div class="page-header">
      <div class="container mx-auto px-4 lg:px-8 py-6">
        <div class="flex items-center justify-between">
          <div>
            <div class="header-accent"></div>
            <span class="header-badge"> <i class="pi pi-user"></i> Hồ Sơ </span>
            <h1 class="header-title">Hồ Sơ Của Tôi</h1>
            <p class="header-subtitle">
              Quản lý thông tin hồ sơ để bảo mật tài khoản
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="container mx-auto px-4 lg:px-8 py-8">
      <Card class="profile-card">
        <template #content>
          <div class="space-y-6">
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
                  v-model="profileForm.fullName"
                  placeholder="Nhập tên của bạn"
                  class="w-full"
                  :class="{ 'p-invalid': submitted && !profileForm.fullName }"
                />
                <small
                  v-if="submitted && !profileForm.fullName"
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
                  v-model="profileForm.phoneNumber"
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
                      :value="1"
                    />
                    <label for="male" class="ml-2 text-gray-700">Nam</label>
                  </div>
                  <div class="flex items-center">
                    <RadioButton
                      id="female"
                      v-model="profileForm.gender"
                      name="gender"
                      :value="2"
                    />
                    <label for="female" class="ml-2 text-gray-700">Nữ</label>
                  </div>
                </div>
              </div>
            </div>

            <!-- Ngày sinh -->
            <div class="grid grid-cols-12 gap-4 items-start">
              <label
                for="birthday"
                class="col-span-12 md:col-span-3 text-right text-gray-700 font-medium pt-2"
              >
                Ngày sinh
              </label>
              <div class="col-span-12 md:col-span-6">
                <IconField>
                  <DatePicker
                    id="birthday"
                    v-model="profileForm.birthday"
                    dateFormat="dd/mm/yy"
                    placeholder="Chọn ngày sinh"
                    iconDisplay="input"
                    :maxDate="maxDate"
                    showIcon
                    fluid
                    :class="{ 'p-invalid': submitted && !profileForm.birthday }"
                  />
                </IconField>
                <small
                  v-if="submitted && !profileForm.birthday"
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
                  class="save-btn"
                  @click="handleSubmit"
                />
              </div>
            </div>
          </div>
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
import IconField from "primevue/iconfield";
import InputIcon from "primevue/inputicon";
import Toast from "primevue/toast";
import userApi from "@/apis/userApi";
// import userApi from "../../apis/userApi";

const toast = useToast();

// State
const isSubmitting = ref(false);
const submitted = ref(false);

// Max date for birth date (today)
const maxDate = ref(new Date());

// Form data
const profileForm = ref({});

// Validation
const isValidEmail = computed(() => {
  if (!profileForm.value.email) return false;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(profileForm.value.email);
});

const isValidPhone = computed(() => {
  if (!profileForm.value.phoneNumber) return true; // Phone is optional
  const phoneRegex = /^[0-9]{10,11}$/;
  return phoneRegex.test(profileForm.value.phoneNumber);
});

// Methods
const loadUserProfile = async () => {
  try {
    // Simulate API call
    const response = await userApi.getUserInfo();
    profileForm.value = response.result;

    // Mock data
    // profileForm.value = {
    //   username: "tran_nhat_danh",
    //   name: "",
    //   email: "",
    //   phone: "",
    //   gender: null,
    //   birthDate: null,
    // };
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
    !profileForm.value.fullName ||
    !isValidEmail.value ||
    !isValidPhone.value
    // || !profileForm.value.birthday
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
    const response = await userApi.updateUserInfo(profileForm.value);
    if (response?.status) {
      toast.add({
        severity: "success",
        summary: "Thành công",
        detail: "Đã cập nhật thông tin hồ sơ",
        life: 3000,
      });

      submitted.value = false;
    } else {
      toast.add({
        severity: "error",
        summary: "Lỗi",
        detail: "Không thể cập nhật thông tin hồ sơ",
        life: 3000,
      });
    }

    // const response = await userApi.updateProfile(profileForm.value);

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
/* ─── Design Tokens ─── */
.user-profile-page {
  --primary: #2563eb;
  --primary-light: #3b82f6;
  --primary-dark: #1d4ed8;
  --primary-bg: #eff6ff;
  --primary-bg-deep: #dbeafe;
  --primary-ring: rgba(37, 99, 235, 0.15);
  --surface: #ffffff;
  --surface-alt: #f8fafc;
  --text-primary: #0f172a;
  --text-secondary: #475569;
  --text-muted: #94a3b8;
  --border: #e2e8f0;
  --border-hover: #cbd5e1;
  --radius: 12px;
  --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
  --shadow-md: 0 4px 12px rgba(0, 0, 0, 0.08);
  --shadow-lg: 0 8px 24px rgba(0, 0, 0, 0.1);

  min-height: 100vh;
  background: linear-gradient(135deg, var(--surface-alt) 0%, #eef2ff 100%);
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
}

/* ─── Page Header ─── */
.page-header {
  background: var(--surface);
  box-shadow: var(--shadow-md);
  border-bottom: 1px solid var(--border);
}

.header-accent {
  width: 48px;
  height: 4px;
  border-radius: 4px;
  background: linear-gradient(90deg, var(--primary), var(--primary-light));
  margin-bottom: 12px;
}

.header-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 14px;
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.5px;
  text-transform: uppercase;
  color: var(--primary);
  background: var(--primary-bg);
  border: 1px solid var(--primary-bg-deep);
  border-radius: 999px;
  margin-bottom: 10px;
}

.header-title {
  font-size: clamp(1.5rem, 3vw, 2rem);
  font-weight: 800;
  color: var(--text-primary);
  margin: 0 0 4px;
  line-height: 1.25;
}

.header-subtitle {
  font-size: 0.9rem;
  font-weight: 500;
  color: var(--text-secondary);
  margin: 0;
}

/* ─── Profile Card ─── */
:deep(.profile-card.p-card) {
  border-radius: 16px;
  border: 1px solid var(--border);
  box-shadow: var(--shadow-lg);
  background: var(--surface);
  position: relative;
  overflow: hidden;
}

:deep(.profile-card.p-card)::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(
    90deg,
    var(--primary),
    var(--primary-light),
    #818cf8
  );
}

:deep(.profile-card .p-card-content) {
  padding: 2rem;
}

/* ─── Form Inputs ─── */
:deep(.p-inputtext),
:deep(.p-datepicker) {
  border: 1.5px solid var(--border);
  border-radius: var(--radius);
  font-family: inherit;
  transition: all 0.2s ease;
}

:deep(.p-inputtext:focus),
:deep(.p-datepicker:focus-within) {
  border-color: var(--primary-light);
  box-shadow: 0 0 0 3px var(--primary-ring);
}

:deep(.p-inputtext:disabled),
:deep(.p-inputtext[readonly]) {
  background-color: var(--surface-alt);
  color: var(--text-muted);
  cursor: not-allowed;
}

/* ─── Radio Button ─── */
:deep(.p-radiobutton .p-radiobutton-box) {
  border-color: var(--border-hover);
  transition: all 0.2s ease;
}

:deep(.p-radiobutton .p-radiobutton-box:hover) {
  border-color: var(--primary-light);
}

:deep(.p-radiobutton.p-highlight .p-radiobutton-box) {
  border-color: var(--primary) !important;
  background: var(--primary) !important;
  box-shadow: 0 0 0 3px var(--primary-ring);
}

:deep(.p-datepicker-input-icon-container) {
  cursor: pointer;
}

/* ─── Save Button ─── */
:deep(.save-btn.p-button) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  );
  border: none;
  padding: 12px 28px;
  font-size: 0.95rem;
  font-weight: 700;
  border-radius: var(--radius);
  box-shadow: 0 4px 14px rgba(37, 99, 235, 0.35);
  transition: all 0.25s ease;
  letter-spacing: 0.3px;
}

:deep(.save-btn.p-button:not(:disabled):hover) {
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  );
  box-shadow: 0 6px 20px rgba(37, 99, 235, 0.45);
  transform: translateY(-2px);
}

:deep(.save-btn.p-button:active) {
  transform: translateY(0);
}

/* ─── Animations ─── */
.page-header,
.profile-card {
  animation: fadeInUp 0.4s ease-out both;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(14px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* ─── Dark Mode ─── */
@media (prefers-color-scheme: dark) {
  .user-profile-page {
    --surface: #1e293b;
    --surface-alt: #0f172a;
    --text-primary: #f1f5f9;
    --text-secondary: #cbd5e1;
    --text-muted: #64748b;
    --border: #334155;
    --border-hover: #475569;
    --primary-bg: rgba(37, 99, 235, 0.12);
    --primary-bg-deep: rgba(37, 99, 235, 0.2);
  }

  .page-header {
    background: #1e293b;
    border-bottom-color: #334155;
  }

  .header-badge {
    color: var(--primary-light);
    background: rgba(37, 99, 235, 0.15);
    border-color: rgba(37, 99, 235, 0.25);
  }

  :deep(.p-inputtext),
  :deep(.p-datepicker) {
    background: #374151;
    border-color: #4b5563;
    color: var(--text-primary);
  }

  :deep(.p-radiobutton .p-radiobutton-box) {
    background: #374151;
    border-color: #4b5563;
  }
}

/* ─── Responsive ─── */
@media (max-width: 768px) {
  :deep(.profile-card .p-card-content) {
    padding: 1rem;
  }

  .text-right {
    text-align: left;
  }
}
</style>
