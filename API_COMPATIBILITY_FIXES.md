# PhpStorm 2021.3.3 API 兼容性修复

## 修复的编译错误

### 1. IntentionPreviewInfo 不存在
**文件**: `CodeBuddyRefactorIntention.java`
**问题**: `com.intellij.codeInsight.intention.preview.IntentionPreviewInfo` 在 PhpStorm 2021.3.3 中不存在
**修复**: 
- 移除 `IntentionPreviewInfo` 导入
- 移除 `generatePreview()` 方法实现

### 2. getCatchType() 类型转换错误
**文件**: `CodeBuddyInspection.java`
**问题**: `catchSection.getCatchType()` 返回 `PsiType` 而不是 `PsiTypeElement`
**修复**: 
- 使用遍历子元素的方式查找 `PsiTypeElement`
- 检查文本内容是否为 "Exception"

### 3. ContentFactory.getInstance() 不存在
**文件**: `CodeBuddyToolWindowFactory.java`
**问题**: `ContentFactory.getInstance()` 方法在旧版本中可能不存在
**修复**: 
- 创建 `createCompatibleContent()` 方法
- 使用 try-catch 处理不同版本的 API
- 备用方案：使用反射调用

## API 版本差异

### PhpStorm 2021.3.3 对应的 IntelliJ Platform
- **版本**: 2021.3 (213)
- **API 级别**: 较旧的 API
- **Java 版本**: JDK 11

### 不兼容的 API
1. **IntentionPreviewInfo** - 新版本引入的特性
2. **ContentFactory.getInstance()** - 方法签名可能不同
3. **某些返回类型** - 如 `getCatchType()` 的返回类型

## 解决方案

### 1. 移除新版本特性
- 移除 `IntentionPreviewInfo` 相关代码
- 简化实现，只保留核心功能

### 2. 使用兼容性包装
- 为可能不存在的 API 创建兼容性方法
- 使用反射作为备用方案
- 添加适当的错误处理

### 3. 简化代码逻辑
- 避免使用复杂的 API 调用
- 使用基础 API 确保兼容性
- 添加详细的错误信息

## 测试建议

### 构建测试
1. 本地构建测试（如果安装了 JDK）
2. GitHub Actions 自动构建测试
3. 检查编译输出是否有警告

### 功能测试
1. 安装到 PhpStorm 2021.3.3
2. 测试所有菜单功能
3. 验证工具窗口是否正常显示
4. 检查代码分析功能

## 未来兼容性考虑

### 版本检测
可以在插件启动时检测 IDE 版本：
```java
String ideVersion = ApplicationInfo.getInstance().getBuild().getBaselineVersion();
```

### 条件编译
考虑使用 Gradle 配置管理不同版本：
```kotlin
val isOldVersion = intellij.version.get().startsWith("2021")
```

### 功能降级
对于新版本特性，提供降级方案：
- 简化界面
- 使用替代 API
- 提供基本功能

## 已知限制

### 功能限制
1. 意图预览功能不可用
2. 某些高级代码检查可能受限
3. 界面组件可能使用旧版样式

### 性能考虑
1. 反射调用可能影响性能
2. 兼容性检查增加启动时间
3. 需要处理更多异常情况

## 维护建议

### 代码组织
1. 将兼容性代码集中管理
2. 添加版本注释
3. 创建兼容性测试

### 文档更新
1. 记录 API 差异
2. 说明功能限制
3. 提供升级指南

## 参考链接
- IntelliJ Platform SDK 文档
- PhpStorm 版本历史
- API 变更日志