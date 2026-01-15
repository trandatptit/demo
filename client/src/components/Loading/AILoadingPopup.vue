<template>
  <Teleport to="body">
    <Transition name="fade">
      <div v-if="loadingStore.globalLoading" class="ai-loading-overlay">
        <div class="ai-loading-popup">
          <!-- Brain Icon with Pulse Animation -->
          <div class="ai-icon-container">
            <div class="ai-brain-ring"></div>
            <div class="ai-brain-ring ai-brain-ring-2"></div>
            <div class="ai-brain-ring ai-brain-ring-3"></div>
            <i class="pi pi-sparkles ai-brain-icon"></i>
          </div>

          <!-- Title -->
          <h3 class="ai-loading-title">{{ loadingStore.loadingMessage }}</h3>

          <!-- Loading Dots -->
          <div class="ai-loading-dots">
            <span class="ai-dot"></span>
            <span class="ai-dot"></span>
            <span class="ai-dot"></span>
          </div>

          <!-- Progress Bar -->
          <div class="ai-progress-container">
            <div class="ai-progress-bar"></div>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { useLoadingStore } from "@/stores/globalLoading.js";

const loadingStore = useLoadingStore();
</script>

<style scoped>
.ai-loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.6);
  backdrop-filter: blur(8px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.ai-loading-popup {
  background: linear-gradient(135deg, #1e3a5f 0%, #0d1b2a 100%);
  border-radius: 24px;
  padding: 48px 56px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5),
    0 0 60px rgba(59, 130, 246, 0.3), inset 0 1px 0 rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(59, 130, 246, 0.3);
  min-width: 320px;
}

/* Brain Icon Container */
.ai-icon-container {
  position: relative;
  width: 100px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 24px;
}

.ai-brain-icon {
  font-size: 48px;
  color: #60a5fa;
  z-index: 10;
  animation: pulse-glow 2s ease-in-out infinite;
}

/* Rotating Rings */
.ai-brain-ring {
  position: absolute;
  width: 100%;
  height: 100%;
  border: 3px solid transparent;
  border-top-color: #3b82f6;
  border-radius: 50%;
  animation: spin 2s linear infinite;
}

.ai-brain-ring-2 {
  width: 80%;
  height: 80%;
  border-top-color: #60a5fa;
  animation: spin-reverse 1.5s linear infinite;
}

.ai-brain-ring-3 {
  width: 60%;
  height: 60%;
  border-top-color: #93c5fd;
  animation: spin 1s linear infinite;
}

/* Title */
.ai-loading-title {
  color: #ffffff;
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0 0 20px 0;
  text-align: center;
  text-shadow: 0 2px 10px rgba(59, 130, 246, 0.5);
}

/* Loading Dots */
.ai-loading-dots {
  display: flex;
  gap: 8px;
  margin-bottom: 24px;
}

.ai-dot {
  width: 12px;
  height: 12px;
  background: #3b82f6;
  border-radius: 50%;
  animation: bounce 1.4s ease-in-out infinite both;
}

.ai-dot:nth-child(1) {
  animation-delay: -0.32s;
}

.ai-dot:nth-child(2) {
  animation-delay: -0.16s;
}

.ai-dot:nth-child(3) {
  animation-delay: 0s;
}

/* Progress Bar */
.ai-progress-container {
  width: 200px;
  height: 4px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 2px;
  overflow: hidden;
}

.ai-progress-bar {
  height: 100%;
  width: 40%;
  background: linear-gradient(90deg, #3b82f6, #60a5fa, #3b82f6);
  background-size: 200% 100%;
  border-radius: 2px;
  animation: progress-slide 1.5s ease-in-out infinite;
}

/* Animations */
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

@keyframes spin-reverse {
  0% {
    transform: rotate(360deg);
  }
  100% {
    transform: rotate(0deg);
  }
}

@keyframes pulse-glow {
  0%,
  100% {
    transform: scale(1);
    filter: drop-shadow(0 0 10px rgba(59, 130, 246, 0.5));
  }
  50% {
    transform: scale(1.1);
    filter: drop-shadow(0 0 20px rgba(59, 130, 246, 0.8));
  }
}

@keyframes bounce {
  0%,
  80%,
  100% {
    transform: scale(0.6);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes progress-slide {
  0% {
    transform: translateX(-100%);
    background-position: 0% 0%;
  }
  50% {
    background-position: 100% 0%;
  }
  100% {
    transform: translateX(350%);
    background-position: 0% 0%;
  }
}

/* Fade Transition */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* Responsive */
@media (max-width: 480px) {
  .ai-loading-popup {
    padding: 32px 40px;
    min-width: 280px;
    margin: 0 20px;
  }

  .ai-loading-title {
    font-size: 1.25rem;
  }

  .ai-icon-container {
    width: 80px;
    height: 80px;
  }

  .ai-brain-icon {
    font-size: 36px;
  }
}
</style>
