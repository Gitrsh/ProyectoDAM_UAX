package com.rsh.app_jornet

import ParteTrabajo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ArchivarParteTest {

    private lateinit var parteParaArchivar: ParteTrabajo
    private lateinit var listaActiva: MutableList<ParteTrabajo>
    private lateinit var listaArchivada: MutableList<ParteTrabajo>

    @Before
    fun setUp() {
        parteParaArchivar = ParteTrabajo(
            fecha = "2025-05-01",
            obra = "Obra Prueba",
            maquinas = emptyList(),
            trabajosRealizados = "Trabajos prueba",
            turno = "Mañana",
            tipoJornada = "Laboral",
            observaciones = "",
            empleados = listOf(mapOf("nombre" to "Juan", "apellidos" to "Pérez", "categoria" to "Jefe de máquina")),
            horasTrabajadas = 8,
            dieta = "Completa"
        )
        //Iniciamos dos listas. Una para partes activos en el dispositivo y la otra para los archivados
        listaActiva = mutableListOf(parteParaArchivar)
        listaArchivada = mutableListOf()
    }

    @Test
    fun test_archivarParte() {
        assertTrue(listaActiva.contains(parteParaArchivar))//Verificamos que el parte esta en la lista del dispositivo
        assertFalse(listaArchivada.contains(parteParaArchivar))//Verificamos que no está archivado

        listaActiva.remove(parteParaArchivar)//Simulamos que lo eliminamos de la losta del dispositivo
        listaArchivada.add(parteParaArchivar)//Simulamos que lo hemos archivado

        assertFalse(listaActiva.contains(parteParaArchivar))
        assertTrue(listaArchivada.contains(parteParaArchivar))
    }
}