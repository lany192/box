pluginManagement {
    repositories {
        mavenLocal()
        maven { setUrl("https://mirrors.tencent.com/nexus/repository/maven-public/") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://www.jitpack.io") }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        // 腾讯镜像对以下组的构件只缓存了 POM 而缺失 JAR/AAR，且 Gradle 不会回退到其他仓库取构件，这里强制走 JitPack 源站
        maven {
            setUrl("https://www.jitpack.io")
            content {
                includeGroup("com.github.lany192.app-router")
                includeGroup("com.github.android")
                includeGroup("com.github.JavaNoober.BackgroundLibrary")
                includeGroup("com.github.MikeOrtiz")
                includeGroup("com.github.forJrking")
                includeGroup("com.github.getActivity")
                // 其余 lany192 构件（如 KeyboardWatcher）本地 mavenLocal 优先，不做整体接管
                includeModule("com.github.lany192", "okdownload")
            }
        }
        // aws-android-sdk 系列在镜像中缺失，直接走 Maven Central
        maven {
            setUrl("https://repo1.maven.org/maven2")
            content {
                includeGroup("com.amazonaws")
            }
        }
        maven { setUrl("https://mirrors.tencent.com/nexus/repository/maven-public/") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://www.jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "box"
//include(":app:demo")
include(":app:avatar")
//include(":app:hello")

include(":library:arch")
include(":library:view")
include(":library:core")
include(":library:dialog")
include(":library:update")
include(":library:time")
include(":library:tablayout")
include(":library:html")
include(":library:video")
include(":library:toolkit")
include(":library:blackbox")
include(":library:blurview")

include(":common:network")
include(":common:router")
include(":common:database")
include(":common:navigation")

include(":feature:browser")
include(":feature:login")
include(":feature:hello")
include(":feature:math")
include(":feature:user")
 