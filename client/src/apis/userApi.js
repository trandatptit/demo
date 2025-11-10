import BaseApi from './baseApi';

class UserApi extends BaseApi {
    constructor() {
        super('users');
    }

    async getAll() {
        let res = await this.get(this.ApiURL);
        return res;
    }

    async getUserById(userId) {
        let res = await this.get(this.ApiURL + `/${userId}`);
        return res;
    }

    async getUserInfo() {
        let res = await this.get(this.ApiURL + '/my-info');
        return res;
    }

    async createSimpleUserInfo(userInfo) {
        let res = await this.post(`${this.ApiURL}/create-basic`, userInfo);
        return res;
    }

    async createFullUserInfo(userInfo) {
        let res = await this.post(`${this.ApiURL}/create-detail`, userInfo);
        return res;
    }

    async getAll(param, page = 0, size = 10) {
        let res = await this.get(
            `${this.ApiURL}/getAll?page=${page}&size=${size}`
        );
        return res;
    }

    async updateUserInfo(userId, userInfo) {
        let res = await this.put(this.ApiURL + `/${userId}`, userInfo);
        return res;
    }

    async rateUser(userId, ratePoint) {
        let res = await this.put(
            this.ApiURL + `/rate?userId=${userId}&ratePoint=${ratePoint}`
        );
        return res;
    }

    async getSuggestFreelancer(workField, fieldTags) {
        let res = await this.get(
            this.ApiURL +
                `/suggest-freelancer?workField=${workField}&fieldTags=${fieldTags}`
        );
        return res;
    }
}

export default new UserApi();
