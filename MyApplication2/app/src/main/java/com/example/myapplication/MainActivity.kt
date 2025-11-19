package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HeartRateLimits(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HeartRateLimits(modifier: Modifier = Modifier) {

    var ageInput by remember { mutableStateOf("") }
    val age = ageInput.toIntOrNull() ?: 0

    val upper = if (age > 0) (220 - age) * 0.85f else 0f
    val lower = if (age > 0) (220 - age) * 0.65f else 0f

    val upperFormatted = String.format(Locale.getDefault(), "%.2f", upper)
    val lowerFormatted = String.format(Locale.getDefault(), "%.2f", lower)

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = ageInput,
            onValueChange = { ageInput = it },
            label = { Text(text = stringResource(R.string.age)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(
                R.string.your_heart_rate_limits_are,
                lowerFormatted,
                upperFormatted
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeartRateLimitsPreview() {
    MyApplicationTheme {
        HeartRateLimits()
    }
}
