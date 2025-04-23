package com.rsh.app_jornet

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.rsh.app_jornet.ui.VistaModelo
import com.rsh.app_jornet.ui.theme.App_JornetTheme

/*Con esta clase lanzamos la UI. Es el punto de entrada
Controlamos la sesión del usuario en Firebase

*/
@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : ComponentActivity() {

    private val vistaModelo: VistaModelo by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberNavController()//Inicializamos el navController

            App_JornetTheme {//Con esto aplicamos el tema visual predefinido. VER PARA MODIFICAR MAS ADELANTE
                Surface(modifier = Modifier.fillMaxSize()) {
                    Navigation(navHostController, vistaModelo)
                }
            }
        }
    }
}