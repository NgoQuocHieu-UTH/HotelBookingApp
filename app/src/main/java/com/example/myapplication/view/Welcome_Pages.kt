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


@Composable
fun Welcome_Pages(navController: NavController){
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
                .background(Color.LightGray), // màu nền cho hình tròn
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_logo_image),
                contentDescription = "Welcome Image",
                modifier = Modifier.size(225.dp) // ảnh nhỏ hơn hình tròn một chút
            )
        }
    }
}