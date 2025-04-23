package com.rsh.app_jornet.utils

import ParteTrabajo
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*Creamos un objeto Kotlin y definimos una función para exportar partes de trabajo a un archivo csv
*  que guardaremos en la carpeta de descargas del dispositivo
*/
object ExportadorCSV {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun exportarPartesCSV(context: Context, partes: List<ParteTrabajo>) {
        if (partes.isEmpty()) {
            mostrarToast(context, false, "No hay partes para exportar.")
            return
        }

        val fechaHora = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())//Creamo un formato string con fecha y hora para el ID del CSV
        val idCSV = fechaHora.format(Date())//Y aplicamos ese formato a la fecha y hora actual para obtener un ID unico para cada CSV
        val nombreCSV = "partes_$idCSV.csv" //Se lo pasamos al formato definitivo que dará nombre a cada archivo CSV
        val contenidoCSV = GeneradorCSV.generarContenidoCSV(partes)


        //Este bloque es para poder almacenar en la carpeta downloads de nuestro dispositivo
        val gestorArch = context.contentResolver//Declaramos una variable (gestArch) para crear un "gestor de archivos" que nos da acceso a nuestro sistema de almacenamiento
        val carpetaCSV = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)//Con esto apuntamos a la carpeta de descargas para guardar el CSV

        //Creamos un objto contentValues (un contenedor clave/valor(diccionario)) y dentro del apply le vamos metiendo los datos
        val miClaveValor = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, nombreCSV)//Como se llamará el archivo en la carpeta
            put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")//El tipo de archivo que guardamos
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)//La carpeta donde guardamos los archivos
        }
        //Creamos el arhivo y lo "insertamos" en la carpeta con los metadatos de miDiccionario devolviendonos una URI que apunta a este archivo
        // q nos permitirá acceder, leer o modificarlo en cualquier momento
        val rutaURI = gestorArch.insert(carpetaCSV, miClaveValor)

        if (rutaURI == null) {
            mostrarToast(context, false, "No se pudo crear el archivo CSV.")
            return
        }

        try {
            gestorArch.openOutputStream(rutaURI)?.use { outputStream ->//Abrimos un flujo de ssalida (openOutputStream) para escribir datos (outputStream)
                OutputStreamWriter(outputStream).use { writer ->//OutputStreamWriter convierte un flujo/salida de bytes(outputStream) en flujo/salida en caracteres (writer)
                    writer.write(contenidoCSV) //Escribe las columnas con un salto de linea
                    writer.flush()//Flush asegura que se escriben todos los datos que queden pendientes en memoria
                }//.use a segura de cerrar el flujo    //it cada linea(conjunto de campos) que se escribiran en el archivo
            }

            mostrarToast(context, true, "CSV exportado correctamente en la carpeta Descargas",)

        } catch (e: IOException) {
            mostrarToast(context, false, "Error al exportar CSV: ${e.message}")
            e.printStackTrace()
        }
    }
}
