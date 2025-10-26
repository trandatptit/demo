import BaseApi from './baseApi';

class ContractApi extends BaseApi {
    constructor() {
        super('Contracts');
    }

    async newContract(contract) {
        let res = await this.post(this.ApiURL, contract);
        return res;
    }

    async getContractByFreelancerId(freelancerId) {
        let res = await this.get(
            this.ApiURL + `/freelancer?freelancerId=${freelancerId}`
        );
        return res;
    }

    async approveContract(contractId, freelancerId) {
        let res = await this.post(
            this.ApiURL +
                `/approve?contractId=${contractId}&freelancerId=${freelancerId}`
        );
        return res;
    }

    async rejectContract(contractId, freelancerId) {
        let res = await this.post(
            this.ApiURL +
                `/reject?contractId=${contractId}&freelancerId=${freelancerId}`
        );
        return res;
    }

    async getContractDetail(contractId) {
        let res = await this.get(
            this.ApiURL + `/detail?contractId=${contractId}`
        );
        return res;
    }
}

export default new ContractApi();
