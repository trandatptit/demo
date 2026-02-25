<template>
  <div class="card-package-wrapper">
    <!-- Featured Badge - Outside Card -->
    <div v-if="featured && badge" class="badge-container">
      <span class="featured-badge">
        {{ badge }}
      </span>
    </div>

    <Card
      :class="['card-package', featured ? 'card-featured' : 'card-default']"
    >
      <!-- Header Slot - Icon -->
      <template #header>
        <slot name="icon">
          <div :class="{ 'mt-6': featured }" class="pt-4">
            <div :class="['icon-circle', iconBgClass]">
              <i :class="`pi ${icon} text-5xl ${iconColorClass}`"></i>
            </div>
          </div>
        </slot>
      </template>

      <!-- Title Slot -->
      <template #title>
        <slot name="title">
          <h3 class="card-title">{{ title }}</h3>
        </slot>
      </template>

      <!-- Subtitle Slot -->
      <template #subtitle>
        <slot name="subtitle">
          <p class="card-description">{{ description }}</p>
          <p
            v-if="subtitle"
            :class="`text-center text-base font-semibold mt-2 ${subtitleColorClass}`"
          >
            {{ subtitle }}
          </p>
        </slot>
      </template>

      <!-- Content Slot -->
      <template #content>
        <slot name="content" :props="props">
          <!-- Original Price -->
          <div v-if="originalPrice" class="text-center mb-2">
            <p class="original-price">{{ originalPrice }}</p>
          </div>

          <!-- Price or Coming Soon -->
          <div class="text-center mb-4">
            <template v-if="flag">
              <!-- Empty for coming soon -->
            </template>
            <template v-else-if="price">
              <p class="current-price">
                {{ price }}<span class="price-unit">{{ priceUnit }}</span>
              </p>
            </template>
          </div>

          <!-- Warning Message -->
          <div v-if="warning" class="text-center mb-4">
            <div class="warning-box">
              <p class="warning-text">⚠️ {{ warning }}</p>
            </div>
          </div>

          <!-- Features List -->
          <div v-if="features && features.length > 0" class="features-list">
            <div
              v-for="(feature, index) in features"
              :key="index"
              class="feature-item"
            >
              <i
                :class="[
                  'pi text-sm flex-shrink-0 mt-0.5',
                  feature.available !== false
                    ? 'pi-check-circle feature-icon-available'
                    : 'pi-times-circle feature-icon-unavailable',
                ]"
              ></i>
              <span
                :class="[
                  'feature-text',
                  feature.available !== false ? '' : 'feature-text-unavailable',
                ]"
              >
                {{ feature.text || feature }}
              </span>
            </div>
          </div>

          <!-- Custom Content Slot -->
          <slot name="custom-content"></slot>
        </slot>
      </template>

      <!-- Footer Slot -->
      <template #footer>
        <slot name="footer" :handleClick="handleClick" :props="props">
          <div class="flex gap-4 mt-1">
            <Button
              :label="buttonText"
              :disabled="flag"
              :class="[
                'w-full font-semibold py-3',
                flag ? 'secondary-btn' : 'primary-btn',
              ]"
              :severity="flag ? 'secondary' : undefined"
              @click="!flag && handleClick()"
            />
          </div>
        </slot>
      </template>
    </Card>
  </div>
</template>

<script setup>
import Card from "primevue/card";
import Button from "primevue/button";

const props = defineProps({
  // Package info
  title: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  subtitle: {
    type: String,
    default: "",
  },
  subtitleColorClass: {
    type: String,
    default: "text-gray-700 dark:text-gray-300",
  },

  // Icon
  icon: {
    type: String,
    default: "pi-box",
  },
  iconBgClass: {
    type: String,
    default: "bg-blue-100 dark:bg-blue-900/30",
  },
  iconColorClass: {
    type: String,
    default: "text-blue-600 dark:text-blue-400",
  },

  // Featured
  featured: {
    type: Boolean,
    default: false,
  },
  badge: {
    type: String,
    default: "",
  },

  // Pricing
  price: {
    type: String,
    default: "",
  },
  priceUnit: {
    type: String,
    default: "/tháng",
  },
  originalPrice: {
    type: String,
    default: "",
  },

  // Features List
  features: {
    type: Array,
    default: () => [],
  },

  // Warning
  warning: {
    type: String,
    default: "",
  },

  // Button
  buttonText: {
    type: String,
    default: "Chọn gói",
  },
  flag: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["select"]);

const handleClick = () => {
  emit("select", props);
};
</script>

<style scoped>
/* ─── Design Tokens (matching NutrilRoadMapSchedule) ─── */
.card-package-wrapper {
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
  --success: #22c55e;
  --danger: #ef4444;

  position: relative;
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
}

/* ─── Featured Badge ─── */
.badge-container {
  position: absolute;
  top: -14px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 50;
}

.featured-badge {
  display: inline-block;
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  );
  color: white;
  font-size: 0.8rem;
  font-weight: 700;
  padding: 6px 20px;
  border-radius: 999px;
  box-shadow: 0 4px 12px rgba(37, 99, 235, 0.35);
  letter-spacing: 0.3px;
}

/* ─── Card Base ─── */
.card-package {
  border-radius: 16px;
  overflow: visible;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
}

.card-default {
  border: 1.5px solid var(--border);
}

.card-featured {
  border: 2px solid var(--primary-light);
  box-shadow: 0 4px 16px rgba(37, 99, 235, 0.12);
}

.card-package:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
}

.card-featured:hover {
  box-shadow: 0 12px 32px rgba(37, 99, 235, 0.18);
}

/* ─── Icon Circle ─── */
.icon-circle {
  width: 96px;
  height: 96px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
}

/* ─── Card Title / Description ─── */
.card-title {
  font-size: 1.35rem;
  font-weight: 800;
  color: var(--text-primary);
  text-align: center;
}

.card-description {
  color: var(--text-secondary);
  text-align: center;
  font-size: 0.9rem;
  line-height: 1.6;
}

/* ─── Pricing ─── */
.original-price {
  color: var(--text-muted);
  text-decoration: line-through;
  font-size: 1.15rem;
}

.current-price {
  font-size: 2.25rem;
  font-weight: 800;
  color: var(--text-primary);
}

.price-unit {
  font-size: 1rem;
  font-weight: 400;
  color: var(--text-secondary);
}

/* ─── Warning ─── */
.warning-box {
  display: inline-block;
  background: #fef2f2;
  padding: 10px 18px;
  border-radius: 10px;
  border: 1px solid #fecaca;
}

.warning-text {
  color: var(--danger);
  font-weight: 700;
  font-size: 0.8rem;
}

/* ─── Features List ─── */
.features-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 16px;
}

.feature-item {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  text-align: left;
}

.feature-icon-available {
  color: var(--success);
}

.feature-icon-unavailable {
  color: var(--text-muted);
}

.feature-text {
  font-size: 0.85rem;
  line-height: 1.5;
  color: var(--text-secondary);
}

.feature-text-unavailable {
  color: var(--text-muted);
  text-decoration: line-through;
}

/* ─── PrimeVue Card Overrides ─── */
:deep(.p-card) {
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow: visible;
  background: var(--surface);
  border-radius: 16px;
}

:deep(.p-card-body) {
  display: flex;
  flex-direction: column;
  flex: 1;
  padding: 2rem;
}

:deep(.p-card-icon) {
  margin-bottom: 1.5rem;
}

:deep(.p-card-title) {
  margin-bottom: 1rem;
}

:deep(.p-card-subtitle) {
  margin-bottom: 1.5rem;
}

:deep(.p-card-content) {
  flex: 1;
  padding-top: 0;
}

:deep(.p-card-footer) {
  padding-top: 1rem;
}

/* ─── Buttons ─── */
:deep(.primary-btn.p-button) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  );
  border: none;
  border-radius: var(--radius);
  font-weight: 700;
  box-shadow: 0 4px 14px rgba(37, 99, 235, 0.35);
  transition: all 0.25s ease;
}

:deep(.primary-btn.p-button:not(:disabled):hover) {
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  );
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(37, 99, 235, 0.45);
}

:deep(.secondary-btn.p-button) {
  border-radius: var(--radius);
  opacity: 0.6;
}

/* ─── Animations ─── */
.card-package-wrapper {
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
  .card-package-wrapper {
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

  .card-featured {
    border-color: var(--primary);
  }

  .warning-box {
    background: rgba(127, 29, 29, 0.3);
    border-color: #7f1d1d;
  }

  .warning-text {
    color: #fca5a5;
  }
}
</style>