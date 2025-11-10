import BaseApi from './baseApi';

class OrderApi extends BaseApi {
    constructor() {
        super('orders');
    }

    async getAll(param, page = 0, size = 10) {
        let res = await this.get(
            `${this.ApiURL}/getAll?page=${page}&size=${size}`
        );
        return res;
    }
    async createOrder(param) {
        let res = await this.post(this.ApiURL + `/create`, param);
        return res;
    }
}

export default new OrderApi();
