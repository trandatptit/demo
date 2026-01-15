<template>
  <HomeHeader />
  <router-view :key="$route.fullPath" />
  <AILoadingPopup />
</template>

<script setup>
import { useHead } from "@vueuse/head";
import HomeHeader from "./views/Header/HomeHeader.vue";
import AILoadingPopup from "@/components/Loading/AILoadingPopup.vue";

// SEO Configuration
useHead({
  title: "Everest Pharma - Giải pháp tăng chiều cao hiệu quả",
  meta: [
    {
      name: "description",
      content:
        "Everest Pharma cung cấp giải pháp tăng chiều cao toàn diện với lộ trình dinh dưỡng, tập luyện và tư vấn chuyên gia. Phát triển chiều cao tối ưu cho mọi lứa tuổi.",
    },
    {
      name: "keywords",
      content:
        "tăng chiều cao, dinh dưỡng tăng chiều cao, tập luyện tăng chiều cao, Everest Pharma, phát triển chiều cao",
    },
    {
      name: "author",
      content: "Everest Pharma",
    },
    // Open Graph tags
    {
      property: "og:title",
      content: "Everest Pharma - Giải pháp tăng chiều cao hiệu quả",
    },
    {
      property: "og:description",
      content:
        "Giải pháp tăng chiều cao toàn diện với lộ trình dinh dưỡng, tập luyện và tư vấn chuyên gia",
    },
    {
      property: "og:type",
      content: "website",
    },
    {
      property: "og:url",
      content: "https://everestpharma.vn",
    },
    {
      property: "og:image",
      content: "https://everestpharma.vn/og-image.jpg",
    },
    // Twitter Card tags
    {
      name: "twitter:card",
      content: "summary_large_image",
    },
    {
      name: "twitter:title",
      content: "Everest Pharma - Giải pháp tăng chiều cao hiệu quả",
    },
    {
      name: "twitter:description",
      content:
        "Giải pháp tăng chiều cao toàn diện với lộ trình dinh dưỡng, tập luyện và tư vấn chuyên gia",
    },
    {
      name: "twitter:image",
      content: "https://everestpharma.vn/og-image.jpg",
    },
    // Mobile optimization
    {
      name: "format-detection",
      content: "telephone=no",
    },
    {
      name: "theme-color",
      content: "#4F46E5",
    },
  ],
  link: [
    {
      rel: "canonical",
      href: "https://everestpharma.vn",
    },
    {
      rel: "icon",
      type: "image/svg+xml",
      href: "/vite.svg",
    },
  ],
  script: [
    {
      type: "application/ld+json",
      children: JSON.stringify({
        "@context": "https://schema.org",
        "@type": "Organization",
        name: "Everest Pharma",
        url: "https://everestpharma.vn",
        logo: "https://everestpharma.vn/logo.png",
        description:
          "Giải pháp tăng chiều cao toàn diện với lộ trình dinh dưỡng, tập luyện và tư vấn chuyên gia",
        contactPoint: {
          "@type": "ContactPoint",
          telephone: "+84-xxx-xxx-xxx",
          contactType: "Customer Service",
        },
      }),
    },
  ],
  htmlAttrs: {
    lang: "vi",
  },
});
</script>
