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

## 快速启动

1. 创建 MySQL 数据库 `reggie`，导入业务表
2. 修改 `application.yaml` 中的数据库连接配置
3. 启动 Redis 服务
4. 运行 `ReggieApplication.main()`
5. 后台管理端：http://localhost/backend/index.html
6. 用户移动端：http://localhost/front/index.html

## 项目收获

- 掌握 Spring Boot 项目分层架构与 RESTful 接口设计
- 熟练使用 MyBatis-Plus 进行 CRUD、分页、条件构造
- 理解 Filter 过滤器原理与 ThreadLocal 上下文传递
- 学会 Redis 缓存策略设计与缓存一致性处理
- 前后端分离开发模式下的接口联调经验
