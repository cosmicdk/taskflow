import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({ token: '', user: null }),
  getters: { isLoggedIn: (state) => !!state.token },
  actions: {
    setLogin(token, user) { this.token = token; this.user = user },
    setUser(user) { this.user = user },
    logout() { this.token = ''; this.user = null }
  },
  persist: { key: 'taskflow-auth', storage: localStorage, paths: ['token', 'user'] }
})