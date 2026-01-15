import BaseApi from './baseApi';

class AiApi extends BaseApi {
    constructor() {
        super('ai');
    }

    async genHeightParental(data) {
        let res = await this.post(this.ApiURL + '/gen-height-parental', data);
        return res;
    }

    async genHeight30k(data) {
        let res = await this.post(this.ApiURL + '/gen-height-30k', data);
        return res;
    }

    async getYearlyRoadmap(data) {
        let res = await this.post(this.ApiURL + '/growth/yearly-roadmap', data);
        return res;
    }

    async genHeightMotivation(data) {
        let res = await this.post(this.ApiURL + '/gen-height-motivation', data);
        return res;
    }

    async genMotivation(data) {
        let res = await this.post(this.ApiURL + '/gen-motivation', data);
        return res;
    }

    async getCalciumTotalRequired(data) {
        let res = await this.post(
            this.ApiURL + '/calcium/total-required',
            data
        );
        return res;
    }

    async getMonthlyPlan(data) {
        let res = await this.post(this.ApiURL + '/growth/monthly-plan', data);
        return res;
    }
}

export default new AiApi();
