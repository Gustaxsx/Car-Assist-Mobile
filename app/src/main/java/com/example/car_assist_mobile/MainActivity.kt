package com.example.car_assist_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.car_assist_mobile.screens.adicionarcarro.AddCarScreen
import com.example.car_assist_mobile.screens.adicionarlembrete.AddLembreteScreen
import com.example.car_assist_mobile.screens.adicionarmanutencao.AddManutencaoScreen
import com.example.car_assist_mobile.screens.cadastro.RegisterScreen
import com.example.car_assist_mobile.screens.cadastrodecarro.RegisterCarScreen
import com.example.car_assist_mobile.screens.carrodetalhes.DetailsCarScreen
import com.example.car_assist_mobile.screens.chatbot.ChatBotScreen
import com.example.car_assist_mobile.screens.editarcarro.EditCarScreen
import com.example.car_assist_mobile.screens.garagem.GaragemScreen
import com.example.car_assist_mobile.screens.gastos.GastosScreen
import com.example.car_assist_mobile.screens.guincho.GuinchoScreen
import com.example.car_assist_mobile.screens.lavarapido.LavaRapidoScreen
import com.example.car_assist_mobile.screens.lembrete.LembreteScreen
import com.example.car_assist_mobile.screens.login.LoginScreen
import com.example.car_assist_mobile.screens.manutencao.ManutencaoScreen
import com.example.car_assist_mobile.screens.oficina.OficinaScreen
import com.example.car_assist_mobile.screens.perfil.EditProfileScreen
import com.example.car_assist_mobile.screens.posto.PostoScreen
import com.example.car_assist_mobile.screens.service.ServicesScreen
import com.example.car_assist_mobile.screens.transferencia.TransferenciaScreen
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
                        startDestination = "Transferencia",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = "login") {
                            LoginScreen(navController)
                        }
                        composable(route = "register") {
                            RegisterScreen(navController)
                        }
                        composable(route = "cadastro") {
                            RegisterScreen(navController)
                        }
                        composable(route = "profile") {
                            EditProfileScreen(navController)
                        }

                        composable(
                            route = "garagem/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            GaragemScreen(navController = navController, idUsuarioLogado = idUsuario)
                        }

                        composable(route = "service") {
                            ServicesScreen(navController)
                        }

                        composable(
                            route = "AddCar/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            AddCarScreen(navController = navController, idUsuarioLogado = idUsuario)
                        }

                        composable(
                            route = "RegisterCar/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            RegisterCarScreen(navController = navController, idUsuarioLogado = idUsuario)
                        }

                        composable(route = "DetailsCar") {
                            DetailsCarScreen(navController)
                        }
                        composable(route = "ChatBot") {
                            ChatBotScreen(navController)
                        }
                        composable(route = "Lembrete") {
                            LembreteScreen(navController)
                        }
                        composable(route = "AddLembrete") {
                            AddLembreteScreen(navController)
                        }
                        composable(route = "Gastos") {
                            GastosScreen(navController)
                        }
                        composable(route = "Manutencao") {
                            ManutencaoScreen(navController)
                        }
                        composable(route = "AddManutencao") {
                            AddManutencaoScreen(navController)
                        }
                        composable(route = "EditCar") {
                            EditCarScreen(navController)
                        }
                        composable(route = "Posto") {
                            PostoScreen(navController)
                        }
                        composable(route = "Oficina") {
                            OficinaScreen(navController)
                        }
                        composable(route = "LavaRapido") {
                            LavaRapidoScreen(navController)
                        }
                        composable(route = "Guincho") {
                            GuinchoScreen(navController)
                        }

                        composable(
                            route = "Transferencia",
                            arguments = listOf(navArgument("idVeiculo") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idVeiculo = backStackEntry.arguments?.getInt("idVeiculo") ?: 0
                            TransferenciaScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onTransferirClick = { email, senha, permissao ->
                                    // Chame a sua ViewModel ou lógica de API aqui passando o idVeiculo
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}