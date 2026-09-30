package com.github.lany192.hello

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.lany192.hello.screen.DetailScreen
import com.github.lany192.hello.login.LoginScreen
import com.github.lany192.hello.screen.MainScreen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("detail") {
            DetailScreen()
        }
        composable("login") {
            LoginScreen(navController)
        }
    }
}