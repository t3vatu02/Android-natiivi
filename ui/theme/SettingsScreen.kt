package com.example.week4.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Settings Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))
        Text("This is a simple second screen.")
    }
}
