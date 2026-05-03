<script setup>
import { register } from '@/api/user'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(false)
const form = reactive({ username: '', password: '', nickname: '', email: '' })

async function handleRegister() {
  if (!form.username || !form.password) return ElMessage.warning('请填写用户名和密码')
  if (form.password.length < 6) return ElMessage.warning('密码至少6位')
  loading.value = true
  try { await register(form); ElMessage.success('注册成功'); router.push('/login') }
  catch {} finally { loading.value = false }
}
</script>

<template>
  <div class="auth-page">
    <div class="auth-card">
      <h2>注册 TaskFlow</h2>
      <el-form @keyup.enter="handleRegister">
        <el-form-item><el-input v-model="form.username" placeholder="用户名（3-20位）" prefix-icon="User" size="large" /></el-form-item>
        <el-form-item><el-input v-model="form.password" type="password" placeholder="密码（至少6位）" prefix-icon="Lock" size="large" show-password /></el-form-item>
        <el-form-item><el-input v-model="form.nickname" placeholder="昵称（选填）" prefix-icon="Avatar" size="large" /></el-form-item>
        <el-form-item><el-input v-model="form.email" placeholder="邮箱（选填）" prefix-icon="Message" size="large" /></el-form-item>
        <el-form-item><el-button type="primary" size="large" :loading="loading" @click="handleRegister">注 册</el-button></el-form-item>
      </el-form>
      <div class="footer-link">已有账号？<router-link to="/login">去登录</router-link></div>
    </div>
  </div>
</template>