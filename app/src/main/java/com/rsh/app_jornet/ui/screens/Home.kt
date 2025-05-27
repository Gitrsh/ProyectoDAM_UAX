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
import com.rsh.app_jornet.utils.normalizar

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen(navController: NavHostController, vistaModelo: VistaModelo = viewModel()) {
    val partes = vistaModelo.listaPartes
    var parteSeleccionado by remember { mutableStateOf<ParteTrabajo?>(null) }
    var parteEliminar by remember { mutableStateOf<ParteTrabajo?>(null) }
    var parteArchivar by remember { mutableStateOf<ParteTrabajo?>(null) }

    val context = LocalContext.current

    var filtro by remember { mutableStateOf("") }
    var ordenFechaDesc by remember { mutableStateOf(true) } // Si es true, muestra primero los descendentes

    //Llamamos a ViewModel para traernos los parte del usuario que tiene iniciada la sesión
    LaunchedEffect(Unit) {
        vistaModelo.cargarPartesDelUsuario { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    //  Filtro por cadena de caracteres
    val partesFiltrados = partes.filter { parte ->
        if (filtro.isBlank()) {
            true // Si el campo de filtrado está vacío, muestra todos
        } else {
            val consulta = filtro.normalizar()

            // En esta variable almacenamos los campos en los que actuará el filtro
            val camposAfiltrar = listOf(
                parte.obra,
                parte.fecha,
                parte.observaciones,
                parte.trabajosRealizados,
                parte.turno,
                parte.tipoJornada,
                parte.dieta
            ).joinToString(" ").normalizar()

            // En esta variable metemos todos los campos de empleado para "simular" que es un campo único por el que podamos filtra
            val filasEmpleado = parte.empleados.joinToString(" ") { emp ->
                "${emp["nombre"] ?: ""} ${emp["apellidos"] ?: ""} ${emp["categoria"] ?: ""}"
            }.normalizar()

            // metemos todos los campos de maquinas para "simular" que es un campo único ppor el que podamos filtrar
            val filasMaquina = parte.maquinas.joinToString(" ") { maq ->
                "${maq["matricula"] ?: ""} ${maq["tipo"] ?: ""} ${maq["marca"] ?: ""} ${maq["modelo"] ?: ""}"
            }.normalizar()

            // Busca la cadena de caracteres del filtro de búsqueda en los campos que hemos marcado antes
            camposAfiltrar.contains(consulta) ||
                    filasEmpleado.contains(consulta) ||
                    filasMaquina.contains(consulta)
        }
    }

    // Ordenar por fecha según la selección del usuario
    val partesFiltradosOrdenados = partesFiltrados.sortedBy { it.fecha }
        .let { if (ordenFechaDesc) it.reversed() else it }

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
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Filtro
                    Column(
                        modifier = Modifier
                            .widthIn(max = anchoMax)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Búsqueda filtrada de los campos
                        TextField(
                            value = filtro,
                            onValueChange = { filtro = it },
                            label = { Text("Buscar en cualquier campo", color = Color.Black) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Barra con botón de exportar y ordenar
                    Row(
                        modifier = Modifier
                            .widthIn(max = anchoMax)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Botón de exportar
                        Button(
                            onClick = { ExportadorCSV.exportarPartesCSV(context, partesFiltradosOrdenados) },
                            enabled = partesFiltradosOrdenados.isNotEmpty(),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Red1,
                                contentColor = Color.White,
                                disabledContainerColor = Color.LightGray,
                                disabledContentColor = Color.White
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_export),
                                contentDescription = "Exportar partes filtrados",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Exportar",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Botón de ordenar por fecha
                        var expanded by remember { mutableStateOf(false) }
                        Button(
                            onClick = { expanded = true },
                            enabled = partesFiltradosOrdenados.isNotEmpty(),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Red1,
                                contentColor = Color.White,
                                disabledContainerColor = Color.LightGray,
                                disabledContentColor = Color.White
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "Ordenar por",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Más recientes") },
                                onClick = {
                                    ordenFechaDesc = true
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Más antiguos") },
                                onClick = {
                                    ordenFechaDesc = false
                                    expanded = false
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Contenido limitado en ancho
                    Column(
                        modifier = Modifier
                            .widthIn(max = anchoMax)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (partesFiltradosOrdenados.isEmpty()) {
                            Text(
                                text = "No hay partes que coincidan con la búsqueda.",
                                color = Color.White
                            )
                        } else {
                            LazyColumn {
                                items(partesFiltradosOrdenados) { parte ->
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