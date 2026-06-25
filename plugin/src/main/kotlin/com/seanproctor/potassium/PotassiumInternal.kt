/*
 * Copyright 2026 Sean Proctor and respective authors and developers.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

package com.seanproctor.potassium

/**
 * Marks a declaration as internal to the Potassium plugin's implementation.
 *
 * Such declarations are `public` only for technical reasons — for example they appear in the
 * constructors of public Gradle task types, or they form the OS-agnostic union that the per-OS DSL
 * enums map to. They are **not** part of the supported consumer DSL and may change or be removed
 * without notice.
 *
 * Consumers should never need to opt in. The plugin itself opts in module-wide via the
 * `-opt-in=com.seanproctor.potassium.PotassiumInternal` compiler argument in `plugin/build.gradle.kts`.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal Potassium API not intended for consumer use; it may change without notice.",
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.TYPEALIAS,
)
annotation class PotassiumInternal
