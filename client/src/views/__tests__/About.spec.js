import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import About from '../About.vue';

// Mock router-link
const RouterLinkStub = {
    name: 'RouterLink',
    template: '<a><slot></slot></a>',
    props: ['to'],
};

describe('About.vue', () => {
    it('should render the About page', () => {
        const wrapper = mount(About, {
            global: {
                stubs: {
                    'router-link': RouterLinkStub,
                },
            },
        });

        expect(wrapper.text()).toContain('About Page');
    });

    it('should display tech stack items', () => {
        const wrapper = mount(About, {
            global: {
                stubs: {
                    'router-link': RouterLinkStub,
                },
            },
        });

        expect(wrapper.text()).toContain('Vue 3');
        expect(wrapper.text()).toContain('Tailwind CSS');
        expect(wrapper.text()).toContain('Vue Router');
        expect(wrapper.text()).toContain('Pinia');
        expect(wrapper.text()).toContain('Vite');
    });

    it('should have a back to home link', () => {
        const wrapper = mount(About, {
            global: {
                stubs: {
                    'router-link': RouterLinkStub,
                },
            },
        });

        expect(wrapper.text()).toContain('Back to Home');
    });

    it('should have proper styling classes', () => {
        const wrapper = mount(About, {
            global: {
                stubs: {
                    'router-link': RouterLinkStub,
                },
            },
        });

        expect(wrapper.find('.min-h-screen').exists()).toBe(true);
        expect(wrapper.find('.container').exists()).toBe(true);
    });
});
