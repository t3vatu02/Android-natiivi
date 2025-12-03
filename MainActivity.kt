package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions // Import added for KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.toSize // Import added for toSize
import com.example.myapplication.ui.theme.CaloriesTheme

// The problematic line "private val ui: Any" has been removed.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaloriesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalorieApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Heading(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    )
}

@Composable
fun WeightField(weightInput: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = weightInput,
        onValueChange = onValueChange,
        label = { Text("Enter weight") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun GenderChoices(male: Boolean, setGenderMale: (Boolean) -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = male, onClick = { setGenderMale(true) })
            Text("Male")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = !male, onClick = { setGenderMale(false) })
            Text("Female")
        }
    }
}

@Composable
fun IntensityList(onClick: (Float) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Light") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val items = listOf("Light", "Usual", "Moderate", "Hard", "Very hard")

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text("Select intensity") },
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = "Select intensity",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            items.forEach { item ->

                val intensity = when (item) {
                    "Light" -> 1.3f
                    "Usual" -> 1.5f
                    "Moderate" -> 1.7f
                    "Hard" -> 2f
                    "Very hard" -> 2.2f
                    else -> 0f
                }

                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedText = item
                        onClick(intensity)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Calculation(
    male: Boolean,
    weight: Int,
    intensity: Float,
    setResult: (Int) -> Unit
) {
    Button(
        onClick = {
            if (male) {
                setResult(((879 + 10.2 * weight) * intensity).toInt())
            } else {
                setResult(((795 + 7.18 * weight) * intensity).toInt())
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Calculate")
    }
}

@Composable
fun CalorieApp(modifier: Modifier = Modifier) {

    var weightInput by remember { mutableStateOf("") }
    val weight = weightInput.toIntOrNull() ?: 0

    var male by remember { mutableStateOf(true) }
    var intensity by remember { mutableStateOf(1.3f) }

    var result by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Heading("Calories")

        WeightField(weightInput = weightInput, onValueChange = { weightInput = it })

        GenderChoices(male = male, setGenderMale = { male = it })

        IntensityList(onClick = { intensity = it })

        Text(
            text = result.toString(),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )

        Calculation(
            male = male,
            weight = weight,
            intensity = intensity,
            setResult = { result = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalorieAppPreview() {
    CaloriesTheme {
        CalorieApp()
    }
}
