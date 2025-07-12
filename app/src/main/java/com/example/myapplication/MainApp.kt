package com.example.myapplication

import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.myapplication.view.Welcome_Pages
import com.example.myapplication.view.Login
import com.example.myapplication.view.Home
import com.example.myapplication.view.Search
import com.example.myapplication.view.Profile

import com.example.myapplication.view.Booking_Screen
import com.example.myapplication.view.Checkout_Screen
import com.example.myapplication.view.Listroom
import com.example.myapplication.view.Register
import com.example.myapplication.viewmodel.SearchViewModel
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication.view.BookingSuccess
import com.example.myapplication.view.Bookings

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel: SearchViewModel = viewModel()

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
            Home(navController,viewModel)
        }
        composable(Routes.REGISTER) {
            Register(navController)
        }
        composable(Routes.SEARCH) {
            Search(navController,viewModel)
        }
        composable(Routes.PROFILE) {
            Profile(navController)
        }
        composable (Routes.LISTROOM ){
            Listroom(navController,viewModel)
        }
        composable(
            "${Routes.BOOKING}/{roomJson}",
            arguments = listOf(navArgument("roomJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val roomJson = backStackEntry.arguments?.getString("roomJson") ?: ""
            Booking_Screen(navController, roomJson,viewModel)
        }
        composable (Routes.CHECKOUT){
            Checkout_Screen(navController)
        }
        composable (Routes.BOOKINGS){
            Bookings(navController)
        }
        composable (Routes.BOOKINGSUCCESS){
            BookingSuccess(navController)
        }
    }
}
