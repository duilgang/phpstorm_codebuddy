# 推送代码到 GitHub 并构建插件

## 第一步：推送现有代码到 GitHub

如果你还没有推送代码，按照以下步骤：

### 1. 初始化 Git 仓库（如果还没做）
```bash
cd d:/aicode/phpstorm_codebuddy
git init
```

### 2. 添加所有文件
```bash
git add .
```

### 3. 提交更改
```bash
git commit -m "初始提交: PhpStorm 2021.3.3 CodeBuddy 插件"
```

### 4. 添加远程仓库
```bash
git remote add origin https://github.com/duilgang/phpstorm_codebuddy.git
```

### 5. 推送到 GitHub
```bash
git branch -M main
git push -u origin main
```

## 第二步：使用 GitHub Actions 构建

### 自动构建
一旦代码推送到 GitHub：
1. 打开 https://github.com/duilgang/phpstorm_codebuddy
2. 点击 `Actions` 标签页
3. 你会看到 `Build CodeBuddy Plugin` 工作流自动开始运行

### 手动触发构建（如果需要）
1. 在 `Actions` 标签页
2. 点击 `Build CodeBuddy Plugin`
3. 点击 `Run workflow`
4. 选择 `main` 分支
5. 点击绿色的 `Run workflow` 按钮

## 第三步：下载构建的插件

### 等待构建完成
构建过程大约需要 3-5 分钟，步骤如下：
1. ✅ 设置 JDK 11 环境
2. ✅ 准备构建环境
3. ✅ 运行 Gradle 构建
4. ✅ 上传构建产物

### 下载插件文件
构建完成后：
1. 点击完成的构建任务
2. 在 `Summary` 页面找到 `Artifacts` 部分
3. 下载 `codebuddy-plugin` 压缩包
4. 解压后找到 `CodeBuddy Local-1.0.0.zip`

## 第四步：安装到 PhpStorm

### 安装步骤
1. 打开 PhpStorm 2021.3.3
2. `File → Settings → Plugins`
3. 点击右上角 ⚙️ 图标
4. 选择 `Install Plugin from Disk...`
5. 选择从 GitHub 下载的 ZIP 文件
6. 点击 OK
7. 重启 PhpStorm

### 验证安装
1. 重启后，查看右侧是否有 `CodeBuddy` 工具窗口
2. 检查 `Tools` 菜单是否有 `Open CodeBuddy` 选项
3. 使用 `Ctrl+Alt+C` 测试快捷键

## 第五步：后续开发流程

### 修改代码后
1. 修改源代码
2. 提交更改：
   ```bash
   git add .
   git commit -m "描述你的更改"
   git push origin main
   ```
3. GitHub Actions 会自动构建新版本

### 更新插件版本
修改以下文件中的版本号：
1. `build.gradle.kts`:
   ```kotlin
   version = "1.0.1"  # 更新版本号
   ```
2. `src/main/resources/META-INF/plugin.xml`:
   ```xml
   <version>1.0.1</version>  # 更新版本号
   ```

## 快速命令参考

### Git 命令
```bash
# 查看状态
git status

# 添加所有更改
git add .

# 提交更改
git commit -m "提交信息"

# 推送到 GitHub
git push origin main

# 拉取最新代码
git pull origin main
```

### 构建相关
```bash
# 本地测试构建（需要 JDK 11）
gradlew.bat buildPlugin  # Windows
./gradlew buildPlugin    # Linux/Mac

# 清理构建缓存
gradlew.bat clean
```

## 故障排除

### 问题1：GitHub Actions 构建失败
**解决**：
1. 查看构建日志中的错误信息
2. 检查 `gradle/wrapper/gradle-wrapper.properties` 文件是否存在
3. 确保所有必要的文件都已提交

### 问题2：找不到构建产物
**解决**：
1. 等待构建完全完成
2. 检查是否有构建错误
3. 确保工作流运行成功

### 问题3：插件安装失败
**解决**：
1. 确保下载的是正确的 ZIP 文件
2. 检查 PhpStorm 版本是否为 2021.3.3
3. 重启 PhpStorm 后重试

## 注意事项

1. **首次推送**：可能需要输入 GitHub 用户名和密码
2. **构建时间**：首次构建可能需要下载依赖，时间较长
3. **文件大小**：构建产物大约 5-10MB
4. **版本管理**：建议使用语义化版本号（如 1.0.0、1.0.1）

## 获取帮助

如果遇到问题：
1. 查看 GitHub Actions 日志
2. 检查项目 README 文件
3. 提交 GitHub Issue