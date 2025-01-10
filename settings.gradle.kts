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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
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
//include(":app:avatar")
include(":app:hello")

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

include(":feature:browser")
include(":feature:login")
include(":feature:hello")
include(":feature:math")
include(":feature:user")
 