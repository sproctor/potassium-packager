package com.example.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() =
    application {
        // Injected by the Potassium plugin as a -Dapp.version launcher option.
        val version = System.getProperty("app.version")
        Window(
            onCloseRequest = ::exitApplication,
            title = "Potassium Sample",
        ) {
            App(version = version)
        }
    }
