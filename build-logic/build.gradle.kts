plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.android.gradlePlugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "matetrip.android.library"
            implementationClass = "com.matetrip.build_logic.AndroidLibraryPlugin"
        }
    }
}