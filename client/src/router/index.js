import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/Home.vue'),
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About.vue'),
    },
    // Route cho 404 - phải đặt ở cuối cùng
    {
        path: '/:pathMatch(.*)*',
        redirect: { name: 'Home' },
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// Navigation guard toàn cục
router.beforeEach((to, from, next) => {
    // Nếu route không tồn tại
    if (!to.matched.length) {
        next({ name: 'Home' });
        return;
    }
    next();
});

export default router;
