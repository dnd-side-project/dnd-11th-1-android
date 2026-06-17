package com.matetrip.build_logic

import com.android.build.api.dsl.ApplicationExtension
import com.matetrip.build_logic.extensions.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.getByType<ApplicationExtension>().run {
                configureAndroid()

                defaultConfig {
                    targetSdk = 34
                    versionCode = 3
                    versionName = "1.0.1"
                }
            }
        }
    }
}