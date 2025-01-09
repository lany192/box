plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}
apply(from = "../../gradle/publish.gradle")

android {
    namespace = "com.github.lany192." + project.name
    ndkVersion = libs.versions.android.ndk.version.get()
    compileSdk = libs.versions.android.compile.sdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        vectorDrawables {
            useSupportLibrary = true
        }

        externalNativeBuild {
            cmake {
                cppFlags("-std=c++17")
            }
        }
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    lint {
        targetSdk = libs.versions.android.target.sdk.get().toInt()
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar", "*.aar"), "dir" to "libs")))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit2)
    androidTestImplementation(libs.androidx.test.espresso)
}