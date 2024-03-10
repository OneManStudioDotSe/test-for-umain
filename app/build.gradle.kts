plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    kotlin("plugin.serialization")
    kotlin("kapt")
}

android {
    namespace = "se.onemanstudio.test.umain"
    compileSdk = 34

    defaultConfig {
        applicationId = "se.onemanstudio.test.umain"

        minSdk = 24
        targetSdk = 34

        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isDefault = true

            buildConfigField("Boolean", "DEBUG", "true")
            buildConfigField("String", "BASE_URL", "\"https://food-delivery.umain.io/api/v1/\"")

            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"

            isDebuggable = true
            isJniDebuggable = false

            //isShrinkResources = false
            //isMinifyEnabled = false
            proguardFiles("proguard-android.txt", "proguard-rules.pro")

        }

        getByName("release") {
            buildConfigField("Boolean", "DEBUG", "false")
            buildConfigField("String", "BASE_URL", "\"https://food-delivery.umain.io/api/v1/\"")

            isDebuggable = false

            //isShrinkResources = true
            //isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Android magic
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.material3)

    // Compose magic
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    //Retrofit + OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.sandwich)
    implementation(libs.retrofit.sandwich.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logginginterceptor)
    implementation(libs.okhttp.urlconnection)

    // DI
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    //Tools to make life easier
    implementation(libs.coil.compose)
    implementation(libs.timber)

    // Testing
    debugImplementation(libs.androidx.ui.tooling)
}