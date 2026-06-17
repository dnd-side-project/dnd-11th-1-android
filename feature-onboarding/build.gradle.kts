plugins {
    id("matetrip.android.hilt")
    id("matetrip.android.library")
    id("matetrip.android.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.materip.feature_onboarding"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    packaging {
        resources {
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
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
    implementation(libs.serialization) //serialization
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