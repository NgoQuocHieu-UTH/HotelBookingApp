package com.example.myapplication.view


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.border

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
fun Profile(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        // Back icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "Back Icon",
                modifier = Modifier
                    .size(25.dp)
                    .background(Color.White)
                    .clickable {
                        navController.navigate("Home") {
                            popUpTo("Profile") { inclusive = true }
                        }
                    }

            )
        }

        // Profile banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF4FC0FF))
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.avatar),
                        contentDescription = "Avatar Image",
                        modifier = Modifier.size(68.dp)
                    )
                }

                // Text bar
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF1F9CE2))
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Chạm để thay đổi", color = Color.Black)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tên",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(220.dp))
            Text(
                text = "Nguyễn Văn A",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = "Arrow",
                modifier = Modifier.size(24.dp)
            )
        }
        Divider(
            color = Color(0xFFC2C2C2),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Giới tính",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(250.dp))
            Text(
                text = "Nam",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = "Arrow",
                modifier = Modifier.size(24.dp)
            )
        }
        Divider(
            color = Color(0xFFC2C2C2),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ngày Sinh",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(185.dp))
            Text(
                text = "22/12/2212",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = "Arrow",
                modifier = Modifier.size(24.dp)
            )
        }
        Divider(
            color = Color(0xFFC2C2C2),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CCCD/Pastpost",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(115.dp))
            Text(
                text = "0000 0000 0000",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = "Arrow",
                modifier = Modifier.size(24.dp)
            )
        }
        Divider(
            color = Color(0xFFC2C2C2),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // hoặc .width(200.dp) nếu muốn ngắn hơn
                .padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SDT",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(205.dp))
            Text(
                text = "0XXX XXX XXX",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = "Arrow",
                modifier = Modifier.size(24.dp)
            )
        }
        Divider(
            color = Color(0xFFC2C2C2),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // hoặc .width(200.dp) nếu muốn ngắn hơn
                .padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tài Khoản Liên Kết",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(210.dp))
            Text(
                text = "",
                color = Color(0xFF000000),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = "Arrow",
                modifier = Modifier.size(24.dp)
            )
        }
        Divider(
            color = Color(0xFFC2C2C2),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // hoặc .width(200.dp) nếu muốn ngắn hơn
                .padding(vertical = 8.dp)
        )

    }
}

