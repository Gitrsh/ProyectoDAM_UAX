package com.rsh.app_jornet.ui

import ParteTrabajo
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rsh.app_jornet.auth.MiAutenticador
import com.rsh.app_jornet.data.RepositorioPartes
import com.rsh.app_jornet.data.model.SeleccionItem

//Esta clase conecta la logica con la ui es la encargada de intereactuar con Firebase
// (usa la clase RepositorioPartes como intermediario )
class VistaModelo : ViewModel() {

    val empleados = mutableStateListOf<SeleccionItem>()
    val maquinas = mutableStateListOf<SeleccionItem>()
    val listaPartes = mutableStateListOf<ParteTrabajo>()
    val uid: String //Obtenemos un identificador unico del usuario logado
        get() = MiAutenticador.auth.currentUser?.uid ?: ""  //con el get nos aseguramos que se actualice el usuario siempre que cambie

    // El parte a editar. Nos devuelve null si no se está editando ninguno
    var parteEnEdicion: MutableState<ParteTrabajo?> = mutableStateOf(null)

    fun modificarParte(parte: ParteTrabajo?) {
        parteEnEdicion.value = parte
    }

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

    fun generarParte(parte: ParteTrabajo, onSuccess: () -> Unit = {}, onError: (String) -> Unit) {
        val parteConUid = parte.copy(uid = uid)
        repositorio.saveParteFB(
            parte = parteConUid,
            onSuccess = { parteGuardado ->
                listaPartes.add(parteGuardado)
                onSuccess()
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
            onError = { exception ->
                onError(exception.message ?: "Error al archivar parte")
            }
        )
    }

    //Esta funcion llama a la clase repositorioPArtes para actualizar el parte en firestore
    fun actualizarParte(parte: ParteTrabajo, onSuccess: () -> Unit, onError: (String) -> Unit) {
        repositorio.actualizarParteFB( //Esta es la instancia a repositorioPartes
            parte = parte,//Aquí justo es donde recibe el parte nuevo YA MODIFICADO con la misma id que el parte que se hamodificado
            onSuccess = {
                //Con esta linea buscamos en la lista del dispositivo el indice del parte que coincida con el id
                //Si lo encuentra idxParte sera la posicion del parte en la lista, si no lo encuentra, devuelve -1 (IMPORTANTE siguiente linea)
                val idxParte = listaPartes.indexOfFirst { it.id == parte.id }
                //Si el indice devuelto es distinto a -1 actualiza el parte con el nuevo obj parte modificado. Si no, no hace nada
                if (idxParte != -1) listaPartes[idxParte] = parte
                onSuccess()
            },
            onFailure = { exception ->
                onError(exception.message ?: "Error al actualizar parte")
            }
        )
    }
}
