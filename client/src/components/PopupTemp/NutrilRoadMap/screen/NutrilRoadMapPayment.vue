<template>
  <section class="nutril-roadmap-payment">
    <!-- Container 12 Grid Main -->
    <div class="grid grid-cols-12 gap-4 lg:gap-6">
      <!-- Header Section -->
      <div class="col-span-12">
        <div class="flex justify-between items-center mb-4">
          <!-- Back Button -->
          <Button
            @click="$emit('back')"
            label="Quay Lại"
            icon="pi pi-arrow-left"
            text
            class="back-btn"
          />

          <!-- Close Button -->
          <Button
            @click="$emit('close')"
            icon="pi pi-times"
            rounded
            text
            class="close-btn"
          />
        </div>
      </div>

      <!-- Title Section -->
      <div class="col-span-12 survey-header">
        <div class="header-accent"></div>
        <span class="header-badge">
          <i class="pi pi-credit-card"></i> Thanh Toán
        </span>
        <h2 class="header-title">{{ title }}</h2>
        <p class="header-subtitle">{{ description }}</p>
      </div>

      <!-- Amount Section -->
      <div class="col-span-12">
        <div class="amount-section">
          <span class="amount-label">Số tiền:</span>
          <span class="amount-value">{{ formattedAmount }}</span>
        </div>
      </div>

      <!-- QR Code Section -->
      <div class="col-span-12">
        <div class="qr-section">
          <div class="qr-container">
            <img :src="qrCodeUrl" :alt="qrCodeAlt" class="qr-image" />
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

      <!-- Action Section -->
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
          />
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, watch, onUnmounted } from "vue";
import Button from "primevue/button";
import ProgressSpinner from "primevue/progressspinner";

const props = defineProps({
  title: {
    type: String,
    default: "Quét mã để thanh toán",
  },
  description: {
    type: String,
    default: "Sử dụng ứng dụng ngân hàng hoặc ví điện tử để quét mã VietQR",
  },
  amount: {
    type: Number,
    required: true,
  },
  qrCodeUrl: {
    type: String,
    required: true,
  },
  qrCodeAlt: {
    type: String,
    default: "QR Code thanh toán",
  },
  transactionContent: {
    type: String,
    required: true,
  },
  statusText: {
    type: String,
    default: "Chờ thanh toán...",
  },
  successButtonLabel: {
    type: String,
    default: "Đã thanh toán",
  },
  autoPaymentDelay: {
    type: Number,
    default: 5000, // 5 seconds
  },
  autoStart: {
    type: Boolean,
    default: true,
  },
});

const emit = defineEmits(["back", "close", "paymentSuccess"]);

const isPaid = ref(false);
let paymentTimer = null;

// Format amount với dấu phân cách
const formattedAmount = computed(() => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
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
  emit("paymentSuccess");
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
  isPaid,
});
</script>

<style scoped>
/* ─── Design Tokens (matching NutrilRoadMapSchedule) ─── */
.nutril-roadmap-payment {
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
  --success: #059669;
  --success-light: #10b981;
  --success-bg: #ecfdf5;
  --warning-bg: #fffbeb;
  --warning-border: #fcd34d;
  --warning-text: #92400e;

  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
}

/* ─── Header ─── */
.survey-header {
  text-align: center;
  margin-bottom: 0.5rem;
}

.header-accent {
  width: 64px;
  height: 4px;
  border-radius: 4px;
  background: linear-gradient(90deg, var(--primary), var(--primary-light));
  margin: 0 auto 1rem;
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
  margin-bottom: 12px;
}

.header-title {
  font-size: clamp(1.35rem, 3vw, 2rem);
  font-weight: 800;
  color: var(--text-primary);
  margin: 0 0 6px;
  line-height: 1.25;
}

.header-subtitle {
  font-size: 0.85rem;
  font-weight: 500;
  color: var(--text-secondary);
  margin: 0;
}

/* ─── Back/Close Buttons ─── */
:deep(.back-btn) {
  color: var(--text-secondary) !important;
  font-weight: 600 !important;
}

:deep(.back-btn:hover) {
  color: var(--primary) !important;
}

:deep(.close-btn) {
  color: var(--text-muted) !important;
}

:deep(.close-btn:hover) {
  color: var(--text-primary) !important;
  background: var(--surface-alt) !important;
}

/* ─── Amount Section ─── */
.amount-section {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 14px 20px;
  background: var(--primary-bg);
  border-radius: var(--radius);
  border: 1px solid var(--primary-bg-deep);
  margin: 0 auto;
  max-width: 400px;
}

.amount-label {
  font-size: 0.9rem;
  color: var(--primary);
  font-weight: 600;
}

.amount-value {
  font-size: 1.15rem;
  color: var(--primary-dark);
  font-weight: 800;
}

/* ─── QR Section ─── */
.qr-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.qr-container {
  background: var(--surface);
  padding: 16px;
  border-radius: 16px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.qr-container::before {
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

.qr-container:hover {
  border-color: var(--border-hover);
  box-shadow: var(--shadow-lg);
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
  background: var(--surface-alt);
  border-radius: var(--radius);
  border: 1px solid var(--border);
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
}

.info-icon {
  font-size: 18px;
  color: var(--primary);
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
  font-size: 0.75rem;
  color: var(--text-muted);
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.3px;
}

.info-value {
  font-size: 0.9rem;
  color: var(--text-primary);
  font-weight: 700;
  font-family: "Courier New", monospace;
  letter-spacing: 0.5px;
}

/* ─── Action Section ─── */
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
  background: var(--warning-bg);
  border-radius: var(--radius);
  border: 1.5px solid var(--warning-border);
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%,
  100% {
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
  font-size: 0.9rem;
  color: var(--warning-text);
  font-weight: 600;
}

/* ─── Success Button ─── */
:deep(.success-btn.p-button) {
  background: linear-gradient(
    135deg,
    var(--success) 0%,
    var(--success-light) 100%
  );
  border: none;
  padding: 14px 24px;
  font-size: 1rem;
  font-weight: 700;
  border-radius: var(--radius);
  box-shadow: 0 4px 14px rgba(5, 150, 105, 0.35);
  transition: all 0.25s ease;
  max-width: 400px;
  margin: 0 auto;
  animation: slideIn 0.4s ease;
}

:deep(.success-btn.p-button:hover) {
  box-shadow: 0 6px 20px rgba(5, 150, 105, 0.45);
  transform: translateY(-1px);
}

/* ─── Animations ─── */
.survey-header,
.amount-section,
.qr-container {
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

/* ─── Dark Mode ─── */
@media (prefers-color-scheme: dark) {
  .nutril-roadmap-payment {
    --surface: #1e293b;
    --surface-alt: #0f172a;
    --text-primary: #f1f5f9;
    --text-secondary: #cbd5e1;
    --text-muted: #64748b;
    --border: #334155;
    --border-hover: #475569;
    --primary-bg: rgba(37, 99, 235, 0.12);
    --primary-bg-deep: rgba(37, 99, 235, 0.2);
    --warning-bg: rgba(120, 53, 15, 0.3);
    --warning-border: #92400e;
    --warning-text: #fde68a;
  }

  .header-badge {
    color: var(--primary-light);
    background: rgba(37, 99, 235, 0.15);
    border-color: rgba(37, 99, 235, 0.25);
  }

  .amount-value {
    color: var(--primary-light);
  }
}

/* ─── Responsive ─── */
@media (max-width: 640px) {
  .qr-image {
    width: 180px;
    height: 180px;
  }
}

@media (max-width: 768px) {
  .nutril-roadmap-payment {
    padding: 1.25rem 0.75rem;
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
}

@media (min-width: 1024px) {
  .nutril-roadmap-payment {
    padding: 2.5rem 2.5rem;
  }
}

/* ─── Smooth transitions ─── */
.col-span-12 {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
</style>