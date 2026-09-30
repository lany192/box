apply(plugin = "maven-publish")

// apk 信息来自根项目 extra
@Suppress("UNCHECKED_CAST")
val apk = rootProject.extra["apk"] as Map<String, Any>

group = apk["groupId"] as String
version = apk["versionName"] as String

// apply(from=) 引入的 .kts 脚本编译类路径不含 AGP，android.publishing.singleVariant 通过动态调用配置
pluginManager.withPlugin("com.android.library") {
    val publishing = extensions.getByName("android").withGroovyBuilder { "getPublishing"() }
    publishing!!.withGroovyBuilder { "singleVariant"("release") }
}

afterEvaluate {
    extensions.configure<org.gradle.api.publish.PublishingExtension> {
        publications {
            create<org.gradle.api.publish.maven.MavenPublication>("release") {
                groupId = apk["groupId"] as String
                version = apk["versionName"] as String
                from(components["release"])
                pom {
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                }
            }
        }
        repositories {
            mavenLocal() // 发布到本地仓库
            maven {
                name = "mavenCustom"
                url = rootProject.uri(rootProject.file("repository"))
            }
//            maven {
//                isAllowInsecureProtocol = true
//                url = uri("http://localhost:5001/repository/maven-snapshots/")
//                credentials {
//                    username = "admin"
//                    password = "dev123456"
//                }
//            }
        }
    }
}
