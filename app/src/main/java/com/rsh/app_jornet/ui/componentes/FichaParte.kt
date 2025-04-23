package com.rsh.app_jornet.ui.componentes

import ParteTrabajo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rsh.app_jornet.R
import com.rsh.app_jornet.utils.ExportadorCSV

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun FichaParte(
    parte: ParteTrabajo,
    onClick: () -> Unit,
    onEliminarClick: () -> Unit,
    onArchivarClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Fecha: ${parte.fecha}", fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEliminarClick() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar parte",
                        tint = Color.Red
                    )
                }

                IconButton(onClick = { onArchivarClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.file_move),
                        contentDescription = "Archivar parte",
                        tint = Color.Blue
                    )
                }

                IconButton(onClick = {
                    ExportadorCSV.exportarPartesCSV(context, listOf(parte))
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_export),
                        contentDescription = "Exportar parte",
                        tint = Color(0xFF4CAF50)
                    )
                }
            }
        }
    }
}