package com.rsh.app_jornet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rsh.app_jornet.auth.MiAutenticador
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.rsh.app_jornet.R
import com.rsh.app_jornet.ui.theme.Red1
import com.rsh.app_jornet.ui.theme.Red2
import com.rsh.app_jornet.utils.mostrarToast

@Composable
fun SignScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current // Para usar en Toast


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
            .background(Color.White)
    ) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Red1, Red2)
                )
            )
            .padding(16.dp)
    ) {
        val anchoMax = if (maxWidth > 600.dp) 500.dp else maxWidth


            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Barra superior
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Red1,
                    shadowElevation = 8.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Appartes",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .widthIn(max = anchoMax)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Registro",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
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

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar Contraseña") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
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

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(id = R.drawable.logoprincipal),
                        contentDescription = "Logo Principal",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(150.dp)
                    )
                }
            }
        }
    }
}