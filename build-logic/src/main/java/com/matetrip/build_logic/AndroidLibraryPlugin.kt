package com.matetrip.build_logic

import com.matetrip.build_logic.extensions.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            configureAndroid()
        }
    }
}