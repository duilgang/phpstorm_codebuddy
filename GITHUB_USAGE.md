# GitHub 构建 CodeBuddy 插件指南

## 准备工作

1. 确保代码已推送到 GitHub：
   ```bash
   git add .
   git commit -m "Initial commit: CodeBuddy plugin for PhpStorm 2021.3.3"
   git push origin main
   ```

## 使用 GitHub Actions 构建

### 方法一：自动构建（推荐）

1. **代码推送到 GitHub 后**：
   - 打开 GitHub 仓库：https://github.com/duilgang/phpstorm_codebuddy
   - 点击 `Actions` 标签页
   - 你会看到 `Build CodeBuddy Plugin` 工作流正在运行

2. **查看构建结果**：
   - 工作流完成后，点击对应的运行记录
   - 在 `Summary` 页面找到 `Artifacts` 部分
   - 下载 `codebuddy-plugin` 压缩包

3. **获取构建的插件**：
   - 解压下载的压缩包
   - 找到 `CodeBuddy Local-1.0.0.zip` 文件
   - 这就是可以在 PhpStorm 中安装的插件

### 方法二：手动触发构建

1. **手动运行工作流**：
   - 打开 GitHub 仓库
   - 进入 `Actions` 标签页
   - 选择 `Build CodeBuddy Plugin` 工作流
   - 点击 `Run workflow` 按钮
   - 选择分支（默认为 main）
   - 点击绿色的 `Run workflow` 按钮

2. **等待构建完成**：
   - 构建过程大约需要 3-5 分钟
   - 完成后下载插件文件

## 手动构建（如果需要）

如果你需要在本地构建，但不想安装 Java 环境，可以使用 Docker：

```bash
# 使用 Docker 构建
docker run --rm -v "$(pwd):/project" -w /project gradle:7.4-jdk11 gradle buildPlugin
```

## 安装到 PhpStorm

1. **下载插件文件**：
   - 从 GitHub Actions 的 Artifacts 下载插件 ZIP 文件

2. **在 PhpStorm 中安装**：
   - 打开 PhpStorm 2021.3.3
   - `File → Settings → Plugins` (Windows) 或 `PhpStorm → Preferences → Plugins` (Mac)
   - 点击右上角的 ⚙️ 图标
   - 选择 `Install Plugin from Disk...`
   - 选择从 GitHub 下载的 `CodeBuddy Local-1.0.0.zip` 文件
   - 点击 OK 并重启 PhpStorm

## GitHub 仓库设置建议

### 1. 创建 Releases（可选）

如果你想发布正式版本：

1. 在 GitHub 仓库创建新的 Release
2. 打上标签，例如 `v1.0.0`
3. GitHub Actions 会自动将构建的插件附加到 Release 中

### 2. 保护主分支

建议设置分支保护规则：
1. 进入仓库 `Settings → Branches`
2. 添加分支保护规则到 `main` 分支
3. 启用 "Require status checks to pass before merging"

### 3. 启用 Pages（可选）

如果你想要一个下载页面：
1. `Settings → Pages`
2. 选择 `Deploy from a branch`
3. 选择 `gh-pages` 分支和 `/root` 文件夹

## 常见问题

### Q1: 构建失败怎么办？
- 检查 GitHub Actions 日志中的错误信息
- 确保 `gradle/wrapper/gradle-wrapper.properties` 文件存在
- 确保 `gradlew` 脚本有执行权限（GitHub Actions 会自动设置）

### Q2: 找不到构建产物？
- 构建完成后可能需要等几分钟才会显示 Artifacts
- 确保工作流运行成功（绿色勾号）
- 检查是否有构建错误

### Q3: 插件版本如何更新？
1. 修改 `build.gradle.kts` 中的 `version` 属性
2. 修改 `src/main/resources/META-INF/plugin.xml` 中的 `version`
3. 提交并推送代码
4. GitHub Actions 会自动构建新版本

### Q4: 如何添加新的功能？
1. 在 `src/main/java/com/tencent/codebuddy/` 中添加新类
2. 更新 `plugin.xml` 注册新组件
3. 推送到 GitHub，等待自动构建

## 自动化构建流程

每次推送到 main 分支时，GitHub Actions 会：

1. ✅ 检出代码
2. ✅ 设置 JDK 11 环境
3. ✅ 给 gradlew 添加执行权限
4. ✅ 使用 Gradle 构建插件
5. ✅ 上传构建产物作为 Artifacts
6. ✅ 如果打了标签，自动创建 Release

## 监控构建状态

你可以：
- 在仓库主页查看最新的构建状态
- 设置 GitHub 通知，接收构建结果邮件
- 使用 GitHub Mobile App 监控构建进度

## 联系支持

如果在使用 GitHub 构建过程中遇到问题：
1. 查看 GitHub Actions 文档
2. 检查构建日志中的具体错误
3. 如果需要帮助，可以提交 GitHub Issue