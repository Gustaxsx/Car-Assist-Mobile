package com.example.car_assist_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.car_assist_mobile.screens.adicionarcarro.AddCarScreen
import com.example.car_assist_mobile.screens.cadastro.RegisterScreen
import com.example.car_assist_mobile.screens.cadastrodecarro.RegisterCarScreen
import com.example.car_assist_mobile.screens.garagem.GarageScreen
import com.example.car_assist_mobile.screens.login.LoginScreen
import com.example.car_assist_mobile.screens.perfil.EditProfileScreen
import com.example.car_assist_mobile.screens.service.ServicesScreen
import com.example.car_assist_mobile.ui.theme.Car_Assist_MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Car_Assist_MobileTheme {

                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "service",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(
                            route = "login"
                        ) {
                            LoginScreen(navController)
                        }
                        composable(
                            route = "register"
                        ) {
                            RegisterScreen(navController)
                        }
                        composable(
                            route = "profile"
                        ) {
                            EditProfileScreen(navController)
                        }
                        composable(
                            route = "garage"
                        ) {
                            GarageScreen(navController)
                        }
                        composable(
                            route = "service"
                        ) {
                            ServicesScreen(navController)
                        }
                        composable(
                            route = "AddCar"
                        ) {
                            AddCarScreen(navController)
                        }
                        composable(
                            route = "RegisterCar"
                        ) {
                            RegisterCarScreen(navController)
                        }
                    }
                }
            }
        }
    }
}