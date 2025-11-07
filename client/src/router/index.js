import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    // {
    //     path: '/home',
    //     name: 'Home',
    //     component: () =>
    //         import(/* webpackChunkName: "home" */ '../views/Home.vue'),
    // },
    // {
    //     path: '/about',
    //     name: 'About',
    //     component: () =>
    //         import(/* webpackChunkName: "about" */ '../views/About.vue'),
    // },
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
    {
        path: '/test-screen',
        name: 'TestScreen',
        component: () =>
            import(
                /* webpackChunkName: "TestScreen" */ '../views/TestScreen.vue'
            ),
    },
    {
        path: '/account',
        name: 'Account',
        children: [
            {
                path: 'profile',
                name: 'UserProfile',
                component: () =>
                    import(
                        /* webpackChunkName: "UserProfile" */ '../views/Account/UserProfile.vue'
                    ),
            },
            {
                path: 'manage-account',
                name: 'ManageAccount',
                component: () =>
                    import(
                        /* webpackChunkName: "ManageAccount" */ '../views/Account/ManagerAccount.vue'
                    ),
            },
        ],
    },
    {
        path: '/height-prediction',
        name: 'HeightPrediction',
        component: () =>
            import(
                /* webpackChunkName: "HeightPrediction" */ '../views/HeightPrediction/HeightPrediction.vue'
            ),
    },
    // Route cho 404 - phải đặt ở cuối cùng
    {
        path: '/:pathMatch(.*)*',
        redirect: { name: 'HomePage' },
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
        next({ name: 'HomePage' });
        return;
    }
    next();
});

export default router;
