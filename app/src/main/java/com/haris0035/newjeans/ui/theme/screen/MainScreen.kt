package com.haris0035.newjeans.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.haris0035.newjeans.R
import com.haris0035.newjeans.navigation.Screen
import com.haris0035.newjeans.ui.theme.NewJeansTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {navController.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var harga by remember { mutableStateOf("") }
    var biaya by remember { mutableStateOf("") }
    var hotel by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val options = (1..10).map { it.toString() }
    var selectedOption by remember { mutableStateOf(options.first()) }

    var totalBiaya by remember { mutableStateOf<Double?>(null) }

    var isHargaError by remember { mutableStateOf(false) }
    var isBiayaError by remember { mutableStateOf(false) }
    var isHotelError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = harga,
            onValueChange = {
                harga = it
                isHargaError = false
            },
            label = { Text(stringResource(R.string.harga)) },
            trailingIcon = { Text("Rp") },
            singleLine = true,
            isError = isHargaError,
            supportingText = {
                if (isHargaError) Text("Harga tidak boleh kosong atau nol")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = biaya,
            onValueChange = {
                biaya = it
                isBiayaError = false
            },
            label = { Text(stringResource(R.string.biaya)) },
            trailingIcon = { Text("Rp") },
            singleLine = true,
            isError = isBiayaError,
            supportingText = {
                if (isBiayaError) Text("Biaya tidak boleh kosong atau nol")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = hotel,
            onValueChange = {
                hotel = it
                isHotelError = false
            },
            label = { Text(stringResource(R.string.hotel)) },
            trailingIcon = { Text("Rp/Malam") },
            singleLine = true,
            isError = isHotelError,
            supportingText = {
                if (isHotelError) Text(stringResource(R.string.input_invalid))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedOption,
                onValueChange = {},
                label = { Text("Jumlah Orang") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = TextFieldDefaults.outlinedTextFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOption = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        androidx.compose.material3.Button(
            onClick = {
                val hrg = harga.toDoubleOrNull()
                val trp = biaya.toDoubleOrNull()
                val htl = hotel.toDoubleOrNull()

                isHargaError = hrg == null || hrg == 0.0
                isBiayaError = trp == null || trp == 0.0
                isHotelError = htl == null || htl == 0.0

                val allValid = !(isHargaError || isBiayaError || isHotelError)

                if (allValid) {
                    val orang = selectedOption.toIntOrNull() ?: 1
                    val total = (hrg!! + trp!! + htl!!) * orang
                    totalBiaya = total
                } else {
                    totalBiaya = null
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Hitung")
        }

        totalBiaya?.let {
            Text(
                text = "Total estimasi biaya: Rp ${"%,.0f".format(it)}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    NewJeansTheme {
        MainScreen(rememberNavController())

    }
}