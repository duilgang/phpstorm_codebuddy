# CodeBuddy Plugin for PhpStorm

> 腾讯云代码助手的本地版 PhpStorm 插件

## 项目简介

这是一个为 PhpStorm 2021.3.3 开发的本地版 CodeBuddy（腾讯云代码助手）插件。插件提供智能代码分析、快速修复、重构建议等功能。

## 快速开始

### 下载插件

从 GitHub Releases 下载最新版本：

[![Latest Release](https://img.shields.io/github/v/release/duilgang/phpstorm_codebuddy?style=for-the-badge)](https://github.com/duilgang/phpstorm_codebuddy/releases/latest)

### 安装步骤

1. **下载插件**：
   - 从 [Releases](https://github.com/duilgang/phpstorm_codebuddy/releases) 页面下载 `CodeBuddy Local-x.x.x.zip`

2. **安装到 PhpStorm**：
   - 打开 PhpStorm 2021.3.3
   - `File → Settings → Plugins`
   - 点击 ⚙️ 图标，选择 `Install Plugin from Disk`
   - 选择下载的 ZIP 文件
   - 重启 PhpStorm

3. **验证安装**：
   - 使用 `Ctrl+Alt+C` 打开 CodeBuddy 面板
   - 检查 `Tools` 菜单是否有 `Open CodeBuddy` 选项

## 功能特性

### 🎯 智能代码分析
- 检测常见的代码问题
- 提供改进建议
- 检查代码质量

### ⚡ 快速修复
- 添加 TODO 注释
- 代码格式化
- 添加缺失的导入

### 🔧 重构建议
- 提取方法建议
- 提取变量建议
- 重命名建议

### 🖥️ 用户界面
- 右侧工具窗口
- 菜单集成
- 快捷键支持

## 构建状态

![Build Status](https://github.com/duilgang/phpstorm_codebuddy/actions/workflows/build-plugin.yml/badge.svg)

## 系统要求

- **PhpStorm**: 2021.3.3
- **Java**: JDK 11+
- **操作系统**: Windows, macOS, Linux

## 开发构建

插件使用 GitHub Actions 自动构建。每次推送到 main 分支都会触发构建。

### 手动构建（开发者）

```bash
# 使用 Docker
docker run --rm -v "$(pwd):/project" -w /project gradle:7.4-jdk11 gradle buildPlugin

# 或者使用本地 Gradle（需要 JDK 11）
./gradlew buildPlugin
```

## 项目结构

```
├── .github/workflows/     # GitHub Actions 配置
├── src/main/java/         # Java 源代码
├── src/main/resources/    # 资源文件
├── build.gradle.kts       # Gradle 构建配置
├── gradlew               # Gradle wrapper (Linux/Mac)
├── gradlew.bat           # Gradle wrapper (Windows)
└── README.md             # 项目说明
```

## 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建 Pull Request

## 许可证

本项目基于 MIT 许可证开源。

## 技术支持

- 提交 Issue: [GitHub Issues](https://github.com/duilgang/phpstorm_codebuddy/issues)
- 查看文档: [GitHub Wiki](https://github.com/duilgang/phpstorm_codebuddy/wiki)

---

**注意**: 这是一个演示版插件，实际功能可能有限。完整的 CodeBuddy 功能需要连接到腾讯云服务。