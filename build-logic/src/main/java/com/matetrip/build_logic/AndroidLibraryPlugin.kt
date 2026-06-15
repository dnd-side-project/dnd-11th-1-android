package com.matetrip.build_logic

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

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
        }
    }
}