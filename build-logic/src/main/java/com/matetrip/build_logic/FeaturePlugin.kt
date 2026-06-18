package com.matetrip.build_logic

import org.gradle.api.Plugin
import org.gradle.api.Project

class FeaturePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("matetrip.android.hilt")
            pluginManager.apply("matetrip.android.library")
            pluginManager.apply("matetrip.android.compose")
        }
    }
}