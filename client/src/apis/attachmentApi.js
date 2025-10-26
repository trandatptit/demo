import BaseApi from './baseApi';
import { useAuthStore } from '@/stores';
class AttachmentApi extends BaseApi {
    constructor() {
        super('Attachments');
        this.config = {
            headers: {
                // 'Content-Type': 'multipart/form-data'
                'Content-Type': '*',
            },
        };
    }

    async getByKey(key) {
        let res = await this.getFile(this.ApiURL + `/aws?key=${key}`);
        return res;
    }

    getUrlFile(key) {
        return this.ApiURL + `/aws?key=${key}`;
    }

    async getFileKeyByRef(refId, refType) {
        let res = await this.get(
            this.ApiURL + `/file-key?refId=${refId}&refType=${refType}`
        );
        return res;
    }

    async uploadFile(file, prefix, refId, refType) {
        const formData = new FormData();
        formData.append('file', file);
        let createdBy = useAuthStore().userInfo.id;
        let res = await this.post(
            this.ApiURL +
                `/aws?prefix=${prefix}&refId=${refId}&refType=${refType}&createdBy=${createdBy}`,
            formData
        );
        return res;
    }

    async deleteFile(key) {
        let res = await this.delete(this.ApiURL + `/aws?key=${key}`);
        return res;
    }
}

export default new AttachmentApi();
