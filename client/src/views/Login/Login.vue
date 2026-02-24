<template>
  <div
    class="login-page bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center px-4 py-8"
  >
    <Toast />
    <div class="w-full max-w-md">
      <!-- Logo and Title -->
      <div class="text-center mb-8">
        <div class="inline-flex items-center justify-center mb-4">
          <div
            class="w-16 h-16 bg-blue-600 rounded-2xl flex items-center justify-center shadow-lg"
          >
            <span class="text-white font-bold text-3xl">E</span>
          </div>
        </div>
        <h1 class="text-3xl font-bold text-gray-800 mb-2">Chào mừng trở lại</h1>
        <p class="text-gray-600">Đăng nhập để tiếp tục với EVEREST</p>
      </div>

      <!-- Login Form Card -->
      <div class="bg-white rounded-2xl shadow-xl p-8">
        <form @submit.prevent="handleLogin">
          <!-- Username Field -->
          <div class="mb-6">
            <label
              for="username"
              class="block text-sm font-semibold text-gray-700 mb-2"
            >
              Tên đăng nhập
            </label>
            <IconField>
              <InputIcon class="pi pi-user" />
              <InputText
                id="username"
                v-model="loginForm.username"
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

          <!-- Password Field -->
          <div class="mb-6">
            <label
              for="password"
              class="block text-sm font-semibold text-gray-700 mb-2"
            >
              Mật khẩu
            </label>
            <IconField>
              <InputIcon class="pi pi-lock" />
              <InputText
                id="password"
                v-model="loginForm.password"
                :type="showPassword ? 'text' : 'password'"
                placeholder="Nhập mật khẩu"
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

          <!-- Error Message -->
          <div
            v-if="loginError"
            class="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg"
          >
            <p class="text-red-600 text-sm flex items-center">
              <i class="pi pi-exclamation-circle mr-2"></i>
              {{ loginError }}
            </p>
          </div>

          <!-- Login Button -->
          <Button
            type="submit"
            label="Đăng nhập"
            :loading="isLoading"
            class="w-full py-3 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition duration-200"
          />
        </form>

        <!-- Sign Up Link -->
        <div class="mt-6 text-center">
          <p class="text-gray-600">
            Chưa có tài khoản?
            <router-link
              to="/register"
              class="text-blue-600 hover:text-blue-700 font-semibold"
            >
              Đăng ký ngay
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
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserInfoStore } from "@/stores/userInfo";
import { useAuthStore } from "@/stores/auth";
import authApi from "@/apis/authApi";
import IconField from "primevue/iconfield";
import InputIcon from "primevue/inputicon";
import { useToast } from "primevue/usetoast";

const router = useRouter();
const userStore = useUserInfoStore();
const authStore = useAuthStore();
const toast = useToast();

// Form data
const loginForm = ref({
  username: "",
  password: "",
});

// UI state
const showPassword = ref(false);
const rememberMe = ref(false);
const isLoading = ref(false);
const loginError = ref("");
const errors = ref({
  username: "",
  password: "",
});

// Toggle password visibility
const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};

// Clear specific error
const clearError = (field) => {
  errors.value[field] = "";
  loginError.value = "";
};

// Validate form
const validateForm = () => {
  let isValid = true;
  errors.value = {
    username: "",
    password: "",
  };

  if (!loginForm.value.username.trim()) {
    errors.value.username = "Vui lòng nhập tên người dùng";
    isValid = false;
  }

  if (!loginForm.value.password) {
    errors.value.password = "Vui lòng nhập mật khẩu";
    isValid = false;
  } else if (loginForm.value.password.length < 4) {
    errors.value.password = "Mật khẩu phải có ít nhất 4 ký tự";
    isValid = false;
  }

  return isValid;
};

// Handle login
const handleLogin = async () => {
  if (!validateForm()) {
    return;
  }

  isLoading.value = true;
  loginError.value = "";

  try {
    const response = await authStore.login({
      username: loginForm.value.username,
      password: loginForm.value.password,
    });

    if (response) {
      toast.add({
        severity: "success",
        summary: "Thành công",
        detail: "Đăng nhập thành công",
        life: 5000,
      });
      // Redirect to home page
      setTimeout(() => {
        // router.push({ name: "HomePage" });
        window.location.href = "/";
      }, 2000);
    } else {
      loginError.value = "Đăng nhập không thành công. Vui lòng thử lại.";
    }
  } catch (error) {
    console.error("Login error:", error);

    if (error.response) {
      switch (error.response.status) {
        case 401:
          loginError.value = "Tên người dùng hoặc mật khẩu không đúng";
          break;
        case 404:
          loginError.value = "Tài khoản không tồn tại";
          break;
        case 500:
          loginError.value = "Lỗi máy chủ. Vui lòng thử lại sau";
          break;
        default:
          loginError.value = "Đã xảy ra lỗi. Vui lòng thử lại";
      }
    } else {
      loginError.value =
        "Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối internet";
    }
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.login-page {
  min-height: calc(100vh - 110px);
}

/* Custom input styles to match HomeBody.vue */
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

:deep(.p-inputtext.border-red-500) {
  border-bottom-color: #ef4444;
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

:deep(.p-button) {
  background-color: #2563eb;
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
button:focus {
  outline: 2px solid transparent;
  outline-offset: 2px;
}
</style>
