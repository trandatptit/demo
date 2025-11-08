function formatStr(str, ...args) {
    return str.replace(/{(\d+)}/g, (match, index) => {
        return typeof args[index] !== 'undefined' ? args[index] : match;
    });
}

export default formatStr;
