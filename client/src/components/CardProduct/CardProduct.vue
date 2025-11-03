<template>
    <Card 
        class="product-card"
        :pt="{
            root: { class: 'rounded-2xl border border-gray-200 dark:border-gray-700 shadow-sm hover:shadow-lg transition-all duration-300 overflow-hidden' },
            body: { class: 'p-4' },
            header: { class: 'p-0' },
            content: { class: 'p-0 space-y-2' }
        }"
    >
        <!-- Product Image -->
        <template #header>
            <div class="relative bg-gray-100 dark:bg-gray-800 aspect-square flex items-center justify-center overflow-hidden group">
                <img 
                    :src="product.image" 
                    :alt="product.name"
                    class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
                />
                
                <!-- Badge using PrimeVue Badge concept -->
                <div v-if="product.badge" class="absolute top-3 left-3">
                    <span class="inline-flex items-center bg-red-500 text-white text-xs font-semibold px-2.5 py-1 rounded-full">
                        {{ product.badge }}
                    </span>
                </div>
            </div>
        </template>

        <!-- Product Info -->
        <template #content>
            <!-- Product Name -->
            <h3 class="text-base font-semibold text-gray-800 dark:text-white line-clamp-2 min-h-[3rem]">
                {{ product.name }}
            </h3>

            <!-- Product Description -->
            <p class="text-xs text-gray-500 dark:text-gray-400 line-clamp-2 min-h-[2.5rem]">
                {{ product.description }}
            </p>

            <!-- Price and Cart Button -->
            <div class="flex items-center justify-between pt-2">
                <span class="text-lg font-bold text-gray-900 dark:text-white">
                    {{ formatPrice(product.price) }}
                </span>

                <Button
                    icon="pi pi-shopping-cart"
                    rounded
                    outlined
                    severity="secondary"
                    size="small"
                    @click="addToCart"
                    aria-label="Add to cart"
                    :pt="{
                        root: { class: 'w-9 h-9 hover:bg-blue-50 dark:hover:bg-blue-900/20' },
                        icon: { class: 'text-base' }
                    }"
                />
            </div>
        </template>
    </Card>
</template>

<script setup>
import Card from 'primevue/card';
import Button from 'primevue/button';

const props = defineProps({
    product: {
        type: Object,
        required: true,
        validator: (value) => {
            return value.id && value.name && value.price;
        }
    }
});

const emit = defineEmits(['add-to-cart']);

const formatPrice = (price) => {
    return new Intl.NumberFormat('vi-VN', {
        style: 'decimal',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    }).format(price) + 'đ';
};

const addToCart = () => {
    emit('add-to-cart', props.product);
};
</script>

<style scoped>
.product-card {
    max-width: 300px;
    width: 100%;
    transition: transform 0.2s;
}

.product-card:hover {
    transform: translateY(-2px);
}
</style>