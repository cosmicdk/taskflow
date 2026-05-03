import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '@/store/auth'

const request = axios.create({ baseURL: '/api', timeout: 15000 })

request.interceptors.request.use((config) => {
  const authStore = useAuthStore()
  if (authStore.token) config.headers.Authorization = `Bearer ${authStore.token}`
  return config
}, (error) => Promise.reject(error))

request.interceptors.response.use((response) => response.data, (error) => {
  if (error.response) {
    const { status } = error.response
    if (status === 401) { ElMessage.error('登录已过期'); useAuthStore().logout(); window.location.href = '/login' }
    else if (status === 403) ElMessage.error('无权访问')
    else if (status === 500) ElMessage.error('服务器错误')
  } else ElMessage.error('网络异常')
  return Promise.reject(error)
})

export default request