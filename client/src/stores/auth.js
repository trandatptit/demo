import { ref } from 'vue';
import { defineStore } from 'pinia';
import authApi from '@/apis/authApi';
import userApi from '@/apis/userApi';
import getKeyLocalStorage from '@/utilities/commons/getKeyLocalStorage';

export const useAuthStore = defineStore('auth', () => {
    const loggedIn = ref(
        localStorage.getItem(getKeyLocalStorage('Token')) != null
    );
    const userInfo = ref(
        JSON.parse(localStorage.getItem(getKeyLocalStorage('UserInfo')))
    );
    const isAdmin = ref(
        JSON.parse(
            localStorage.getItem(getKeyLocalStorage('IsAdmin')) ?? 'false'
        )
    );

    /**
     * Thực hiện đăng nhập
     * @param {*} user
     * @returns
     */
    async function login(user) {
        // Lấy token từ API
        let res = await authApi.login(user);
        if (!res?.status || !res?.result) {
            return;
        }

        localStorage.setItem(
            getKeyLocalStorage('Token'),
            res?.result?.token ?? ''
        );

        let userInfoResponse = await userApi.getUserInfo();
        if (!userInfoResponse.status || !userInfoResponse?.result) {
            return;
        }
        localStorage.setItem(
            getKeyLocalStorage('UserInfo'),
            JSON.stringify(userInfoResponse.result)
        );
        localStorage.setItem(
            getKeyLocalStorage('IsAdmin'),
            userInfoResponse?.result?.isAdmin ?? 'false'
        );
        userInfo.value = userInfoResponse.result;
        isAdmin.value = userInfoResponse?.result?.isAdmin;
        loggedIn.value = true;
        return userInfoResponse;
    }

    /**
     * Thực hiện đăng xuất
     */
    function logout() {
        localStorage.removeItem(getKeyLocalStorage('Token'));
        localStorage.removeItem(getKeyLocalStorage('UserInfo'));
        localStorage.removeItem(getKeyLocalStorage('IsAdmin'));
        loggedIn.value = false;
        userInfo.value = {};
        isAdmin.value = null;
    }

    async function checkToken() {
        var isValid = true;
        var tokenLocal = localStorage.getItem(getKeyLocalStorage('Token'));
        if (tokenLocal) {
            var response = await authApi.introspect({
                token: tokenLocal,
            });
            if (response?.status && response?.result?.val) {
                isValid = true;
            } else {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Thực hiện đăng ký tài khoản
     * @param {*} user
     * @returns
     */
    async function signUp(user) {
        let userInfoResponse = await userApi.createFullUserInfo(user);
        if (!userInfoResponse.status || !userInfoResponse?.result) {
            return;
        }
        localStorage.setItem(
            getKeyLocalStorage('UserInfo'),
            JSON.stringify(userInfoResponse.result)
        );
        localStorage.setItem(
            getKeyLocalStorage('IsAdmin'),
            userInfoResponse?.result?.admin
        );
        userInfo.value = userInfoResponse.result;
        isAdmin.value = userInfoResponse?.result?.admin;
        loggedIn.value = true;

        // Lấy token từ API
        let res = await authApi.login({
            username: user.username,
            password: user.password,
        });
        if (!res) {
            return;
        }

        localStorage.setItem(
            getKeyLocalStorage('Token'),
            res?.result?.token ?? ''
        );

        return userInfoResponse;
    }

    /**
     * Lấy ra thông tin người dùng hiện tại
     * @returns
     */
    async function getUserInfoCurrent() {
        var result = userInfo.value;
        if (!result) {
            var resultUser = await userApi.getUserInfo();
            if (resultUser?.status && resultUser?.result) {
                result = resultUser.result;
                userInfo.value = resultUser.result;
            }
        }
        return result;
    }

    return {
        loggedIn,
        userInfo,
        isAdmin,
        login,
        logout,
        getUserInfoCurrent,
        signUp,
        checkToken,
    };
});
