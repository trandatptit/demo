import axios from 'axios';
import getKeyLocalStorage from '@/utilities/commons/getKeyLocalStorage.js';
// import { useLoadingStore } from '@/stores';

// Add a request interceptor
axios.interceptors.request.use(
    function (config) {
        let token = localStorage.getItem(getKeyLocalStorage('Token'));
        config.headers.Authorization = '';
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    function (error) {
        // useLoadingStore().unmask();
        return Promise.reject(error);
    }
);

// Add a response interceptor
axios.interceptors.response.use(
    function (response) {
        return response;
    },
    function (error) {
        // useLoadingStore().unmask();
        return Promise.reject(error);
    }
);

export default class BaseApi {
    constructor(pathName = '') {
        this.ApiURL = `${import.meta.env.VITE_BASE_URL}/api/${pathName}`;
        this.config = {
            headers: {
                'Content-Type': 'application/json',
            },
        };
    }

    async getById(id) {
        let url = `${this.ApiURL}/${id}`;
        return await this.get(url);
    }

    async get(url) {
        let response = {};
        try {
            var res = await axios.get(url, this.config);
            if (res?.status == 200 && res?.data) {
                response = { ...res.data };
            }
        } catch (error) {
            response = this.handleError(error);
        } finally {
            return response;
        }
    }

    async post(url, param) {
        let response = {};
        try {
            var res = await axios.post(url, param, this.config);
            if (res?.status == 200 && res?.data) {
                response = { ...res.data };
            }
        } catch (error) {
            response = this.handleError(error);
        } finally {
            return response;
        }
    }

    async put(url, param) {
        let response = {};
        try {
            var res = await axios.put(url, param, this.config);
            if (res?.status == 200 && res?.data) {
                response = { ...res.data };
            }
        } catch (error) {
            response = this.handleError(error);
        } finally {
            return response;
        }
    }

    async delete(url) {
        let response = {};
        try {
            var res = await axios.delete(url, this.config);
            if (res?.status == 200 && res?.data) {
                response = { ...res.data };
            }
        } catch (error) {
            response = this.handleError(error);
        } finally {
            return response;
        }
    }

    async getFile(url) {
        try {
            let response = await axios.get(url, {
                responseType: 'blob',
            });
            return response;
        } catch (error) {
            return this.handleError(error);
        }
    }

    //#region CustomeFunction
    // Custome giá trị trả về
    handleError(error) {
        console.log(error);
        return {
            success: false,
            userMsg: error,
        };
    }
    //#endregion
}
