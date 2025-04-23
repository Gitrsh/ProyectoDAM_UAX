package com.rsh.app_jornet.auth

import com.google.firebase.auth.FirebaseAuth
//Esta clase es un objeto singleton encargada de gestionar la autenticacion de usuarios a través de firebase
object MiAutenticador {
    //Cada vez que usamos auth, get hace que se ejecute el codigo y se actualice de manera dinámica
    val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    //Funcion para iniciar sesion con mail y contraseña
    fun accesoUsuario(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)//función de Firebase
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    //Para registrar un nuevo usuario
    fun registroUsuario(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)//funcion de Firebase
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }
}