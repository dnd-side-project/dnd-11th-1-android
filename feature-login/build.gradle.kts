import java.util.*

plugins {
    id("matetrip.feature")
}

val localProperties = Properties().apply{
    load(project.file("local.properties").inputStream())
}

android {
    namespace = "com.materip.feature_login"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["NATIVE_APP_KEY"] = localProperties.getProperty("NATIVE_APP_KEY")
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
    packaging {
        resources {
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
    buildFeatures{
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
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