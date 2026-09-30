// 是否是单模块运行
val singleRun = (findProperty("run_browser_module") as String).toBoolean()

// plugins 块无法条件应用，且 parcelize/hilt 等插件依赖 android 插件先行应用，这里统一按需在脚本体内应用；
// 体内 apply(plugin=) 不生成类型安全访问器，android 配置统一走 configure<> + 属性链，依赖使用字符串调用形式
if (singleRun) {
    apply(plugin = "com.android.application")
} else {
    apply(plugin = "com.android.library")
}
apply(plugin = "com.android.legacy-kapt")
apply(plugin = "kotlin-parcelize")
apply(plugin = "com.google.dagger.hilt.android")
apply(plugin = "com.github.lany192.router")

configure<org.jetbrains.kotlin.gradle.dsl.KaptExtensionConfig> {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}

val apk = rootProject.extra["apk"] as Map<String, Any>

configure<com.android.build.api.dsl.CommonExtension> {
    namespace = apk["appId"] as String + "." + project.name
    compileSdk = libs.versions.app.compile.sdk.get().toInt()

    defaultConfig.apply {
        minSdk = libs.versions.app.min.sdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures.viewBinding = true

    buildTypes.getByName("release") {
        isMinifyEnabled = false
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging.resources.excludes += listOf(
        "**/*.properties", "**/*.txt",
        "META-INF/*.info", "META-INF/*.version",
        "META-INF/*.md", "META-INF/*.kotlin_module",
        "META-INF/maven/com.squareup.okio/okio/pom.xml", "META-INF/ASL2.0",
        "META-INF/MANIFEST.MF", "META-INF/NOTICE", "META-INF/LICENSE",
        "META-INF/INDEX.LIST", "META-INF/DEPENDENCIES", "META-INF/LGPL2.1"
    )
}

if (singleRun) {
    configure<com.android.build.api.dsl.ApplicationExtension> {
        defaultConfig.apply {
            targetSdk = libs.versions.app.target.sdk.get().toInt()
            applicationId = namespace
            versionCode = apk["versionCode"] as Int
            versionName = apk["versionName"] as String
            multiDexEnabled = true
            ndk.abiFilters += listOf("armeabi-v7a", "arm64-v8a")
        }
        sourceSets.getByName("main") {
            java.setSrcDirs(listOf("src/main/java", "src/develop/java"))
            res.setSrcDirs(listOf("src/main/res", "src/develop/res"))
            manifest.srcFile("src/develop/AndroidManifest.xml")
        }
    }
} else {
    // AGP 9 中 library DSL 已移除 targetSdk；consumerProguardFiles 仅 library 角色可用
    configure<com.android.build.api.dsl.LibraryExtension> {
        defaultConfig.consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    "implementation"(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))
    "testImplementation"(libs.junit)
    "androidTestImplementation"(libs.androidx.test.ext.junit2)
    "androidTestImplementation"(libs.androidx.test.espresso)

    "implementation"(project(":common:router"))
    "implementation"(project(":common:network"))

    "implementation"(project(":library:arch"))
    "implementation"(project(":library:view"))
    "implementation"(project(":library:core"))
    "implementation"(project(":library:html"))

    "implementation"(libs.hilt)
    "kapt"(libs.hilt.compiler)

    "implementation"(libs.lany.router)
    "kapt"(libs.lany.router.compiler)

    "implementation"(libs.androidx.appcompat)
    "implementation"(libs.androidx.constraintlayout)
    "implementation"(libs.androidx.material)
    "implementation"(libs.immersionbar)
    if (singleRun) {
        "implementation"(project(":feature:hello"))
        "implementation"(project(":feature:login"))
        "implementation"(project(":feature:math"))
        "implementation"(project(":feature:user"))
        "implementation"(libs.glide)
        "kapt"(libs.glide.compiler)

        "implementation"(libs.androidx.room.runtime)
        "implementation"(libs.androidx.room.ktx)
        "kapt"(libs.androidx.room.compiler)

        "implementation"(libs.androidx.activity)
        "implementation"(libs.androidx.activity.ktx)
        "implementation"(libs.androidx.fragment)
        "implementation"(libs.androidx.fragment.ktx)
        "implementation"(libs.androidx.core.ktx)
        "implementation"(libs.androidx.lifecycle.livedata)
        "implementation"(libs.androidx.lifecycle.livedata.ktx)
        "implementation"(libs.androidx.lifecycle.viewmodel)
        "implementation"(libs.androidx.lifecycle.viewmodel.ktx)
        "implementation"(libs.androidx.recyclerview)
        "implementation"(libs.androidx.multidex)
        "implementation"(libs.androidx.exifinterface)
        "implementation"(libs.androidx.paging.runtime.ktx)
        "implementation"(libs.androidx.paging.runtime)
        "implementation"(libs.androidx.startup.runtime)
        "implementation"(libs.androidx.media3.exoplayer)
        "implementation"(libs.androidx.media3.exoplayer.dash)
        "implementation"(libs.androidx.media3.ui)
        "implementation"(libs.androidx.camera.core)
        "implementation"(libs.androidx.camera.camera2)
        "implementation"(libs.androidx.camera.lifecycle)
        "implementation"(libs.androidx.camera.view)
        "implementation"(libs.androidx.camera.extensions)

        "implementation"(libs.coroutines.core)
        "implementation"(libs.coroutines.android)

        "implementation"(libs.toaster)
        "implementation"(libs.brvah)
        "implementation"(libs.retrofit)
        "implementation"(libs.retrofit.moshi)
        "implementation"(libs.retrofit.gson)

        "implementation"(libs.moshi)

        "implementation"(libs.tencent.mmkv)
        "implementation"(libs.tencent.xlog)
        "implementation"(libs.tencent.sonic)
        "implementation"(libs.tencent.wechat)
        "implementation"(libs.tencent.tbs)
        "implementation"(libs.tencent.vasdolly.reader)
        "implementation"(libs.tencent.vasdolly.writer)

        "implementation"(libs.xcrash)
        "implementation"(libs.refresh.header)
        "implementation"(libs.refresh.layout)

        "implementation"(libs.backgroundx)
        "implementation"(libs.kluban)
        "implementation"(libs.luban)
        "implementation"(libs.lany.keyboard)
        "implementation"(libs.flexbox)
        "implementation"(libs.number.picker)
        "implementation"(libs.eventbus)
        "implementation"(libs.lany.download)
        "implementation"(libs.licenses)

        "implementation"(libs.banner)
        "implementation"(libs.touchimageview)
        "implementation"(libs.zxing.lite)
        "implementation"(libs.rxjava)
        "implementation"(libs.rxandroid)
        "implementation"(libs.spinner)
        "implementation"(libs.html.spanner)
        "implementation"(libs.phoenix)
        "implementation"(libs.oss.sdk.client)
        "implementation"(libs.oss.sdk.s33)
        "implementation"(libs.permissions)
    } else {
        "implementation"(libs.tencent.tbs)
    }
}
