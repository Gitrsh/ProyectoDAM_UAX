package com.rsh.app_jornet.ui.screens

import ParteTrabajo
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsh.app_jornet.ui.components.DialogoSeleccion
import com.rsh.app_jornet.ui.theme.Red1
import com.rsh.app_jornet.ui.theme.Red2
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import com.rsh.app_jornet.ui.VistaModelo
import com.rsh.app_jornet.ui.componentes.SeleccionFecha
import com.rsh.app_jornet.util.ValidacionParte.validarCamposParteTrabajo

//Pantalla para crear los partes (formulario)
@Composable
fun ParteScreen(navController: NavController, vistaModelo: VistaModelo) {
    var fecha by remember { mutableStateOf("Seleccionar Fecha") }
    var obra by remember { mutableStateOf("") }
    var turno by remember { mutableStateOf("") }
    var tipoJornada by remember { mutableStateOf("") }
    var descripcionTrabajo by remember { mutableStateOf("") }
    var horasTrabajadas by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }
    var horasExtras by remember { mutableStateOf("") }
    var dieta by remember { mutableStateOf("") }
    var mostrarDialogoEmp by remember { mutableStateOf(false) }
    var mostrarDialogoMaq by remember { mutableStateOf(false) }
    val vistaModelo: VistaModelo = viewModel()
    val empleados = vistaModelo.empleados
    val maquinas = vistaModelo.maquinas
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
            .background(Color.White)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(Red1, Red2)))
        ) {
            val anchoMax = if (maxWidth > 720.dp) 700.dp else maxWidth

            Box(modifier = Modifier.fillMaxSize()) {


                Column(
                    modifier = Modifier
                        .widthIn(max = anchoMax)
                        .align(Alignment.TopCenter)
                        .padding(top = 100.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

            // Selección de Fecha (ver clase SeleccionFecha)
            SeleccionFecha(fecha) { nuevaFecha -> fecha = nuevaFecha }

            Spacer(modifier = Modifier.height(8.dp))

            // Campo Nombre de la obra
            OutlinedTextField(
                value = obra,
                onValueChange = { obra = it },
                label = { Text("Nombre de la obra", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Selección de Turno
            Text("Turno:", color = Color.White, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Mañana", "Tarde", "Noche").forEach { opcion ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = turno == opcion,
                            onClick = { turno = opcion },
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Blue))
                        Text(opcion, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Selección tipo de jornada
            Text("Tipo de jornada:", color = Color.White, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Laboral", "Festivo").forEach { opcion ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = tipoJornada == opcion,
                            onClick = { tipoJornada = opcion },
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Blue))
                        Text(opcion, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Selección Dieta
            Text("Dieta:", color = Color.White, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("No", "1/2", "Completa").forEach { opcion ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = dieta == opcion,
                            onClick = { dieta = opcion },
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Blue))
                        Text(opcion, color = Color.White)
                    }
                }
            }

            // Campo Descripción del Trabajo
            OutlinedTextField(
                value = descripcionTrabajo,
                onValueChange = { descripcionTrabajo = it },
                label = { Text("Descripción del Trabajo", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Nº de horas:",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = horasTrabajadas,
                    onValueChange = { horasTrabajadas = it.filter { c -> c.isDigit() } },
                    label = { Text("Ordinarias", color = Color.Black) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                OutlinedTextField(
                    value = horasExtras,
                    onValueChange = { horasExtras = it.filter { c -> c.isDigit() } },
                    label = { Text("Extras", color = Color.Black) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Botones para seleccionar máquinas y trabajadores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { mostrarDialogoMaq = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Máquinaria", color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { mostrarDialogoEmp = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Operarios", color = Color.Black)
                }
            }

            if (mostrarDialogoMaq) {
                DialogoSeleccion(
                    lista = maquinas,
                    onDismiss = { mostrarDialogoMaq = false },
                    onSeleccion = { maquinaSeleccionada ->
                        val index = maquinas.indexOfFirst { it.campo1 == maquinaSeleccionada.campo1 }
                        if (index != -1) {
                            maquinas[index] = maquinas[index].copy(seleccionado = !maquinas[index].seleccionado)
                        }
                    }
                )
            }

            if (mostrarDialogoEmp) {
                DialogoSeleccion(
                    lista = empleados,
                    onDismiss = { mostrarDialogoEmp = false },
                    onSeleccion = { empleadoSeleccionado ->
                        val index = empleados.indexOfFirst { it.campo1 == empleadoSeleccionado.campo1 }
                        if (index != -1) {
                            empleados[index] = empleados[index].copy(seleccionado = !empleados[index].seleccionado)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = observaciones,
                onValueChange = { observaciones = it },
                label = { Text("Observaciones", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val empSeleccionados = empleados.filter { it.seleccionado }
                            val maqSeleccionadas = maquinas.filter { it.seleccionado }

                            val mensajeError = validarCamposParteTrabajo(
                                fecha, obra, descripcionTrabajo, horasTrabajadas,
                                turno, tipoJornada, dieta, empSeleccionados
                            )

                            if (mensajeError != null) {
                                Toast.makeText(navController.context, mensajeError, Toast.LENGTH_SHORT).show()
                            } else {
                                val parte = ParteTrabajo(
                                    fecha = fecha,
                                    obra = obra,
                                    maquinas = maqSeleccionadas.map { maq ->
                                        mapOf(
                                            "matricula" to maq.campo1,
                                            "modelo" to maq.campo2,
                                            "tipo" to maq.campo3,
                                            "marca" to maq.campo4
                                        )
                                    },
                                    trabajosRealizados = descripcionTrabajo,
                                    turno = turno,
                                    tipoJornada = tipoJornada,
                                    observaciones = observaciones,
                                    empleados = empSeleccionados.map { emp ->
                                        mapOf(
                                            "id" to emp.campo1,
                                            "nombre" to emp.campo2,
                                            "apellidos" to emp.campo3,
                                            "categoria" to emp.campo4
                                        )
                                    },
                                    horasTrabajadas = horasTrabajadas.toIntOrNull() ?: 0,
                                    dieta = dieta
                                )
                                vistaModelo.generarParte(parte, onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                })
                                Toast.makeText(navController.context, "Parte de Trabajo Creado", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("Crear Parte de Trabajo", color = Color.Black)
                    }
                }
               //BArra superior
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
                            text = "PARTE DIARIO",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
        }
    }
}