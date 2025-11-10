import BaseApi from './baseApi';

class AuthApi extends BaseApi {
    constructor() {
        super('auth');
    }

    async login(userInfo) {
        let res = await this.post(this.ApiURL + '/login', userInfo);
        return res;
    }

    async introspect(paramToken) {
        let res = await this.post(this.ApiURL + '/introspect', paramToken);
        return res;
    }

    async forgot(userInfo) {
        let res = await this.post(this.ApiURL + '/forgot-password', userInfo);
        return res;
    }

    async logout(paramToken) {
        let res = await this.post(this.ApiURL + '/logout', paramToken);
        return res;
    }
}

export default new AuthApi();
