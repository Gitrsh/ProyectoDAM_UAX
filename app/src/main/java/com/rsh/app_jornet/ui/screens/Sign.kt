package com.rsh.app_jornet.ui.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rsh.app_jornet.auth.MiAutenticador
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.rsh.app_jornet.ui.theme.Red1
import com.rsh.app_jornet.ui.theme.Red2
import com.rsh.app_jornet.utils.mostrarToast

@Composable
fun SignScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current // Para usar en Toast

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Red1, Red2
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth().padding(8.dp ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black, // Texto en negro
                unfocusedTextColor = Color.Black, // Texto en negro cuando no está enfocado
                focusedContainerColor = Color.White, // Fondo blanco cuando está enfocado
                unfocusedContainerColor = Color.White, // Fondo blanco cuando no está enfocado
                disabledContainerColor = Color.White // Fondo blanco cuando está deshabilitado
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth().padding(8.dp ),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black, // Texto en negro
                unfocusedTextColor = Color.Black, // Texto en negro cuando no está enfocado
                focusedContainerColor = Color.White, // Fondo blanco cuando está enfocado
                unfocusedContainerColor = Color.White, // Fondo blanco cuando no está enfocado
                disabledContainerColor = Color.White // Fondo blanco cuando está deshabilitado
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth().padding(8.dp ),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black, // Texto en negro
                unfocusedTextColor = Color.Black, // Texto en negro cuando no está enfocado
                focusedContainerColor = Color.White, // Fondo blanco cuando está enfocado
                unfocusedContainerColor = Color.White, // Fondo blanco cuando no está enfocado
                disabledContainerColor = Color.White // Fondo blanco cuando está deshabilitado
            )

        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == confirmPassword) {
                    MiAutenticador.registroUsuario(email, password) { success, errorMessage ->
                        mostrarToast(context, success, errorMessage)
                    }
                } else {
                    mostrarToast(context, false, "Las contraseñas no coinciden")
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, // Fondo blanco
                contentColor = Color.Black    // Texto negro
            )
        ) {
            Text("Registrar Cuenta")
        }
    }
}





