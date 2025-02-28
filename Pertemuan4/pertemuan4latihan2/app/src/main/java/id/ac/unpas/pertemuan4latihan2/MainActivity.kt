package id.ac.unpas.pertemuan4latihan2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.unpas.pertemuan4latihan2.ui.theme.RegisterAppTheme // Pastikan ini ada dan benar!
import androidx.compose.ui.text.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterAppTheme { // Menggunakan RegisterAppTheme
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistrationForm(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegistrationForm(modifier: Modifier = Modifier) {
    val nama = remember { mutableStateOf(TextFieldValue("")) }
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val nomorTelepon = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val alamatRumah = remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = "Nama Lengkap", modifier = Modifier.padding(4.dp))
        TextField(
            value = nama.value,
            onValueChange = { nama.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Username", modifier = Modifier.padding(4.dp))
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Nomor Telepon", modifier = Modifier.padding(4.dp))
        TextField(
            value = nomorTelepon.value,
            onValueChange = { nomorTelepon.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Email", modifier = Modifier.padding(4.dp))
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Alamat Rumah", modifier = Modifier.padding(4.dp))
        TextField(
            value = alamatRumah.value,
            onValueChange = { alamatRumah.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp)) // Menggunakan Modifier.height untuk Spacer

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    if (nama.value.text.isNotEmpty() && username.value.text.isNotEmpty() &&
                        nomorTelepon.value.text.isNotEmpty() && email.value.text.isNotEmpty() &&
                        alamatRumah.value.text.isNotEmpty()
                    ) {
                        Toast.makeText(context, "Halo, ${nama.value.text}!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Semua inputan harus diisi!", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green, contentColor = Color.White)
            ) {
                Text(text = "Simpan", style = TextStyle(fontSize = 16.sp))
            }

            Button(
                onClick = {
                    nama.value = TextFieldValue("")
                    username.value = TextFieldValue("")
                    nomorTelepon.value = TextFieldValue("")
                    email.value = TextFieldValue("")
                    alamatRumah.value = TextFieldValue("")
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White)
            ) {
                Text(text = "Reset", style = TextStyle(fontSize = 16.sp))
            }
        }
    }
}