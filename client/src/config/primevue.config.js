import Aura from '@primeuix/themes/aura';

export const primeVueConfig = {
    theme: {
        preset: Aura,
    },
    ripple: true,
    inputStyle: 'outlined',
    zIndex: {
        modal: 1100,
        overlay: 1000,
        menu: 1000,
        tooltip: 1100,
        toast: 1200,
    },
    pt: {
        // Custom PassThrough (pt) options for PrimeVue components
        button: {
            root: ({ props }) => ({
                class: [
                    // Base
                    'inline-flex items-center justify-center',
                    'px-4 py-2 text-sm font-medium rounded-lg',
                    'focus:outline-none focus:ring-2 focus:ring-offset-2',
                    // Variations
                    {
                        'bg-primary-600 text-white hover:bg-primary-700':
                            props.severity === null,
                        'bg-green-600 text-white hover:bg-green-700':
                            props.severity === 'success',
                        'bg-red-600 text-white hover:bg-red-700':
                            props.severity === 'danger',
                        'bg-yellow-600 text-white hover:bg-yellow-700':
                            props.severity === 'warning',
                        'bg-blue-600 text-white hover:bg-blue-700':
                            props.severity === 'info',
                        'bg-gray-600 text-white hover:bg-gray-700':
                            props.severity === 'help',
                    },
                ],
            }),
        },
        inputtext: {
            root: {
                class: [
                    'block w-full rounded-lg border-gray-300',
                    'focus:border-primary-500 focus:ring-primary-500',
                    'sm:text-sm',
                ],
            },
        },
        dialog: {
            root: {
                class: ['rounded-lg shadow-xl', 'bg-white dark:bg-gray-800'],
            },
            header: {
                class: ['p-4 border-b', 'bg-gray-50 dark:bg-gray-700'],
            },
            content: {
                class: 'p-4',
            },
            footer: {
                class: ['p-4 border-t', 'bg-gray-50 dark:bg-gray-700'],
            },
        },
        toast: {
            root: {
                class: 'rounded-lg shadow-lg',
            },
            message: {
                class: 'p-4',
            },
        },
    },
};
