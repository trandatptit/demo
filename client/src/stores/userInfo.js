import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useUserInfoStore = defineStore('userInfo', () => {
    // State
    const user = ref(null);

    // Getters
    const isLoggedIn = ref(false);

    const isAdmin = ref(false);

    // Actions
    function setUserInfo(userInfo) {
        user.value = userInfo;
    }

    function loginUser(userInfo) {
        user.value = userInfo;
        isLoggedIn.value = true;
        isAdmin.value = userInfo.RoleType === 'admin';
    }

    function logoutUser() {
        user.value = null;
        isLoggedIn.value = false;
        isAdmin.value = false;
    }

    function clearUserInfo() {
        user.value = null;
    }

    function getUserInfo() {
        return user.value;
    }

    function getIsLoggedIn() {
        return isLoggedIn.value;
    }

    function getIsAdmin() {
        return isAdmin.value;
    }

    return {
        user,
        isLoggedIn,
        isAdmin,
        getUserInfo,
        getIsLoggedIn,
        getIsAdmin,
        setUserInfo,
        clearUserInfo,
        loginUser,
        logoutUser,
    };
});
