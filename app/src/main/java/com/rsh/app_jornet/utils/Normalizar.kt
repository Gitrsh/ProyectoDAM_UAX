package com.rsh.app_jornet.utils

import java.text.Normalizer

//Clase para normalizar un texto(Quitar caracteres especiales)
fun String.normalizar(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "")//Busca y elimina tildes, dieresis...
        .lowercase()
}