# CodeBuddy Plugin for PhpStorm 2021.3.3

这是一个为 PhpStorm 2021.3.3 开发的本地版 CodeBuddy（腾讯云代码助手）插件。

## 功能特性

1. **智能代码分析**
   - 检测常见的代码问题
   - 提供改进建议
   - 检查代码质量

2. **快速修复**
   - 添加 TODO 注释
   - 格式化代码
   - 添加缺失的导入

3. **代码重构建议**
   - 提取方法建议
   - 提取变量建议
   - 重命名建议

4. **工具窗口**
   - 独立的 CodeBuddy 面板
   - 实时代码分析
   - 结果展示

## 安装方法

### 方法一：从源码构建
1. 确保已安装 JDK 11 或更高版本
2. 确保已安装 Gradle
3. 运行以下命令构建插件：
   ```bash
   ./gradlew buildPlugin
   ```
4. 构建完成后，在 `build/distributions/` 目录下找到 `CodeBuddy Local-1.0.0.zip`
5. 在 PhpStorm 中安装：`File → Settings → Plugins → ⚙️ → Install Plugin from Disk`

### 方法二：直接使用预构建版本（需要先构建）
1. 按照方法一构建插件
2. 将生成的 ZIP 文件导入 PhpStorm

## 使用方法

1. **打开 CodeBuddy 面板**
   - 菜单：`Tools → Open CodeBuddy`
   - 快捷键：`Ctrl + Alt + C`
   - 右侧工具栏：点击 CodeBuddy 图标

2. **分析代码**
   - 右键菜单：`Analyze with CodeBuddy`
   - 工具窗口：点击 "Analyze Current File"

3. **使用快速修复**
   - 菜单：`Code → CodeBuddy Quick Fix`
   - 右键菜单：选择快速修复选项

4. **代码检查**
   - 插件会自动检查代码并显示建议
   - 在编辑器左侧会有 CodeBuddy 的警告和提示

## 开发环境要求

- PhpStorm 2021.3.3
- JDK 11+
- Gradle 7.0+

## 项目结构

```
src/main/java/com/tencent/codebuddy/
├── actions/                    # 动作类
│   ├── OpenCodeBuddyPanelAction.java
│   ├── AnalyzeCodeAction.java
│   └── QuickFixAction.java
├── ui/                        # 用户界面
│   └── CodeBuddyToolWindowFactory.java
├── inspection/                # 代码检查
│   └── CodeBuddyInspection.java
├── completion/                # 代码补全
│   └── CodeBuddyCompletionContributor.java
└── intentions/               # 意图动作
    └── CodeBuddyRefactorIntention.java
```

## 许可证

本项目基于 MIT 许可证开源。

## 支持与反馈

如有问题或建议，请提交 Issue 或联系支持团队。

---

**注意**：这是一个演示版插件，实际功能可能有限。完整的 CodeBuddy 功能需要连接到腾讯云服务。