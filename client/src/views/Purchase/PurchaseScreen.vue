<template>
  <div class="purchase-screen">
    <!-- Back Header -->
    <div class="grid grid-cols-12 gap-4 mb-6">
      <div class="col-span-12">
        <button class="back-btn" @click="goBack">
          <i class="pi pi-arrow-left"></i>
          <span>Quay lại</span>
        </button>
      </div>
    </div>

    <!-- Main Content: 2-column layout -->
    <div class="grid grid-cols-12 gap-6">
      <!-- ═══════════════ LEFT PANEL: Order Form ═══════════════ -->
      <div class="col-span-12 lg:col-span-5">
        <Card class="content-card">
          <template #content>
            <!-- Section Title -->
            <div class="flex items-center gap-2 mb-5">
              <i class="pi pi-clipboard section-icon"></i>
              <h2 class="card-heading">Thông tin đặt hàng</h2>
            </div>

            <!-- Name + Age row -->
            <div class="grid grid-cols-12 gap-4 mb-4">
              <div class="col-span-6">
                <label class="field-label">Tên:</label>
                <InputText
                  v-model="orderForm.name"
                  placeholder="Bạn"
                  class="custom-input w-full"
                />
              </div>
              <div class="col-span-6">
                <label class="field-label">Tuổi:</label>
                <InputNumber
                  v-model="orderForm.age"
                  :min="1"
                  :max="100"
                  placeholder="15"
                  class="custom-input w-full"
                  inputClass="w-full"
                />
              </div>
            </div>

            <!-- Gender + Height row -->
            <div class="grid grid-cols-12 gap-4 mb-4">
              <div class="col-span-6">
                <label class="field-label">Giới tính:</label>
                <InputText
                  v-model="orderForm.gender"
                  placeholder="male"
                  class="custom-input w-full"
                />
              </div>
              <div class="col-span-6">
                <label class="field-label">Chiều cao:</label>
                <div class="flex items-center gap-2">
                  <InputNumber
                    v-model="orderForm.height"
                    :min="50"
                    :max="250"
                    placeholder="160"
                    class="custom-input w-full"
                    inputClass="w-full"
                    suffix=" cm"
                  />
                </div>
              </div>
            </div>

            <!-- Phone -->
            <div class="mb-4">
              <label class="field-label">
                <i class="pi pi-phone text-xs"></i> Số điện thoại
              </label>
              <InputText
                v-model="orderForm.phone"
                placeholder="09xxxxxxxx"
                class="custom-input w-full"
              />
            </div>

            <!-- Email -->
            <div class="mb-4">
              <label class="field-label">
                <i class="pi pi-envelope text-xs"></i> Email
              </label>
              <InputText
                v-model="orderForm.email"
                placeholder="ban@email.com"
                class="custom-input w-full"
              />
            </div>

            <!-- Address -->
            <div class="mb-4">
              <label class="field-label">
                <i class="pi pi-map-marker text-xs"></i> Địa chỉ nhận hàng
              </label>
              <InputText
                v-model="orderForm.address"
                placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố"
                class="custom-input w-full"
              />
            </div>

            <!-- Discount code -->
            <div class="mb-2">
              <label class="field-label">
                <i class="pi pi-tag text-xs"></i> Mã giảm giá
              </label>
              <div class="flex gap-2">
                <InputText
                  v-model="orderForm.discountCode"
                  placeholder="Nhập mã giảm giá của bạn"
                  class="custom-input flex-1"
                />
                <Button
                  label="Áp dụng"
                  class="apply-btn"
                  @click="applyDiscount"
                />
              </div>
            </div>
          </template>
        </Card>

        <!-- Product Images Gallery -->
        <Card class="content-card mt-6">
          <template #content>
            <div class="product-gallery">
              <img
                :src="productImages[activeImage]"
                alt="Sản phẩm"
                class="gallery-main-img"
                @error="onImageError"
              />
              <div class="gallery-thumbs">
                <div
                  v-for="(img, idx) in productImages"
                  :key="idx"
                  :class="[
                    'gallery-thumb',
                    idx === activeImage ? 'thumb-active' : '',
                  ]"
                  @click="activeImage = idx"
                >
                  <img
                    :src="img"
                    :alt="`Ảnh ${idx + 1}`"
                    @error="onImageError"
                  />
                </div>
              </div>
            </div>
          </template>
        </Card>
      </div>

      <!-- ═══════════════ RIGHT PANEL: Product & Payment ═══════════════ -->
      <div class="col-span-12 lg:col-span-7">
        <!-- Product Name & Description -->
        <Card class="content-card">
          <template #content>
            <h2 class="product-name">Kẹo Canxi Everest Vị Dâu</h2>
            <p class="product-desc">
              Giải pháp bổ sung canxi thơm ngon, hiệu quả.
            </p>

            <!-- Quantity -->
            <div class="flex items-center gap-4 mt-4 mb-5">
              <span class="field-label mb-0">Số lượng:</span>
              <div class="quantity-control">
                <button class="qty-btn" @click="decreaseQty">
                  <i class="pi pi-minus"></i>
                </button>
                <span class="qty-value">{{ quantity }}</span>
                <button class="qty-btn" @click="increaseQty">
                  <i class="pi pi-plus"></i>
                </button>
              </div>
            </div>

            <!-- Total Price -->
            <div class="price-box">
              <span class="price-label">Tổng tiền:</span>
              <span class="price-amount">{{ formatCurrency(totalPrice) }}</span>
            </div>

            <!-- Payment Methods -->
            <div class="mt-5">
              <p class="field-label">Phương thức thanh toán:</p>
              <div class="payment-grid">
                <div
                  v-for="method in paymentMethods"
                  :key="method.value"
                  :class="[
                    'payment-option',
                    selectedPayment === method.value ? 'payment-selected' : '',
                  ]"
                  @click="selectedPayment = method.value"
                >
                  <RadioButton
                    v-model="selectedPayment"
                    :value="method.value"
                    :inputId="method.value"
                    class="custom-radio"
                  />
                  <div class="payment-info">
                    <span
                      v-if="method.icon"
                      class="payment-icon"
                      :style="{ color: method.color }"
                    >
                      <i :class="method.icon"></i>
                    </span>
                    <img
                      v-if="method.logo"
                      :src="method.logo"
                      :alt="method.label"
                      class="payment-logo"
                    />
                    <span class="payment-label">{{ method.label }}</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Order Button -->
            <Button
              label="Thanh toán khi nhận hàng"
              icon="pi pi-shopping-cart"
              class="order-btn mt-5 w-full"
              @click="placeOrder"
            />
          </template>
        </Card>

        <!-- ─── AI Recommendation Chart ─── -->
        <Card class="content-card mt-6">
          <template #content>
            <div class="flex items-center gap-2 mb-2">
              <i class="pi pi-sparkles section-icon"></i>
              <h3 class="card-heading">Everest Smart AI Đề Xuất</h3>
            </div>
            <p class="text-sm text-muted mb-4">
              Dựa trên lượng canxi xương, AI dự phóng lộ trình tăng trưởng hiệu
              quả nhất.
            </p>
            <div class="chart-container">
              <Chart
                type="bar"
                :data="aiChartData"
                :options="aiChartOptions"
                class="h-56"
              />
            </div>
          </template>
        </Card>

        <!-- ─── Usage Guide ─── -->
        <Card class="content-card mt-6">
          <template #content>
            <div class="flex items-center gap-2 mb-4">
              <i class="pi pi-book section-icon"></i>
              <h3 class="card-heading">Hướng dẫn sử dụng</h3>
            </div>
            <div class="grid grid-cols-12 gap-4">
              <div class="col-span-6">
                <div class="guide-box">
                  <div class="guide-icon-wrap">
                    <i class="pi pi-clock"></i>
                  </div>
                  <p class="guide-title">Thời gian uống</p>
                  <p class="guide-text">Tốt nhất vào buổi sáng, sau khi ăn.</p>
                </div>
              </div>
              <div class="col-span-6">
                <div class="guide-box">
                  <div class="guide-icon-wrap">
                    <i class="pi pi-heart"></i>
                  </div>
                  <p class="guide-title">Liều lượng</p>
                  <p class="guide-text">1-2 viên/ngày (theo chỉ dẫn AI)</p>
                </div>
              </div>
            </div>
          </template>
        </Card>

        <!-- ─── Policies ─── -->
        <Card class="content-card mt-6">
          <template #content>
            <div class="policies-grid">
              <div class="policy-item">
                <i class="pi pi-verified policy-icon"></i>
                <div>
                  <p class="policy-title">Cam kết</p>
                  <p class="policy-text">
                    Hàng chính hãng 100%, hoàn tiền nếu phát hiện hàng giả.
                  </p>
                </div>
              </div>
              <div class="policy-item">
                <i class="pi pi-shield policy-icon"></i>
                <div>
                  <p class="policy-title">Bảo hành</p>
                  <p class="policy-text">
                    Đổi trả trong 7 ngày nếu có lỗi từ nhà sản xuất.
                  </p>
                </div>
              </div>
            </div>
          </template>
        </Card>

        <!-- ─── Growth Simulation ─── -->
        <Card class="content-card mt-6">
          <template #content>
            <div class="flex items-center gap-2 mb-4">
              <i class="pi pi-chart-line section-icon"></i>
              <h3 class="card-heading">Mô Phỏng Tăng Trưởng</h3>
            </div>
            <div class="chart-container">
              <Chart
                type="bar"
                :data="growthChartData"
                :options="growthChartOptions"
                class="h-56"
              />
            </div>
            <!-- CTA -->
            <Button
              label="Đặt Hàng Ngay"
              icon="pi pi-shopping-cart"
              class="cta-btn mt-5 w-full"
              @click="placeOrder"
            />
          </template>
        </Card>
      </div>
    </div>

    <!-- ═══════════════ FULL-WIDTH: Product Details ═══════════════ -->
    <div class="grid grid-cols-12 gap-6 mt-8">
      <div class="col-span-12 text-center">
        <h2 class="section-title-lg">Thông Tin Sản Phẩm Chi Tiết</h2>
      </div>
    </div>

    <div class="grid grid-cols-12 gap-6 mt-4">
      <!-- Card 1: Ingredients -->
      <div class="col-span-12 md:col-span-6">
        <Card class="content-card detail-card">
          <template #content>
            <div class="flex items-center gap-2 mb-4">
              <i class="pi pi-list section-icon"></i>
              <h3 class="card-heading">Thành phần & Cơ chế hấp thu</h3>
            </div>

            <div class="detail-item">
              <span class="detail-key">Thành phần:</span>
              <span class="detail-val"
                >Canxi nano, Vitamin D3, Vitamin K2 (MK7), Kẽm gluconat.</span
              >
            </div>
            <div class="detail-item">
              <span class="detail-key">Hàm lượng:</span>
              <span class="detail-val"
                >Canxi (300mg), D3 (200IU), K2 (20mcg) mỗi viên.</span
              >
            </div>
            <div class="detail-item">
              <span class="detail-key">Nguồn gốc:</span>
              <span class="detail-val"
                >Canxi từ tảo biển đỏ (Lithothamnion), an toàn và dễ hấp
                thu.</span
              >
            </div>
            <div class="detail-item mt-3">
              <span class="detail-key">Cơ chế:</span>
              <span class="detail-val"
                >D3 giúp hấp thu Canxi từ ruột vào máu. K2 "dẫn đường" cho Canxi
                đi thẳng vào xương, tránh lắng đọng.</span
              >
            </div>

            <!-- Images -->
            <div class="grid grid-cols-2 gap-3 mt-4">
              <div class="detail-img-wrap">
                <img
                  src="https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=400&h=300&fit=crop"
                  alt="Thành phần"
                  class="detail-img"
                />
              </div>
              <div class="detail-img-wrap">
                <img
                  src="https://images.unsplash.com/photo-1559757148-5c350d0d3c56?w=400&h=300&fit=crop"
                  alt="Canxi"
                  class="detail-img"
                />
              </div>
            </div>
          </template>
        </Card>
      </div>

      <!-- Card 2: Scientific Proof -->
      <div class="col-span-12 md:col-span-6">
        <Card class="content-card detail-card">
          <template #content>
            <div class="flex items-center gap-2 mb-4">
              <i class="pi pi-check-circle section-icon"></i>
              <h3 class="card-heading">Khoa học chứng minh</h3>
            </div>

            <ul class="science-list">
              <li>
                <i class="pi pi-circle-fill dot-icon"></i>
                <span
                  >Công trình nghiên cứu về Canxi Nano tại Viện Hàn Lâm Khoa học
                  Việt Nam.</span
                >
              </li>
              <li>
                <i class="pi pi-circle-fill dot-icon"></i>
                <span
                  >Nghiên cứu lâm sàng về hiệu quả của bộ ba Canxi-D3-K2 trên
                  tạp chí Nutrients.</span
                >
              </li>
              <li>
                <i class="pi pi-circle-fill dot-icon"></i>
                <span
                  >Tỉ lệ hấp thu cao hơn 37% so với Canxi thường thường.</span
                >
              </li>
            </ul>

            <!-- Images -->
            <div class="grid grid-cols-2 gap-3 mt-4">
              <div class="detail-img-wrap">
                <img
                  src="https://images.unsplash.com/photo-1532187863486-abf9dbad1b69?w=400&h=300&fit=crop"
                  alt="Nghiên cứu"
                  class="detail-img"
                />
              </div>
              <div class="detail-img-wrap">
                <img
                  src="https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=400&h=300&fit=crop"
                  alt="Khoa học"
                  class="detail-img"
                />
              </div>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- ═══════════════ EXPERT REVIEWS ═══════════════ -->
    <div class="grid grid-cols-12 gap-6 mt-8">
      <div class="col-span-12 text-center">
        <h2 class="section-title-lg section-title-underline">
          Đánh Giá Từ Chuyên Gia & Người Nổi Tiếng
        </h2>
      </div>
    </div>

    <div class="grid grid-cols-12 gap-4 mt-4">
      <!-- Expert says -->
      <div class="col-span-12 md:col-span-6">
        <div class="flex items-center gap-2 mb-3">
          <i class="pi pi-user section-icon"></i>
          <h3 class="subsection-title">Chuyên gia nói gì</h3>
        </div>
        <div class="grid grid-cols-12 gap-3">
          <div v-for="expert in experts" :key="expert.id" class="col-span-6">
            <Card class="video-card">
              <template #content>
                <div class="video-thumb" @click="openVideo(expert.videoUrl)">
                  <img
                    :src="expert.thumbnail"
                    :alt="expert.name"
                    class="video-img"
                    @error="onImageError"
                  />
                  <div class="play-overlay">
                    <div class="play-btn-circle">
                      <i class="pi pi-play-circle"></i>
                    </div>
                  </div>
                </div>
                <div class="video-info">
                  <p class="video-name">{{ expert.name }}</p>
                  <p class="video-role">{{ expert.role }}</p>
                </div>
              </template>
            </Card>
          </div>
        </div>
      </div>

      <!-- Celebrity reviews -->
      <div class="col-span-12 md:col-span-6">
        <div class="flex items-center gap-2 mb-3">
          <i class="pi pi-star section-icon"></i>
          <h3 class="subsection-title">Người nổi tiếng đánh giá</h3>
        </div>
        <div class="grid grid-cols-12 gap-3">
          <div v-for="celeb in celebrities" :key="celeb.id" class="col-span-6">
            <Card class="video-card">
              <template #content>
                <div class="video-thumb" @click="openVideo(celeb.videoUrl)">
                  <img
                    :src="celeb.thumbnail"
                    :alt="celeb.name"
                    class="video-img"
                    @error="onImageError"
                  />
                  <div class="play-overlay">
                    <div class="play-btn-circle">
                      <i class="pi pi-play-circle"></i>
                    </div>
                  </div>
                </div>
                <div class="video-info">
                  <p class="video-name">{{ celeb.name }}</p>
                  <p class="video-role">{{ celeb.role }}</p>
                </div>
              </template>
            </Card>
          </div>
        </div>
      </div>
    </div>

    <!-- ═══════════════ FEEDBACK ═══════════════ -->
    <div class="grid grid-cols-12 gap-6 mt-8 mb-8">
      <div class="col-span-12 text-center">
        <h2 class="section-title-lg section-title-underline">
          Feedback & Kết Quả Thật
        </h2>
      </div>
      <div class="col-span-12">
        <Card class="content-card">
          <template #content>
            <div class="feedback-grid">
              <div v-for="fb in feedbacks" :key="fb.id" class="feedback-card">
                <div class="feedback-avatar">
                  <i class="pi pi-user"></i>
                </div>
                <div class="feedback-body">
                  <div class="flex items-center gap-2 mb-1">
                    <span class="feedback-name">{{ fb.name }}</span>
                    <div class="feedback-stars">
                      <i
                        v-for="s in 5"
                        :key="s"
                        :class="[
                          'pi',
                          s <= fb.rating ? 'pi-star-fill' : 'pi-star',
                          s <= fb.rating ? 'star-filled' : 'star-empty',
                        ]"
                      ></i>
                    </div>
                  </div>
                  <p class="feedback-result">{{ fb.result }}</p>
                  <p class="feedback-text">{{ fb.comment }}</p>
                </div>
              </div>
            </div>
          </template>
        </Card>
      </div>
    </div>

    <!-- ═══════════════ LEGAL INFORMATION ═══════════════ -->
    <div class="grid grid-cols-12 gap-6 mt-8 mb-4">
      <div class="col-span-12">
        <Card class="content-card legal-card">
          <template #content>
            <div class="flex items-center gap-2 mb-5">
              <i class="pi pi-file-edit section-icon"></i>
              <h3 class="card-heading">Thông tin pháp lý</h3>
            </div>

            <div class="grid grid-cols-12 gap-6">
              <!-- Left: Text info -->
              <div class="col-span-12 md:col-span-7">
                <div class="legal-item">
                  <span class="legal-key">Số ĐKSP:</span>
                  <span class="legal-val">12345/2025/ĐKSP</span>
                </div>
                <div class="legal-item">
                  <span class="legal-key">Công dụng:</span>
                  <span class="legal-val"
                    >Bổ sung Canxi, Vitamin D3 và K2, hỗ trợ xương và răng chắc
                    khỏe, hỗ trợ phát triển chiều cao.</span
                  >
                </div>
                <div class="legal-item">
                  <span class="legal-key">Tiêu chuẩn:</span>
                  <span class="legal-val">TCCS</span>
                </div>
                <div class="legal-item">
                  <span class="legal-key">Bảo quản:</span>
                  <span class="legal-val"
                    >Nơi khô ráo, thoáng mát, tránh ánh nắng trực tiếp.</span
                  >
                </div>
                <div class="legal-item">
                  <span class="legal-key">Thương nhân chịu trách nhiệm:</span>
                  <span class="legal-val">CÔNG TY TNHH EVEREST PHARMA</span>
                </div>
                <div class="legal-item">
                  <span class="legal-key">Sản xuất tại:</span>
                  <span class="legal-val">Nhà máy ABC, Việt Nam</span>
                </div>
              </div>

              <!-- Right: Certificate images -->
              <div class="col-span-12 md:col-span-5">
                <div class="grid grid-cols-2 gap-3">
                  <div class="cert-img-wrap">
                    <img
                      src="https://images.unsplash.com/photo-1554224155-6726b3ff858f?w=300&h=200&fit=crop"
                      alt="Giấy công bố ATTP"
                      class="cert-img"
                      @error="onImageError"
                    />
                    <p class="cert-caption">Giấy công bố ATTP</p>
                  </div>
                  <div class="cert-img-wrap">
                    <img
                      src="https://images.unsplash.com/photo-1450101499163-c8848c66ca85?w=300&h=200&fit=crop"
                      alt="Giấy phép quảng cáo"
                      class="cert-img"
                      @error="onImageError"
                    />
                    <p class="cert-caption">Giấy phép quảng cáo</p>
                  </div>
                </div>
              </div>
            </div>

            <!-- Disclaimer -->
            <div class="legal-disclaimer">
              <i class="pi pi-info-circle"></i>
              <p>
                <em
                  >Chú ý: Thực phẩm này không phải là thuốc, không có tác dụng
                  thay thế thuốc chữa bệnh. Không sử dụng cho người nhạy cảm với
                  bất cứ thành phần nào của sản phẩm.</em
                >
              </p>
            </div>
          </template>
        </Card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import Card from "primevue/card";
import Button from "primevue/button";
import InputText from "primevue/inputtext";
import InputNumber from "primevue/inputnumber";
import RadioButton from "primevue/radiobutton";
import Chart from "primevue/chart";

const router = useRouter();

// ─── Order Form ───
const orderForm = ref({
  name: "Bạn",
  age: 15,
  gender: "male",
  height: 160,
  phone: "",
  email: "",
  address: "",
  discountCode: "",
});

// ─── Product ───
const pricePerUnit = 150000;
const quantity = ref(1);
const activeImage = ref(0);

const productImages = ref([
  "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=600&h=500&fit=crop",
  "https://images.unsplash.com/photo-1559757148-5c350d0d3c56?w=600&h=500&fit=crop",
  "https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=600&h=500&fit=crop",
]);

const totalPrice = computed(() => pricePerUnit * quantity.value);

const decreaseQty = () => {
  if (quantity.value > 1) quantity.value--;
};
const increaseQty = () => {
  quantity.value++;
};

// ─── Payment Methods ───
const selectedPayment = ref("cod");
const paymentMethods = ref([
  {
    value: "vnpay",
    label: "VNPAY",
    icon: "pi pi-credit-card",
    color: "#0066b2",
    logo: null,
  },
  {
    value: "momo",
    label: "Momo",
    icon: "pi pi-wallet",
    color: "#d82d8b",
    logo: null,
  },
  {
    value: "creditcard",
    label: "Credit Card",
    icon: "pi pi-credit-card",
    color: "#1a1a2e",
    logo: null,
  },
  {
    value: "cod",
    label: "Thanh toán khi nhận hàng",
    icon: "pi pi-money-bill",
    color: "#16a34a",
    logo: null,
  },
]);

// ─── AI Chart Data ───
const aiChartData = ref({
  labels: ["1 Hộp", "2 Hộp", "3 Hộp"],
  datasets: [
    {
      label: "Tăng trưởng dự kiến (cm)",
      backgroundColor: ["#93c5fd", "#60a5fa", "#3b82f6"],
      borderColor: ["#93c5fd", "#60a5fa", "#3b82f6"],
      borderWidth: 1,
      borderRadius: 8,
      data: [0.65, 0.95, 1.6],
    },
  ],
});

const aiChartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false },
    tooltip: {
      callbacks: {
        label: (ctx) => `+${ctx.parsed.y} cm`,
      },
    },
  },
  scales: {
    y: {
      beginAtZero: true,
      max: 2,
      ticks: {
        stepSize: 0.25,
        callback: (val) => `${val} cm`,
      },
      grid: { display: true, drawBorder: false },
    },
    x: {
      grid: { display: false },
    },
  },
});

// ─── Growth Simulation Chart ───
const growthChartData = ref({
  labels: ["Trước", "Sau 3 Hộp"],
  datasets: [
    {
      label: "Chiều cao (cm)",
      backgroundColor: ["#cbd5e1", "#3b82f6"],
      borderColor: ["#cbd5e1", "#3b82f6"],
      borderWidth: 1,
      borderRadius: 8,
      data: [160, 162.5],
      barThickness: 60,
    },
  ],
});

const growthChartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { display: false },
    tooltip: {
      callbacks: {
        label: (ctx) => `${ctx.parsed.y} cm`,
      },
    },
  },
  scales: {
    y: {
      min: 155,
      max: 165,
      ticks: {
        stepSize: 1,
        callback: (val) => `${val}cm`,
      },
      grid: { display: true, drawBorder: false },
    },
    x: {
      grid: { display: false },
      ticks: {
        font: { size: 13, weight: "bold" },
      },
    },
  },
});

// ─── Experts & Celebrities ───
const experts = ref([
  {
    id: 1,
    name: "BS. Nguyễn Thị Lan",
    role: "Trưởng khoa Dinh dưỡng",
    thumbnail:
      "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=400&h=250&fit=crop",
    videoUrl: "#",
  },
  {
    id: 2,
    name: "TS. Trần Văn Hùng",
    role: "Chuyên gia Nội tiết",
    thumbnail:
      "https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=400&h=250&fit=crop",
    videoUrl: "#",
  },
]);

const celebrities = ref([
  {
    id: 1,
    name: "Gia đình Quyền Linh",
    role: "Review từ gia đình nổi tiếng",
    thumbnail:
      "https://images.unsplash.com/photo-1609220136736-443140cffec6?w=400&h=250&fit=crop",
    videoUrl: "#",
  },
  {
    id: 2,
    name: "Hot Mom Lan Anh",
    role: "Bí quyết chăm con cao lớn",
    thumbnail:
      "https://images.unsplash.com/photo-1594824476967-48c8b964ac31?w=400&h=250&fit=crop",
    videoUrl: "#",
  },
]);

// ─── Feedbacks ───
const feedbacks = ref([
  {
    id: 1,
    name: "Chị Hương - Hà Nội",
    rating: 5,
    result: "+2.5cm sau 3 tháng",
    comment:
      "Con trai tôi 14 tuổi, sau 3 tháng dùng Kẹo Canxi Everest đã cao thêm 2.5cm. Rất hài lòng!",
  },
  {
    id: 2,
    name: "Anh Tuấn - TP.HCM",
    rating: 5,
    result: "+1.8cm sau 2 tháng",
    comment:
      "Sản phẩm rất tốt, con gái tôi thích ăn vì vị dâu ngon. Đã đặt thêm 3 hộp.",
  },
  {
    id: 3,
    name: "Chị Mai - Đà Nẵng",
    rating: 4,
    result: "+3cm sau 4 tháng",
    comment:
      "Ban đầu hơi nghi ngờ nhưng kết quả thực sự ấn tượng. Con tôi cao hơn hẳn so với bạn cùng lớp.",
  },
]);

// ─── Methods ───
const formatCurrency = (value) => {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(value);
};

const goBack = () => {
  router.back();
};

const applyDiscount = () => {
  console.log("Applying discount:", orderForm.value.discountCode);
};

const placeOrder = () => {
  console.log("Placing order:", {
    form: orderForm.value,
    quantity: quantity.value,
    total: totalPrice.value,
    payment: selectedPayment.value,
  });
};

const openVideo = (url) => {
  if (url && url !== "#") {
    window.open(url, "_blank");
  }
};

const onImageError = (e) => {
  e.target.src = "https://via.placeholder.com/400x300/E5E7EB/6B7280?text=Image";
};
</script>

<style scoped>
/* ─── Design Tokens (matching NutrilRoadMapSchedule) ─── */
.purchase-screen {
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

  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
  font-family: "Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    sans-serif;
}

/* ─── Back Button ─── */
.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: none;
  border: none;
  color: var(--text-secondary);
  font-size: 0.95rem;
  font-weight: 600;
  cursor: pointer;
  padding: 8px 4px;
  transition: color 0.2s ease;
}

.back-btn:hover {
  color: var(--primary);
}

.back-btn i {
  font-size: 0.85rem;
}

/* ─── Content Cards ─── */
:deep(.content-card) {
  --p-card-background: var(--surface);
  --p-card-border-radius: 16px;
  --p-card-shadow: var(--shadow-md);
  border: 1px solid var(--border);
  overflow: hidden;
  position: relative;
}

:deep(.content-card)::before {
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
  z-index: 1;
}

:deep(.p-card-content) {
  padding: 1.5rem;
}

/* ─── Section Titles ─── */
.section-icon {
  color: var(--primary);
  font-size: 1.1rem;
}

.card-heading {
  font-size: 1.05rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0;
}

.section-title-lg {
  font-size: 1.5rem;
  font-weight: 800;
  color: var(--text-primary);
  margin: 0;
}

.section-title-underline {
  display: inline-block;
  border-bottom: 3px solid var(--primary);
  padding-bottom: 8px;
}

.subsection-title {
  font-size: 0.95rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0;
}

/* ─── Form Fields ─── */
.field-label {
  display: block;
  font-size: 0.8rem;
  font-weight: 600;
  color: var(--text-secondary);
  margin-bottom: 6px;
  text-transform: uppercase;
  letter-spacing: 0.3px;
}

:deep(.custom-input .p-inputtext),
:deep(.custom-input input) {
  border-radius: 10px;
  border: 1.5px solid var(--border);
  font-weight: 500;
  color: var(--text-primary);
  transition: all 0.2s ease;
  font-size: 0.9rem;
  padding: 0.6rem 0.85rem;
}

:deep(.custom-input .p-inputtext:focus),
:deep(.custom-input input:focus) {
  border-color: var(--primary);
  box-shadow: 0 0 0 3px var(--primary-ring);
}

:deep(.custom-input .p-inputnumber-input) {
  border-radius: 10px;
  border: 1.5px solid var(--border);
  font-weight: 500;
  color: var(--text-primary);
  transition: all 0.2s ease;
  font-size: 0.9rem;
  padding: 0.6rem 0.85rem;
}

:deep(.custom-input .p-inputnumber-input:focus) {
  border-color: var(--primary);
  box-shadow: 0 0 0 3px var(--primary-ring);
}

/* ─── Apply Button ─── */
:deep(.apply-btn) {
  background: var(--primary) !important;
  border: none !important;
  border-radius: 10px !important;
  font-weight: 600 !important;
  padding: 0.6rem 1.2rem !important;
  font-size: 0.85rem !important;
  transition: all 0.2s ease !important;
}

:deep(.apply-btn:hover) {
  background: var(--primary-dark) !important;
  transform: translateY(-1px);
}

/* ─── Product Gallery ─── */
.product-gallery {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.gallery-main-img {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 12px;
  border: 1px solid var(--border);
}

.gallery-thumbs {
  display: flex;
  gap: 8px;
}

.gallery-thumb {
  width: 72px;
  height: 72px;
  border-radius: 8px;
  overflow: hidden;
  border: 2px solid var(--border);
  cursor: pointer;
  transition: all 0.2s ease;
}

.gallery-thumb img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.gallery-thumb:hover {
  border-color: var(--primary-light);
}

.thumb-active {
  border-color: var(--primary) !important;
  box-shadow: 0 0 0 3px var(--primary-ring);
}

/* ─── Product Info ─── */
.product-name {
  font-size: 1.5rem;
  font-weight: 800;
  color: var(--text-primary);
  margin: 0 0 6px;
}

.product-desc {
  font-size: 0.9rem;
  color: var(--text-muted);
  margin: 0;
}

/* ─── Quantity Control ─── */
.quantity-control {
  display: inline-flex;
  align-items: center;
  border: 1.5px solid var(--border);
  border-radius: 10px;
  overflow: hidden;
}

.qty-btn {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--surface-alt);
  border: none;
  cursor: pointer;
  color: var(--text-secondary);
  transition: all 0.15s ease;
}

.qty-btn:hover {
  background: var(--primary-bg);
  color: var(--primary);
}

.qty-btn i {
  font-size: 0.7rem;
}

.qty-value {
  width: 48px;
  text-align: center;
  font-weight: 700;
  font-size: 1rem;
  color: var(--text-primary);
  border-left: 1px solid var(--border);
  border-right: 1px solid var(--border);
  line-height: 36px;
}

/* ─── Price ─── */
.price-box {
  display: flex;
  align-items: baseline;
  gap: 12px;
  background: var(--primary-bg);
  padding: 14px 20px;
  border-radius: 12px;
  border: 1px solid var(--primary-bg-deep);
}

.price-label {
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--text-secondary);
}

.price-amount {
  font-size: 1.75rem;
  font-weight: 800;
  color: var(--primary);
}

/* ─── Payment Methods ─── */
.payment-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
}

.payment-option {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 14px;
  border: 1.5px solid var(--border);
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
  background: var(--surface);
}

.payment-option:hover {
  border-color: var(--primary-light);
  background: var(--primary-bg);
}

.payment-selected {
  border-color: var(--primary) !important;
  background: var(--primary-bg) !important;
  box-shadow: 0 0 0 3px var(--primary-ring);
}

.payment-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.payment-icon {
  font-size: 1.2rem;
}

.payment-logo {
  height: 20px;
  object-fit: contain;
}

.payment-label {
  font-size: 0.82rem;
  font-weight: 600;
  color: var(--text-primary);
}

:deep(.custom-radio .p-radiobutton-box) {
  border-color: var(--border);
}

/* ─── Order Button ─── */
:deep(.order-btn) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  ) !important;
  border: none !important;
  border-radius: var(--radius) !important;
  padding: 12px 24px !important;
  font-size: 0.95rem !important;
  font-weight: 700 !important;
  box-shadow: 0 4px 14px rgba(37, 99, 235, 0.35);
  transition: all 0.25s ease !important;
  letter-spacing: 0.3px;
}

:deep(.order-btn:hover) {
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  ) !important;
  box-shadow: 0 6px 20px rgba(37, 99, 235, 0.45);
  transform: translateY(-1px);
}

/* ─── CTA Button ─── */
:deep(.cta-btn) {
  background: linear-gradient(
    135deg,
    var(--primary) 0%,
    var(--primary-light) 100%
  ) !important;
  border: none !important;
  border-radius: var(--radius) !important;
  padding: 14px 32px !important;
  font-size: 1.05rem !important;
  font-weight: 700 !important;
  box-shadow: 0 4px 14px rgba(37, 99, 235, 0.35);
  transition: all 0.25s ease !important;
  letter-spacing: 0.3px;
}

:deep(.cta-btn:hover) {
  background: linear-gradient(
    135deg,
    var(--primary-dark) 0%,
    var(--primary) 100%
  ) !important;
  box-shadow: 0 6px 20px rgba(37, 99, 235, 0.45);
  transform: translateY(-2px);
}

/* ─── Chart ─── */
.chart-container {
  position: relative;
  width: 100%;
  max-width: 100%;
}

.text-muted {
  color: var(--text-muted);
}

/* ─── Guide Boxes ─── */
.guide-box {
  background: var(--primary-bg);
  border: 1px solid var(--primary-bg-deep);
  border-radius: 12px;
  padding: 16px;
  text-align: center;
  height: 100%;
}

.guide-icon-wrap {
  width: 40px;
  height: 40px;
  background: var(--primary);
  color: #fff;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 10px;
  font-size: 1rem;
}

.guide-title {
  font-size: 0.85rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 4px;
}

.guide-text {
  font-size: 0.8rem;
  color: var(--text-secondary);
  margin: 0;
  line-height: 1.5;
}

/* ─── Policies ─── */
.policies-grid {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.policy-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.policy-icon {
  font-size: 1.2rem;
  color: var(--primary);
  margin-top: 2px;
}

.policy-title {
  font-size: 0.85rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 2px;
}

.policy-text {
  font-size: 0.8rem;
  color: var(--text-secondary);
  margin: 0;
  line-height: 1.5;
}

/* ─── Detail Cards ─── */
.detail-card {
  height: 100%;
}

.detail-item {
  margin-bottom: 8px;
}

.detail-key {
  font-size: 0.82rem;
  font-weight: 700;
  color: var(--text-primary);
  text-decoration: underline;
  text-underline-offset: 2px;
}

.detail-val {
  font-size: 0.82rem;
  color: var(--text-secondary);
  margin-left: 4px;
  line-height: 1.6;
}

.detail-img-wrap {
  border-radius: 10px;
  overflow: hidden;
  border: 1px solid var(--border);
}

.detail-img {
  width: 100%;
  height: 160px;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.detail-img-wrap:hover .detail-img {
  transform: scale(1.05);
}

.science-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.science-list li {
  display: flex;
  align-items: flex-start;
  gap: 8px;
  font-size: 0.82rem;
  color: var(--text-secondary);
  line-height: 1.6;
}

.dot-icon {
  font-size: 0.4rem;
  color: var(--primary);
  margin-top: 6px;
  flex-shrink: 0;
}

/* ─── Video Cards ─── */
:deep(.video-card) {
  --p-card-background: var(--surface);
  --p-card-border-radius: 12px;
  --p-card-shadow: var(--shadow-sm);
  border: 1px solid var(--border);
  overflow: hidden;
  transition: all 0.3s ease;
}

:deep(.video-card:hover) {
  box-shadow: var(--shadow-md);
  transform: translateY(-2px);
}

:deep(.video-card .p-card-content) {
  padding: 0 !important;
}

.video-thumb {
  position: relative;
  cursor: pointer;
  overflow: hidden;
}

.video-img {
  width: 100%;
  height: 140px;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.video-thumb:hover .video-img {
  transform: scale(1.05);
}

.play-overlay {
  position: absolute;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.25);
  transition: background 0.2s ease;
}

.video-thumb:hover .play-overlay {
  background: rgba(0, 0, 0, 0.4);
}

.play-btn-circle {
  width: 48px;
  height: 48px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.play-btn-circle i {
  font-size: 1.8rem;
  color: #dc2626;
}

.video-info {
  padding: 10px 12px;
}

.video-name {
  font-size: 0.82rem;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 2px;
}

.video-role {
  font-size: 0.72rem;
  color: var(--text-muted);
  margin: 0;
}

/* ─── Feedback ─── */
.feedback-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
}

.feedback-card {
  display: flex;
  gap: 12px;
  padding: 16px;
  background: var(--surface-alt);
  border-radius: 12px;
  border: 1px solid var(--border);
  transition: all 0.2s ease;
}

.feedback-card:hover {
  box-shadow: var(--shadow-sm);
  border-color: var(--primary-bg-deep);
}

.feedback-avatar {
  width: 40px;
  height: 40px;
  background: var(--primary-bg);
  border: 1px solid var(--primary-bg-deep);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.feedback-avatar i {
  font-size: 1rem;
  color: var(--primary);
}

.feedback-body {
  flex: 1;
  min-width: 0;
}

.feedback-name {
  font-size: 0.85rem;
  font-weight: 700;
  color: var(--text-primary);
}

.feedback-stars {
  display: inline-flex;
  gap: 1px;
}

.feedback-stars i {
  font-size: 0.7rem;
}

.star-filled {
  color: #f59e0b;
}

.star-empty {
  color: var(--border);
}

.feedback-result {
  font-size: 0.78rem;
  font-weight: 700;
  color: var(--success);
  margin: 4px 0;
  background: rgba(34, 197, 94, 0.08);
  display: inline-block;
  padding: 2px 10px;
  border-radius: 999px;
  border: 1px solid rgba(34, 197, 94, 0.2);
}

.feedback-text {
  font-size: 0.8rem;
  color: var(--text-secondary);
  margin: 6px 0 0;
  line-height: 1.55;
}

/* ─── Animations ─── */
.purchase-screen {
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
  .purchase-screen {
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

  .back-btn {
    color: var(--text-secondary);
  }

  .qty-btn {
    background: var(--surface);
    color: var(--text-secondary);
  }

  .payment-option {
    background: var(--surface);
  }
}

/* ─── Responsive ─── */
@media (max-width: 768px) {
  .purchase-screen {
    padding: 1rem 0.75rem;
  }

  .payment-grid {
    grid-template-columns: 1fr;
  }

  .feedback-grid {
    grid-template-columns: 1fr;
  }

  .price-amount {
    font-size: 1.35rem;
  }

  .product-name {
    font-size: 1.2rem;
  }

  .section-title-lg {
    font-size: 1.2rem;
  }
}

@media (min-width: 1024px) {
  .purchase-screen {
    padding: 2.5rem 2.5rem;
  }
}

/* ─── Legal Information ─── */
.legal-item {
  display: flex;
  gap: 6px;
  margin-bottom: 10px;
  line-height: 1.6;
}

.legal-key {
  font-size: 0.82rem;
  font-weight: 700;
  color: var(--text-primary);
  white-space: nowrap;
  flex-shrink: 0;
}

.legal-val {
  font-size: 0.82rem;
  color: var(--text-secondary);
}

.cert-img-wrap {
  text-align: center;
}

.cert-img {
  width: 100%;
  height: 120px;
  object-fit: cover;
  border-radius: 10px;
  border: 1px solid var(--border);
  transition: transform 0.3s ease;
}

.cert-img-wrap:hover .cert-img {
  transform: scale(1.03);
}

.cert-caption {
  font-size: 0.72rem;
  color: var(--text-muted);
  font-weight: 600;
  margin-top: 6px;
  text-align: center;
}

.legal-disclaimer {
  display: flex;
  align-items: flex-start;
  gap: 8px;
  margin-top: 20px;
  padding: 12px 16px;
  background: var(--surface-alt);
  border: 1px solid var(--border);
  border-radius: 10px;
}

.legal-disclaimer i {
  color: var(--text-muted);
  font-size: 0.9rem;
  margin-top: 2px;
  flex-shrink: 0;
}

.legal-disclaimer p {
  font-size: 0.75rem;
  color: var(--text-muted);
  margin: 0;
  line-height: 1.6;
}
</style>
