
# 开发规范指南

本项目基于 **Spring Boot 3.4.6** 与 **MyBatis-Plus 3.5.8** 构建，旨在提供高可用、易维护的餐饮服务后端系统（Reggie Takeout）。为保证代码质量、可维护性、安全性与可扩展性，请在开发过程中严格遵循以下规范。

## 一、项目基础信息

- **工作目录**：`E:\Pycharm--IDEA\IntelliJ IDEA 2024.1\Webproject\reggie`
- **项目包名**：`com.cque1.reggie`
- **作者**：Lenovo
- **构建工具**：Maven
- **JDK 版本**：Java 17 (17.0.16)
- **主框架**：Spring Boot 3.4.6
- **ORM 框架**：MyBatis-Plus 3.5.8
- **数据库连接池**：Druid 1.2.20
- **缓存**：Redis (Spring Data Redis)
- **JSON 处理**：Fastjson 1.2.76
- **注释语言**：**中文**（所有类、方法、字段必须使用中文 Javadoc 或注释）

## 二、目录结构规范

项目采用标准的 Maven 分层结构，具体目录树如下：

```text
reggie
└── src
    ├── img                     # 静态图片资源（配置文件 reggie.path 指向此处）
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── cque1
    │   │           └── reggie
    │   │               ├── common        # 公共组件（常量、异常、结果封装等）
    │   │               ├── config        # 配置类（Redis、WebMvc、Druid等）
    │   │               ├── controller    # 控制器层（处理HTTP请求）
    │   │               ├── dto           # 数据传输对象（接收前端参数或返回数据）
    │   │               ├── entity        # 实体类（映射数据库表）
    │   │               ├── filter        # 过滤器（如登录检查）
    │   │               ├── mapper        # 数据访问层接口（继承 BaseMapper）
    │   │               ├── service       # 业务逻辑接口
    │   │               │   └── impl      # 业务逻辑实现类
    │   │               └── utils         # 工具类
    │   └── resources
    │       ├── backend                 # 后台管理前端静态资源
    │       │   ├── api
    │       │   ├── images
    │       │   ├── js
    │       │   ├── page                # 页面HTML
    │       │   ├── plugins             # 第三方库(Vue, Axios, ElementUI)
    │       │   └── styles
    │       └── front                   # 前台用户端静态资源
    │           ├── api
    │           ├── fonts
    │           ├── images
    │           ├── js
    │           ├── page
    │           └── styles
    └── test
        └── java
            └── com
                └── cque1
                    └── reggie
```

## 三、技术栈与依赖规范

### 1. 核心依赖说明
- **Spring Boot Starter Web**：构建 RESTful API。
- **MyBatis-Plus Starter**：简化 CRUD 操作，使用 `BaseMapper` 接口，无需编写 XML 即可实现基本增删改查。
- **Druid Spring Boot Starter**：使用阿里 Druid 连接池，配置了监控和性能优化参数（初始连接数5，最大20）。
- **Spring Data Redis**：用于缓存热点数据（如菜品分类、会话信息等）。
- **Fastjson**：用于 JSON 序列化和反序列化（注意处理特殊字符）。
- **Commons Lang**：用于字符串、日期等基础工具处理。

### 2. 数据库规范
- **驱动**：`mysql-connector-j 9.2.0`
- **命名映射**：开启 `map-underscore-to-camel-case: true`，数据库下划线命名自动映射为 Java 驼峰命名。
- **主键策略**：使用雪花算法 (`ASSIGN_ID`) 自动生成主键 ID，禁止使用数据库自增 ID。
- **日志输出**：开发环境开启 SQL 日志输出 (`StdOutImpl`) 以便调试。

## 四、分层架构规范

| 层级        | 职责说明                         | 开发约束与注意事项                                               |
|-------------|----------------------------------|----------------------------------------------------------------|
| **Controller** | 处理 HTTP 请求与响应，定义 API 接口 | 保持轻薄，仅做参数校验和结果封装；必须通过 Service 层调用      |
| **Service**    | 实现业务逻辑、事务管理与数据校验   | 必须通过 Mapper 层访问数据库；返回 DTO 而非 Entity             |
| **Mapper**     | 数据库访问与持久化操作             | 继承 `BaseMapper<T>`；复杂查询使用 XML 或 LambdaQueryWrapper |
| **Entity**     | 映射数据库表结构                   | 使用 `@TableName` 指定表名；使用 `@TableId(type = IdType.ASSIGN_ID)` |
| **DTO/VO**     | 数据传输对象                       | 严禁将 Entity 直接暴露给前端；需进行对象转换                   |

### 接口与实现分离

- 所有 Service 接口需放在 `service` 包下，具体实现类放在 `service.impl` 子包中。
- Mapper 接口直接放在 `mapper` 包下，无需 impl 层（由 MyBatis-Plus 代理）。

## 五、安全与性能规范

### 输入校验
- 使用 `jakarta.validation.constraints.*` 进行参数校验（Spring Boot 3.x 已迁移至 Jakarta 包）。
- 禁止手动拼接 SQL 字符串，防止 SQL 注入攻击。
- 文件上传需校验文件类型和大小，防止恶意文件上传。

### 事务管理
- `@Transactional` 注解仅用于 **Service 层** 方法。
- 避免在循环中频繁提交事务或执行数据库查询，尽量使用批量操作。

### 缓存使用
- 对于查询频繁且数据更新不频繁的数据（如菜品分类、字典表），优先使用 **Redis** 缓存。
- 缓存键名需具有业务含义，避免冲突。
- 注意缓存穿透、击穿和雪崩的处理。

## 六、代码风格规范

### 命名规范

| 类型       | 命名方式             | 示例                  |
|------------|----------------------|-----------------------|
| 类名       | UpperCamelCase       | `DishServiceImpl`     |
| 方法/变量  | lowerCamelCase       | `saveDish()`          |
| 常量       | UPPER_SNAKE_CASE     | `MAX_LOGIN_ATTEMPTS`  |
| Mapper接口 | 名词复数或单数       | `DishMapper`          |

### 注释规范

- **强制要求**：所有类、公共方法、复杂逻辑字段必须添加 **中文 Javadoc** 注释。
- 示例：
  ```java
  /**
   * 菜品业务层实现
   * @author Lenovo
   */
  public class DishServiceImpl implements DishService {
      // ...
  }
  ```

### 类型命名规范（阿里巴巴风格）

| 后缀 | 用途说明                     | 示例         |
|------|------------------------------|--------------|
| DTO  | 数据传输对象（接收前端参数）   | `DishDTO`    |
| DO   | 数据库实体对象（通常对应 Entity）| `DishDO`     |
| BO   | 业务逻辑封装对象             | `DishBO`     |
| VO   | 视图展示对象（返回给前端）     | `DishVO`     |
| Query| 查询参数封装对象             | `DishQuery`  |

### 实体类简化工具

- 使用 Lombok 注解替代手动编写 getter/setter/构造方法：
  - `@Data`
  - `@NoArgsConstructor`
  - `@AllArgsConstructor`
  - `@TableName("dishes")` (MyBatis-Plus 注解)
  - `@TableId(type = IdType.ASSIGN_ID)` (主键策略)

## 七、扩展性与日志规范

### 接口优先原则

- 所有业务逻辑通过接口定义（如 `DishService`），具体实现放在 `impl` 包中（如 `DishServiceImpl`）。

### 日志记录

- 使用 `@Slf4j` 注解代替 `System.out.println`。
- 记录关键业务操作、异常堆栈及性能瓶颈点。
- 日志级别：
  - `ERROR`: 系统错误、异常
  - `WARN`: 警告信息、潜在风险
  - `INFO`: 关键业务流程
  - `DEBUG`: 调试信息（生产环境关闭）

## 八、编码原则总结

| 原则       | 说明                                       |
|------------|--------------------------------------------|
| **SOLID**  | 高内聚、低耦合，增强可维护性与可扩展性     |
| **DRY**    | 避免重复代码，提高复用性                   |
| **KISS**   | 保持代码简洁易懂                           |
| **YAGNI**  | 不实现当前不需要的功能                     |
| **OWASP**  | 防范常见安全漏洞，如 SQL 注入、XSS、非法文件上传等 |
