package com.matetrip.build_logic.extensions

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureAndroid() {

    extensions.getByType<LibraryExtension>().run {
        compileSdk = 34
        defaultConfig {
            minSdk = 26
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    extensions.getByType<KotlinAndroidProjectExtension>().compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}