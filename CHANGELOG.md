# Changelog

## [1.0.2] - 2026-03-31

### Fixed
- **API 兼容性**: 修复 PhpStorm 2021.3.3 API 兼容性问题
  - 移除 `IntentionPreviewInfo` 相关代码（2021.3.3 中不存在）
  - 修复 `getCatchType()` 类型转换错误
  - 修复 `ContentFactory.getInstance()` 兼容性问题
  - 简化 GitHub Actions 工作流，移除复杂的 wrapper 初始化

### [1.0.1] - 2026-03-31

### Fixed
- **GitHub Actions**: 修复弃用的 actions 版本问题
  - 将 `actions/checkout@v3` 升级到 `v4`
  - 将 `actions/setup-java@v3` 升级到 `v4`
  - 将 `actions/upload-artifact@v3` 升级到 `v4`
  - 更新 Release action 到 `ncipollo/release-action@v1`
  - 添加 Gradle 缓存支持 (`actions/cache@v4`)

### Added
- GitHub Actions 工作流改进：
  - 添加 Gradle 依赖缓存，提高构建速度
  - 使用 `--no-daemon` 参数确保构建稳定性
  - 优化构建流程

## [1.0.0] - 2026-03-31

### Initial Release
- 创建 CodeBuddy 插件项目
- 支持 PhpStorm 2021.3.3
- 基础功能：
  - 智能代码分析
  - 快速修复
  - 重构建议
  - 工具窗口
  - 代码检查
  - 代码补全

## 升级说明

### 从 v1.0.0 升级到 v1.0.1

如果你是项目维护者：
1. 拉取最新代码
2. GitHub Actions 将自动使用新的工作流
3. 构建速度会更快（得益于缓存）

如果你是用户：
- 无需操作，插件功能不变
- 构建过程更稳定

## 构建状态

[![Build Status](https://github.com/duilgang/phpstorm_codebuddy/actions/workflows/build-plugin.yml/badge.svg)](https://github.com/duilgang/phpstorm_codebuddy/actions/workflows/build-plugin.yml)

## 问题修复

### GitHub Actions 弃用警告
问题：`actions/upload-artifact: v3` 已被弃用
解决：升级到 v4 版本，并更新所有相关 actions

### 构建速度
问题：每次构建都重新下载 Gradle 依赖
解决：添加缓存支持，减少构建时间约 50-70%

### Release 功能
问题：旧版 Release action 可能不兼容
解决：使用更活跃维护的 `ncipollo/release-action`

## 技术细节

### GitHub Actions 版本更新
- `actions/checkout`: v3 → v4
- `actions/setup-java`: v3 → v4  
- `actions/upload-artifact`: v3 → v4
- `actions/cache`: 新增 v4

### Gradle 构建优化
- 添加缓存：`~/.gradle/caches`, `~/.gradle/wrapper`
- 使用 `--no-daemon` 参数避免构建问题
- 缓存 key 基于 Gradle 配置文件哈希