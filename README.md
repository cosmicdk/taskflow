# TaskFlow - 个人任务管理系统

> 🚀 Spring Boot 3.x + Vue 3 全栈项目，适合新手入门练习。

## 📦 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.2.5 |
| 安全认证 | Spring Security + JWT |
| 数据库 | MySQL 8.0 |
| ORM | MyBatis-Plus 3.5.5 |
| 缓存 | Redis（可选） |
| 接口文档 | SpringDoc OpenAPI 3 |
| 构建工具 | Maven |
| 前端 | Vue 3 + Vite + Element Plus |
| 状态管理 | Pinia |

---

## 🚀 快速启动

### 1. 环境准备

- **JDK 17+**
- **Maven 3.8+**
- **MySQL 8.0**
- **Node.js 18+**（前端）

### 2. 初始化数据库

```bash
mysql -u root -p < sql/init.sql
```

### 3. 修改配置

编辑 `taskflow-web/src/main/resources/application-dev.yml`，修改数据库连接信息。

### 4. 启动后端

```bash
cd TaskFlow
mvn clean install -DskipTests
cd taskflow-web
mvn spring-boot:run
```

启动后访问：
- **API 文档**: http://localhost:8080/doc.html
- **API 根路径**: http://localhost:8080/api

### 5. 启动前端

```bash
cd taskflow-ui
npm install
npm run dev
```

浏览器访问：**http://localhost:5173**

---

## 📂 项目结构

```
TaskFlow/
├── pom.xml                          # 父 POM
├── sql/init.sql                     # 数据库初始化
├── Dockerfile                       # Docker 部署
│
├── taskflow-common/                 # 公共模块
│   └── src/main/java/com/taskflow/
│       ├── common/                  # Result, PageResult, BusinessException
│       ├── config/                  # MyBatisPlus, Redis, WebMVC 配置
│       └── constant/                # 常量定义
│
├── taskflow-web/                    # 业务模块
│   └── src/main/java/com/taskflow/
│       ├── TaskFlowApplication.java # 启动类
│       ├── controller/              # Auth / Task / Category / User / File
│       ├── service/ + impl/         # 业务逻辑
│       ├── mapper/                  # MyBatis-Plus Mapper
│       ├── entity/                  # User / Task / Category
│       ├── dto/                     # 数据传输对象
│       ├── security/                # JWT + Security 配置
│       ├── handler/                 # 全局异常处理 / 自动填充
│       └── utils/                   # SecurityUtils
│
└── taskflow-ui/                     # 前端项目
    └── src/
        ├── api/                     # API 封装 (axios)
        ├── views/                   # 页面 (Login, TaskList, Profile...)
        ├── router/                  # Vue Router
        ├── store/                   # Pinia 状态管理
        └── utils/request.js         # HTTP 拦截器
```

---

## 🔌 API 清单

| 模块 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 认证 | POST | `/api/auth/register` | 注册 |
| 认证 | POST | `/api/auth/login` | 登录 |
| 用户 | GET | `/api/user/info` | 当前用户信息 |
| 用户 | PUT | `/api/user/profile` | 修改个人信息 |
| 任务 | GET | `/api/tasks` | 任务列表（分页+筛选） |
| 任务 | POST | `/api/tasks` | 创建任务 |
| 任务 | GET | `/api/tasks/{id}` | 任务详情 |
| 任务 | PUT | `/api/tasks/{id}` | 更新任务 |
| 任务 | DELETE | `/api/tasks/{id}` | 删除任务 |
| 任务 | PATCH | `/api/tasks/{id}/status` | 修改状态 |
| 分类 | GET | `/api/categories` | 分类列表 |
| 分类 | POST | `/api/categories` | 创建分类 |
| 分类 | PUT | `/api/categories/{id}` | 更新分类 |
| 分类 | DELETE | `/api/categories/{id}` | 删除分类 |
| 文件 | POST | `/api/upload/avatar` | 上传头像 |

---

## 🐳 Docker 部署

```bash
# 1. 构建 JAR
mvn clean package -DskipTests

# 2. 构建镜像
docker build -t taskflow:1.0.0 .

# 3. 运行
docker run -d -p 8080:8080 \
  -e DB_USERNAME=root -e DB_PASSWORD=yourpass \
  -e REDIS_HOST=your-redis \
  taskflow:1.0.0
```

---

## 🛠️ 进阶扩展

- [ ] 任务标签系统（多对多）
- [ ] 数据导出 Excel（EasyExcel）
- [ ] 定时任务统计（@Scheduled）
- [ ] 邮件通知（任务到期提醒）
- [ ] 单元测试（JUnit 5 + Mockito）
- [ ] GitHub Actions CI/CD
- [ ] WebSocket 实时协作

---

## 📝 学习建议

1. **先跑通阶段 1-2**：理解 `请求 → Controller → Service → Mapper → DB`
2. **阶段 3 重点看**：JWT + Security 是面试高频
3. **模仿后改业务**：把 TaskFlow 改成你的"读书笔记"或"记账本"
4. **看日志调试**：Spring Boot 错误提示很详细

---

**Made with ❤️ by DevMaster**