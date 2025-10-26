import BaseApi from './baseApi';

class MessageApi extends BaseApi {
    constructor() {
        super('Messages');
    }

    async getUserMessage(userId) {
        let res = await this.get(this.ApiURL + '/user' + `?userId=${userId}`);
        return res;
    }

    async getChatHistory(param) {
        let res = await this.post(this.ApiURL + '/history', param);
        return res;
    }

    async getAll() {
        let res = await this.get(this.ApiURL);
        return res;
    }
}

export default new MessageApi();
