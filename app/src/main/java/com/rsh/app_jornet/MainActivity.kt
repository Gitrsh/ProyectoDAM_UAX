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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rsh.app_jornet.ui.VistaModelo
import com.rsh.app_jornet.ui.theme.App_JornetTheme

/*Con esta clase lanzamos la UI. Es el punto de entrada
inicializa la navegacion y el estado global

*/
@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val vistaModelo: VistaModelo = viewModel()
            Navigation(navController, vistaModelo)
        }
    }
}