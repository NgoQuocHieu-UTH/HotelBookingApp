package com.example.myapplication

import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.myapplication.view.Welcome_Pages

import com.example.myapplication.Routes

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

    }
}
