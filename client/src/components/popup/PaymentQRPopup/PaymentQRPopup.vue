<template>
  <Dialog
    v-model:visible="visible"
    modal
    :closable="true"
    :draggable="false"
    class="payment-qr-popup"
    :style="{ width: '400px' }"
  >
    <template #header>
      <div class="flex items-center justify-center w-full">
        <h2 class="text-xl font-bold text-gray-800">Quét mã để thanh toán</h2>
      </div>
    </template>

    <div class="flex flex-col items-center py-4">
      <!-- Mô tả -->
      <p class="text-center text-gray-600 mb-2 text-sm px-4">
        Sử dụng ứng dụng ngân hàng để quét mã QR và tiến hành thanh toán
      </p>

      <!-- Số tiền -->
      <div class="text-center mb-4">
        <p class="text-sm text-gray-500">Số tiền</p>
        <p class="text-3xl font-bold text-blue-600">{{ formatAmount }}</p>
      </div>

      <!-- QR Code -->
      <div class="bg-white p-4 rounded-lg shadow-sm mb-4">
        <img :src="qrCodeUrl" alt="QR Code" class="w-64 h-64 object-contain" />
      </div>

      <!-- Nội dung chuyển khoản -->
      <div class="text-center mb-4">
        <p class="text-xs text-gray-500">Nội dung</p>
        <p class="text-sm font-semibold text-gray-700">{{ transferContent }}</p>
      </div>

      <!-- Nút xác nhận -->
      <Button
        :label="buttonLabel"
        class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg"
        @click="handleConfirm"
        :loading="isProcessing"
      />

      <!-- Lưu ý -->
      <p class="text-xs text-gray-400 text-center mt-4 px-4">
        Sau khi thanh toán, vui lòng chờ hệ thống xác nhận
      </p>
    </div>
  </Dialog>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import Dialog from "primevue/dialog";
import Button from "primevue/button";

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  amount: {
    type: Number,
    default: 30000,
  },
  bankAccount: {
    type: String,
    default: "0123456789", // Số tài khoản ngân hàng
  },
  accountName: {
    type: String,
    default: "EVEREST HEIGHT GROWTH", // Tên chủ tài khoản
  },
  bankCode: {
    type: String,
    default: "MB", // Mã ngân hàng (VD: VPBank = 970422, Vietcombank = 970436)
  },
  orderId: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(["update:modelValue", "confirm", "cancel"]);

const visible = computed({
  get: () => props.modelValue,
  set: (value) => emit("update:modelValue", value),
});

const isProcessing = ref(false);
const buttonLabel = ref("Đã thanh toán");

// Format số tiền
const formatAmount = computed(() => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(props.amount);
});

// Nội dung chuyển khoản
const transferContent = computed(() => {
  return props.orderId || `EP ${Date.now().toString().slice(-5)}`;
});

// Generate VietQR URL
const qrCodeUrl = computed(() => {
  // VietQR API format
  // https://img.vietqr.io/image/{BANK_ID}-{ACCOUNT_NO}-{TEMPLATE}.png?amount={AMOUNT}&addInfo={DESCRIPTION}&accountName={ACCOUNT_NAME}

  const template = "compact2"; // hoặc "compact", "qr_only", "print"
  const addInfo = encodeURIComponent(transferContent.value);
  const accountName = encodeURIComponent(props.accountName);

  return `https://img.vietqr.io/image/${props.bankCode}-${props.bankAccount}-${template}.png?amount=${props.amount}&addInfo=${addInfo}&accountName=${accountName}`;
});

// Xử lý xác nhận
const handleConfirm = async () => {
  isProcessing.value = true;
  buttonLabel.value = "Đang xử lý...";

  // Simulate API call
  setTimeout(() => {
    isProcessing.value = false;
    buttonLabel.value = "Đã thanh toán";
    emit("confirm", {
      amount: props.amount,
      orderId: transferContent.value,
      timestamp: new Date().toISOString(),
    });
  }, 1500);
};

// Reset state khi đóng popup
watch(visible, (newVal) => {
  if (!newVal) {
    isProcessing.value = false;
    buttonLabel.value = "Đã thanh toán";
  }
});
</script>

<style scoped>
:deep(.p-dialog-header) {
  padding: 1.5rem 1.5rem 1rem 1.5rem;
  border-bottom: 1px solid #e5e7eb;
}

:deep(.p-dialog-content) {
  padding: 0;
}

:deep(.p-dialog-header-close) {
  position: absolute;
  right: 1rem;
  top: 1rem;
}

.payment-qr-popup {
  border-radius: 1rem;
}

:deep(.p-button) {
  background-color: #2563eb;
}
</style>
