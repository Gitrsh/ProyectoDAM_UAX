package com.rsh.app_jornet.ui.screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rsh.app_jornet.auth.MiAutenticador
import com.rsh.app_jornet.ui.theme.App_JornetTheme
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rsh.app_jornet.R
import com.rsh.app_jornet.ui.theme.Red1
import com.rsh.app_jornet.ui.theme.Red2
//PAntalla de logueo. Primera pantalla de la aplicación
@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                Brush.verticalGradient(
                    listOf(Red1, Red2)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logoprincipal),
            contentDescription = "Logo",
            modifier = Modifier
                .clip(CircleShape)
                .size(200.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Appartes. Cada día contigo.",
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        //  Email
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        //  Contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Iniciar Sesión
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    // Usamos el objeto MiAutenticador para hacer la autenticación
                    MiAutenticador.accesoUsuario(email, password) { success, errorMessage ->
                        if (success) {
                            Toast.makeText(context, "Login correcto", Toast.LENGTH_SHORT).show()
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                errorMessage ?: "Error desconocido",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para ir a Registro
        TextButton(
            onClick = { navController.navigate("Sign") },
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}