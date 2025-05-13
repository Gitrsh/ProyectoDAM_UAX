package com.rsh.app_jornet

import ParteTrabajo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EliminarParteTest {

    private lateinit var listaPartes: MutableList<ParteTrabajo>
    private lateinit var parteParaEliminar: ParteTrabajo

    @Before
    fun setUp() {//Creamos una lista de partes para el test
        parteParaEliminar = ParteTrabajo(
            fecha = "2025-05-01",
            obra = "Obra Prueba",
            maquinas = emptyList(),
            trabajosRealizados = "Trabajos de prueba",
            turno = "Mañana",
            tipoJornada = "Laboral",
            observaciones = "",
            empleados = listOf(mapOf("nombre" to "Juan", "apellidos" to "Pérez", "categoria" to "Oficial")),
            horasTrabajadas = 8,
            dieta = "Completa"
        )
        listaPartes = mutableListOf(parteParaEliminar)
    }

    @Test
    fun test_eliminarParte() {
        assertTrue(listaPartes.contains(parteParaEliminar))//Comprobamos que el parte existe

        listaPartes.remove(parteParaEliminar)//Eliminamos el parte

        assertFalse(listaPartes.contains(parteParaEliminar))//Comprobamos que se eliminó
    }
}