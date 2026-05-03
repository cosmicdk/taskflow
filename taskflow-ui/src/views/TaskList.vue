<script setup>
import { getTaskList, createTask, updateTask, deleteTask, updateTaskStatus } from '@/api/task'
import { getCategoryList } from '@/api/category'

const taskList = ref([])
const categories = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentTaskId = ref(null)
const filters = reactive({ status: '', priority: '', categoryId: null, keyword: '' })
const pagination = reactive({ page: 1, size: 10, total: 0 })
const form = reactive({ title: '', description: '', priority: 'MEDIUM', status: 'TODO', categoryId: null, dueDate: null })

async function loadTasks() {
  loading.value = true
  try { const res = await getTaskList({ ...filters, page: pagination.page, size: pagination.size }); taskList.value = res.data.records || []; pagination.total = res.data.total || 0 } finally { loading.value = false }
}
async function loadCategories() { try { const res = await getCategoryList(); categories.value = res.data || [] } catch {} }
function openCreate() { isEdit.value = false; currentTaskId.value = null; Object.assign(form, { title:'', description:'', priority:'MEDIUM', status:'TODO', categoryId:null, dueDate:null }); dialogVisible.value = true }
function openEdit(task) { isEdit.value = true; currentTaskId.value = task.id; Object.assign(form, { title:task.title, description:task.description||'', priority:task.priority, status:task.status, categoryId:task.categoryId, dueDate:task.dueDate }); dialogVisible.value = true }
async function handleSave() { if(!form.title.trim()) return ElMessage.warning('请输入标题'); try { if(isEdit.value){ await updateTask(currentTaskId.value,form); ElMessage.success('更新成功') } else { await createTask(form); ElMessage.success('创建成功') }; dialogVisible.value = false; loadTasks() } catch {} }
async function handleDelete(id) { try { await ElMessageBox.confirm('确定删除？','提示',{type:'warning'}); await deleteTask(id); ElMessage.success('删除成功'); loadTasks() } catch {} }
async function handleStatusChange(id, status) { try { await updateTaskStatus(id, status); ElMessage.success('状态已更新'); loadTasks() } catch {} }
function handlePageChange(p) { pagination.page = p; loadTasks() }
function priorityTag(p) { const m={LOW:'info',MEDIUM:'warning',HIGH:'danger',URGENT:''};return m[p]||'info' }
function priorityText(p) { const m={LOW:'低',MEDIUM:'中',HIGH:'高',URGENT:'紧急'};return m[p]||p }
omMounted(() => { loadTasks(); loadCategories() })
</script>

<template>
  <div>
    <h2 class="page-title">📋 任务管理</h2>
    <div class="card-box filter-bar" style="padding:16px 24px">
      <el-row :gutter="16" align="middle">
        <el-col :span="5"><el-input v-model="filters.keyword" placeholder="搜索标题..." clearable @clear="loadTasks" @keyup.enter="loadTasks" /></el-col>
        <el-col :span="4"><el-select v-model="filters.status" placeholder="状态" clearable @change="loadTasks"><el-option label="待办" value="TODO"/><el-option label="进行中" value="IN_PROGRESS"/><el-option label="已完成" value="DONE"/></el-select></el-col>
        <el-col :span="4"><el-select v-model="filters.priority" placeholder="优先级" clearable @change="loadTasks"><el-option label="低" value="LOW"/><el-option label="中" value="MEDIUM"/><el-option label="高" value="HIGH"/><el-option label="紧急" value="URGENT"/></el-select></el-col>
        <el-col :span="4"><el-select v-model="filters.categoryId" placeholder="分类" clearable @change="loadTasks"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id"/></el-select></el-col>
        <el-col :span="4"><el-button type="primary" @click="loadTasks">查询</el-button><el-button type="success" @click="openCreate">+ 创建任务</el-button></el-col>
      </el-row>
    </div>
    <div class="card-box" style="margin-top:16px">
      <el-table :data="taskList" v-loading="loading" style="width:100%">
        <el-table-column label="状态" width="120"><template #default="{row}"><el-select :model-value="row.status" size="small" @change="v=>handleStatusChange(row.id,v)"><el-option label="待办" value="TODO"/><el-option label="进行中" value="IN_PROGRESS"/><el-option label="已完成" value="DONE"/></el-select></template></el-table-column>
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column label="优先级" width="100"><template #default="{row}"><el-tag :type="priorityTag(row.priority)" size="small">{{priorityText(row.priority)}}</el-tag></template></el-table-column>
        <el-table-column label="截止日期" width="120"><template #default="{row}">{{row.dueDate||'-'}}</template></el-table-column>
        <el-table-column label="操作" width="180"><template #default="{row}"><el-button size="small" @click="openEdit(row)">编辑</el-button><el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button></template></el-table-column>
      </el-table>
      <div style="margin-top:16px;text-align:right"><el-pagination v-model:current-page="pagination.page" :page-size="pagination.size" :total="pagination.total" layout="total,prev,pager,next" @current-change="handlePageChange" /></div>
    </div>
    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑任务':'创建任务'" width="520px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题" required><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="优先级"><el-radio-group v-model="form.priority"><el-radio value="LOW">低</el-radio><el-radio value="MEDIUM">中</el-radio><el-radio value="HIGH">高</el-radio><el-radio value="URGENT">紧急</el-radio></el-radio-group></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio value="TODO">待办</el-radio><el-radio value="IN_PROGRESS">进行中</el-radio><el-radio value="DONE">已完成</el-radio></el-radio-group></el-form-item>
        <el-form-item label="分类"><el-select v-model="form.categoryId" placeholder="选择分类" clearable style="width:100%"><el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id"/></el-select></el-form-item>
        <el-form-item label="截止日期"><el-date-picker v-model="form.dueDate" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" style="width:100%"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="handleSave">保存</el-button></template>
    </el-dialog>
  </div>
</template>