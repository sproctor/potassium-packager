/*
 * Copyright 2020-2021 JetBrains s.r.o. and respective authors and developers.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

package com.seanproctor.potassium.internal

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal val Project.mppExt: KotlinMultiplatformExtension
    get() =
        extensions.findByType(KotlinMultiplatformExtension::class.java)
            ?: error("Could not find KotlinMultiplatformExtension ($project)")

internal val Project.javaSourceSets: SourceSetContainer
    get() = extensions.getByType(JavaPluginExtension::class.java).sourceSets
