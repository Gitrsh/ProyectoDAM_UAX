package com.rsh.app_jornet

import ParteTrabajo
import com.rsh.app_jornet.utils.GeneradorCSV
import org.junit.Assert.assertTrue
import org.junit.Test

class ExportarParteTest {

    @Test
    fun generarCSV_Test() {
        val parteGenerado = listOf(
            ParteTrabajo(
                id = "123",
                fecha = "2025-05-01",
                obra = "Estación Albacete",
                maquinas = listOf(mapOf("matricula" to "15059", "modelo" to "B66-U")),
                trabajosRealizados = "Bateo cambio DS1",
                turno = "Mañana",
                tipoJornada = "Laboral",
                observaciones = "Sin incidencias",
                empleados = listOf(
                    mapOf("id" to "1", "nombre" to "Juan", "apellidos" to "Pérez", "categoria" to "Jefe de máquina")
                ),
                horasTrabajadas = 8,
                horasExtras = 2,
                dieta = "Completa"
            )
        )

        //Llamamos  la funcion que genera el contenido del parte y le pasamos la lista ParteGenerado
        val resultado = GeneradorCSV.generarContenidoCSV(parteGenerado)
        //Comprobamos que coinciden los datos
        assertTrue(resultado.contains("Estación Albacete"))
        assertTrue(resultado.contains("Juan Pérez"))
        assertTrue(resultado.contains("B66-U"))
        // Para confirmar que el CSV tiene al menos una linea que sería el encabezado y otra que sería el parte.
        assertTrue(resultado.lines().size >= 2)
    }
}
