package id.ac.unpas.hellocompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.unpas.hellocompose.ui.theme.HelloComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Paksa tema terang (light mode)
            HelloComposeTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FormRegistrasi()
                }
            }
        }
    }
}

@Composable
fun FormRegistrasi(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val nama = remember { mutableStateOf(TextFieldValue("")) }
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val telepon = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val alamat = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = modifier.padding(16.dp)) {
        InputField("Nama", nama)
        InputField("Username", username)
        InputField("No Telepon", telepon, KeyboardType.Phone)
        InputField("Email", email, KeyboardType.Email)
        InputField("Alamat", alamat)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (nama.value.text.isNotBlank() &&
                        username.value.text.isNotBlank() &&
                        telepon.value.text.isNotBlank() &&
                        email.value.text.isNotBlank() &&
                        alamat.value.text.isNotBlank()
                    ) {
                        Toast.makeText(context, "Halo, ${nama.value.text}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Semua input harus diisi", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(text = "Simpan", style = TextStyle(fontSize = 18.sp), modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    nama.value = TextFieldValue("")
                    username.value = TextFieldValue("")
                    telepon.value = TextFieldValue("")
                    email.value = TextFieldValue("")
                    alamat.value = TextFieldValue("")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "Reset", style = TextStyle(fontSize = 18.sp), modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    state: MutableState<TextFieldValue>,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Text(text = label, modifier = Modifier.padding(vertical = 4.dp))
    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}
