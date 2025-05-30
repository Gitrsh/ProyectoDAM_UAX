
# APP Gestión de partes de trabajo
Aplicación diseñada para la creación, gestión, consulta y descarga de partes diarios de trabajo.

## Descripción del proyecto. 
Esta aplicación fácilita a los trabajadores a crear de manera fácil y rápida partes que documenten digitalmente su día a día laboral para su posterior consulta o descarga. Pensada ayudar a las empresas a mejorar en cuanto a eficiencia a la hora de gestionar u organizar otras rutinas con la información obtenida de ellos como puede ser gestión de personal, maquinaria, elaboración de nóminas o cualquier tipo de consulta derivada, y poder hacerlo de una manera rápida y sencilla. 

## Principales funcionalidades.
+ Inicio de sesión: El usuario podrá iniciar sesión con Firebase Authentication.

+ Pantalla de Registro: El usuario podrá registrarse en el sistema con un correo y contraseña adecuados y gestionados por Firebase Authentication. 

+ Visualización de partes: El usuario podrá ver una lista de los partes creados por él.

+ Creación de Partes de Trabajo: El usuario debe poder crear un parte con rellenando los campos necesarios.

+ Consultas de Partes: El usuario tiene la opción de ver en detalle cada parte una vez creados.
       
+ Eliminar Partes: El usuario puede eliminar los partes creados de la lista y la base de datos
    
+ Archivar Partes: El usuario tiene la opción de archivar los partes, lo que borrará los partes de la lista el dispositivo, sin eliminarlos de la base de datos.

+ Exportación de Datos: Existe la opción de exportar los partes a formato CSV en la carpeta de descargas del dispositivo.

## Tecnologías.
**Kotlin** 
Como lenguaje dde desarrollo.

**Jetpack Compose** 
Framework moderno de programación declarativa. Ayuda a reducir código y a una mejor legibilidad.

**FireBase** 
Como servicio backend.    
+ **Firebase Authentication:** Para gestionar el inicio de sesión o el registro de usuarios.
+ **Firestore:** Base de datos en la nube para guardar partes, maquinas y empleados.

## Requisitos.
+ AndroidStudio instalado.
+ Kotlin **2.0.0**
+ Gradle Plugin: **8.5.1**
+ Cuenta activa de Firebase.
+ SDK mínimo: **24**
+ Target SDK: **35**
+ Archivo google-services.json descargado desde Firebase Console y ubicado en /app/src/google-services.json

##### Algunas dependencias claves: 
+ Firebase BOm 33.1.1
+ Firebase Auth
+ Firestore
+ Navigation Compose

## Instalación.
1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Conecta con firebase     
   +  Crea un proyecto en Firebase Console.
   +  Registra la app Android con el mismo applicationid (com.rsh.app_jornet)
   +  Descarga google-services.json desde Firebase Console.
   +  Colocarlo en: /app/google-services.json
4. Habilita los servicios Firebase Authentication (correo y contraseña) y Firestore
5. Correr en el emulador o un dispositivo conectado.