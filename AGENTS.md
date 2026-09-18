# AGENTS.md

This file provides guidance to the AI agent when working with code in this repository.

## 项目概览

多模块 Android 工程，AGP 9.4.0 + Gradle 9.7.1 + AGP 内置 Kotlin，构建脚本已全部迁移为 Kotlin DSL（.gradle.kts）。

- 当前生效的 app 只有 `app:hello`；`app:demo`、`app:avatar` 在 settings.gradle.kts 中被注释、不参与构建（README/Jenkinsfile 里仍有它们的旧命令，不要照抄）。
- 依赖版本统一维护在 `gradle/libs.versions.toml`，构建脚本通过 `libs.xxx` 引用。

## 构建与验证

已验证的调用方式（Windows Git Bash）：

    JAVA_HOME=/d/java/jdk-21.0.2 ./gradlew :app:hello:assembleDebug --console=plain

- 构建出 APK（debug/release）即视为验证通过；快速自检可用 `:library:arch:help`（会加载公共脚本 buildinfo/publish）。
- Windows 文件锁：若报 `另一个程序正在使用此文件`（classes.jar 等构建产物被占用），先 `./gradlew --stop`，仍不行则结束残留的 Gradle/Kotlin java 守护进程后重试。

## AGP 9 迁移约束（改动构建脚本前必读）

- 不要再应用 `org.jetbrains.kotlin.android`（AGP 9 自带 Kotlin）；kapt 用 `com.android.legacy-kapt`；`kotlinOptions` 已移除，jvmTarget 由根脚本对 KotlinCompile 统一设为 11。
- library 角色 DSL 没有 `targetSdk`；`consumerProguardFiles` 仅 library 可用。
- `gradle/*.gradle.kts` 是通过 `apply(from=)` 引入的脚本，其编译类路径不含 AGP（工程 plugins 块的类路径也不会传入），脚本里不能引用 `com.android.*` 类型。现有实现：publish.gradle.kts 用 `withGroovyBuilder` 动态调用 `android.publishing.singleVariant`；package.gradle.kts 只用 Gradle API，从 AGP 约定输出目录 `outputs/apk/<buildType>`、`outputs/mapping/<buildType>` 拷贝产物。
- feature 模块因按条件在脚本体内 `apply(plugin=...)`，没有类型安全访问器：不能用 `android {}` 块，需 `configure<com.android.build.api.dsl.CommonExtension>` + 属性链，依赖用 `"implementation"(...)` 字符串形式。
- kapt 参数（AROUTER_* 路由配置）走 `configure<org.jetbrains.kotlin.gradle.dsl.KaptExtensionConfig>`，其 `arg` 只接受 String 值（布尔要写 "true"/"false"）。

## 模块与运行模式

- feature/* 支持单模块独立运行：`gradle.properties` 中 `run_<name>_module=true` 时该模块按 application 构建，否则按 library 被 app 依赖（脚本内 `singleRun` 分支）；同时只应开启一个。
- 打包信息通过根 build.gradle.kts 的 `extra["apk"]`（appId/groupId/versionCode/versionName）传给公共脚本，publish/package 脚本依赖该约定。

## 提交规范

提交信息用中文 Conventional Commits：`feat(hello): ...`、`build: ...`、`refactor(database): ...`。
