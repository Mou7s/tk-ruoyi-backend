# tk-ruoyi-backend

基于 RuoYi 3.8.8 的多模块 Spring Boot 后端项目，包含标准模块与项目定制模块 `tk_custom`。

## 模块说明
- `ruoyi-admin`：应用入口与 REST 控制器
- `ruoyi-framework`：通用配置、安全与框架支撑
- `ruoyi-system`：系统核心领域、服务与 MyBatis 映射
- `ruoyi-quartz`：定时任务
- `ruoyi-generator`：代码生成
- `ruoyi-common`：公共工具与基础类
- `tk_custom`：项目定制扩展（业务控制器/服务/工具类）

## 环境要求
- JDK 8
- Maven 3.x
- MySQL 5.7/8.x
- Redis

## 配置说明
- 数据源：`ruoyi-admin/src/main/resources/application-druid.yml`
- 通用配置：`ruoyi-admin/src/main/resources/application.yml`
- SQL 脚本：`sql/`
- 运行说明文档：`doc/若依环境使用手册.docx`

建议将数据库、Redis、上传目录等配置按环境拆分或外置，避免提交敏感信息。

## 快速启动
1) 初始化数据库  
   - 导入 `sql/` 下的脚本到目标数据库

2) 修改配置  
   - 在 `application-druid.yml` 中配置数据库连接  
   - 在 `application.yml` 中配置上传路径、端口、Redis 等

3) 本地运行  
```powershell
mvn -pl ruoyi-admin -am clean package
mvn -pl ruoyi-admin spring-boot:run
```

默认启动端口为 `8180`，上下文路径为 `/`（以 `application.yml` 为准）。

## 常用命令
```powershell
# 构建 ruoyi-admin 及其依赖
mvn -pl ruoyi-admin -am clean package

# 本地启动
mvn -pl ruoyi-admin spring-boot:run

# 运行测试（如有）
mvn test
```

## 目录结构
```
.
├─ ruoyi-admin
├─ ruoyi-framework
├─ ruoyi-system
├─ ruoyi-quartz
├─ ruoyi-generator
├─ ruoyi-common
├─ tk_custom
├─ sql
└─ doc
```

## 备注
- `tk_custom` 为项目定制模块，包含自定义控制器、服务与工具类。
- 默认配置中包含本地路径与开发参数，请按实际环境调整。
