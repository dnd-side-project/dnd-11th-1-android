import java.util.Properties

plugins {
    id("matetrip.android.hilt")
    id("matetrip.android.library")
}
val localProperties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.materip.feature_mypage"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "PRIVACY_URL", localProperties.getProperty("PRIVACY_URL"))
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.bundles.paging) //paging
    //hilt
    implementation(libs.bundles.hilt.impl)
    kapt(libs.bundles.hilt.kapt)
    implementation(libs.open.licenses) //open licence

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}