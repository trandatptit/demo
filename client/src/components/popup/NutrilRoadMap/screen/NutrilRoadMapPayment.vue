<template>
  <section class="nutril-roadmap-payment mx-auto px-4 lg:px-8 py-8 lg:py-12 bg-white dark:bg-gray-900">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section - Full Width -->
      <div class="col-span-12">
        <div class="flex justify-between items-center mb-6">
          <!-- Back Button -->
          <Button
            @click="$emit('back')"
            label="Quay Lại"
            icon="pi pi-arrow-left"
            text
            severity="secondary"
            size="small"
            class="!p-0"
          />

          <!-- Close Button -->
          <Button
            @click="$emit('close')"
            icon="pi pi-times"
            rounded
            text
            severity="secondary"
            size="small"
          />
        </div>
      </div>

      <!-- Title Section - Full Width -->
      <div class="col-span-12 text-center mb-2">
        <h2 class="text-2xl sm:text-3xl lg:text-4xl font-bold text-gray-800 dark:text-white">
          {{ title }}
        </h2>
        <p class="text-xs md:text-sm text-gray-600 dark:text-gray-400 mt-2">
          {{ description }}
        </p>
      </div>

      <!-- Amount Section - Full Width -->
      <div class="col-span-12">
        <div class="amount-section">
          <span class="amount-label">Số tiền:</span>
          <span class="amount-value">{{ formattedAmount }}</span>
        </div>
      </div>

      <!-- QR Code Section - Full Width -->
      <div class="col-span-12">
        <div class="qr-section">
          <div class="qr-container">
            <img 
              :src="qrCodeUrl" 
              :alt="qrCodeAlt"
              class="qr-image"
            />
          </div>
          
          <div class="transaction-info">
            <i class="pi pi-info-circle info-icon"></i>
            <div class="info-content">
              <span class="info-label">Nội dung chuyển khoản:</span>
              <span class="info-value">{{ transactionContent }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Action Section - Full Width -->
      <div class="col-span-12 mt-2">
        <div class="action-section">
          <!-- Waiting Status -->
          <div v-if="!isPaid" class="status-waiting">
            <ProgressSpinner 
              style="width: 20px; height: 20px" 
              strokeWidth="4"
              class="status-spinner"
            />
            <span class="status-text">{{ statusText }}</span>
          </div>

          <!-- Success Button -->
          <Button 
            v-if="isPaid"
            :label="successButtonLabel"
            icon="pi pi-check-circle"
            class="success-btn w-full"
            @click="handleSuccess"
            :pt="{
              root: { 
                class: 'bg-gradient-to-r from-green-600 to-green-700 hover:from-green-700 hover:to-green-800 border-0 py-4 text-base md:text-lg font-semibold shadow-lg hover:shadow-xl transition-all duration-200'
              }
            }"
          />
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, watch, onUnmounted } from 'vue';
import Button from 'primevue/button';
import ProgressSpinner from 'primevue/progressspinner';

const props = defineProps({
  title: {
    type: String,
    default: 'Quét mã để thanh toán'
  },
  description: {
    type: String,
    default: 'Sử dụng ứng dụng ngân hàng hoặc ví điện tử để quét mã VietQR'
  },
  amount: {
    type: Number,
    required: true
  },
  qrCodeUrl: {
    type: String,
    required: true
  },
  qrCodeAlt: {
    type: String,
    default: 'QR Code thanh toán'
  },
  transactionContent: {
    type: String,
    required: true
  },
  statusText: {
    type: String,
    default: 'Chờ thanh toán...'
  },
  successButtonLabel: {
    type: String,
    default: 'Đã thanh toán'
  },
  autoPaymentDelay: {
    type: Number,
    default: 5000 // 5 seconds
  },
  autoStart: {
    type: Boolean,
    default: true
  }
});

const emit = defineEmits(['back', 'close', 'paymentSuccess']);

const isPaid = ref(false);
let paymentTimer = null;

// Format amount với dấu phân cách
const formattedAmount = computed(() => {
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND'
  }).format(props.amount);
});

// Start timer để fake payment success sau 5s
const startPaymentTimer = () => {
  clearPaymentTimer(); // Clear existing timer nếu có
  
  paymentTimer = setTimeout(() => {
    isPaid.value = true;
  }, props.autoPaymentDelay);
};

// Clear payment timer
const clearPaymentTimer = () => {
  if (paymentTimer) {
    clearTimeout(paymentTimer);
    paymentTimer = null;
  }
};

const handleSuccess = () => {
  emit('paymentSuccess');
};

// Auto start timer nếu enabled
if (props.autoStart) {
  startPaymentTimer();
}

// Cleanup timer khi component unmount
onUnmounted(() => {
  clearPaymentTimer();
});

// Expose methods để parent có thể control
defineExpose({
  startPaymentTimer,
  clearPaymentTimer,
  isPaid
});
</script>

<style scoped>
/* Base Section Styles */
.nutril-roadmap-payment {
  max-width: 1200px;
  border-radius: 1.5rem;
}

.amount-section {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 12px 16px;
  background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
  border-radius: 10px;
  border: 2px solid #bae6fd;
  margin: 0 auto;
  max-width: 400px;
}

.amount-label {
  font-size: 14px;
  color: #0369a1;
  font-weight: 600;
}

.amount-value {
  font-size: 18px;
  color: #0c4a6e;
  font-weight: 700;
}

.qr-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.qr-container {
  background: white;
  padding: 16px;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  border: 3px solid #e2e8f0;
  transition: all 0.3s ease;
}

.qr-container:hover {
  border-color: #cbd5e1;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.12);
  transform: translateY(-2px);
}

.qr-image {
  width: 200px;
  height: 200px;
  display: block;
  border-radius: 8px;
}

.transaction-info {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  padding: 14px 16px;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  border-radius: 10px;
  border: 2px solid #e2e8f0;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
}

.info-icon {
  font-size: 18px;
  color: #3b82f6;
  flex-shrink: 0;
  margin-top: 2px;
}

.info-content {
  display: flex;
  flex-direction: column;
  gap: 4px;
  flex: 1;
}

.info-label {
  font-size: 12px;
  color: #64748b;
  font-weight: 500;
}

.info-value {
  font-size: 14px;
  color: #1e293b;
  font-weight: 700;
  font-family: 'Courier New', monospace;
  letter-spacing: 0.5px;
}

.action-section {
  min-height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.status-waiting {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  padding: 14px 20px;
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  border-radius: 10px;
  border: 2px solid #fcd34d;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% {
    box-shadow: 0 0 0 0 rgba(251, 191, 36, 0.4);
  }
  50% {
    box-shadow: 0 0 0 8px rgba(251, 191, 36, 0);
  }
}

.status-spinner {
  flex-shrink: 0;
}

.status-text {
  font-size: 14px;
  color: #92400e;
  font-weight: 600;
}

.success-btn {
  position: relative;
  overflow: hidden;
  max-width: 400px;
  margin: 0 auto;
}

.success-btn::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  transform: translate(-50%, -50%);
  transition: width 0.6s, height 0.6s;
}

.success-btn:hover::before {
  width: 300px;
  height: 300px;
}

/* Dark mode adjustments */
@media (prefers-color-scheme: dark) {
  .amount-section {
    background: linear-gradient(135deg, #1e3a8a 0%, #1e293b 100%);
    border-color: #3b82f6;
  }
  
  .amount-label {
    color: #93c5fd;
  }
  
  .amount-value {
    color: #dbeafe;
  }
  
  .qr-container {
    background: #1f2937;
    border-color: #374151;
  }
  
  .transaction-info {
    background: linear-gradient(135deg, #1f2937 0%, #111827 100%);
    border-color: #374151;
  }
  
  .info-label {
    color: #9ca3af;
  }
  
  .info-value {
    color: #e5e7eb;
  }
  
  .status-waiting {
    background: linear-gradient(135deg, #78350f 0%, #451a03 100%);
    border-color: #92400e;
  }
  
  .status-text {
    color: #fde68a;
  }
}

/* Grid Responsive Adjustments */
@media (max-width: 640px) {
  .qr-image {
    width: 180px;
    height: 180px;
  }
  
  .amount-value {
    font-size: 16px;
  }
  
  .transaction-info {
    padding: 12px 14px;
  }
  
  .info-value {
    font-size: 13px;
  }
  
  .status-text {
    font-size: 13px;
  }
}

@media (min-width: 768px) {
  .qr-image {
    width: 220px;
    height: 220px;
  }
  
  .qr-container {
    padding: 20px;
  }
  
  .amount-section {
    padding: 14px 20px;
  }
  
  .amount-value {
    font-size: 20px;
  }
  
  .info-value {
    font-size: 15px;
  }
}

@media (min-width: 1024px) {
  .nutril-roadmap-payment {
    padding: 2rem 2.5rem;
  }
}

/* Smooth transitions for grid layout changes */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

/* Success animation */
@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.success-btn {
  animation: slideIn 0.4s ease;
}
</style>