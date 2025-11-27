import BaseApi from './baseApi';
class GeneticsApi extends BaseApi {
    constructor() {
        super('genetics');
    }

    async geneticResult30k(userId) {
        let res = await this.get(this.ApiURL + `/results?userId=${userId}`);
        return res;
    }

    async saveResult30k(formData) {
        let res = await this.post(this.ApiURL + `/save-result-30k`, formData);
        return res;
    }
}

export default new GeneticsApi();
