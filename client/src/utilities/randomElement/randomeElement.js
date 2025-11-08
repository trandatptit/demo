/**
 * Randomly selects and returns one element from an array of strings
 * @param {string[]} array - Array of strings to select from
 * @returns {string|null} A random string element from the array, or null if array is empty
 */
export function randomElement(array) {
    if (!array || array.length === 0) {
        return null;
    }

    const randomIndex = Math.floor(Math.random() * array.length);
    return array[randomIndex];
}

export default randomElement;
