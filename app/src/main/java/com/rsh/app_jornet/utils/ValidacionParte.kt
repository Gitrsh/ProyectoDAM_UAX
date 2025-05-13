package com.rsh.app_jornet.util

import com.rsh.app_jornet.data.model.SeleccionItem

object ValidacionParte {

    fun validarCamposParteTrabajo(
        fecha: String,
        obra: String,
        descripcionTrabajo: String,
        horasTrabajadas: String,
        turno: String,
        tipoJornada: String,
        dieta: String,
        empleadosSeleccionados: List<SeleccionItem>
    ): String? {
        return when {
            fecha == "Seleccionar Fecha" -> "Debe seleccionar una fecha."
            obra.isBlank() -> "El campo 'obra' no puede estar vacío."
            descripcionTrabajo.isBlank() -> "Debe describir el trabajo realizado."
            horasTrabajadas.isBlank() -> "Debe indicar las horas trabajadas."
            turno.isBlank() -> "Debe seleccionar el turno."
            tipoJornada.isBlank() -> "Debe seleccionar el tipo de jornada."
            dieta.isBlank() -> "Debe seleccionar la dieta."
            empleadosSeleccionados.isEmpty() -> "Debe seleccionar al menos un empleado."
            else -> null
        }
    }
}