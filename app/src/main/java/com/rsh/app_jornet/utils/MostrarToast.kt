package com.rsh.app_jornet.utils

import android.content.Context
import android.widget.Toast

fun mostrarToast(context: Context, success: Boolean, message: String?) {
    val texto = if (success) "$message" else "${message ?: "Error"}"
    Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
}