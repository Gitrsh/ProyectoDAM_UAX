package com.rsh.app_jornet.utils

import ParteTrabajo


object GeneradorCSV {
    fun generarContenidoCSV(partes: List<ParteTrabajo>): String {
        val columnas =//El encabezado de las columnas en el csv
            "ID,Fecha,Obra,Turno,Tipo Jornada,Trabajos Realizados,Observaciones,Horas Trabajadas,Horas Extras,Dieta,Empleados,Maquinas"
        val filas = partes.map { parte ->
            val empleadosStr = parte.empleados.joinToString("; ") {
                "${it["nombre"]} ${it["apellidos"]} (${it["categoria"]})"
            }
            val maquinasStr = parte.maquinas.joinToString("; ") {
                "${it["matricula"]} - ${it["modelo"]}"
            }

            listOf(
                parte.id,
                parte.fecha,
                parte.obra,
                parte.turno,
                parte.tipoJornada,
                wrapCSV(parte.trabajosRealizados),
                wrapCSV(parte.observaciones),
                parte.horasTrabajadas.toString(),
                parte.horasExtras.toString(),
                parte.dieta,
                wrapCSV(empleadosStr),
                wrapCSV(maquinasStr)
            ).joinToString(",")
        }

        return columnas + "\n" + filas.joinToString("\n")
    }

    /*
    Esta funcion es para si un texto tiene comas o comillas dobles no tengamos problemas al generar el
    csv y nos cree otra columna por ejemplo
     */
    private fun wrapCSV(campo: String): String {
        val valor = campo.replace("\"", "\"\"")
        return "\"$valor\""
    }
}