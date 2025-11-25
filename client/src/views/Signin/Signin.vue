<template>
  <div
    class="signin-page bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center px-4 py-8"
  >
    <Toast />
    <div class="w-full max-w-2xl">
      <!-- Logo and Title -->
      <div class="text-center mb-8">
        <div class="inline-flex items-center justify-center mb-4">
          <div
            class="w-16 h-16 bg-blue-600 rounded-2xl flex items-center justify-center shadow-lg"
          >
            <span class="text-white font-bold text-3xl">E</span>
          </div>
        </div>
        <h1 class="text-3xl font-bold text-gray-800 mb-2">Tạo tài khoản mới</h1>
        <p class="text-gray-600">Đăng ký để bắt đầu hành trình với EVEREST</p>
      </div>

      <!-- Signin Form Card -->
      <div class="bg-white rounded-2xl shadow-xl p-8">
        <form @submit.prevent="handleSignin">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- Username Field -->
            <div class="md:col-span-2">
              <label
                for="username"
                class="block text-sm font-semibold text-gray-700 mb-2"
              >
                Tên đăng nhập <span class="text-red-500">*</span>
              </label>
              <IconField>
                <InputIcon class="pi pi-user" />
                <InputText
                  id="username"
                  v-model="signinForm.username"
                  type="text"
                  placeholder="Nhập tên đăng nhập"
                  class="w-full"
                  :class="{ 'border-red-500': errors.username }"
                  @input="clearError('username')"
                />
              </IconField>
              <span
                v-if="errors.username"
                class="text-red-500 text-sm mt-1 block"
              >
                {{ errors.username }}
              </span>
            </div>

            <!-- Full Name Field -->
            <div>
              <label
                for="fullName"
                class="block text-sm font-semibold text-gray-700 mb-2"
              >
                Tên <span class="text-red-500">*</span>
              </label>
              <IconField>
                <InputIcon class="pi pi-id-card" />
                <InputText
                  id="fullName"
                  v-model="signinForm.fullName"
                  type="text"
                  placeholder="Nhập họ và tên"
                  class="w-full"
                  :class="{ 'border-red-500': errors.fullName }"
                  @input="clearError('fullName')"
                />
              </IconField>
              <span
                v-if="errors.fullName"
                class="text-red-500 text-sm mt-1 block"
              >
                {{ errors.fullName }}
              </span>
            </div>

            <!-- Email Field -->
            <div>
              <label
                for="email"
                class="block text-sm font-semibold text-gray-700 mb-2"
              >
                Email <span class="text-red-500">*</span>
              </label>
              <IconField>
                <InputIcon class="pi pi-envelope" />
                <InputText
                  id="email"
                  v-model="signinForm.email"
                  type="email"
                  placeholder="example@email.com"
                  class="w-full"
                  :class="{ 'border-red-500': errors.email }"
                  @input="clearError('email')"
                />
              </IconField>
              <span v-if="errors.email" class="text-red-500 text-sm mt-1 block">
                {{ errors.email }}
              </span>
            </div>

            <!-- Phone Number Field -->
            <div>
              <label
                for="phone"
                class="block text-sm font-semibold text-gray-700 mb-2"
              >
                Số điện thoại <span class="text-red-500">*</span>
              </label>
              <IconField>
                <InputIcon class="pi pi-phone" />
                <InputText
                  id="phone"
                  v-model="signinForm.phone"
                  type="tel"
                  placeholder="0xxxxxxxxx"
                  class="w-full"
                  :class="{ 'border-red-500': errors.phone }"
                  @input="clearError('phone')"
                />
              </IconField>
              <span v-if="errors.phone" class="text-red-500 text-sm mt-1 block">
                {{ errors.phone }}
              </span>
            </div>

            <!-- Gender Field -->
            <div>
              <label
                for="gender"
                class="block text-sm font-semibold text-gray-700 mb-2"
              >
                Giới tính <span class="text-red-500">*</span>
              </label>
              <div class="flex gap-6 mt-3">
                <div
                  v-for="option in genderOptions"
                  :key="option.value"
                  class="flex items-center"
                >
                  <RadioButton
                    v-model="signinForm.gender"
                    :inputId="'gender-' + option.value"
                    :value="option.value"
                    @change="clearError('gender')"
                  />
                  <label
                    :for="'gender-' + option.value"
                    class="ml-2 cursor-pointer text-gray-700"
                  >
                    {{ option.label }}
                  </label>
                </div>
              </div>
              <span
                v-if="errors.gender"
                class="text-red-500 text-sm mt-1 block"
              >
                {{ errors.gender }}
              </span>
            </div>

            <!-- Date of Birth Field -->
            <div>
              <label
                for="dateOfBirth"
                class="block text-sm font-semibold text-gray-700 mb-2"
              >
                Ngày sinh <span class="text-red-500">*</span>
              </label>
              <DatePicker
                v-model="signinForm.dateOfBirth"
                dateFormat="dd/mm/yy"
                placeholder="Chọn ngày sinh"
                class="w-full"
                :class="{ 'border-red-500': errors.dateOfBirth }"
                showIcon
                :maxDate="maxDate"
                @date-select="clearError('dateOfBirth')"
              />
              <span
                v-if="errors.dateOfBirth"
                class="text-red-500 text-sm mt-1 block"
              >
                {{ errors.dateOfBirth }}
              </span>
            </div>

            <!-- Password Field -->
            <div class="md:col-span-2">
              <label
                for="password"
                class="block text-sm font-semibold text-gray-700 mb-2"
              >
                Mật khẩu <span class="text-red-500">*</span>
              </label>
              <IconField>
                <InputIcon class="pi pi-lock" />
                <InputText
                  id="password"
                  v-model="signinForm.password"
                  :type="showPassword ? 'text' : 'password'"
                  placeholder="Nhập mật khẩu (tối thiểu 6 ký tự)"
                  class="w-full"
                  :class="{ 'border-red-500': errors.password }"
                  @input="clearError('password')"
                />
                <InputIcon
                  :class="showPassword ? 'pi pi-eye-slash' : 'pi pi-eye'"
                  class="cursor-pointer"
                  @click="togglePasswordVisibility"
                />
              </IconField>
              <span
                v-if="errors.password"
                class="text-red-500 text-sm mt-1 block"
              >
                {{ errors.password }}
              </span>
            </div>

            <!-- Confirm Password Field -->
            <div class="md:col-span-2">
              <label
                for="confirmPassword"
                class="block text-sm font-semibold text-gray-700 mb-2"
              >
                Xác nhận mật khẩu <span class="text-red-500">*</span>
              </label>
              <IconField>
                <InputIcon class="pi pi-lock" />
                <InputText
                  id="confirmPassword"
                  v-model="signinForm.confirmPassword"
                  :type="showConfirmPassword ? 'text' : 'password'"
                  placeholder="Nhập lại mật khẩu"
                  class="w-full"
                  :class="{ 'border-red-500': errors.confirmPassword }"
                  @input="clearError('confirmPassword')"
                />
                <InputIcon
                  :class="showConfirmPassword ? 'pi pi-eye-slash' : 'pi pi-eye'"
                  class="cursor-pointer"
                  @click="toggleConfirmPasswordVisibility"
                />
              </IconField>
              <span
                v-if="errors.confirmPassword"
                class="text-red-500 text-sm mt-1 block"
              >
                {{ errors.confirmPassword }}
              </span>
            </div>
          </div>

          <!-- Error Message -->
          <div
            v-if="signinError"
            class="mt-6 p-3 bg-red-50 border border-red-200 rounded-lg"
          >
            <p class="text-red-600 text-sm flex items-center">
              <i class="pi pi-exclamation-circle mr-2"></i>
              {{ signinError }}
            </p>
          </div>

          <!-- Terms and Conditions -->
          <div class="mt-6">
            <div class="flex items-start">
              <Checkbox
                v-model="acceptTerms"
                inputId="terms"
                :binary="true"
                class="mr-2"
              />
              <label for="terms" class="text-sm text-gray-600">
                Tôi đồng ý với
                <a href="#" class="text-blue-600 hover:text-blue-700">
                  Điều khoản dịch vụ
                </a>
                và
                <a href="#" class="text-blue-600 hover:text-blue-700">
                  Chính sách bảo mật
                </a>
              </label>
            </div>
            <span v-if="errors.terms" class="text-red-500 text-sm mt-1 block">
              {{ errors.terms }}
            </span>
          </div>

          <!-- Signin Button -->
          <Button
            type="submit"
            label="Đăng ký"
            :loading="isLoading"
            class="w-full mt-6 py-3 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition duration-200"
          />
        </form>

        <!-- Login Link -->
        <div class="mt-6 text-center">
          <p class="text-gray-600">
            Đã có tài khoản?
            <router-link
              to="/login"
              class="text-blue-600 hover:text-blue-700 font-semibold"
            >
              Đăng nhập ngay
            </router-link>
          </p>
        </div>
      </div>

      <!-- Footer Text -->
      <p class="text-center text-gray-500 text-sm mt-8">
        © 2025 EVEREST. All rights reserved.
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useToast } from "primevue/usetoast";
import IconField from "primevue/iconfield";
import InputIcon from "primevue/inputicon";
import RadioButton from "primevue/radiobutton";
import DatePicker from "primevue/datepicker";
import Checkbox from "primevue/checkbox";
import genderType from "@/constants/genderType";
import authApi from "@/apis/authApi";

const router = useRouter();
const toast = useToast();

// Form data
const signinForm = ref({
  username: "",
  fullName: "",
  email: "",
  phone: "",
  gender: null,
  dateOfBirth: null,
  password: "",
  confirmPassword: "",
});

// UI state
const showPassword = ref(false);
const showConfirmPassword = ref(false);
const acceptTerms = ref(false);
const isLoading = ref(false);
const signinError = ref("");
const errors = ref({
  username: "",
  fullName: "",
  email: "",
  phone: "",
  gender: "",
  dateOfBirth: "",
  password: "",
  confirmPassword: "",
  terms: "",
});

// Gender options
const genderOptions = [
  { label: "Nam", value: genderType.MALE },
  { label: "Nữ", value: genderType.FEMALE },
];

// Max date (must be at least 1 year old)
const maxDate = computed(() => {
  const date = new Date();
  date.setFullYear(date.getFullYear() - 1);
  return date;
});

// Toggle password visibility
const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

const toggleConfirmPasswordVisibility = () => {
  showConfirmPassword.value = !showConfirmPassword.value;
};

// Clear specific error
const clearError = (field) => {
  errors.value[field] = "";
  signinError.value = "";
};

// Validate email format
const isValidEmail = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

// Validate phone number (Vietnamese format)
const isValidPhone = (phone) => {
  const phoneRegex = /^(0|\+84)[0-9]{9}$/;
  return phoneRegex.test(phone.replace(/\s/g, ""));
};

// Validate form
const validateForm = () => {
  let isValid = true;
  errors.value = {
    username: "",
    fullName: "",
    email: "",
    phone: "",
    gender: "",
    dateOfBirth: "",
    password: "",
    confirmPassword: "",
    terms: "",
  };

  // Username validation
  if (!signinForm.value.username.trim()) {
    errors.value.username = "Vui lòng nhập tên đăng nhập";
    isValid = false;
  } else if (signinForm.value.username.length < 4) {
    errors.value.username = "Tên đăng nhập phải có ít nhất 4 ký tự";
    isValid = false;
  } else if (!/^[a-zA-Z0-9_]+$/.test(signinForm.value.username)) {
    errors.value.username =
      "Tên đăng nhập chỉ được chứa chữ, số và dấu gạch dưới";
    isValid = false;
  }

  // Full name validation
  if (!signinForm.value.fullName.trim()) {
    errors.value.fullName = "Vui lòng nhập họ và tên";
    isValid = false;
  } else if (signinForm.value.fullName.length < 2) {
    errors.value.fullName = "Tên phải có ít nhất 2 ký tự";
    isValid = false;
  }

  // Email validation
  if (!signinForm.value.email.trim()) {
    errors.value.email = "Vui lòng nhập email";
    isValid = false;
  } else if (!isValidEmail(signinForm.value.email)) {
    errors.value.email = "Email không hợp lệ";
    isValid = false;
  }

  // Phone validation
  if (!signinForm.value.phone.trim()) {
    errors.value.phone = "Vui lòng nhập số điện thoại";
    isValid = false;
  } else if (!isValidPhone(signinForm.value.phone)) {
    errors.value.phone = "Số điện thoại không hợp lệ";
    isValid = false;
  }

  // Gender validation
  if (!signinForm.value.gender) {
    errors.value.gender = "Vui lòng chọn giới tính";
    isValid = false;
  }

  // Date of birth validation
  if (!signinForm.value.dateOfBirth) {
    errors.value.dateOfBirth = "Vui lòng chọn ngày sinh";
    isValid = false;
  }

  // Password validation
  if (!signinForm.value.password) {
    errors.value.password = "Vui lòng nhập mật khẩu";
    isValid = false;
  } else if (signinForm.value.password.length < 6) {
    errors.value.password = "Mật khẩu phải có ít nhất 6 ký tự";
    isValid = false;
  }

  // Confirm password validation
  if (!signinForm.value.confirmPassword) {
    errors.value.confirmPassword = "Vui lòng xác nhận mật khẩu";
    isValid = false;
  } else if (signinForm.value.password !== signinForm.value.confirmPassword) {
    errors.value.confirmPassword = "Mật khẩu xác nhận không khớp";
    isValid = false;
  }

  // Terms validation
  if (!acceptTerms.value) {
    errors.value.terms = "Bạn phải đồng ý với điều khoản dịch vụ";
    isValid = false;
  }

  return isValid;
};

// Format date for API
const formatDateForAPI = (date) => {
  if (!date) return null;
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

// Handle signin
const handleSignin = async () => {
  if (!validateForm()) {
    return;
  }

  isLoading.value = true;
  signinError.value = "";

  try {
    // Prepare data for API
    const registrationData = {
      username: signinForm.value.username,
      fullName: signinForm.value.fullName,
      email: signinForm.value.email,
      phone: signinForm.value.phone,
      gender: signinForm.value.gender,
      dateOfBirth: formatDateForAPI(signinForm.value.dateOfBirth),
      password: signinForm.value.password,
    };

    // Call registration API
    const response = await authApi.register(registrationData);

    if (response) {
      toast.add({
        severity: "success",
        summary: "Thành công",
        detail: "Đăng ký tài khoản thành công! Vui lòng đăng nhập.",
        life: 5000,
      });

      // Redirect to login page after 2 seconds
      setTimeout(() => {
        router.push({ path: "/login" });
      }, 2000);
    } else {
      signinError.value = "Đăng ký không thành công. Vui lòng thử lại.";
    }
  } catch (error) {
    console.error("Signin error:", error);

    if (error.response) {
      switch (error.response.status) {
        case 400:
          signinError.value =
            error.response.data?.message ||
            "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.";
          break;
        case 409:
          signinError.value = "Tên đăng nhập hoặc email đã tồn tại";
          break;
        case 500:
          signinError.value = "Lỗi máy chủ. Vui lòng thử lại sau";
          break;
        default:
          signinError.value = "Đã xảy ra lỗi. Vui lòng thử lại";
      }
    } else {
      signinError.value =
        "Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối internet";
    }
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.signin-page {
  min-height: calc(100vh - 110px);
}

/* Custom input styles to match Login.vue */
:deep(.p-inputtext),
:deep(.p-inputnumber-input),
:deep(.p-dropdown),
:deep(.p-datepicker-input) {
  border: none;
  border-bottom: 2px solid #d1d5db;
  border-radius: 0;
  padding: 8px 0;
  transition: border-bottom-color 0.2s ease;
}

:deep(.p-inputtext:hover),
:deep(.p-inputnumber-input:hover),
:deep(.p-dropdown:hover),
:deep(.p-datepicker-input:hover) {
  border-bottom-color: #3b82f6;
}

:deep(.p-inputtext:focus),
:deep(.p-inputnumber-input:focus),
:deep(.p-dropdown:focus),
:deep(.p-datepicker-input:focus) {
  border-bottom-color: #2563eb;
  box-shadow: none;
}

:deep(.p-inputtext.border-red-500),
:deep(.p-dropdown.border-red-500),
:deep(.p-datepicker.border-red-500) {
  border-bottom-color: #ef4444;
}

/* Dropdown custom styles */
:deep(.p-dropdown) {
  width: 100%;
}

:deep(.p-dropdown-label) {
  padding: 8px 0;
}

/* DatePicker custom styles */
:deep(.p-datepicker) {
  width: 100%;
}

:deep(.p-datepicker-input-icon-container) {
  right: 0;
}

/* Checkbox styles */
:deep(.p-checkbox) {
  width: 20px;
  height: 20px;
}

/* Button hover effect */
:deep(.p-button) {
  transition: all 0.2s ease;
}

:deep(.p-button:hover) {
  transform: translateY(-1px);
}

/* Animation for error messages */
.text-red-500 {
  animation: shake 0.3s ease;
}

@keyframes shake {
  0%,
  100% {
    transform: translateX(0);
  }
  25% {
    transform: translateX(-5px);
  }
  75% {
    transform: translateX(5px);
  }
}

/* Focus ring for accessibility */
input:focus,
button:focus,
select:focus {
  outline: 2px solid transparent;
  outline-offset: 2px;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .signin-page {
    padding: 1rem;
  }

  :deep(.p-inputtext),
  :deep(.p-dropdown),
  :deep(.p-datepicker-input) {
    font-size: 16px; /* Prevent zoom on mobile */
  }
}
</style>
