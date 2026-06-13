package com.example.car_assist_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.car_assist_mobile.data.SessionManager
import com.example.car_assist_mobile.screens.adicionarcarro.AcquireCarScreen
import com.example.car_assist_mobile.screens.adicionarcarro.AddCarScreen
import com.example.car_assist_mobile.screens.adicionarmanutencao.AddManutencaoScreen
import com.example.car_assist_mobile.screens.cadastro.RegisterScreen
import com.example.car_assist_mobile.screens.cadastrodecarro.RegisterCarScreen
import com.example.car_assist_mobile.screens.carrodetalhes.DetailsCarScreen
//import com.example.car_assist_mobile.screens.chatbot.ChatBotScreen
import com.example.car_assist_mobile.screens.editarcarro.EditCarScreen
import com.example.car_assist_mobile.screens.garagem.GaragemScreen
import com.example.car_assist_mobile.screens.gastos.AddGastoScreen
import com.example.car_assist_mobile.screens.gastos.GastosScreen
//import com.example.car_assist_mobile.screens.guincho.GuinchoScreen
import com.example.car_assist_mobile.screens.historico.HistoricoDonoScreen
//import com.example.car_assist_mobile.screens.lavarapido.LavaRapidoScreen
import com.example.car_assist_mobile.screens.login.LoginScreen
import com.example.car_assist_mobile.screens.manutencao.ManutencaoScreen
//import com.example.car_assist_mobile.screens.oficina.OficinaScreen
import com.example.car_assist_mobile.screens.perfil.EditProfileScreen
//import com.example.car_assist_mobile.screens.posto.PostoScreen
import com.example.car_assist_mobile.screens.service.ServicesScreen
import com.example.car_assist_mobile.screens.transferencia.TransferenciaScreen
import com.example.car_assist_mobile.screens.transferencia.TransferenciaConfirmarScreen
import com.example.car_assist_mobile.screens.transferencia.TransferenciaCodigoScreen
import com.example.car_assist_mobile.screens.transferencia.TransferenciaScreenViewModel
import com.example.car_assist_mobile.ui.theme.Car_Assist_MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Car_Assist_MobileTheme {

                val navController = rememberNavController()
                val transferenciaViewModel: TransferenciaScreenViewModel = viewModel()

                val context = LocalContext.current
                val sessionManager = remember { SessionManager(context) }
                val idUsuarioLogadoGlobal = sessionManager.getUserId()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable(
                            route = "transferencia/{veiculoId}/{nomeVeiculo}/{placaVeiculo}",
                            arguments = listOf(
                                navArgument("veiculoId") { type = NavType.IntType },
                                navArgument("nomeVeiculo") { type = NavType.StringType },
                                navArgument("placaVeiculo") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val veiculoId = backStackEntry.arguments?.getInt("veiculoId") ?: 0
                            val nome = backStackEntry.arguments?.getString("nomeVeiculo") ?: ""
                            val placa = backStackEntry.arguments?.getString("placaVeiculo") ?: ""

                            LaunchedEffect(veiculoId) {
                                transferenciaViewModel.carregarDadosVeiculo(veiculoId, nome, placa)
                            }

                            TransferenciaScreen(
                                navController = navController,
                                idUsuarioLogado = idUsuarioLogadoGlobal,
                                viewModel = transferenciaViewModel
                            )
                        }

                        composable(route = "transferencia_confirmar") {
                            TransferenciaConfirmarScreen(
                                navController = navController,
                                idUsuarioLogado = idUsuarioLogadoGlobal,
                                viewModel = transferenciaViewModel
                            )
                        }

                        composable(route = "transferencia_codigo") {
                            TransferenciaCodigoScreen(
                                navController = navController,
                                idUsuarioLogado = idUsuarioLogadoGlobal,
                                viewModel = transferenciaViewModel
                            )
                        }

                        composable(route = "login") { LoginScreen(navController) }
                        composable(route = "register") { RegisterScreen(navController) }

                        composable(
                            route = "profile/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            EditProfileScreen(navController, idUsuario)
                        }

                        composable(
                            route = "garagem/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            GaragemScreen(navController, idUsuario)
                        }

                        composable(
                            route = "service/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            ServicesScreen(navController, idUsuario)
                        }

                        composable(
                            route = "AddCar/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            AddCarScreen(navController, idUsuario)
                        }

                        composable(
                            route = "RegisterCar/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            RegisterCarScreen(navController, idUsuario)
                        }

                        composable(
                            route = "DetailsCar/{idUsuario}/{veiculoId}",
                            arguments = listOf(
                                navArgument("idUsuario") { type = NavType.IntType },
                                navArgument("veiculoId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            val veiculoId = backStackEntry.arguments?.getInt("veiculoId") ?: 0

                            DetailsCarScreen(
                                navController = navController,
                                idUsuarioLogado = idUsuario,
                                veiculoId = veiculoId
                            )
                        }

                        composable(
                            route = "OwnerHistory/{idUsuario}/{veiculoId}",
                            arguments = listOf(
                                navArgument("idUsuario") { type = NavType.IntType },
                                navArgument("veiculoId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            val veiculoId = backStackEntry.arguments?.getInt("veiculoId") ?: 0
                            HistoricoDonoScreen(navController, veiculoId, idUsuario)
                        }

//                        composable(route = "ChatBot") { ChatBotScreen(navController) }

                        composable(
                            route = "Gastos/{idUsuario}/{veiculoId}",
                            arguments = listOf(
                                navArgument("idUsuario") { type = NavType.IntType },
                                navArgument("veiculoId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            val veiculoId = backStackEntry.arguments?.getInt("veiculoId") ?: 0
                            GastosScreen(
                                navController = navController,
                                idUsuarioLogado = idUsuario,
                                idVeiculo = veiculoId
                            )
                        }

                        composable(
                            route = "AddGasto/{veiculoId}",
                            arguments = listOf(navArgument("veiculoId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val veiculoId = backStackEntry.arguments?.getInt("veiculoId") ?: 0
                            AddGastoScreen(navController = navController, idVeiculo = veiculoId)
                        }

                        composable(
                            route = "Manutencao/{idUsuario}/{veiculoId}",
                            arguments = listOf(
                                navArgument("idUsuario") { type = NavType.IntType },
                                navArgument("veiculoId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            val veiculoId = backStackEntry.arguments?.getInt("veiculoId") ?: 0
                            ManutencaoScreen(navController, idUsuario, veiculoId)
                        }

                        composable(
                            route = "AddManutencao/{idUsuario}/{veiculoId}",
                            arguments = listOf(
                                navArgument("idUsuario") { type = NavType.IntType },
                                navArgument("veiculoId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            val veiculoId = backStackEntry.arguments?.getInt("veiculoId") ?: 0
                            AddManutencaoScreen(navController, idUsuario, veiculoId)
                        }

                        composable(
                            route = "EditCar/{veiculoId}",
                            arguments = listOf(navArgument("veiculoId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val veiculoId = backStackEntry.arguments?.getInt("veiculoId") ?: 0
                            EditCarScreen(navController, veiculoId, idUsuarioLogadoGlobal)
                        }

//                        composable(route = "Posto") { PostoScreen(navController) }
//                        composable(route = "Oficina") { OficinaScreen(navController) }
//                        composable(route = "LavaRapido") { LavaRapidoScreen(navController) }
//                        composable(route = "Guincho") { GuinchoScreen(navController) }

                        composable(
                            route = "AcquireCar/{idUsuario}",
                            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
                            AcquireCarScreen(navController, idUsuario)
                        }
                    }
                }
            }
        }
    }
}