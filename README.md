# 瑞吉外卖管理系统

基于 Spring Boot + MyBatis-Plus 开发的外卖业务管理平台，涵盖后台运营管理与用户移动端双端，完整实现从菜品管理、购物车到下单支付的全链路业务流程。

## 技术栈

### 后端
- **框架**：Spring Boot 3.4.6
- **ORM**：MyBatis-Plus 3.5.8
- **数据库**：MySQL
- **连接池**：Druid
- **缓存**：Redis + Spring Cache
- **JDK**：Java 17

### 前端
- **管理端**：Vue 2 + Element UI + Axios
- **用户端**：Vant + Vue 2

## 功能模块

### 后台管理端
| 模块 | 功能说明 |
|------|----------|
| 员工管理 | 登录登出、分页查询、新增员工、编辑信息、账号启停 |
| 分类管理 | 菜品分类 / 套餐分类的增删改查，支持排序 |
| 菜品管理 | 菜品信息维护、口味规格配置、分页查询、批量上下架 |
| 套餐管理 | 套餐信息维护、套餐与菜品关联配置、分页查询 |
| 订单管理 | 订单分页查询、订单状态更新、配送跟踪 |

### 用户移动端
| 模块 | 功能说明 |
|------|----------|
| 登录 | 手机号 + 短信验证码登录 |
| 浏览 | 按分类浏览菜品 / 套餐，支持搜索 |
| 购物车 | 添加菜品、修改数量、清空购物车 |
| 地址 | 地址簿增删改查、默认地址设置 |
| 订单 | 提交订单、模拟支付、历史订单查询 |

## 核心技术亮点

### 1. 统一登录校验
基于 Servlet Filter 实现请求拦截，在过滤器中统一校验登录状态，未登录请求直接返回登录页，无需在每个 Controller 中重复判断。

### 2. 上下文用户信息传递
使用 ThreadLocal 在过滤器中存储当前登录用户 ID，业务层通过 BaseContext 工具类获取，实现 MyBatis-Plus 自动填充创建人、修改人字段，无需手动传参。

### 3. 全局异常处理
通过 `@RestControllerAdvice` 统一捕获业务异常，返回标准化错误结果，避免异常信息直接暴露给前端。

### 4. Redis 缓存优化
- 菜品 / 套餐数据加入 Redis 缓存，查询优先走缓存，更新时主动清除缓存
- 使用 Spring Cache 注解简化缓存操作，减少手写 Redis 代码

### 5. 短信验证码
验证码存入 Redis 并设置有效期，登录时校验手机号与验证码是否匹配，校验后自动删除。

### 6. 文件上传
菜品图片支持本地上传，统一配置文件存储路径，通过 Spring MVC 静态资源映射实现图片访问。

## 项目结构

```
reggie/
├── src/main/java/com/cque1/reggie/
│   ├── common/          # 通用组件：返回结果、异常处理、BaseContext
│   ├── config/          # 配置类：MyBatisPlus 分页插件、WebMvc 静态资源映射
│   ├── controller/      # 控制层：接收请求、调用 Service、返回结果
│   ├── dto/             # 数据传输对象：拓展 Entity 字段
│   ├── entity/          # 实体类：对应数据库表
│   ├── filter/          # 过滤器：登录校验
│   ├── mapper/         # Mapper 接口：继承 BaseMapper
│   ├── service/        # 业务层：接口 + 实现类
│   └── utils/          # 工具类：验证码生成
├── src/main/resources/
│   ├── application.yaml  # 应用配置
│   ├── backend/        # 后台管理端静态页面
│   └── front/           # 用户移动端静态页面
└── pom.xml
```

## 环境要求

| 环境 | 版本要求 |
|------|----------|
| JDK | 17+ |
| Maven | 3.6+ |
| MySQL | 8.0+ |
| Redis | 5.0+ |

## 部署步骤

### 1. 克隆项目

```bash
git clone https://github.com/Wang-Studying/reggie.git
cd reggie
```

### 2. 数据库准备

1. 启动 MySQL，创建数据库：

```sql
CREATE DATABASE reggie DEFAULT CHARACTER SET utf8mb4;
```

2. 导入建表 SQL（需要准备 `reggie.sql` 文件，包含所有业务表）：

```bash
mysql -u root -p reggie < reggie.sql
```

> 没有 SQL 文件的话，需要手动创建以下核心表：
> - employee（员工表）
> - category（菜品 / 套餐分类表）
> - dish（菜品表）
> - dish_flavor（菜品口味表）
> - setmeal（套餐表）
> - setmeal_dish（套餐菜品关联表）
> - shopping_cart（购物车表）
> - address_book（地址簿表）
> - orders（订单表）
> - order_detail（订单明细表）
> - user（用户表）

### 3. 修改配置

编辑 `src/main/resources/application.yaml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/reggie?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8
    username: root       # 改成你的 MySQL 用户名
    password: 你的密码     # 改成你的 MySQL 密码

reggie:
  path: /your/upload/path/   # 改成你本地图片存储目录
```

### 4. 启动 Redis

确保 Redis 服务已启动（默认端口 6379）。

### 5. 启动项目

```bash
# 方式一：Maven 启动
mvn spring-boot:run

# 方式二：IDE 中运行
运行 ReggieApplication.java
```

### 6. 访问测试

| 端 | 地址 |
|----|------|
| 后台管理端 | http://localhost/backend/index.html |
| 用户移动端 | http://localhost/front/index.html |

默认管理员账号：admin / 123456（需要手动插入 employee 表）

## 常见问题

**Q: 启动报数据库连接失败？**
A: 检查 MySQL 是否启动、数据库名是否正确、账号密码是否配置正确。

**Q: 页面打开空白？**
A: 检查项目端口是否被占用，或者后端接口是否正常返回数据。

**Q: 图片上传不显示？**
A: 检查 `reggie.path` 配置的目录是否存在，以及 WebMvcConfig 中的静态资源映射路径。

## 项目收获

- 掌握 Spring Boot 项目分层架构与 RESTful 接口设计
- 熟练使用 MyBatis-Plus 进行 CRUD、分页、条件构造
- 理解 Filter 过滤器原理与 ThreadLocal 上下文传递
- 学会 Redis 缓存策略设计与缓存一致性处理
- 前后端分离开发模式下的接口联调经验
