import java.util.Properties

plugins {
    id("matetrip.android.library")
    id("matetrip.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties().apply{
    load(project.rootProject.file("./core-network/local.properties").inputStream())
}

android {
    namespace = "com.materip.core_network"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", localProperties.getProperty("BASE_URL"))
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
    buildFeatures{
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-database"))

    //hilt
    implementation(libs.hilt.android)
    implementation(project(":core-database"))
    kapt(libs.hilt.android.compiler)

    //retrofit
    implementation(libs.bundles.retrofit)

    //sandwich
    implementation(libs.bundles.sandwich)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}