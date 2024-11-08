package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidstudio_koala_template.ui.theme.AndroidStudioKoalaTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidStudioKoalaTemplateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    var selectedIcon by remember { mutableStateOf("Tria un Icon") }
    var minValue by remember { mutableStateOf("0") }
    var maxValue by remember { mutableStateOf("10") }
    var sliderValue by remember { mutableStateOf(3f) }
    var currentIcon by remember { mutableStateOf(Icons.Default.Add) } // Icono inicial

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Repte 01", style = MaterialTheme.typography.headlineMedium)

        // Dropdown para seleccionar el icono
        DropdownMenuComponent(
            selectedIcon = selectedIcon,
            onIconSelected = { icon ->
                selectedIcon = icon
                currentIcon = when (icon) {
                    "Add" -> Icons.Default.Add
                    "Call" -> Icons.Default.Call
                    "Email" -> Icons.Default.Email
                    else -> Icons.Default.Add
                }
            }
        )

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Campos de entrada para valores mínimos y máximos
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Min:")
                BasicTextField(
                    value = minValue,
                    onValueChange = { minValue = it },
                    modifier = Modifier.width(50.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Max:")
                BasicTextField(
                    value = maxValue,
                    onValueChange = { maxValue = it },
                    modifier = Modifier.width(50.dp),
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
                )
            }
        }

        // Slider
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = (minValue.toFloatOrNull() ?: 0f)..(maxValue.toFloatOrNull() ?: 10f),
            steps = ((maxValue.toFloatOrNull() ?: 10f) - (minValue.toFloatOrNull() ?: 0f)).toInt() - 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Enviar
        Button(onClick = { /* Puedes agregar lógica adicional si es necesario */ }) {
            Text("Enviar")
        }

        // Mostrar el icono seleccionado con el valor del Slider en un BadgedBox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            BadgedBox(
                badge = { Text(text = sliderValue.toInt().toString()) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = currentIcon,
                    contentDescription = "Icono seleccionado",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sliderValue.toInt().toString(),
                fontSize = 32.sp
            )
        }
    }
}

@Composable
fun DropdownMenuComponent(selectedIcon: String, onIconSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Add", "Call", "Email", "Icon Default")

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = selectedIcon)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onIconSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAppContent() {
    AndroidStudioKoalaTemplateTheme {
        AppContent()
    }
}
