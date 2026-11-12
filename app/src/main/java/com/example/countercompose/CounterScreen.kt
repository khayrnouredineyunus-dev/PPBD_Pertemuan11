package com.example.countercompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countercompose.ui.theme.CounterComposeTheme

@Composable
fun CounterApp(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel()
) {
    val counter by viewModel.counter
    val nameInput by viewModel.nameInput     // [CHALLENGE]
    val savedName by viewModel.savedName     // [CHALLENGE]

    CounterScreen(
        counter = counter,
        savedName = savedName,
        nameInput = nameInput,
        onIncrement = { viewModel.increment() },
        onDecrement = { viewModel.decrement() },
        onReset = { viewModel.reset() },
        onNameChange = { viewModel.onNameChange(it) },
        onSaveName = { viewModel.saveName() },
        modifier = modifier
    )
}

@Composable
fun CounterScreen(
    counter: Int,
    savedName: String,
    nameInput: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    onNameChange: (String) -> Unit,
    onSaveName: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()          // [CHALLENGE] naik saat keyboard muncul
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Counter App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        CounterDisplay(
            value = counter,
            name = savedName,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CounterButtons(
            onDecrement = onDecrement,
            onIncrement = onIncrement,
            onReset = onReset
        )
        Spacer(modifier = Modifier.height(8.dp))
        NameForm(                                   // [CHALLENGE]
            name = nameInput,
            onNameChange = onNameChange,
            onSave = onSaveName
        )
    }
}

@Composable
fun CounterDisplay(
    value: Int,
    modifier: Modifier = Modifier,
    name: String = ""                               // [CHALLENGE]
) {
    Box(
        modifier = modifier.background(
            color = Color(0xFFF5F5F5),
            shape = RoundedCornerShape(16.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nilai Counter",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )
        Text(
            text = value.toString(),
            fontSize = 120.sp,
            fontWeight = FontWeight.Bold
        )
        // [CHALLENGE] Nama tampil di bawah angka, hanya jika sudah disimpan
        if (name.isNotBlank()) {
            Text(
                text = "Nama: $name",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun CounterButtons(
    onDecrement: () -> Unit = {},
    onIncrement: () -> Unit = {},
    onReset: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onDecrement,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336))
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("KURANG")
            }
            Button(
                onClick = onIncrement,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50))
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("TAMBAH")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3))
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_refresh),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text("RESET")
        }
    }
}

// [CHALLENGE] Form input Nama + tombol Simpan
@Composable
fun NameForm(
    name: String,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nama") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                onSave()
                focusManager.clearFocus()   // tutup keyboard setelah simpan
            },
            enabled = name.isNotBlank(),    // tombol mati kalau form kosong
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIMPAN")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CounterScreenPreview() {
    CounterComposeTheme {
        CounterScreen(
            counter = 7,
            savedName = "Nama Kamu",
            nameInput = "Nama Kamu",
            onIncrement = {},
            onDecrement = {},
            onReset = {},
            onNameChange = {},
            onSaveName = {}
        )
    }
}