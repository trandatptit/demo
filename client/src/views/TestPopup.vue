<template>
  <div class="min-h-screen flex flex-col items-center justify-center gap-4">
    <Button label="Open PopupPackage" @click="showPopup = true" />

    <!-- PopupPackage -->
    <PopupPackage v-model="showPopup" />

    <Button label="Open PopupNatrilRoadmap" @click="showNatriRoadmap = true" />

    <!-- PopupNatriRoadmap -->
    <PopupNatriRoadmap v-model="showNatriRoadmap" />

    <Button label="Open NutrilRoadMapForm" @click="showNutrilRoadMapForm = true" />

    <!-- NutrilRoadMapForm Dialog -->
    <NutrilRoadMapForm 
      v-model="showNutrilRoadMapForm"
      :title="roadmapTitle"
      :options="exerciseOptions"
      :submitButtonLabel="roadmapSubmitLabel"
      @submit="handleRoadmapSubmit"
      @buyNow="handleRoadmapBuyNow"
    />

    <Button label="Open NutrilRoadMapOptimal" @click="showNutrilRoadMapOptimal = true" />

    <!-- NutrilRoadMapOptimal Dialog -->
    <NutrilRoadMapOptimal 
      v-model="showNutrilRoadMapOptimal"
      :title="optimalTitle"
      :subtitle="optimalSubtitle"
      :targetHeight="optimalTargetHeight"
      :monthlyData="optimalMonthlyData"
      :actionTitle="optimalActionTitle"
      :actionDescription="optimalActionDescription"
      :actionButtonLabel="optimalActionButtonLabel"
      @action="handleOptimalAction"
    />

    <Button label="Open NutrilRoadMapPayment" @click="showNutrilRoadMapPayment = true" />

    <!-- NutrilRoadMapPayment Dialog -->
    <NutrilRoadMapPayment 
      v-model="showNutrilRoadMapPayment"
      :title="paymentTitle"
      :description="paymentDescription"
      :amount="paymentAmount"
      :qrCodeUrl="paymentQrCodeUrl"
      :qrCodeAlt="paymentQrCodeAlt"
      :transactionContent="paymentTransactionContent"
      :statusText="paymentStatusText"
      :confirmButtonLabel="paymentConfirmButtonLabel"
      :cancelButtonLabel="paymentCancelButtonLabel"
      @confirm="handlePaymentConfirm"
      @cancel="handlePaymentCancel"
    />

    <Button label="View HomeProduct" @click="showHomeProduct = true" />

    <Button label="View HomeTool" @click="showHomeTool = true" />

    <Button label="View Feature30k" @click="openFeature30k" />

    <Button label="View Feature30kFinal" @click="openFeature30kFinal" />

    <!-- HomeProduct Section -->
    <div v-if="showHomeProduct" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button icon="pi pi-times" text rounded severity="danger" @click="showHomeProduct = false" />
      </div>
      <HomeProduct />
    </div>

    <!-- HomeTool Section -->
    <div v-if="showHomeTool" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button icon="pi pi-times" text rounded severity="danger" @click="showHomeTool = false" />
      </div>
      <HomeTool />
    </div>

    <!-- Feature30k Section - Truyền props -->
    <div v-if="showFeature30k" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button icon="pi pi-times" text rounded severity="danger" @click="closeFeature30k" />
      </div>
      <Feature30k 
        :userId="customUserId"
        :currentHeight="customCurrentHeight"
        :inheritedHeight="customInheritedHeight"
        :potentialHeight="customPotentialHeight"
      />
    </div>

    <!-- Feature30kFinal Section - Truyền props -->
    <div v-if="showFeature30kFinal" class="w-full">
      <div class="flex justify-end px-4 mb-2">
        <Button icon="pi pi-times" text rounded severity="danger" @click="closeFeature30kFinal" />
      </div>
      <Feature30kFinal 
        :userId="finalUserId"
        :ageData="finalAgeData"
        :aiRecommendation="aiRecommendationData"
        :inspirationMessage="inspirationMessageData"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import Button from "primevue/button";
import PopupPackage from "../components/popup/PopupPackage.vue";
import PopupNatriRoadmap from "../components/popup/NutrilRoadMap/PopupNutrilRoadMap.vue";
import NutrilRoadMapForm from "../components/popup/NutrilRoadMap/screen/NutrilRoadMapForm.vue";
import NutrilRoadMapOptimal from "../components/popup/NutrilRoadMap/screen/NutrilRoadMapOptimal.vue";
import NutrilRoadMapPayment from "../components/popup/NutrilRoadMap/screen/NutrilRoadMapPayment.vue";
import HomeProduct from "./HomePage/components/HomeProduct.vue";
import HomeTool from "./HomePage/components/HomeTool.vue";
import Feature30k from "./Feature30k/Feature30k.vue";
import Feature30kFinal from "./Feature30k/Feature30kFinal.vue";

const showPopup = ref(false);
const showNatriRoadmap = ref(false);
const showHomeProduct = ref(false);
const showHomeTool = ref(false);
const showFeature30k = ref(false);
const showFeature30kFinal = ref(false);
const showNutrilRoadMapForm = ref(false);
const showNutrilRoadMapOptimal = ref(false);
const showNutrilRoadMapPayment = ref(false);

// Custom data for Feature30k
const customUserId = ref('User123');
const customCurrentHeight = ref(165);
const customInheritedHeight = ref(169);
const customPotentialHeight = ref(172);

// Custom data for Feature30kFinal
const finalUserId = ref('12121');
const finalAgeData = ref([
  { age: '16t', height: 163 },
  { age: '17t', height: 168 },
  { age: '18t', height: 171, locked: true },
  { age: '19t', height: 175 },
  { age: '20t', height: 179 }
]);

// AI Recommendation data
const aiRecommendationData = ref(
  'Nam, 17 tuổi - Nếu duy trì ngủ trước 10 giờ và bổ sung 40 Canxi, bạn có thể đạt 179.0cm 😊'
)

// NutrilRoadMapForm data
const roadmapTitle = ref('Các loại hình bài tập bạn yêu thích?');
const roadmapSubmitLabel = ref('Xem Demo Phác Đồ');

const exerciseOptions = ref([
  {
    id: 'strength',
    label: 'Tập tạ/Sức mạnh',
    icon: 'pi pi-bolt',
    hasSubOptions: false
  },
  {
    id: 'cardio',
    label: 'Cardio/Sức bền',
    icon: 'pi pi-heart',
    hasSubOptions: true,
    subQuestion: 'Day nhảy (các gì) chưa?',
    subHelper: 'Sử hữu dụng cụ phù hợp để có thể thúc đẩy quá trình tập!',
    subButtonLabel: 'Đặt mua ngay'
  },
  {
    id: 'home-daily',
    label: 'Bài tập tại nhà (Daily dây, Squat.)',
    icon: 'pi pi-home',
    hasSubOptions: false
  },
  {
    id: 'yoga',
    label: 'Yoga/Dẻo dai',
    icon: 'pi pi-heart',
    hasSubOptions: true,
    subQuestion: 'Thảm tập Yoga chưa?',
    subHelper: 'Sử hữu dụng cụ phù hợp để có thể thúc đẩy quá trình tập!',
    subButtonLabel: 'Đặt mua ngay'
  },
  {
    id: 'gym',
    label: 'Bài tập tại phòng Gym',
    icon: 'pi pi-chart-bar',
    hasSubOptions: false
  }
]);

// NutrilRoadMapOptimal data
const optimalTitle = ref('Lộ Trình Dinh Dưỡng Tối Ưu');
const optimalSubtitle = ref('AI Đề Xuất Dành Riêng Cho Bạn');
const optimalTargetHeight = ref(170);
const optimalMonthlyData = ref([160, 160.5, 161, 162, 163, 164, 165, 166, 167, 168, 168.5, 169, 170]);
const optimalActionTitle = ref('Sẵn sàng để bắt đầu?');
const optimalActionDescription = ref('Nhận thực đơn chi tiết hằng tuần, công thức nấu ăn và video hướng dẫn.');
const optimalActionButtonLabel = ref('Thanh toán & Nhận Lộ Trình');

// NutrilRoadMapPayment data
const paymentTitle = ref('Quét mã để thanh toán');
const paymentDescription = ref('Sử dụng ứng dụng ngân hàng hoặc ví điện tử để quét mã VietQR');
const paymentAmount = ref(30000);
const paymentQrCodeUrl = ref('https://api.qrserver.com/v1/create-qr-code/?size=240x240&data=VietQR-EP12121-30000');
const paymentQrCodeAlt = ref('Mã QR thanh toán VietQR');
const paymentTransactionContent = ref('EP 12121');
const paymentStatusText = ref('Chờ thanh toán...');
const paymentConfirmButtonLabel = ref('Đã thanh toán');
const paymentCancelButtonLabel = ref('Hủy');

// Open Feature30k with custom data
const openFeature30k = () => {
  showFeature30k.value = true;
};

// Close Feature30k
const closeFeature30k = () => {
  showFeature30k.value = false;
};

// Open Feature30kFinal with custom data
const openFeature30kFinal = () => {
  showFeature30kFinal.value = true;
};

// Close Feature30kFinal
const closeFeature30kFinal = () => {
  showFeature30kFinal.value = false;
};

// Handle NutrilRoadMapForm submit
const handleRoadmapSubmit = (selectedIds) => {
  console.log('Selected exercise options:', selectedIds);
  alert(`Bạn đã chọn: ${selectedIds.join(', ')}`);
};

// Handle NutrilRoadMapForm buy now
const handleRoadmapBuyNow = (optionId) => {
  console.log('Buy now for option:', optionId);
  alert(`Đặt mua dụng cụ cho: ${optionId}`);
};

// Handle NutrilRoadMapOptimal action
const handleOptimalAction = () => {
  console.log('User clicked payment action');
  showNutrilRoadMapPayment.value = true;
};

// Handle NutrilRoadMapPayment confirm
const handlePaymentConfirm = () => {
  console.log('Payment confirmed');
  alert('Thanh toán thành công! Bạn sẽ nhận được lộ trình qua email.');
};

// Handle NutrilRoadMapPayment cancel
const handlePaymentCancel = () => {
  console.log('Payment cancelled');
  alert('Đã hủy thanh toán');
};
</script>