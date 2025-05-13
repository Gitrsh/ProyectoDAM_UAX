package com.rsh.app_jornet.ui.screens

import ParteTrabajo
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rsh.app_jornet.R
import com.rsh.app_jornet.ui.VistaModelo
import com.rsh.app_jornet.ui.componentes.ConfirmDialog
import com.rsh.app_jornet.ui.componentes.DetalleParteDialog
import com.rsh.app_jornet.ui.componentes.FichaParte
import com.rsh.app_jornet.ui.theme.Red1
import com.rsh.app_jornet.ui.theme.Red2
import com.rsh.app_jornet.utils.ExportadorCSV

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen(navController: NavHostController, vistaModelo: VistaModelo = viewModel()) {
    val partes = vistaModelo.listaPartes
    var parteSeleccionado by remember { mutableStateOf<ParteTrabajo?>(null) }
    var parteEliminar by remember { mutableStateOf<ParteTrabajo?>(null) }
    var parteArchivar by remember { mutableStateOf<ParteTrabajo?>(null) }

    val context = LocalContext.current

    //Llamamos a ViewModel para traernos los parte del usuario que tiene iniciada la sesión
    LaunchedEffect(Unit) {
        vistaModelo.cargarPartesDelUsuario { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        floatingActionButton = {  //Botón flotante que nos lleva a la pantalla de creación de partes
            FloatingActionButton(
                onClick = { navController.navigate("PantallaParte") },
                modifier = Modifier.testTag("botonCrearParte"),//Esta linea es solo para las pruebas instrumentales
                containerColor = Color.White
            ) {
                Text("+", color = Color.Red)
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
                .background(Color.White) // Borde blanco
        ) {

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Brush.verticalGradient(colors = listOf(Red1, Red2)))
            ) {
                val anchoMax = if (maxWidth > 720.dp) 700.dp else maxWidth

                Column(
                    modifier = Modifier.fillMaxSize(),
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
                                .padding(16.dp)
                        ) {
                            // Logo y texto centrados
                            Row(
                                modifier = Modifier.align(Alignment.Center),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logoprincipal),
                                    contentDescription = "Logo",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "LISTA DE PARTES",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }

                            // Botón de exportar a la derecha
                            IconButton(
                                onClick = { ExportadorCSV.exportarPartesCSV(context, partes) },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_export),
                                    contentDescription = "Exportar CSV",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Contenido limitado en ancho
                    Column(
                        modifier = Modifier
                            .widthIn(max = anchoMax)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (partes.isEmpty()) {
                            Text(
                                text = "No hay partes creados todavía.",
                                color = Color.White
                            )
                        } else {
                            LazyColumn {
                                items(partes) { parte ->
                                    FichaParte(
                                        parte = parte,
                                        onClick = { parteSeleccionado = parte },
                                        onEliminarClick = { parteEliminar = parte },
                                        onArchivarClick = { parteArchivar = parte }
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                        }

                        // Diálogos de confirmación de eliminar y archivar
                        parteEliminar?.let { parte ->
                            ConfirmDialog(
                                title = "Eliminar parte",
                                message = "¿Estás seguro de que quieres eliminar este parte?",
                                onConfirm = {
                                    vistaModelo.eliminarParte(parte, onSuccess = {}, onError = {})
                                    parteEliminar = null
                                },
                                onDismiss = { parteEliminar = null }
                            )
                        }

                        parteArchivar?.let { parte ->
                            ConfirmDialog(
                                title = "Archivar parte",
                                message = "¿Deseas archivar este parte? Ya no se mostrará en la lista.",
                                onConfirm = {
                                    vistaModelo.archivarParte(parte, onSuccess = {}, onError = {})
                                    parteArchivar = null
                                },
                                onDismiss = { parteArchivar = null }
                            )
                        }

                        parteSeleccionado?.let { parte ->
                            DetalleParteDialog(parte = parte, vistaModelo = vistaModelo) {
                                parteSeleccionado = null
                            }
                        }
                    }
                }
            }
        }
    }
}


