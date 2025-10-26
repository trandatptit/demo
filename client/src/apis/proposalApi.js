import BaseApi from './baseApi';

class ProposalApi extends BaseApi {
    constructor() {
        super('Proposals');
    }

    async getAll() {
        let res = await this.get(this.ApiURL);
        return res;
    }

    async applyProposal(proposal) {
        let res = await this.post(this.ApiURL, proposal);
        return res;
    }

    async getProposalByWork(workId) {
        let res = await this.get(this.ApiURL + '/work/' + workId);
        return res;
    }

    async acceptProposal(proposalId) {
        let res = await this.post(this.ApiURL + '/accept/' + proposalId);
        return res;
    }

    async rejectProposal(proposalId) {
        let res = await this.post(this.ApiURL + '/reject/' + proposalId);
        return res;
    }
}

export default new ProposalApi();
