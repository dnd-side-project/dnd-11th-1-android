plugins {
    id("matetrip.android.hilt")
    id("matetrip.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.materip.core_datastore"

    defaultConfig {
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
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
    composeOptions{
        kotlinCompilerExtensionVersion="1.5.2"
    }
}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-network"))
    implementation(project(":core-common"))

    //hilt
    implementation(libs.bundles.hilt.impl)
    implementation(project(":core-database"))
    kapt(libs.bundles.hilt.kapt)

    //serialization
    implementation(libs.serialization)
    implementation(libs.bundles.sandwich)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}