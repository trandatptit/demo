<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
    <div class="container mx-auto px-4 py-16">
      <div class="max-w-4xl mx-auto">
        <h1 class="text-5xl font-bold text-gray-800 mb-6 text-center">
          Welcome to Vue 3 + Tailwind
        </h1>
        <p class="text-xl text-gray-600 mb-8 text-center">
          Vue Router and Pinia are ready to use!
        </p>

        <div class="bg-white rounded-lg shadow-xl p-8 mb-8">
          <h2 class="text-2xl font-semibold text-gray-800 mb-4">
            Counter Example
          </h2>
          <div class="flex items-center justify-center space-x-4">
            <button
              @click="counterStore.decrement"
              class="px-6 py-3 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-colors"
            >
              -
            </button>
            <span class="text-4xl font-bold text-gray-800">
              {{ counterStore.count }}
            </span>
            <button
              @click="counterStore.increment"
              class="px-6 py-3 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
            >
              +
            </button>
          </div>
          <p class="text-center mt-4 text-gray-600">
            Double: {{ counterStore.doubleCount }}
          </p>

          <AutoComplete
            class="w-full mt-6 bg-gray-100 p-2 rounded"
            v-model="value"
            :suggestions="items"
            @complete="search"
          />
        </div>

        <div class="text-center">
          <router-link
            to="/about"
            class="inline-block px-8 py-3 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition-colors"
          >
            Go to About Page
          </router-link>
        </div>
      </div>
    </div>
    <GlobalLoading :show="loadingStore.globalLoading" text="Please wait..." />
  </div>
</template>

<script setup>
import { useCounterStore } from "../stores/counter";
import { useLoadingStore } from "../stores/globalLoading";
import AutoComplete from "primevue/autocomplete";
import GlobalLoading from "../components/Loading/GlobalLoading/GlobalLoading.vue";
import { ref } from "vue";

const counterStore = useCounterStore();
const loadingStore = useLoadingStore();
// loadingStore.mask();

const value = ref(null);
const items = ref([]);

const search = (event) => {
  items.value = [...Array(10).keys()].map((item) => event.query + "-" + item);
};
</script>
