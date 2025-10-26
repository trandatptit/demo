import BaseApi from './baseApi';

class WorkApi extends BaseApi {
    constructor() {
        super('Works');
    }

    //#region Nhà tuyển dụng
    async getByClientId(clientId, workStatus = null, workType = null) {
        let queryPath = `/getpost-by-clientid?clientId=${clientId}`;
        if (workStatus !== null) {
            queryPath += `&workStatus=${workStatus}`;
        }
        if (workType !== null) {
            queryPath += `&workType=${workType}`;
        }
        let res = await this.get(this.ApiURL + queryPath);
        return res;
    }

    async postWork(workInfo) {
        let res = await this.post(this.ApiURL, workInfo);
        return res;
    }

    async editWork(workInfo) {
        let res = await this.put(this.ApiURL + '/' + workInfo.id, workInfo);
        return res;
    }

    async removeById(id) {
        let res = await this.delete(this.ApiURL + '/' + id);
        return res;
    }

    async updateStatus(workId, workStatus) {
        let res = await this.put(
            this.ApiURL +
                `/update-status?workId=${workId}&workStatus=${workStatus}`
        );
        return res;
    }
    //#endregion

    //#region Người tìm việc
    async getWorkForFreelancer(freelancerId, workFilter) {
        let queryPath = `/suggest-for-freelancer`;
        var param = {
            FreelancerId: freelancerId,
            WorkFilter: workFilter,
        };
        let res = await this.post(`${this.ApiURL}${queryPath}`, param);
        return res;
    }

    async getByFreelancerId(freelancerId, workStatus = null) {
        let queryPath = `/freelancer?freelancerId=${freelancerId}`;
        if (workStatus !== null) {
            queryPath += `&workStatus=${workStatus}`;
        }
        let res = await this.get(this.ApiURL + queryPath);
        return res;
    }

    async getWorkFreelancerDetail(workId, freelancerId) {
        let res = await this.get(
            this.ApiURL +
                `/detail-freelancer?workId=${workId}&freelancerId=${freelancerId}`
        );
        return res;
    }

    async updateProgress(workId, progress) {
        let res = await this.put(
            this.ApiURL +
                `/update-progress?workId=${workId}&progress=${progress}`
        );
        return res;
    }
    //#endregion

    //#region Profile của người dùng
    async getWorkHistory(userId) {
        let res = await this.get(this.ApiURL + `/history?userId=${userId}`);
        return res;
    }
    //#endregion

    async getProposalList(freelancerId) {
        let res = await this.get(
            this.ApiURL + `/proposal-list?freelancerId=${freelancerId}`
        );
        return res;
    }
}

export default new WorkApi();
