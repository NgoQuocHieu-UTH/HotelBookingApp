package com.example.myapplication.view


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.remember

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myapplication.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font

import androidx.compose.material3.Divider

@Composable
fun Chat(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Nội dung chính của màn hình ở đây
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp) // Để không bị che bởi menu
        ) {
            Column(){
                Text(
                    text = "Nhân viên CSKH",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007EF2),
                    fontSize = 21.sp,
                     modifier = Modifier
                         .padding(top = 50.dp , start = 30.dp)
                )
                Row(){
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(top = 24.dp , start = 20.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.welcome_logo_image),
                            contentDescription = "Welcome Image",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    // Tin nhắn bên trái
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp, bottomStart = 12.dp))
                            .background(Color(0xFFE0F7FA))
                            .padding(12.dp)
                    ) {
                        Text(text = "Xin chào!", color = Color.Black)
                    }

// Tin nhắn bên phải
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(topStart = 12.dp, bottomEnd = 12.dp, bottomStart = 12.dp))
                            .background(Color(0xFFB3E5FC))
                            //.align(Alignment.End)
                            .padding(12.dp)
                    ) {
                        Text(text = "Chào bạn!", color = Color.Black)
                    }



                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .background(Color(0xFFF5F5F5)),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_icon),
                    contentDescription = "home icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Trang chủ",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7F7F7F),
                    fontSize = 12.sp
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f).padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = "search icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Tìm kiếm",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7F7F7F),
                    fontSize = 12.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f).padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.booking_icon),
                    contentDescription = "booking icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Booking",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7F7F7F),
                    fontSize = 12.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f).padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chat_icon),
                    contentDescription = "chat icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Liên hệ",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007EF2),
                    fontSize = 12.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f).padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_icon),
                    contentDescription = "chat icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Profile",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7F7F7F),
                    fontSize = 12.sp
                )
            }
        }
    }

}

