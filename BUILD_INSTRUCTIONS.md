# 构建和安装说明

## 构建插件

1. **使用命令行构建**：
   ```bash
   # Windows
   gradlew.bat buildPlugin
   
   # Linux/Mac
   ./gradlew buildPlugin
   ```

2. **构建结果**：
   - 插件 ZIP 文件位于：`build/distributions/CodeBuddy Local-1.0.0.zip`
   - 可以直接导入 PhpStorm 安装

## 在 PhpStorm 2021.3.3 中安装插件

### 方法一：从磁盘安装（推荐）
1. 打开 PhpStorm 2021.3.3
2. 转到 `File → Settings → Plugins` (Windows) 或 `PhpStorm → Preferences → Plugins` (Mac)
3. 点击右上角的 ⚙️ 图标
4. 选择 `Install Plugin from Disk...`
5. 选择构建好的 `CodeBuddy Local-1.0.0.zip` 文件
6. 点击 OK 并重启 PhpStorm

### 方法二：从本地目录安装
1. 将构建的插件 ZIP 文件解压到 PhpStorm 插件目录：
   - Windows: `%APPDATA%\JetBrains\PhpStorm2021.3\plugins\`
   - Mac: `~/Library/Application Support/JetBrains/PhpStorm2021.3/plugins/`
   - Linux: `~/.local/share/JetBrains/PhpStorm2021.3/plugins/`
2. 重启 PhpStorm

## 插件功能验证

安装成功后，验证以下功能：

1. **菜单项**：
   - `Tools` 菜单中应有 `Open CodeBuddy` 选项
   - 编辑器右键菜单应有 `Analyze with CodeBuddy` 选项
   - `Code` 菜单中应有 `CodeBuddy Quick Fix` 选项

2. **工具窗口**：
   - 右侧应有 CodeBuddy 工具窗口
   - 可以点击 `Analyze Current File` 按钮进行分析

3. **代码检查**：
   - 打开 Java 文件时，编辑器会显示 CodeBuddy 的代码建议
   - 使用 System.out.println 会有提示
   - 捕获通用 Exception 会有警告

## 测试插件

1. 创建一个测试 Java 文件：
   ```java
   import java.util.ArrayList;
   
   public class Test {
       public static void main(String[] args) {
           System.out.println("Hello CodeBuddy");
           ArrayList<String> list = new ArrayList<>();
           try {
               // some code
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }
   ```

2. 测试功能：
   - 右键点击文件，选择 `Analyze with CodeBuddy`
   - 使用快捷键 `Ctrl + Alt + C` 打开 CodeBuddy 面板
   - 选中部分代码，查看 `CodeBuddy Quick Fix` 功能

## 故障排除

### 常见问题

1. **插件无法加载**：
   - 确保 PhpStorm 版本为 2021.3.3
   - 检查 JDK 版本为 11+
   - 重启 PhpStorm

2. **构建失败**：
   - 检查网络连接（Gradle 需要下载依赖）
   - 确保有足够的磁盘空间
   - 检查 Java 版本：`java -version`

3. **功能不可用**：
   - 检查插件是否已启用：`Settings → Plugins`
   - 重启 PhpStorm
   - 清除缓存：`File → Invalidate Caches...`

### 日志查看

如果遇到问题，可以查看日志：
- Windows: `%APPDATA%\JetBrains\PhpStorm2021.3\log\idea.log`
- Mac: `~/Library/Logs/JetBrains/PhpStorm2021.3/idea.log`
- Linux: `~/.cache/JetBrains/PhpStorm2021.3/log/idea.log`

## 开发建议

如果需要扩展功能：
1. 修改 `src/main/java` 中的源代码
2. 更新 `src/main/resources/META-INF/plugin.xml` 配置
3. 重新构建插件：`./gradlew buildPlugin`
4. 在 PhpStorm 中重新安装插件

## GitHub Actions 构建

项目已配置 GitHub Actions 自动构建：

1. **自动构建**：每次推送到 main/master 分支会自动构建
2. **手动触发**：可以在 GitHub Actions 页面手动运行工作流
3. **构建产物**：在 Actions 页面下载 `codebuddy-plugin` artifact

### GitHub Actions 配置
- 使用最新的 actions 版本 (v4)
- 包含 Gradle 依赖缓存，提高构建速度
- 自动创建 Release（当打标签时）

## 注意事项

1. 这是一个基础版插件，主要功能为演示
2. 实际使用时可能需要根据需求扩展功能
3. 建议在生产环境前充分测试
4. GitHub Actions 使用最新的 v4 actions，确保兼容性