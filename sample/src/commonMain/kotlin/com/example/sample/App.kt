package com.example.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Shared (commonMain) UI — the same composable could back Android/iOS/web targets too.
 * The desktop entry point in jvmMain hosts it in a window.
 */
@Composable
fun App(version: String? = null) {
    MaterialTheme {
        var count by remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val heading =
                if (version.isNullOrBlank()) {
                    "Hello from Potassium Packager!"
                } else {
                    "Hello from Potassium Packager! v$version"
                }
            Text(heading, style = MaterialTheme.typography.headlineSmall)
            Text("You clicked $count time(s)")
            Button(onClick = { count++ }) {
                Text("Click me")
            }
        }
    }
}
