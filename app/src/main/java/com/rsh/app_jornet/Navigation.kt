package com.rsh.app_jornet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rsh.app_jornet.ui.*
import com.rsh.app_jornet.ui.screens.HomeScreen
import com.rsh.app_jornet.ui.screens.LoginScreen
import com.rsh.app_jornet.ui.screens.ParteScreen
import com.rsh.app_jornet.ui.screens.SignScreen

/*
Esta pantalla es el centro de navegación. Define las rutas con la pantalla correspondiente y pasamos las dependencias
como auth y vistaModelo a las pantallas que lo necesiten
 */

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Navigation(navHostController: NavHostController, vistaModelo: VistaModelo) {

    NavHost(navController = navHostController, startDestination = "Login") {
        composable("Login") { LoginScreen(navHostController) }
        composable("Sign") { SignScreen(navHostController) }
        composable("Home") { HomeScreen(navHostController) }
        composable("PantallaParte") { ParteScreen(navHostController, vistaModelo) }
    }
}