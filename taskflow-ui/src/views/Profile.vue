<script setup>
import { getUserInfo, updateProfile, uploadAvatar } from '@/api/user'
import { useAuthStore } from '@/store/auth'

const authStore = useAuthStore()
const form = reactive({ nickname: '', email: '', avatar: '' })
const saving = ref(false)
const uploading = ref(false)

onMounted(async () => {
  try { const res = await getUserInfo(); form.nickname = res.data.nickname || ''; form.email = res.data.email || ''; form.avatar = res.data.avatar || '' } catch {}
})

async function handleSave() {
  saving.value = true
  try { const res = await updateProfile({ nickname: form.nickname, email: form.email }); authStore.setUser(res.data); ElMessage.success('保存成功') }
  catch {} finally { saving.value = false }
}

async function handleUpload(e) {
  const file = e.target.files[0]; if (!file) return
  uploading.value = true
  try { const res = await uploadAvatar(file); form.avatar = res.data.url; authStore.setUser({ ...authStore.user, avatar: res.data.url }); ElMessage.success('上传成功') }
  catch {} finally { uploading.value = false }
}
</script>

<template>
  <div>
    <h2 class="page-title">👤 个人中心</h2>
    <div class="card-box" style="max-width:600px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="头像">
          <div style="display:flex;align-items:center;gap:16px">
            <el-avatar :size="80" :src="form.avatar || undefined">{{ form.nickname?.[0] || 'U' }}</el-avatar>
            <div>
              <input type="file" accept="image/*" style="display:none" ref="fileInput" @change="handleUpload" />
              <el-button type="primary" :loading="uploading" @click="$refs.fileInput.click()">上传头像</el-button>
              <p style="margin-top:4px;font-size:12px;color:#999">支持 JPG/PNG，最大2MB</p>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="昵称"><el-input v-model="form.nickname" placeholder="请输入昵称" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" placeholder="请输入邮箱" /></el-form-item>
        <el-form-item><el-button type="primary" :loading="saving" @click="handleSave">保存修改</el-button></el-form-item>
      </el-form>
    </div>
  </div>
</template>