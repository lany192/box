import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    dependencies {
        classpath(libs.lany.router.register)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.google.ksp) apply false
}

allprojects {
    // 配置说明 https://juejin.cn/post/6908232077200588814
    configurations.all {
        // 动态版本缓存时效
        resolutionStrategy.cacheDynamicVersionsFor(10, "minutes")
        // 快照版本缓存时效
        resolutionStrategy.cacheChangingModulesFor(4, "hours")
        // 所有的依赖降级策略
        resolutionStrategy {
            dependencySubstitution {
                all {
                    requested.let { dependency ->
                        if (dependency is ModuleComponentSelector) {
                            // 如果发现项目内存在 group + name 等于远端的 group + name，那么直接采用本地工程进行编译
                            val p = rootProject.allprojects.find { p ->
                                p.group == dependency.group && p.name == dependency.module
                            }
                            if (p != null) {
                                println("选择本地模块：" + p.name)
                                useTarget(project(p.path), "选择本地模块")
                            }
                        }
                    }
                }
            }
        }
    }

    afterEvaluate {
        if (plugins.hasPlugin("com.android.library") || plugins.hasPlugin("com.android.application")) {
            val android = extensions.getByName("android") as com.android.build.gradle.BaseExtension
            android.compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
//            if (plugins.hasPlugin("org.jetbrains.kotlin.android")) {
//                android.kotlinOptions {
//                    jvmTarget = "11"
//                }
//            }
        }
        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
}

allprojects {
    extra.apply {
        // 安装包
        set("apk", mapOf(
            "appId" to "com.lany192.box",
            "groupId" to "com.github.lany192.box",
            "versionCode" to 1,
            "versionName" to "1.0.0"
        ))
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

apply(from = "./gradle/buildinfo.gradle")