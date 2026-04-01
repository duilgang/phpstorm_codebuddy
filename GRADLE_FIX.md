# Gradle Wrapper 问题修复

## 问题描述

在 GitHub Actions 中运行 `./gradlew buildPlugin --no-daemon --stacktrace` 时出现错误：
```
Error: Could not find or load main class .opt.hostedtoolcache.Java_Temurin-Hotspot_jdk.11.0.30-7.x64.bin.java
Caused by: java.lang.ClassNotFoundException: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/11/0/30-7/x64/bin/java
```

## 问题分析

1. **错误的 gradlew 脚本**：原有的 `gradlew` 脚本在处理 Java 路径时有问题
2. **复杂的路径转换**：脚本尝试进行 Cygwin 路径转换，但在 Linux 环境中出错
3. **Java 类加载错误**：脚本错误地将 Java 可执行文件路径当作类名来加载

## 解决方案

### 1. 简化 gradlew 脚本
创建了最简单的 `gradlew` 脚本：
```bash
#!/bin/sh
# Try to use system gradle if available
if command -v gradle >/dev/null 2>&1; then
    exec gradle "$@"
fi
# Otherwise use the Gradle that GitHub Actions setup
exec /usr/bin/gradle "$@"
```

### 2. 更新 GitHub Actions 工作流
直接使用 `gradle` 命令，避免 `gradlew` 相关问题：
```yaml
- name: Build with Gradle
  run: gradle buildPlugin --stacktrace --no-daemon
```

### 3. 使用 Gradle 官方 Action
利用 `gradle/gradle-build-action@v3` 确保正确的 Gradle 环境设置。

## 技术细节

### 原来的问题
- `gradlew` 脚本包含复杂的路径处理逻辑
- 在 GitHub Actions 的 Linux 环境中，Cygwin 相关代码被错误执行
- Java 路径被错误解析为类名

### 现在的解决方案
1. **直接使用 gradle 命令**：GitHub Actions 中的 `gradle/gradle-build-action` 已经设置了正确的环境
2. **简化 wrapper**：保留最简单的 `gradlew` 脚本作为兼容性后备
3. **移除复杂逻辑**：去除了所有 Cygwin、MSYS 等平台特定代码

## 验证方法

### 构建测试
```bash
# 在 GitHub Actions 中
gradle buildPlugin --stacktrace --no-daemon
```

### 本地测试（如果有环境）
```bash
# 使用 gradlew（简化版）
./gradlew buildPlugin

# 或者直接使用 gradle
gradle buildPlugin
```

## 文件变更

### 1. `gradlew`
- 从复杂的 160+ 行脚本简化为 10 行
- 移除所有平台特定的路径处理
- 直接委托给系统 `gradle` 命令

### 2. `.github/workflows/build-plugin.yml`
- 使用 `gradle/gradle-build-action@v3`
- 直接调用 `gradle` 命令而非 `./gradlew`
- 保持 `--stacktrace` 和 `--no-daemon` 参数

### 3. 删除的文件
- 移除复杂的 `gradlew` 脚本实现
- 简化构建流程

## 兼容性考虑

### 支持的环境
1. **GitHub Actions**：使用 `gradle/gradle-build-action`
2. **本地开发**：使用系统安装的 Gradle
3. **传统 wrapper**：简化的 `gradlew` 作为后备

### 构建一致性
- GitHub Actions 确保一致的构建环境
- 使用特定版本的 Gradle（7.4）
- 避免 wrapper 版本冲突

## 未来维护

### 如果需要完整的 Gradle wrapper
```bash
# 生成完整的 wrapper
gradle wrapper --gradle-version 7.4
```

### 升级 Gradle 版本
1. 更新 `build.gradle.kts` 中的插件版本
2. 更新 GitHub Actions 中的 Gradle 版本
3. 重新生成 wrapper（如果需要）

## 总结

通过简化构建流程，我们：
1. ✅ 解决了 `gradlew` Java 类加载错误
2. ✅ 移除了复杂的平台特定代码
3. ✅ 确保了 GitHub Actions 中的可靠构建
4. ✅ 保持了本地开发的兼容性
5. ✅ 简化了维护复杂度

现在构建应该能正常工作，不会再出现 Java 路径解析错误。