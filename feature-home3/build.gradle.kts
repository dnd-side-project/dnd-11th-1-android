import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

val localProperties = Properties().apply {
    load(project.file("local.properties").inputStream())
}

android {
    namespace = "com.materip.feature_home3"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "SERVER_BASE_URL", "\"${localProperties.getProperty("SERVER_BASE_URL")}\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-repository"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-common"))
    implementation(project(":feature-mypage"))

    //coroutine
    implementation(libs.coroutine)
    implementation(libs.kotlinx.coroutines.android)
    implementation(platform(libs.kotlinx.coroutines.bom))

    //coil
    implementation(libs.bundles.coil)

    //navigation
    implementation(libs.navigation)

    //hilt
    implementation(libs.bundles.hilt.impl)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview.android)
    kapt(libs.bundles.hilt.kapt)

    implementation(libs.androidx.lifecycle) //lifecycle
    implementation(libs.bundles.ui) //ui
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}