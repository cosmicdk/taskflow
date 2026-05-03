<script setup>
import { getCategoryList, createCategory, updateCategory, deleteCategory } from '@/api/category'

const categories = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentId = ref(null)
const form = reactive({ name: '', color: '#409EFF', sortOrder: 0 })
const colorPresets = ['#409EFF','#67C23A','#E6A23C','#F56C6C','#909399','#8B5CF6','#F97316','#06B6D4']

async function load() { loading.value = true; try { const res = await getCategoryList(); categories.value = res.data || [] } finally { loading.value = false } }
function openCreate() { isEdit.value = false; currentId.value = null; form.name = ''; form.color = '#409EFF'; form.sortOrder = 0; dialogVisible.value = true }
function openEdit(cat) { isEdit.value = true; currentId.value = cat.id; form.name = cat.name; form.color = cat.color; form.sortOrder = cat.sortOrder; dialogVisible.value = true }
async function handleSave() {
  if (!form.name.trim()) return ElMessage.warning('请输入分类名称')
  try { if (isEdit.value) { await updateCategory(currentId.value, form); ElMessage.success('更新成功') }
    else { await createCategory(form); ElMessage.success('创建成功') }
    dialogVisible.value = false; load() } catch {}
}
async function handleDelete(id) {
  try { await ElMessageBox.confirm('确定删除？','提示',{ type:'warning' }); await deleteCategory(id); ElMessage.success('删除成功'); load() } catch {}
}
onMounted(load)
</script>

<template>
  <div>
    <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:16px">
      <h2 class="page-title" style="margin-bottom:0">🏷️ 分类管理</h2>
      <el-button type="primary" @click="openCreate">+ 新增分类</el-button>
    </div>
    <div class="card-box">
      <el-table :data="categories" v-loading="loading">
        <el-table-column label="颜色" width="80"><template #default="{ row }"><div :style="{width:'24px',height:'24px',borderRadius:'4px',background:row.color}" /></template></el-table-column>
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column label="操作" width="160">
          <template #default="{ row }"><el-button size="small" @click="openEdit(row)">编辑</el-button><el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button></template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑分类':'新增分类'" width="400px">
      <el-form :model="form">
        <el-form-item label="名称" required><el-input v-model="form.name" placeholder="分类名称" /></el-form-item>
        <el-form-item label="颜色">
          <div style="display:flex;gap:8px;flex-wrap:wrap">
            <div v-for="c in colorPresets" :key="c" :style="{ width:'32px',height:'32px',borderRadius:'6px',background:c,cursor:'pointer',border:form.color===c?'3px solid #333':'2px solid #ddd' }" @click="form.color=c" />
          </div>
        </el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sortOrder" :min="0" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>