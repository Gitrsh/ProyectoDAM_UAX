package com.rsh.app_jornet.ui

import ParteTrabajo
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.rsh.app_jornet.auth.MiAutenticador
import com.rsh.app_jornet.data.RepositorioPartes
import com.rsh.app_jornet.data.model.SeleccionItem

//MIRAR CORRUTINAS PARA MAS ADELANTE

//Esta clase conecta la logica de negocio con la ui es la encargada de intereactuar con Firebase
// (usa la clase RepositorioPartes como intermediario )
class VistaModelo : ViewModel() {

    val empleados = mutableStateListOf<SeleccionItem>()
    val maquinas = mutableStateListOf<SeleccionItem>()
    val listaPartes = mutableStateListOf<ParteTrabajo>()
    val uid: String //Obtenemos un identificador unico del usuario logado
        get() = MiAutenticador.auth.currentUser?.uid ?: ""  //con el get nos aseguramos que se actualice el usuario siempre que cambie


    //Usamos la clase RepositorioPartes la usamos como intermediaria para comunicarnos con fireBase
    private val repositorio = RepositorioPartes()

    init {// Con esto nos aseguramos tener los empleados y maquinas directamente disponibles al instanciar VistaModelo
        loadData { error ->
            Log.e("VistaModelo", error)
        }
    }

    fun loadData(onError: (String) -> Unit) { //Carga los datos de empleados y maquinas
        repositorio.cargarEmpleados(
            onSuccess = {
                empleados.clear()
                empleados.addAll(it)
            },
            onFailure = { exception ->
                onError(exception.message ?: "Error al cargar empleados")
            }
        )

        repositorio.cargarMaquinas(
            onSuccess = {
                maquinas.clear()
                maquinas.addAll(it)
            },
            onFailure = { exception ->
                onError(exception.message ?: "Error al cargar máquinas")
            }
        )
    }

    fun generarParte(parte: ParteTrabajo, onError: (String) -> Unit) {
        val parteConUid = parte.copy(uid = uid) //Creamos una copia del objeto Parte y le añadimos el campo nuevo uid

        repositorio.saveParteFB(
            parte = parteConUid,
            onSuccess = { parteGuardado ->
                listaPartes.add(parteGuardado)
            },
            onFailure = { exception ->
                onError(exception.message ?: "Error al guardar parte")
            }
        )
    }

    fun cargarPartesDelUsuario(onError: (String) -> Unit) {
        repositorio.loadPartesFB(
            uid = uid,
            onSuccess = { partes ->
                listaPartes.clear()
                listaPartes.addAll(partes)
            },
            onFailure = { exception ->
                onError(exception.message ?: "Error al cargar partes del usuario")
            }
        )
    }

    fun eliminarParte(parte: ParteTrabajo, onSuccess: () -> Unit, onError: (String) -> Unit) {
        repositorio.deleteParteFB(
            parteId = parte.id,
            onSuccess = {
                listaPartes.remove(parte)
                onSuccess()
            },
            onFailure = { exception ->
                onError(exception.message ?: "Error al eliminar parte")
            }
        )
    }

    fun archivarParte(parte: ParteTrabajo, onSuccess: () -> Unit, onError: (String) -> Unit) {
        repositorio.archivarParteFB(
            parteId = parte.id,
            onSuccess = {
                listaPartes.remove(parte)
                onSuccess()
            },
            onFailure = { exception ->
                onError(exception.message ?: "Error al archivar parte")
            }
        )
    }
}