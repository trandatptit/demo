import genderType from '@/constants/genderType.js';

function findHeightGen(params) {
    const { gender, height, fatherHeight, motherHeight } = params;

    // Simple height prediction logic (for demonstration purposes)
    let predictedHeight = height;

    if (gender === genderType.MALE) {
        predictedHeight = (fatherHeight + motherHeight + 13) / 2;
    } else {
        predictedHeight = (fatherHeight + motherHeight - 13) / 2;
    }

    return predictedHeight ?? 0;
}

export default findHeightGen;
