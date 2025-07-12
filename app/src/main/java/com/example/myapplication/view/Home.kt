package com.example.myapplication.view


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

import com.example.myapplication.R
import androidx.compose.ui.res.painterResource

import androidx.compose.runtime.*
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Icon

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.material3.SnackbarHostState

import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHost

import androidx.compose.ui.layout.ContentScale



import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.viewmodel.SearchViewModel


@Composable
fun Home(navController: NavController, viewModel: SearchViewModel){
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    Box(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
                .verticalScroll(rememberScrollState()),
            //verticalArrangement = Arrangement.Center
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.Center // canh giữa cả Image và Text
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_image),
                    contentDescription = "Welcome Image",
                    modifier = Modifier
                        .fillMaxSize(), // cho ảnh lấp đầy box
                    contentScale = ContentScale.Crop // tùy chỉnh co dãn ảnh
                )
                Text(
                    text = "Tìm kiếm khách sạn tốt nhất",
                    style = TextStyle(
                        color = Color.White, // màu chữ nổi bật trên ảnh
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Xem những khách sạn tốt nhất của chúng tôi",
                style = TextStyle(
                    color = Color(0xFF007EF2), // màu chữ nổi bật trên ảnh
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .padding(start = 40.dp , end = 16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))
            HotelCard(
                imageRes = R.drawable.home_image, // thay bằng ảnh thực tế
                hotelName = "Khách sạn La Vela Saigon",
                address = "280 Nam Kỳ Khởi Nghĩa, Phường 8, Quận 3, TP.HCM",
                /*originalPrice = "2.400.598 VND",
                discountedPrice = "2.200.598 VND",*/
                /*onClick = {
                    viewModel.updateHotelName("Khách sạn La Vela Saigon")
                    navController.navigate("Listroom") {
                        popUpTo("Home") { inclusive = true }
                    }
                },*/
                onBookingClick = {
                    viewModel.updateHotelName("Khách sạn La Vela Saigon")
                    navController.navigate("listroom") // <-- hoặc "booking/..." tùy bạn định nghĩa
                }
            )
            Text(
                text = viewModel.search.hotelName
            )
            HotelCard(
                imageRes = R.drawable.download_1, // thay bằng ảnh thực tế
                hotelName = "Khách sạn des Arts Saigon",
                address = "6-78  Nguyễn Thị Minh Khai, Phường 6, Quận 3, Thành phố Hồ Chí Minh ",
                /*originalPrice = "2.400.598 VND",
                discountedPrice = "2.200.598 VND",*/
                /*onClick = {
                    viewModel.updateHotelName("Khách sạn La Vela Saigon")
                    navController.navigate("Listroom") {
                        popUpTo("Home") { inclusive = true }
                    }
                },*/
                onBookingClick = {
                    viewModel.updateHotelName("Khách sạn des Arts Saigon")
                    navController.navigate("listroom") // Hoặc "booking/1", tuỳ route bạn định nghĩa
                }
            )
            HotelCard(
                imageRes = R.drawable.images_200, // thay bằng ảnh thực tế
                hotelName = "Khách sạnLiberty Central Saigon Riverside",
                address = "280 Nam Kỳ Khởi Nghĩa, Phường 8, Quận 3, TP.HCM",
                /*originalPrice = "2.400.598 VND",
                discountedPrice = "2.200.598 VND",*/
                /*onClick = {
                    viewModel.updateHotelName("Khách sạnLiberty Central Saigon Riverside")
                    navController.navigate("Listroom") {
                        popUpTo("Home") { inclusive = true }
                    }
                },*/
                onBookingClick = {
                    viewModel.updateHotelName("Khách sạnLiberty Central Saigon Riverside")
                    navController.navigate("listroom") // Hoặc "booking/1", tuỳ route bạn định nghĩa
                }
            )
            HotelCard(
                imageRes = R.drawable.home_image, // thay bằng ảnh thực tế
                hotelName = "Khách sạnLiberty Central Saigon Riverside",
                address = "280 Nam Kỳ Khởi Nghĩa, Phường 8, Quận 3, TP.HCM",
                /*originalPrice = "2.400.598 VND",
                discountedPrice = "2.200.598 VND",*/
                /*onClick = {
                    viewModel.updateHotelName("Khách sạnLiberty Central Saigon Riverside")
                    navController.navigate("Listroom") {
                        popUpTo("Home") { inclusive = true }
                    }
                }*/
                onBookingClick = {
                    viewModel.updateHotelName("Khách sạnLiberty Central Saigon Riverside")
                    navController.navigate("listroom") // Hoặc "booking/1", tuỳ route bạn định nghĩa
                }
            )


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(70.dp)
                .background(Color(0xFFF5F5F5)),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .clickable (
                        interactionSource = interactionSource,
                        indication = null
                    ){
                        navController.navigate("Home") {
                            popUpTo("Home") { inclusive = true }
                        }
                    },
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
                    color = Color(0xFF007EF2),
                    fontSize = 12.sp
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f).padding(top = 8.dp)
                    .clickable (
                        interactionSource = interactionSource,
                        indication = null
                    ){
                        navController.navigate("Search") {
                            popUpTo("Home") { inclusive = true }
                        }
                    },
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
                modifier = Modifier.weight(1f).padding(top = 8.dp)
                    .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    navController.navigate("Bookings") {
                        popUpTo("Home") { inclusive = true }
                    }
                },
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
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.navigate("Profile") {
                            popUpTo("Home") { inclusive = true }
                        }
                    },
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
@Composable
fun HotelCard(
    imageRes: Int,
    hotelName: String,
    address: String,
    modifier: Modifier = Modifier,
    /*onClick: () -> Unit*/
    onBookingClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            /*.clickable { onClick() }*/, // Bấm vào toàn bộ card cũng gọi onClick
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.background(Color.White)
        ) {
            // Image section
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .fillMaxHeight()
            )

            // Info section
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = hotelName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )

                // Rating stars (giả lập bằng emoji)
                Text("★★★★★", color = Color(0xFFFFC107))

                Text(
                    text = address,
                    fontSize = 8.sp,
                    color = Color.DarkGray,
                    maxLines = 2
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onBookingClick,
                        modifier = Modifier
                            .width(90.dp)
                            .height(30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Đặt Ngay",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 8.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    }
}



