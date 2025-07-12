package com.example.myapplication.view

import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import android.content.Context
import androidx.compose.ui.platform.LocalContext
@Composable
fun Welcome_Pages(navController: NavController) {

    // Tự động chuyển trang sau 1 giây
    val context = LocalContext.current

    // Tự động chuyển trang sau 1 giây
    LaunchedEffect(Unit) {
        delay(1000)

        val token = getTokenFromPreferences(context)
        if (token.isNullOrBlank()) {
            navController.navigate("Login") {
                popUpTo("welcome_pages") { inclusive = true }
            }
        } else {
            navController.navigate("Home") {
                popUpTo("welcome_pages") { inclusive = true }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4FC0FF)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(225.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_logo_image),
                contentDescription = "Welcome Image",
                modifier = Modifier.size(225.dp)
            )
        }
    }
}

fun getTokenFromPreferences(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    return sharedPreferences.getString("user_token", null)
}
