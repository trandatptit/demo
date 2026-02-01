import { describe, it, expect, beforeEach } from 'vitest';
import { setActivePinia, createPinia } from 'pinia';
import { useUserInfoStore } from '../userInfo.js';

describe('userInfo store', () => {
    beforeEach(() => {
        setActivePinia(createPinia());
    });

    it('should have initial user as null', () => {
        const store = useUserInfoStore();
        expect(store.user).toBeNull();
    });

    it('should have initial isLoggedIn as false', () => {
        const store = useUserInfoStore();
        expect(store.isLoggedIn).toBe(false);
    });

    it('should have initial isAdmin as false', () => {
        const store = useUserInfoStore();
        expect(store.isAdmin).toBe(false);
    });

    describe('setUserInfo', () => {
        it('should set user info', () => {
            const store = useUserInfoStore();
            const userInfo = { name: 'John', email: 'john@example.com' };
            store.setUserInfo(userInfo);
            expect(store.user).toEqual(userInfo);
        });
    });

    describe('getUserInfo', () => {
        it('should return user info', () => {
            const store = useUserInfoStore();
            const userInfo = { name: 'Jane', email: 'jane@example.com' };
            store.setUserInfo(userInfo);
            expect(store.getUserInfo()).toEqual(userInfo);
        });

        it('should return null when not set', () => {
            const store = useUserInfoStore();
            expect(store.getUserInfo()).toBeNull();
        });
    });

    describe('loginUser', () => {
        it('should set user and login status', () => {
            const store = useUserInfoStore();
            const userInfo = { name: 'John', RoleType: 'user' };
            store.loginUser(userInfo);
            expect(store.user).toEqual(userInfo);
            expect(store.isLoggedIn).toBe(true);
            expect(store.isAdmin).toBe(false);
        });

        it('should set isAdmin true for admin role', () => {
            const store = useUserInfoStore();
            const adminInfo = { name: 'Admin', RoleType: 'admin' };
            store.loginUser(adminInfo);
            expect(store.isAdmin).toBe(true);
        });
    });

    describe('logoutUser', () => {
        it('should clear user and reset flags', () => {
            const store = useUserInfoStore();
            store.loginUser({ name: 'John', RoleType: 'admin' });
            store.logoutUser();
            expect(store.user).toBeNull();
            expect(store.isLoggedIn).toBe(false);
            expect(store.isAdmin).toBe(false);
        });
    });

    describe('clearUserInfo', () => {
        it('should set user to null', () => {
            const store = useUserInfoStore();
            store.setUserInfo({ name: 'John' });
            store.clearUserInfo();
            expect(store.user).toBeNull();
        });
    });

    describe('getIsLoggedIn', () => {
        it('should return login status', () => {
            const store = useUserInfoStore();
            expect(store.getIsLoggedIn()).toBe(false);
            store.loginUser({ name: 'John' });
            expect(store.getIsLoggedIn()).toBe(true);
        });
    });

    describe('getIsAdmin', () => {
        it('should return admin status', () => {
            const store = useUserInfoStore();
            expect(store.getIsAdmin()).toBe(false);
            store.loginUser({ name: 'Admin', RoleType: 'admin' });
            expect(store.getIsAdmin()).toBe(true);
        });
    });
});
