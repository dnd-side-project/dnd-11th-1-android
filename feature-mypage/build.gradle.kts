plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.materip.feature_mypage"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    buildFeatures{
        compose = true
    }
}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-repository"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-common"))

    implementation(libs.coroutine) //coroutine
    implementation(libs.bundles.coil) //coil
    implementation(libs.navigation) //navigation
    implementation(libs.bundles.kakao) //kakao
    implementation(libs.bundles.ui) //ui
    implementation(libs.androidx.lifecycle) //lifecycle
    //hilt
    implementation(libs.bundles.hilt.impl)
    kapt(libs.bundles.hilt.kapt)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}