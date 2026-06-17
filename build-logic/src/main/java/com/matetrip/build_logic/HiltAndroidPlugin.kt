package com.matetrip.build_logic

import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltAndroidPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.dagger.hilt.android")
            pluginManager.apply("org.jetbrains.kotlin.kapt")
        }
    }
}