package com.matetrip.build_logic

import com.android.build.api.dsl.LibraryExtension
import com.matetrip.build_logic.extensions.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.getByType<LibraryExtension>().configureAndroid()
        }
    }
}