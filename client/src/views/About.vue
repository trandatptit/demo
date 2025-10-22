<template>
  <div class="min-h-screen bg-gradient-to-br from-purple-50 to-pink-100">
    <div class="container mx-auto px-4 py-16">
      <div class="max-w-4xl mx-auto">
        <h1 class="text-5xl font-bold text-gray-800 mb-6 text-center">
          About Page
        </h1>

        <div class="bg-white rounded-lg shadow-xl p-8 mb-8">
          <h2 class="text-2xl font-semibold text-gray-800 mb-4">Tech Stack</h2>
          <ul class="space-y-3 text-gray-700">
            <li class="flex items-center">
              <span class="w-3 h-3 bg-green-500 rounded-full mr-3"></span>
              <strong class="mr-2">Vue 3:</strong> Progressive JavaScript
              Framework
            </li>
            <li class="flex items-center">
              <span class="w-3 h-3 bg-blue-500 rounded-full mr-3"></span>
              <strong class="mr-2">Tailwind CSS:</strong> Utility-first CSS
              Framework
            </li>
            <li class="flex items-center">
              <span class="w-3 h-3 bg-purple-500 rounded-full mr-3"></span>
              <strong class="mr-2">Vue Router:</strong> Official routing library
            </li>
            <li class="flex items-center">
              <span class="w-3 h-3 bg-yellow-500 rounded-full mr-3"></span>
              <strong class="mr-2">Pinia:</strong> State management for Vue
            </li>
            <li class="flex items-center">
              <span class="w-3 h-3 bg-indigo-500 rounded-full mr-3"></span>
              <strong class="mr-2">Vite:</strong> Next generation frontend
              tooling
            </li>
          </ul>
        </div>

        <div class="text-center">
          <router-link
            to="/"
            class="inline-block px-8 py-3 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors"
          >
            Back to Home
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// About page logic here
</script>
