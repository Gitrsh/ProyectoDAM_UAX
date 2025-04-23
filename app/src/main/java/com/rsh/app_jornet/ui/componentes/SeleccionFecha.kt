package com.rsh.app_jornet.ui.componentes

import android.app.DatePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
//Creamos un boton para seleccionar una fecha con Datepicker
@Composable
fun SeleccionFecha(fecha: String, onFechaSeleccionada: (String) -> Unit) {
    val context = LocalContext.current

    Button(
        onClick = {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            //Lanza el Datepicke al pulsar el boton
            DatePickerDialog(
                context,
                { _, yearSelected, monthSelected, dayOfMonth ->
                    val fechaSeleccionada = String.format("%02d/%02d/%d", dayOfMonth, monthSelected + 1, yearSelected)
                    onFechaSeleccionada(fechaSeleccionada)
                },
                year, month, day
            ).show()
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(fecha, color = Color.Black)
    }
}