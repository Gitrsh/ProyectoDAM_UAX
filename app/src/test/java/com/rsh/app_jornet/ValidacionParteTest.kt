package com.rsh.app_jornet

import com.rsh.app_jornet.data.model.SeleccionItem
import com.rsh.app_jornet.util.ValidacionParte.validarCamposParteTrabajo
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidacionParteTest {

    private val empleadoEjemplo = listOf(
        SeleccionItem("1", "Juan", "Pérez", "Oficial", true)
    )

    @Test
    fun campoFechaVacio() {
        val resultado = validarCamposParteTrabajo(
            fecha = "Seleccionar Fecha", // este es el valor 'vacio' del campo fecha
            obra = "Obra",
            descripcionTrabajo = "Trabajo",
            horasTrabajadas = "8",
            turno = "Mañana",
            tipoJornada = "Laboral",
            dieta = "Completa",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals("Debe seleccionar una fecha.", resultado)
    }

    @Test
    fun campoObraVacio() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "",
            descripcionTrabajo = "Trabajo",
            horasTrabajadas = "8",
            turno = "Mañana",
            tipoJornada = "Laboral",
            dieta = "Completa",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals("El campo 'obra' no puede estar vacío.", resultado)
    }

    @Test
    fun campoDescripcionVacio() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "Obra",
            descripcionTrabajo = "",
            horasTrabajadas = "8",
            turno = "Mañana",
            tipoJornada = "Laboral",
            dieta = "Completa",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals("Debe describir el trabajo realizado.", resultado)
    }

    @Test
    fun campoHorasVacio() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "Obra",
            descripcionTrabajo = "Trabajo",
            horasTrabajadas = "",
            turno = "Mañana",
            tipoJornada = "Laboral",
            dieta = "Completa",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals("Debe indicar las horas trabajadas.", resultado)
    }

    @Test
    fun campoTurnoVacio() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "Obra",
            descripcionTrabajo = "Trabajo",
            horasTrabajadas = "8",
            turno = "",
            tipoJornada = "Laboral",
            dieta = "Completa",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals("Debe seleccionar el turno.", resultado)
    }

    @Test
    fun campoTipoJornadaVacio() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "Obra",
            descripcionTrabajo = "Trabajo",
            horasTrabajadas = "8",
            turno = "Mañana",
            tipoJornada = "",
            dieta = "Completa",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals("Debe seleccionar el tipo de jornada.", resultado)
    }


    @Test
    fun tipoJornadaNoSeleccionado() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "Obra",
            descripcionTrabajo = "Trabajo",
            horasTrabajadas = "8",
            turno = "Mañana",
            tipoJornada = "", // No seleccionado
            dieta = "Completa",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals("Debe seleccionar el tipo de jornada.", resultado)
    }

    @Test
    fun campoDietaVacio() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "Obra",
            descripcionTrabajo = "Trabajo",
            horasTrabajadas = "8",
            turno = "Mañana",
            tipoJornada = "Laboral",
            dieta = "",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals("Debe seleccionar la dieta.", resultado)
    }

    @Test
    fun sinEmpleadosSeleccionados() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "Obra",
            descripcionTrabajo = "Trabajo",
            horasTrabajadas = "8",
            turno = "Mañana",
            tipoJornada = "Laboral",
            dieta = "Completa",
            empleadosSeleccionados = emptyList()
        )
        assertEquals("Debe seleccionar al menos un empleado.", resultado)
    }

    @Test
    fun todosLosCamposOK() {
        val resultado = validarCamposParteTrabajo(
            fecha = "01/05/2025",
            obra = "Obra 1",
            descripcionTrabajo = "Reparaciones",
            horasTrabajadas = "8",
            turno = "Mañana",
            tipoJornada = "Laboral",
            dieta = "Completa",
            empleadosSeleccionados = empleadoEjemplo
        )
        assertEquals(null, resultado)
    }
}