<script setup>
import { login } from '@/api/user'
import { useAuthStore } from '@/store/auth'
import { useRouter } from 'vue-router'

const router = useRouter()
const authStore = useAuthStore()
const form = reactive({ username: '', password: '' })
const loading = ref(false)

async function handleLogin() {
  if (!form.username || !form.password) return ElMessage.warning('请填写用户名和密码')
  loading.value = true
  try {
    const res = await login(form)
    authStore.setLogin(res.data.token, res.data.user)
    ElMessage.success('登录成功')
    router.push('/tasks')
  } catch {} finally { loading.value = false }
}
</script>

<template>
  <div class="auth-page">
    <div class="auth-card">
      <h2>登录 TaskFlow</h2>
      <el-form @keyup.enter="handleLogin">
        <el-form-item><el-input v-model="form.username" placeholder="用户名" prefix-icon="User" size="large" /></el-form-item>
        <el-form-item><el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" size="large" show-password /></el-form-item>
        <el-form-item><el-button type="primary" size="large" :loading="loading" @click="handleLogin">登 录</el-button></el-form-item>
      </el-form>
      <div class="footer-link">还没有账号？<router-link to="/register">立即注册</router-link></div>
    </div>
  </div>
</template>