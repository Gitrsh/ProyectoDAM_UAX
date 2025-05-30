package com.rsh.app_jornet.ui.componentes

import ParteTrabajo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.rsh.app_jornet.R
import com.rsh.app_jornet.ui.VistaModelo
import com.rsh.app_jornet.utils.ExportadorCSV

//Nos muestra un dialogo personalizado con todos los datos de un parte y los botones de archivar y eliminar
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DetalleParteDialog(parte: ParteTrabajo, vistaModelo: VistaModelo, onDismiss: () -> Unit, navController: NavHostController) {
    var eliminarOK by remember { mutableStateOf(false) }//Son variables "reactivas" que usamos como tipo banderas
    var archivarOK by remember { mutableStateOf(false) }//si su estado cambia de true a false, en este caso mostrarian un dialogo
    //ademas con el remember evitamos que la variables se reinicie cada vez que la pantalla se "redibuja"

    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Icono de cerrar en la parte superior derecha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }

                Text("Parte del ${parte.fecha}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Obra: ${parte.obra}")
                Text("Turno: ${parte.turno}")
                Text("Tipo de jornada: ${parte.tipoJornada}")
                Text("Dieta: ${parte.dieta}")
                Text("Descripción: ${parte.trabajosRealizados}")
                Text("Horas trabajadas: ${parte.horasTrabajadas}")
                Text("Horas extras: ${parte.horasExtras}")
                Text("Observaciones: ${parte.observaciones}")

                Spacer(modifier = Modifier.height(12.dp))
                Text("Empleados:", fontWeight = FontWeight.Bold)
                parte.empleados.forEach {
                    Text("- ${it["nombre"]} ${it["apellidos"]} (${it["categoria"]})")
                }

                if (parte.maquinas.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Máquinas:", fontWeight = FontWeight.Bold)
                    parte.maquinas.forEach {
                        Text("- ${it["matricula"]} (${it["modelo"]})")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Icono eliminar
                    IconButton(
                        onClick = {
                            eliminarOK = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.Red
                        )
                    }

                    // Icono archivar
                    IconButton(
                        onClick = {
                            archivarOK = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.file_move),
                            contentDescription = "Archivar",
                            tint = Color.Blue
                        )
                    }

                    // Icono editar
                    IconButton(
                        onClick = {
                            vistaModelo.modificarParte(parte)
                            navController.navigate("PantallaParte")
                            onDismiss()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Editar",
                            tint = Color(0xFFFFA000)
                        )
                    }

                    IconButton(
                        onClick = {
                            ExportadorCSV.exportarPartesCSV(context, listOf(parte))
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_export),
                            contentDescription = "Exportar",
                            tint = Color(0xFF4CAF50)
                        )
                    }

                }
            }
        }
    }

    // La confirmación de eliminar
    if (eliminarOK) {
        ConfirmDialog(
            title = "Eliminar parte",
            message = "¿Seguro que quieres eliminar?",
            onConfirm = {
                vistaModelo.eliminarParte(
                    parte,
                    onSuccess = {
                        eliminarOK = false
                        onDismiss()
                    },
                    onError = { }
                )
            },
            onDismiss = { eliminarOK = false }
        )
    }

// Confirmación de archivar
    if (archivarOK) {
        ConfirmDialog(
            title = "Archivar parte",
            message = "¿Archivar este parte? Desaparecerá de la lista.",
            onConfirm = {
                vistaModelo.archivarParte(
                    parte,
                    onSuccess = {
                        archivarOK = false
                        onDismiss()
                    },
                    onError = { }
                )
            },
            onDismiss = { archivarOK = false }
        )
    }
}