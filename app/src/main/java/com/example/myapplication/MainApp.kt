package com.example.myapplication

import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.myapplication.view.Welcome_Pages
import com.example.myapplication.view.Login
import com.example.myapplication.view.Home
import com.example.myapplication.view.Search
import com.example.myapplication.view.Profile

import com.example.myapplication.Routes
import com.example.myapplication.view.Chat
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.view.Register

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME_PAGES
    ) {
        composable(Routes.WELCOME_PAGES) {
            Welcome_Pages(navController)
        }

        composable(Routes.LOGIN) {
            Login(navController)
        }
        composable(Routes.HOME) {
            Home(navController)
        }
        composable(Routes.REGISTER) {
            Register(navController)
        }
        composable(Routes.SEARCH) {
            Search(navController)
        }
        composable(Routes.PROFILE) {
            Profile(navController)
        }
        composable(Routes.CHAT) {
            Chat(navController)
        }
    }
}
