package com.rsh.app_jornet.data.model

/*
Es un modelo de datos auxiliar  de USO ESPECIFICO EN LA INTERFAZ que nos ayudará a construir dialogos y formularios con seleccion. Lo podemos usar para maquinas y
 empleados (que son modelos de datos puros) podemos modificarlo a necesidad sin tocar las clases originales.
 le añadimos el campo 'seleccionado' que nos permite marcar elementos(Empleado, Maquina) que fueron seleccionaods por el usuario para mostrar en el dialogo.
 */
data class SeleccionItem(
    val campo1: String,   // ID único (Matrícula de máquina o ID del empleado)
    val campo2: String, // Nombre empleado - marca máquina
    val campo3: String, //apellidos empleado - tipo màquina
    val campo4: String, //categoria empleado - modelo maquina
    var seleccionado: Boolean = false // Para indicar si está seleccionado
)