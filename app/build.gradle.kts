import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    id("matetrip.android.application")
    id("matetrip.android.compose")
}

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.materip.matetrip"

    defaultConfig {
        applicationId = "com.materip.matetrip"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "NATIVE_APP_KEY", localProperties["NATIVE_APP_KEY"] as String)
        manifestPlaceholders["REDIRECTION_PATH"] = localProperties["REDIRECTION_PATH"] as String
        buildConfigField("String", "SERVER_BASE_URL", localProperties.getProperty("SERVER_BASE_URL") as String)
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["release"]
        }
        debug {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

dependencies {
    implementation(project(":core-common"))
    implementation(project(":core-model"))
    implementation(project(":core-network"))
    implementation(project(":core-datastore"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-repository"))
    implementation(project(":feature-login"))
    implementation(project(":feature-home3"))
    implementation(project(":feature-mypage"))
    implementation(project(":feature-onboarding"))

    implementation(libs.navigation) //navigation
    implementation(libs.bundles.kakao) //kakao
    implementation(libs.bundles.hilt.impl) //hilt
    kapt(libs.bundles.hilt.kapt)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.open.licenses) //open licence

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}