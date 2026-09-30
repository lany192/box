// plugins 块中 parcelize/hilt 等插件依赖 android 插件先行应用；
// 体内 apply(plugin=) 不生成类型安全访问器，android 配置统一走 configure<> + 属性链，依赖使用字符串调用形式
apply(plugin = "com.android.application")
apply(plugin = "com.android.legacy-kapt")
apply(plugin = "kotlin-parcelize")
apply(plugin = "com.google.dagger.hilt.android")
apply(plugin = "com.github.lany192.router")

configure<dagger.hilt.android.plugin.HiltExtension> {
    enableAggregatingTask = false
}

configure<org.jetbrains.kotlin.gradle.dsl.KaptExtensionConfig> {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
//        arg("AROUTER_GENERATE_DOC", "enable")
        //是否debug模式
        arg("ROUTER_DEBUG", "true")
        //是否打印JS路由文档
        arg("JS_ROUTER_DOC", "true")
        //Uri Scheme标识
        arg("ROUTER_SCHEME", "box")
        //JS路由调用方法
        arg("ROUTER_JS_FUN", "window.app.route")
    }
}

val buildTime = "\"${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(java.util.Date())}\""
val apk = rootProject.extra["apk"] as Map<String, Any>
val reports = layout.buildDirectory.dir("reports")

configure<com.android.build.api.dsl.ApplicationExtension> {
    namespace = apk["appId"] as String + "." + project.name

    compileSdk = libs.versions.app.compile.sdk.get().toInt()

    signingConfigs {
        create("config") {
            keyAlias = "box"
            keyPassword = "box2024"
            storeFile = file("box.jks")
            storePassword = "box2024"
        }
    }

    defaultConfig {
        applicationId = apk["appId"] as String + "." + project.name
        minSdk = libs.versions.app.min.sdk.get().toInt()
        targetSdk = libs.versions.app.target.sdk.get().toInt()
        versionCode = apk["versionCode"] as Int
        versionName = apk["versionName"] as String
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("config")
        vectorDrawables.useSupportLibrary = true
        ndk.abiFilters += listOf("armeabi-v7a", "arm64-v8a")
    }

    buildFeatures.viewBinding = true

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
            //打包时间
            resValue("string", "apk_build_time", buildTime)
        }
        create("develop") {
            matchingFallbacks = mutableListOf("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
            versionNameSuffix = "_dev"
            //打包时间
            resValue("string", "apk_build_time", buildTime)
        }
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("config")
            versionNameSuffix = "_dev"
            //打包时间
            resValue("string", "apk_build_time", "2000-01-01 01:01:01")
        }
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    // AGP 9 已移除 kotlinOptions，jvmTarget 由根工程的 KotlinCompile 统一配置

    packaging.resources.excludes += listOf(
        "**/*.properties", "**/*.txt",
        "META-INF/*.info", "META-INF/*.version",
        "META-INF/*.md", "META-INF/*.kotlin_module",
        "META-INF/maven/com.squareup.okio/okio/pom.xml", "META-INF/ASL2.0",
        "META-INF/MANIFEST.MF", "META-INF/NOTICE", "META-INF/LICENSE",
        "META-INF/INDEX.LIST", "META-INF/DEPENDENCIES", "META-INF/LGPL2.1"
    )

    lint {
        abortOnError = false
        absolutePaths = true
        baseline = file("lint-baseline.xml")
        checkOnly += listOf("NewApi", "InlinedApi")
        checkAllWarnings = true
        checkDependencies = true
        checkGeneratedSources = true
        checkReleaseBuilds = true
        checkTestSources = true
        disable += listOf("TypographyFractions", "TypographyQuotes")
        enable += listOf("RtlHardcoded", "RtlCompat", "RtlEnabled")
        error += listOf("Wakelock", "TextViewEdits")
        explainIssues = false
        fatal += listOf("NewApi", "InlineApi")
        htmlOutput = reports.file("lint-report.html").get().asFile
        htmlReport = true
        ignore += "TypographyQuotes"
        ignoreTestSources = true
        ignoreWarnings = true
        informational += "StopShip"
        lintConfig = file("default-lint.xml")
        noLines = true
        quiet = true
        sarifOutput = reports.file("lint-report.html").get().asFile
        sarifReport = true
        showAll = true
        textOutput = reports.file("lint-results.txt").get().asFile
        textReport = true
        warning += "ResourceAsColor"
        warningsAsErrors = true
        xmlOutput = reports.file("lint-report.xml").get().asFile
        xmlReport = true
    }
}

dependencies {
    "implementation"(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))
    "testImplementation"(libs.junit)
    "androidTestImplementation"(libs.androidx.test.ext.junit2)
    "androidTestImplementation"(libs.androidx.test.espresso)

    "implementation"(libs.glide)
    "kapt"(libs.glide.compiler)

    "implementation"(libs.androidx.room.runtime)
    "implementation"(libs.androidx.room.ktx)
    "kapt"(libs.androidx.room.compiler)

    "implementation"(libs.hilt)
    "kapt"(libs.hilt.compiler)

    "implementation"(libs.lany.router)
    "kapt"(libs.lany.router.compiler)


    //https://github.com/Knight-ZXW/LancetX
    "compileOnly"("io.github.knight-zxw:lancet-runtime:0.0.7")


    "implementation"(project(":library:arch"))
    "implementation"(project(":library:view"))
    "implementation"(project(":library:core"))
    "implementation"(project(":library:dialog"))
    "implementation"(project(":library:update"))
    "implementation"(project(":library:time"))
    "implementation"(project(":library:toolkit"))
    "implementation"(project(":library:blackbox"))
    "implementation"(project(":library:tablayout"))
    "implementation"(project(":library:html"))
    "implementation"(project(":library:video"))
    "implementation"(project(":library:blurview"))

    "implementation"(project(":common:router"))
    "implementation"(project(":common:network"))
    "implementation"(project(":common:database"))

    "implementation"(project(":feature:browser"))
    "implementation"(project(":feature:hello"))
    "implementation"(project(":feature:login"))
    "implementation"(project(":feature:math"))
    "implementation"(project(":feature:user"))

    //内存泄漏检测 https://github.com/square/leakcanary
    "debugImplementation"(libs.leakcanary)

    "implementation"(libs.androidx.annotation)
    "implementation"(libs.androidx.appcompat)
    "implementation"(libs.androidx.activity)
    "implementation"(libs.androidx.activity.ktx)
    "implementation"(libs.androidx.fragment)
    "implementation"(libs.androidx.fragment.ktx)
    "implementation"(libs.androidx.core.ktx)
    "implementation"(libs.androidx.lifecycle.livedata.ktx)
    "implementation"(libs.androidx.lifecycle.viewmodel.ktx)
    "implementation"(libs.androidx.recyclerview)
    "implementation"(libs.androidx.constraintlayout)
    "implementation"(libs.androidx.multidex)
    "implementation"(libs.androidx.material)
    "implementation"(libs.androidx.exifinterface)
    "implementation"(libs.androidx.paging.runtime.ktx)
    "implementation"(libs.androidx.paging.runtime)
    "implementation"(libs.androidx.startup.runtime)
    "implementation"(libs.androidx.cardview)
    "implementation"(libs.androidx.palette)
    "implementation"(libs.androidx.viewpager2)
    "implementation"(libs.androidx.webkit)

    "implementation"(libs.coroutines.android)
    "implementation"(libs.coroutines.core)
    "implementation"(libs.androidx.camera.camera2)
    "implementation"(libs.androidx.camera.core)
    "implementation"(libs.androidx.camera.extensions)
    "implementation"(libs.androidx.camera.lifecycle)
    "implementation"(libs.androidx.camera.view)
    "implementation"(libs.androidx.media3.exoplayer)
    "implementation"(libs.androidx.media3.exoplayer.dash)
    "implementation"(libs.androidx.media3.exoplayer.hls)
    "implementation"(libs.androidx.media3.ui)
    "implementation"(libs.refresh.header)
    "implementation"(libs.refresh.layout)

    "implementation"(libs.toaster)
    "implementation"(libs.brvah)
    "implementation"(libs.immersionbar)
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

    "implementation"(libs.backgroundx)
    "implementation"(libs.kluban)
    "implementation"(libs.luban)
//    "implementation"(libs.lany.decoration)
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
    "implementation"(libs.blur)
    "implementation"(libs.rootbeer)
    "implementation"(libs.lottie)

    "implementation"(libs.lany.mocker)
    "implementation"(libs.transformation.layout)
    "implementation"(libs.live.event.bus)
    "implementation"(libs.tencent.pag)
}
//引入安装包生成配置
apply(from = "../../gradle/package.gradle.kts")
