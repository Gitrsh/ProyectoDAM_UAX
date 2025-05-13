package com.rsh.app_jornet.data

import ParteTrabajo
import com.rsh.app_jornet.data.model.Empleado
import com.rsh.app_jornet.data.model.Maquina
import com.rsh.app_jornet.data.model.SeleccionItem
import com.rsh.app_jornet.data.repositorio.FirestoreService

//Creamos un repositorio para que actue como intermediario entre Firestore y el ViewModel
//Encapsulaos la lógica de acceso a los datos para que nuestra clase VistaModelo
// no tenga que preocuparse en como se accede a los datos
class RepositorioPartes() {

    private val db = FirestoreService.db

    //Esta función obtiene los documentos de la colección staff y los convierte en objetos SeleccionItem
    fun cargarEmpleados(onSuccess: (List<SeleccionItem>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("staff").get()//Traemos todos los documentos de la colección staff en FireStore
            .addOnSuccessListener { result ->//Si ok, se ejecuta el bloque. 'result' contiene todos los documentos de la collecion
                //Mapeamos los documentos (jason) de Firebase a objetos
                val lista = result.map { doc -> //Cada doc es un jason en Firebase
                    val emp = doc.toObject(Empleado::class.java)//los convertimos a objetos 'Empleado'
                    SeleccionItem(//y sacamos una lista de campos de los elementos seleccionados
                        campo1 = emp.id ?: "",
                        campo2 = emp.nombre ?: "",
                        campo3 = emp.apellidos ?: "",
                        campo4 = emp.categoria ?: ""
                    )
                }
                onSuccess(lista)//Si fue ok, devolvemos esa lista
            }
            .addOnFailureListener { onFailure(it) }//Si hubo fallo ejecutamos esto //it lo usamos asi para referirnos al unico parametro que llega a la lambda
        //podriamos sustituirla por .addOnFailureListener { exception -> onFailure(exception) }
    }
     //Es igual que la función anterior pero con la colección machinery
    fun cargarMaquinas(onSuccess: (List<SeleccionItem>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("machinery").get()
            .addOnSuccessListener { result ->
                val lista = result.map { doc ->
                    val maq = doc.toObject(Maquina::class.java)
                    SeleccionItem(
                        campo1 = maq.matricula ?: "",
                        campo2 = maq.modelo ?: "",
                        campo3 = maq.tipo ?: "",
                        campo4 = maq.marca ?: ""
                    )
                }
                onSuccess(lista)
            }
            .addOnFailureListener { onFailure(it) }
    }
    //Guarda un nuevo parte en Firestore y le añadimos un ID único
    fun saveParteFB(parte: ParteTrabajo, onSuccess: (ParteTrabajo) -> Unit, onFailure: (Exception) -> Unit) {
        val parteRef = db.collection("partes").document()
        val parteConIdYUid = parte.copy(id = parteRef.id)

        parteRef.set(parteConIdYUid)
            .addOnSuccessListener { onSuccess(parteConIdYUid) }
            .addOnFailureListener { onFailure(it) }
    }
//Carga desde la coleccion 'partes' todos los partes del usuario que no fueron archivados
    fun loadPartesFB(uid: String, onSuccess: (List<ParteTrabajo>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("partes")
            .whereEqualTo("uid", uid)//Para recuperar solo los partes creados por un usuario especifico
            .whereEqualTo("archivado", false)//Recupera solo los partes NO archivados
            //.orderBy("fecha", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val partes = result.map { it.toObject(ParteTrabajo::class.java) }
                onSuccess(partes)
            }
            .addOnFailureListener { onFailure(it) }
    }
    //Para eliminar partes desde su ID
    fun deleteParteFB(parteId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("partes").document(parteId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
    //Con esto actualizamos en Firestore un parte a 'archivado' 
    fun archivarParteFB(parteId: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        db.collection("partes").document(parteId)
            .update("archivado", true)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }
}