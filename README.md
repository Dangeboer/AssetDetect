# 资产探测平台

一个支持前后端分离的资产探测平台，用户可以注册、登录并进行资产的存活性检测。系统区分普通用户和管理员权限，前端使用 React 和 Ant Design 实现，后端使用 Spring Boot 与 MySQL 构建 RESTful API。

## 项目功能
- 用户注册与登录（支持管理员与普通用户）
- JWT 鉴权与权限控制
- 支持资产的：
  - 类型、名称、地址、探测方式录入
  - 存活性检测（与后端联动）
- 后台实时返回资产存活状态
- 前端交互简洁，支持一键探测

## 系统截图
<img width="1429" alt="image" src="https://github.com/user-attachments/assets/732e1908-3786-4ecd-b038-14157cb2c789" />

## 技术栈

### 后端
- Java 17 + Spring Boot
- Spring Security + JWT
- Spring Data JPA + MySQL
- Gradle 构建

### 前端
- React + Ant Design
- 前后端通过 REST API 通信

