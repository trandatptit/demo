<template>
    <div class="min-h-screen bg-gray-50 py-6 px-4">
        <div class="max-w-5xl mx-auto">
            <!-- Back Button -->
            <div class="mb-4">
                <Button 
                    label="Về trang chủ" 
                    icon="pi pi-arrow-left" 
                    text 
                    severity="secondary" 
                    @click="goBack"
                    class="text-gray-600 hover:text-gray-800" 
                />
            </div>

            <!-- Main Content Card -->
            <div class="bg-white rounded-2xl shadow-xl p-6 md:p-8">
                <!-- Title -->
                <div class="mb-6">
                    <h1 class="text-xl md:text-2xl font-bold text-gray-800 mb-2">
                        Biểu Đồ Tăng Trưởng Dự Kiến (đến 20 tuổi)
                    </h1>
                </div>

                <!-- Chart Section -->
                <div class="card mb-6">
                    <Chart 
                        type="bar" 
                        :data="chartData" 
                        :options="chartOptions" 
                        class="h-[350px]"
                    />
                </div>

                <!-- Info Cards Grid -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
                    <!-- AI Recommendation Card -->
                    <div class="bg-white border border-gray-200 rounded-2xl p-6">
                        <div class="flex items-start gap-3 mb-3">
                            <i class="pi pi-sparkles text-2xl text-blue-600"></i>
                            <h3 class="text-lg font-semibold text-gray-800">
                                Lời khuyên từ AI
                            </h3>
                        </div>
                        <p class="text-sm text-gray-600 leading-relaxed">
                            {{ aiRecommendation }}
                        </p>
                    </div>

                    <!-- Traditional Method Card -->
                    <div class="bg-white border border-gray-200 rounded-2xl p-6">
                        <div class="flex items-start gap-2 mb-2">
                            <i class="pi pi-heart text-2xl text-blue-600"></i>
                            <h3 class="text-lg font-semibold text-gray-800">
                                Thông điệp truyền cảm hứng
                            </h3>
                        </div>
                        <p class="text-sm text-gray-600 leading-relaxed">
                            {{ inspirationMessageDisplay }}
                        </p>
                    </div>
                </div>

                <!-- Download PDF Button -->
                <div class="text-center">
                    <Button
                        label="Tải kết quả PDF"
                        icon="pi pi-download"
                        class="bg-blue-800 hover:bg-blue-900 text-white font-semibold px-6 py-2 rounded-lg shadow-md hover:shadow-lg transition-all duration-300"
                    />
                </div>
            </div>
        </div>

        <!-- Toast for notifications -->
        <Toast />
    </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'; // ✅ Thêm computed
import { useRouter } from 'vue-router';
import Chart from 'primevue/chart';
import Button from 'primevue/button';
import Toast from 'primevue/toast';
import { useToast } from 'primevue/usetoast';

const router = useRouter();
const toast = useToast();

// Accept props
const props = defineProps({
    userId: {
        type: String,
        default: '12121'
    },
    ageData: {
        type: Array,
        default: () => [
            { age: '16t', height: 163 },
            { age: '17t', height: 168 },
            { age: '18t', height: 171, locked: true },
            { age: '19t', height: 175 },
            { age: '20t', height: 179 }
        ]
    },
    aiRecommendation : {
        type: String,
        default: 'Nam, 16 tuổi - Nếu duy trì ngủ trước 10 giờ và bổ sung 40 Canxi, bạn có thể đạt 179.0cm 😊'
    },
    inspirationMessage: {
        type: String,
        default: ''
    }
});

// Computed inspiration message with user ID
const inspirationMessageDisplay = computed(() => {
    if (props.inspirationMessage) {
        return props.inspirationMessage;
    }
    return `Cố lên ${props.userId}, tin vào bản thân và chăm chỉ chăm sóc sức khỏe của chính mình 💙`;
});

// Chart refs
const chartData = ref();
const chartOptions = ref();

// Initialize chart data on mount
onMounted(() => {
    chartData.value = setChartData();
    chartOptions.value = setChartOptions();
});

// Watch for prop changes
watch(() => props.ageData, () => {
    chartData.value = setChartData();
}, { deep: true });

// Set chart data
const setChartData = () => {
    const labels = props.ageData.map(item => item.age);
    const data = props.ageData.map(item => item.height);
    const backgrounds = props.ageData.map(item => 
        item.locked ? 'rgba(200, 200, 200, 0.5)' : 'rgba(30, 64, 175, 0.9)'
    );
    const borderColors = props.ageData.map(item => 
        item.locked ? 'rgba(200, 200, 200, 1)' : 'rgba(30, 64, 175, 1)'
    );

    return {
        labels: labels,
        datasets: [
            {
                label: 'Chiều cao (cm)',
                data: data,
                backgroundColor: backgrounds,
                borderColor: borderColors,
                borderWidth: 0,
                borderRadius: 6,
                barThickness: 60
            }
        ]
    };
};

// Set chart options
const setChartOptions = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--p-text-color') || '#333';
    const textColorSecondary = documentStyle.getPropertyValue('--p-text-muted-color') || '#666';
    const surfaceBorder = documentStyle.getPropertyValue('--p-content-border-color') || 'rgba(0, 0, 0, 0.05)';

    return {
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: false
            },
            tooltip: {
                enabled: true,
                backgroundColor: 'rgba(0, 0, 0, 0.8)',
                titleColor: '#fff',
                bodyColor: '#fff',
                padding: 12,
                cornerRadius: 8,
                displayColors: false,
                callbacks: {
                    title: function(context) {
                        const index = context[0].dataIndex;
                        return props.ageData[index].locked ? 'Chưa mở khóa' : 'Chiều cao dự kiến';
                    },
                    label: function(context) {
                        const index = context.dataIndex;
                        const item = props.ageData[index];
                        if (item.locked) {
                            return 'Mở khóa để xem chi tiết';
                        }
                        return context.parsed.y + ' cm';
                    }
                }
            }
        },
        scales: {
            y: {
                beginAtZero: false,
                min: 150,
                max: 190,
                ticks: {
                    stepSize: 10,
                    callback: function(value) {
                        return value + 'cm';
                    },
                    font: {
                        size: 11
                    },
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder,
                    drawBorder: false
                },
                border: {
                    display: false
                }
            },
            x: {
                grid: {
                    display: false,
                    drawBorder: false
                },
                ticks: {
                    font: {
                        size: 12,
                        weight: '500'
                    },
                    color: textColor
                },
                border: {
                    display: false
                }
            }
        },
        layout: {
            padding: {
                top: 40,
                bottom: 10,
                left: 10,
                right: 10
            }
        },
    };
};

// Go back function
const goBack = () => {
    router.push({ name: 'Home' });
};

// Download PDF
const downloadPDF = () => {
    toast.add({
        severity: 'success',
        summary: 'Thành công',
        detail: 'Đang tải xuống file PDF...',
        life: 3000
    });
    
    // Logic download PDF here
    console.log('Downloading PDF...');
};
</script>

<style scoped>
.card {
    position: relative;
}

:deep(.p-chart) {
    position: relative;
}

:deep(canvas) {
    position: relative;
}
</style>