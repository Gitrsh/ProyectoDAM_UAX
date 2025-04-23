package com.rsh.app_jornet.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rsh.app_jornet.data.model.SeleccionItem
//Nos muestra un dialogo con una lista para seleccionar empleados o maquinas para su selección.
@Composable
fun DialogoSeleccion(
    lista: List<SeleccionItem>,
    onDismiss: () -> Unit,
    onSeleccion: (SeleccionItem) -> Unit
) {
    var busqueda by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Título + Botón "X"
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Seleccionar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de búsqueda
                TextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    label = { Text("Buscar") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Lista filtrada
                val listaFiltrada = lista.filter {
                    it.campo2.contains(busqueda, ignoreCase = true)
                }
                if (listaFiltrada.isEmpty()) {
                    Text("No se encontraron resultados", modifier = Modifier.padding(8.dp))
                }

                LazyColumn(modifier = Modifier.height(200.dp)) {
                    items(listaFiltrada) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSeleccion(item) }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = item.seleccionado,
                                onCheckedChange = { onSeleccion(item) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(item.campo2)
                        }
                    }
                }
            }
        }
    }
}