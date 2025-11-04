<template>
    <div class="min-h-screen bg-gray-100 py-8 px-4">
        <div class="max-w-6xl mx-auto">
            <!-- Back Button -->
            <div class="mb-6">
                <Button label="Về trang chủ" icon="pi pi-arrow-left" text severity="secondary" @click="goBack"
                    class="text-gray-600 hover:text-gray-800" />
            </div>

            <!-- Main Content Card -->
            <div class="bg-white rounded-3xl shadow-xl p-8 md:p-12">
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
                    <!-- Left Side - Chart -->
                    <div>
                        <!-- Title -->
                        <div class="text-center mb-8">
                            <h1 class="text-3xl md:text-4xl font-bold text-gray-800 mb-3">
                                Kết Quả cho {{ userIdDisplay }}
                            </h1>
                            <p class="text-base text-gray-500">
                                Hành trình vươn tới đỉnh cao của bạn bắt đầu từ đây.
                            </p>
                        </div>

                        <!-- Chart with Icons -->
                        <div class="card relative">
                            <Chart type="bar" :data="chartData" :options="chartOptions" class="h-[400px]" />
                            
                            <!-- Stick Figure Icons Overlay -->
                            <div class="absolute bottom-16 left-0 right-0 flex justify-around px-12">
                                <i class="pi pi-user text-3xl text-white drop-shadow-lg"></i>
                                <i class="pi pi-user text-3xl text-white drop-shadow-lg"></i>
                                <i class="pi pi-user text-3xl text-gray-400 drop-shadow-lg"></i>
                            </div>
                        </div>
                    </div>

                    <!-- Right Side - Result & Button -->
                    <div class="flex flex-col items-center justify-center space-y-8">
                        <!-- Prediction Result -->
                        <div class="text-center">
                            <p class="text-sm text-gray-500 mb-2">
                                Chiều cao dự kiến năng (All transfer)
                            </p>
                            <p class="text-7xl md:text-8xl font-bold text-gray-800 mb-1">
                                {{ predictedHeightDisplay }}
                                <span class="text-4xl">cm</span>
                            </p>
                        </div>

                        <!-- Unlock Section -->
                        <div class="bg-gray-50 rounded-2xl p-8 text-center w-full">
                            <p class="text-lg font-semibold text-gray-800 mb-4">
                                Mở khóa tiềm năng thật sự!
                            </p>
                            <Button label="Chỉ với 30.000đ"
                                class="bg-blue-800 hover:bg-blue-900 text-white font-bold text-base px-8 py-3 rounded-xl shadow-md hover:shadow-lg transition-all duration-300"
                                @click="unlockFeature" />
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Toast for notifications -->
        <Toast />
    </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue';
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
    currentHeight: {
        type: Number,
        default: 165
    },
    inheritedHeight: {
        type: Number,
        default: 168
    },
    potentialHeight: {
        type: Number,
        default: 171
    }
    
});

// Display values
const userIdDisplay = ref(props.userId);
const predictedHeightDisplay = ref(props.potentialHeight.toFixed(1));

// Chart refs - theo pattern PrimeVue
const chartData = ref();
const chartOptions = ref();

// Initialize chart data on mount
onMounted(() => {
    chartData.value = setChartData();
    chartOptions.value = setChartOptions();
});

// Watch for prop changes
watch(() => props, (newProps) => {
    userIdDisplay.value = newProps.userId;
    predictedHeightDisplay.value = newProps.potentialHeight.toFixed(1);
    
    // Update chart data when props change
    chartData.value = setChartData();
}, { deep: true });

// Set chart data - theo pattern PrimeVue
const setChartData = () => {
    return {
        labels: ['Hiện tại', 'Dự truyền', 'Tiềm năng (CDC)'],
        datasets: [
            {
                label: 'Chiều cao (cm)',
                data: [props.currentHeight, props.inheritedHeight, props.potentialHeight],
                backgroundColor: [
                    'rgba(79, 134, 198, 0.9)',   // Dark Blue
                    'rgba(96, 150, 210, 0.9)',   // Medium Blue
                    'rgba(180, 200, 225, 0.9)'   // Light Blue
                ],
                borderColor: [
                    'rgba(79, 134, 198, 1)',
                    'rgba(96, 150, 210, 1)',
                    'rgba(180, 200, 225, 1)'
                ],
                borderWidth: 0,
                borderRadius: 8,
                barThickness: 100
            }
        ]
    };
};

// Set chart options - theo pattern PrimeVue
const setChartOptions = () => {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--p-text-color') || '#333';
    const textColorSecondary = documentStyle.getPropertyValue('--p-text-muted-color') || '#666';
    const surfaceBorder = documentStyle.getPropertyValue('--p-content-border-color') || 'rgba(0, 0, 0, 0.05)';

    return {
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: false,
                labels: {
                    color: textColor
                }
            },
            tooltip: {
                enabled: true, // Bật tooltip
                backgroundColor: 'rgba(0, 0, 0, 0.8)',
                titleColor: '#fff',
                bodyColor: '#fff',
                padding: 12,
                cornerRadius: 8,
                displayColors: false,
                callbacks: {
                    label: function(context) {
                        return 'Chiều cao: ' + context.parsed.y + ' cm';
                    }
                }
            },
        },
        scales: {
            y: {
                beginAtZero: true,
                max: 200,
                ticks: {
                    stepSize: 50,
                    callback: function (value) {
                        return value === 0 ? '' : value + 'cm';
                    },
                    font: {
                        size: 12,
                        family: 'Arial'
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
                    drawBorder: false,
                    color: surfaceBorder
                },
                ticks: {
                    font: {
                        size: 13,
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
                top: 30, // Tăng padding top để có chỗ hiển thị số
                bottom: 20
            }
        },
    };
};

// Go back function
const goBack = () => {
    router.push({ name: 'Home' });
};

// Unlock feature
const unlockFeature = () => {
    toast.add({
        severity: 'info',
        summary: 'Đang xử lý',
        detail: 'Đang chuyển đến trang thanh toán...',
        life: 2000
    });

    setTimeout(() => {
        router.push({ name: 'Payment', params: { feature: '30k' } });
    }, 2000);
};
</script>

<style scoped>
/* Card wrapper for chart */
.card {
    position: relative;
}

/* Custom styling for chart canvas */
:deep(.p-chart) {
    position: relative;
}

:deep(canvas) {
    position: relative;
}
</style>