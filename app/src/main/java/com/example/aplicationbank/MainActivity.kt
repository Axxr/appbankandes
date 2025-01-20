package com.example.aplicationbank

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.aplicationbank.ui.theme.AplicationbankTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aplicationbank.presentation.detail.ProductDetailScreen
import com.example.aplicationbank.presentation.home.HomeScreen
import com.example.aplicationbank.presentation.login.LoginScreen
import androidx.compose.foundation.lazy.items


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicationbankTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                onProductSelected = { productId ->
                                    navController.navigate("product_detail/$productId")
                                },
                                onNavigationItemClick = { destination ->
                                    when (destination) {
                                        "Inicio" -> navController.navigate("home") {
                                            popUpTo("home") { inclusive = true }
                                        }
                                        "Operaciones" -> {

                                        }
                                        "Servicios" -> {

                                        }
                                        "Menu" -> {

                                        }
                                    }
                                },
                                onNavigateToLogin = {
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(
                            route = "product_detail/{productId}",
                            arguments = listOf(
                                navArgument("productId") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
                            ProductDetailScreen(productId = productId)
                        }
                    }
                }
            }
        }
    }
}
