import { ref } from 'vue';
import attachmentApi from '@/apis/attachmentApi';

export function useAttachments() {
    const attachments = ref([]);
    const removedAttachments = ref([]);
    const attachmentLinks = ref([]);

    const getFileKey = async (refId, refType) => {
        attachments.value = [];
        removedAttachments.value = [];
        let res = await attachmentApi.getFileKeyByRef(refId, refType);
        if (res && res.statusCode == 200) {
            let fileKeys = res.data;
            for (var fileKey of fileKeys) {
                await getAttachment(fileKey);
            }
        }
    };

    const getAttachment = async (fileName) => {
        attachmentLinks.value.push({
            href: attachmentApi.getUrlFile(fileName),
            fileName: fileName,
        });
        let res = await attachmentApi.getByKey(fileName);
        if (res && res.status == 200) {
            let fileObj = {
                href: URL.createObjectURL(res.data),
                // href: res.data,
                fileName: fileName,
            };
            attachments.value.push(fileObj);
        }
    };

    const removeFile = (file) => {
        attachments.value = attachments.value.filter((el) => el != file);
        removedAttachments.value.push(file);
    };

    const confirmDeleteFile = () => {
        removedAttachments.value.forEach((file) => {
            attachmentApi.deleteFile(file.fileName);
        });
    };

    const addedFiles = ref([]);
    const uploadAFile = async (file, prefix, refId, refType) => {
        let res = await attachmentApi.uploadFile(file, prefix, refId, refType);
    };
    const uploadAll = async (prefix, refId, refType) => {
        addedFiles.value.forEach((file) => {
            uploadAFile(file, prefix, refId, refType);
        });
    };

    return {
        attachments,
        attachmentLinks,
        getFileKey,
        removeFile,
        confirmDeleteFile,
        addedFiles,
        uploadAll,
    };
}
