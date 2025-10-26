import BaseApi from './baseApi';

class ReviewApi extends BaseApi {
    constructor() {
        super('Reviews');
    }

    async getReviewHistory(userId) {
        let res = await this.get(this.ApiURL + `/history?userId=${userId}`);
        return res;
    }

    async addReview(review) {
        let res = await this.post(this.ApiURL, review);
        return res;
    }
}

export default new ReviewApi();
