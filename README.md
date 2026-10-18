# 瑞吉外卖（Reggie）

基于 Spring Boot 的外卖管理系统，包含后台管理端和用户移动端两个部分。

## 技术栈

**后端**
- Spring Boot 3.4.6
- MyBatis-Plus 3.5.8
- MySQL
- Druid 数据连接池
- Redis + Spring Cache
- Java 17

**前端**
- 管理端：Vue 2 + Element UI + Axios
- 用户端：Vant + Vue 2

## 功能模块

### 后台管理端
- 员工管理：登录、登出、分页查询、新增、编辑、状态切换
- 分类管理：菜品分类 / 套餐分类的增删改查
- 菜品管理：菜品信息维护、口味配置、分页查询、上下架
- 套餐管理：套餐信息维护、套餐与菜品关联
- 订单管理：订单分页查询、状态更新

### 用户移动端
- 短信验证码登录
- 浏览菜品 / 套餐
- 购物车添加、修改、清空
- 地址簿管理
- 下单、支付、订单查询

## 项目结构

```
reggie/
├── src/main/java/com/cque1/reggie/
│   ├── common/          # 通用返回结果、异常处理、工具类
│   ├── config/          # 配置类（MyBatisPlus、WebMvc）
│   ├── controller/      # 控制器层
│   ├── dto/             # 数据传输对象
│   ├── entity/          # 实体类
│   ├── filter/          # 登录校验过滤器
│   ├── mapper/         # Mapper 接口
│   ├── service/        # 业务逻辑层
│   └── utils/          # 工具类
├── src/main/resources/
│   ├── application.yaml  # 配置文件
│   ├── backend/        # 后台管理端静态页面
│   └── front/           # 用户移动端静态页面
└── pom.xml
```

## 运行方式

1. 创建 MySQL 数据库 `reggie`，导入建表 SQL
2. 修改 `application.yaml` 中的数据库连接信息
3. 启动 Redis（用于缓存和登录验证码）
4. 运行 `ReggieApplication.java`
5. 访问后台管理端：http://localhost:80/backend/index.html
6. 访问用户端：http://localhost:80/front/index.html

## 技术要点

- 基于 Filter 实现登录校验，未登录请求统一拦截
- 使用 LocalThread 存储当前登录用户 ID，实现自动填充
- MyBatis-Plus 实现逻辑删除、自动填充、分页插件
- Redis 缓存菜品和套餐数据，减轻数据库压力
- Spring Cache + Redis 实现短信验证码缓存
- 文件上传下载功能
