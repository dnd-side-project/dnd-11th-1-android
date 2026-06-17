package com.matetrip.build_logic

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val android = extensions.getByName("android") as CommonExtension<*, *, *, *, *, *>

            android.buildFeatures {
                compose = true
            }
        }
    }
}