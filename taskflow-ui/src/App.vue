<script setup>
</script>

<template>
  <div id="app-container">
    <template v-if="$route.meta.guest">
      <router-view />
    </template>
    <template v-else>
      <header class="page-header">
        <div class="logo" @click="$router.push('/')" style="cursor:pointer">📋 TaskFlow</div>
        <nav class="nav-links">
          <router-link to="/tasks">任务管理</router-link>
          <router-link to="/categories">分类管理</router-link>
          <router-link to="/profile">个人中心</router-link>
          <a href="#" @click.prevent="$auth?.logout(); $router.push('/login')" style="color:#e74c3c">退出</a>
        </nav>
      </header>
      <main class="page-content"><router-view /></main>
    </template>
  </div>
</template>

<style scoped>
#app-container { min-height: 100vh; }
</style>