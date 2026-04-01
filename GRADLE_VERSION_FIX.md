# Gradle 版本兼容性问题修复

## 问题描述

在 GitHub Actions 中运行构建时出现错误：
```
Gradle requires JVM 17 or later to run. Your build is currently configured to use JVM 11.
```

## 问题分析

1. **Gradle 版本不匹配**：
   - GitHub Actions 默认安装了 Gradle 9.4.1
   - Gradle 9.4.1 需要 JDK 17 或更高版本
   - 我们的项目配置使用 JDK 11

2. **版本冲突**：
   - 项目配置：Gradle 7.4 + JDK 11
   - 实际环境：Gradle 9.4.1 + JDK 11（不兼容）

3. **解决方案**：
   - 明确指定使用 Gradle 7.4
   - 确保 Gradle 版本与 JDK 版本兼容

## 修复方案

### 1. 指定 Gradle 版本
在 GitHub Actions 工作流中明确指定 Gradle 7.4：
```yaml
- name: Setup Gradle 7.4
  uses: gradle/gradle-build-action@v3
  with:
    gradle-version: '7.4'
```

### 2. 使用正确的构建命令
直接使用 `gradle` 命令（不是 `./gradlew`）：
```yaml
- name: Build with Gradle 7.4
  run: gradle buildPlugin --stacktrace --no-daemon
```

### 3. 确保 Gradle wrapper 配置正确
`gradle/wrapper/gradle-wrapper.properties` 已配置为：
```
distributionUrl=https\://services.gradle.org/distributions/gradle-7.4-bin.zip
```

## 技术细节

### Gradle 版本要求
- **Gradle 7.4**：兼容 JDK 8-17（我们的选择）
- **Gradle 9.4.1**：需要 JDK 17+（不兼容）

### GitHub Actions 环境
- **默认 Gradle**：最新版（9.4.1）
- **指定版本**：通过 `gradle-version: '7.4'` 参数
- **JDK 版本**：我们配置的是 JDK 11

### 构建配置
- `build.gradle.kts`：配置为 JDK 11
- `gradle-wrapper.properties`：配置为 Gradle 7.4
- GitHub Actions：显式指定 Gradle 7.4

## 验证方法

### 构建命令验证
```bash
# 在 GitHub Actions 中
gradle --version
# 应该显示：Gradle 7.4

java -version
# 应该显示：Java 11
```

### 兼容性检查
1. ✅ Gradle 7.4 ✓ JDK 11 = 兼容
2. ❌ Gradle 9.4.1 ✓ JDK 11 = 不兼容
3. ✅ 项目配置 ✓ 环境配置 = 一致

## 文件变更

### 1. `.github/workflows/build-plugin.yml`
- 添加 `gradle-version: '7.4'` 参数
- 使用 `gradle` 命令而非 `./gradlew`
- 保持 `--stacktrace --no-daemon` 参数

### 2. `gradlew` 脚本
- 保持简化版本作为兼容性后备
- 不依赖 wrapper 的复杂逻辑

### 3. 配置检查
- 确认 `gradle-wrapper.properties` 正确
- 确认 `build.gradle.kts` 配置正确

## 构建流程

### 修正后的流程
1. 设置 JDK 11 环境
2. 设置 Gradle 7.4 环境
3. 运行 `gradle buildPlugin --stacktrace --no-daemon`
4. 上传构建产物

### 环境一致性
- **开发环境**：可以使用 `./gradlew` 或本地 Gradle
- **CI 环境**：使用指定版本的 Gradle
- **版本对齐**：确保所有环境使用相同的 Gradle 版本

## 故障排除

### 如果仍然出现问题
1. **检查 Gradle 版本**：
   ```bash
   gradle --version
   ```

2. **检查 JDK 版本**：
   ```bash
   java -version
   ```

3. **清理缓存**：
   - GitHub Actions 缓存可能包含错误的 Gradle 版本
   - 可以临时禁用缓存进行测试

### 版本升级考虑
如果需要升级：
1. 同时升级 JDK 和 Gradle 版本
2. 更新所有配置文件
3. 测试兼容性

## 总结

通过明确指定 Gradle 7.4，我们：
1. ✅ 解决了 Gradle 9.4.1 与 JDK 11 的兼容性问题
2. ✅ 确保了构建环境的一致性
3. ✅ 保持了项目的原始配置（Gradle 7.4 + JDK 11）
4. ✅ 简化了构建流程
5. ✅ 提供了清晰的版本管理

现在构建应该能正常工作，不会再出现 Gradle 版本不兼容的错误。