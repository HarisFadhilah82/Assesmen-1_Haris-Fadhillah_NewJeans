package com.haris0035.newjeans.ui.theme.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
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
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
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
    var harga by rememberSaveable { mutableStateOf("") }
    var biaya by rememberSaveable { mutableStateOf("") }
    var hotel by rememberSaveable { mutableStateOf("") }

    var expanded by rememberSaveable { mutableStateOf(false) }
    val options = (1..10).map { it.toString() }
    var selectedOption by rememberSaveable { mutableStateOf(options.first()) }

    var totalBiaya by remember { mutableStateOf<Double?>(null) }

    var isHargaError by rememberSaveable { mutableStateOf(false) }
    var isBiayaError by rememberSaveable { mutableStateOf(false) }
    var isHotelError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(13.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo NewJeans",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )

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

            Button(
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
        Button(
            onClick = {
                totalBiaya?.let {
                    val message = context.getString(R.string.bagikan_template, it.toInt())
                    shareData(context, message)
                }
            },
            enabled = totalBiaya != null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.bagikan))
        }



    }
}
private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
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