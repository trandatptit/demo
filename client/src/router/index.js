import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/home',
        name: 'Home',
        component: () =>
            import(/* webpackChunkName: "home" */ '../views/Home.vue'),
    },
    {
        path: '/about',
        name: 'About',
        component: () =>
            import(/* webpackChunkName: "about" */ '../views/About.vue'),
    },
    {
        path: '/',
        name: 'HomePage',
        component: () =>
            import(
                /* webpackChunkName: "homepage" */ '../views/HomePage/HomePage.vue'
            ),
    },
    {
        path: '/test',
        name: 'Test',
        component: () =>
            import(/* webpackChunkName: "test" */ '../views/TestPopup.vue'),
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
