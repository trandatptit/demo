import genderType from '@/constants/genderType.js';

function findHeightGen(params) {
    const { gender, height, parentHeight, motherHeight } = params;

    // Simple height prediction logic (for demonstration purposes)
    let predictedHeight = height;

    if (gender === genderType.MALE) {
        predictedHeight = (parentHeight + motherHeight + 13) / 2;
    } else {
        predictedHeight = (parentHeight + motherHeight - 13) / 2;
    }

    return predictedHeight ?? 0;
}

export default findHeightGen;
