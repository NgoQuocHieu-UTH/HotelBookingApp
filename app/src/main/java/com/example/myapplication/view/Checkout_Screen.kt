package com.example.myapplication.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun Checkout_Screen(navController: NavController, roomPrice: Double = 2200598.0) {

    var fullName by remember { mutableStateOf("") }
    var numRoom by remember { mutableStateOf("") }
    var numGuest by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var promoCode by remember { mutableStateOf("") }

    val discount = if (promoCode.equals("GIAM10", ignoreCase = true)) 0.1 else 0.0
    val totalPrice = roomPrice * (1 - discount)

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(visible = visible, enter = fadeIn()) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Ảnh nền toàn màn hình
            Image(
                painter = painterResource(id = R.drawable.phong_lon),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Nội dung form đặt phòng
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Spacer(Modifier.height(16.dp))

                Icon(
                    painter = painterResource(id = R.drawable.welcome_logo_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterHorizontally),
                    tint = Color(0xFF1975DC)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Thông tin khách nhận phòng",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(16.dp))

                Text("Họ và tên", fontWeight = FontWeight.Medium)
                TextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                Text("Số lượng phòng", fontWeight = FontWeight.Medium)
                TextField(
                    value = numRoom,
                    onValueChange = { numRoom = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                Text("Số lượng khách", fontWeight = FontWeight.Medium)
                TextField(
                    value = numGuest,
                    onValueChange = { numGuest = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                Text("Số điện thoại", fontWeight = FontWeight.Medium)
                TextField(
                    value = phone,
                    onValueChange = { phone = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                Text("Email", fontWeight = FontWeight.Medium)
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )

                Spacer(Modifier.height(8.dp))

                Text("Mã khuyến mãi", fontWeight = FontWeight.Medium)
                TextField(
                    value = promoCode,
                    onValueChange = { promoCode = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    "Tổng tiền: ${"%,.0f".format(totalPrice)} VND",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                if (discount > 0) {
                    Text("Đã áp dụng mã khuyến mãi -${(discount * 100).toInt()}%", color = Color.Green)
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1975DC)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Xác nhận đặt phòng", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
