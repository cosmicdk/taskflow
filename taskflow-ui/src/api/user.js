import request from '@/utils/request'
export function login(data) { return request.post('/auth/login', data) }
export function register(data) { return request.post('/auth/register', data) }
export function getUserInfo() { return request.get('/user/info') }
export function updateProfile(data) { return request.put('/user/profile', data) }
export function uploadAvatar(file) {
  const fd = new FormData(); fd.append('file', file)
  return request.post('/upload/avatar', fd, { headers: { 'Content-Type': 'multipart/form-data' } })
}