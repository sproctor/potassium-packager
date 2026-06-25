/*
 * Copyright 2020-2021 JetBrains s.r.o. and respective authors and developers.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

package com.seanproctor.potassium

import com.seanproctor.potassium.internal.configureDesktop
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("AbstractClassCanBeConcreteClass") // Required abstract for Gradle ObjectFactory.newInstance()
abstract class PotassiumPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val potassiumExtension = project.extensions.create("potassium", PotassiumExtension::class.java)

        project.afterEvaluate {
            configureDesktop(project, potassiumExtension)
        }
    }
}
