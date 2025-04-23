package com.rsh.app_jornet.data.repositorio

import com.google.firebase.firestore.FirebaseFirestore
/*
Creamos un objeto singleton para generar una instancia única/reutilizable de Firestor para interactuar con la BD
Punto de acceso centralizado a la BD
 */
object FirestoreService {
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() } //'lazy' hace que se inicia la primera vez q se acceda a ella y no al inicializar la app
}