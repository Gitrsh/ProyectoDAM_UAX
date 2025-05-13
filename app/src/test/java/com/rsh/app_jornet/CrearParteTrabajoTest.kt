package com.rsh.app_jornet.tests.model

import ParteTrabajo
import org.junit.Assert.assertNotNull
import org.junit.Test

class CrearParteTrabajoTest {

    @Test
    fun test_crearParteTrabajo() {
        val parte = ParteTrabajo(
            fecha = "2025-05-08",
            obra = "Obra Test",
            trabajosRealizados = "Movimiento de tierras",
            turno = "Mañana",
            tipoJornada = "Laboral",
            observaciones = "Sin incidencias",
            empleados = listOf(
                mapOf("id" to "1", "nombre" to "Carlos", "apellidos" to "Gómez", "categoria" to "Peón")
            ),
            horasTrabajadas = 8,
            dieta = "Completa"
        )

        assertNotNull(parte)

    }
}