plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.legacy.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.android.hilt)
}

android {
    namespace = libs.versions.app.group.id.get() + "." + project.name
    compileSdk = libs.versions.app.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.app.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


    lint {
        targetSdk = libs.versions.app.target.sdk.get().toInt()
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit2)
    androidTestImplementation(libs.androidx.test.espresso)

    // 项目依赖（PageInfo.java 使用 com.github.lany192.arch.entity.Page）
    implementation(project(":library:arch"))

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.coroutines)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.gson)

    implementation(libs.moshi)
    // AGP 9 内置 Kotlin 下 parcelize 插件不会自动添加该运行时依赖（其 forAllAndroidVariants 依赖已移除的旧 Variant API）
    implementation(libs.kotlin.parcelize.runtime)
}