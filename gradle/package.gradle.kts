// 安装包输出/拷贝/压缩脚本
// apply(from=) 引入的 .kts 脚本编译类路径不含 AGP，这里仅使用 Gradle API 编排任务，
// 从 AGP 约定的输出目录（outputs/apk、outputs/mapping）拷贝产物
@Suppress("UNCHECKED_CAST")
val apk = rootProject.extra["apk"] as Map<String, Any>

val properties = java.util.Properties()
val propertyFile = file("${rootDir.absolutePath}/local.properties")
if (propertyFile.exists()) {
    propertyFile.inputStream().use { properties.load(it) }
}

val buildTime = java.text.SimpleDateFormat("yyyyMMddHHmm").format(java.util.Date())
val versionName = apk["versionName"] as String

//apk输出根路径，如果有需要输出到其它路径，在local.properties配置APK_OUT_PATH
val ROOT_PATH = (properties.getProperty("APK_OUT_PATH") ?: rootDir.path) + "/apk"
//zip文件夹
val ZIP_FOLDER_PATH = "$ROOT_PATH/zip/$buildTime/"

// 需要输出安装包的构建类型：buildType -> 文件名后缀（与模块内 versionNameSuffix 对应）
val buildTypes = mapOf("develop" to "_dev", "release" to "")

buildTypes.forEach { (buildType, suffix) ->
    val variantName = buildType.replaceFirstChar { it.uppercaseChar() }
    //apk输出路径
    val outputPath = "$ROOT_PATH/$buildTime"
    //apk文件名称
    val fileName = "box_$versionName$suffix" + "_$buildTime.apk"

    val copyApkTask = tasks.register<Copy>("copy${variantName}Apk") {
        from(layout.buildDirectory.dir("outputs/apk/$buildType")) {
            include("*.apk")
        }
        into(outputPath)
        rename { fileName }
    }
    val copyMappingTask = tasks.register<Copy>("copy${variantName}Mapping") {
        from(layout.buildDirectory.file("outputs/mapping/$buildType/mapping.txt"))
        into(outputPath)
        rename { "mapping_${buildType}_$buildTime.txt" }
    }
    tasks.matching { it.name == "assemble$variantName" }.configureEach {
        finalizedBy(copyApkTask, copyMappingTask)
    }

    tasks.register(variantName) {
        group = "Package"
        dependsOn("assemble$variantName")
        doLast {
            println("\n$variantName apk包路径:$outputPath/$fileName")
            println("\n--- 打包任务完成---")
        }
    }
}

//拷贝任务
tasks.register("copy2zip") {
    dependsOn("assembleDevelop", "assembleRelease")
    doFirst {
        println("\n拷贝开始...")
    }
    doLast {
        //测试环境的包
        val developApkPath = "$ROOT_PATH/$buildTime/box_${versionName}_dev_$buildTime.apk"
        println("\n测试环境apk包:$developApkPath")
        //正式环境的包
        val releaseApkPath = "$ROOT_PATH/$buildTime/box_${versionName}_$buildTime.apk"
        println("\n正式环境apk包:$releaseApkPath")

        val zipDir = file(ZIP_FOLDER_PATH)
        if (!zipDir.exists()) {
            zipDir.mkdirs()
        }
        println("\n拷贝到zip文件夹：$ZIP_FOLDER_PATH")
        copy {
            from(developApkPath)
            into(ZIP_FOLDER_PATH)
        }
        copy {
            from(releaseApkPath)
            into(ZIP_FOLDER_PATH)
        }
        println("\n拷贝完成")
    }
}

//压缩任务
tasks.register<Zip>("zipApk") {
    dependsOn("copy2zip")
    archiveFileName.set("$buildTime.zip")
    destinationDirectory.set(file("$ROOT_PATH/zip/"))
    from(ZIP_FOLDER_PATH) {
        include("*.apk")
        exclude("**/*.zip")
    }
    doFirst {
        println("\n压缩开始...")
    }
    doLast {
        val dir = file(ZIP_FOLDER_PATH)
        println("\n删除多余文件和文件夹")
        dir.listFiles()?.forEach { it.delete() }
        dir.delete()
        println("\n压缩完成")
    }
}

//同时打正式、测试包，并且压缩成zip文件
tasks.register("develop&release&zip") {
    group = "Package"
    dependsOn("zipApk")
    doLast {
        println("\nzip所在文件夹:" + ROOT_PATH + "/zip/")
        println("\n--- 打包、拷贝、压缩任务完成---")
    }
}
