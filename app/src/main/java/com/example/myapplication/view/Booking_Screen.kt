package com.example.myapplication.view

import com.example.myapplication.remote.RetrofitClient
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import android.content.Context
import android.net.Uri
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.model.Room
import java.net.URLDecoder
import com.google.gson.Gson
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import java.text.DecimalFormat
import com.example.myapplication.model.BookingRequest
import com.example.myapplication.viewmodel.SearchViewModel


@Composable
fun Booking_Screen(navController: NavController, roomJson: String,viewModel: SearchViewModel) {

    val decodedJson = URLDecoder.decode(roomJson, "UTF-8")
    val room = Gson().fromJson(decodedJson, Room::class.java)

    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackbarHostState)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCFC))
    ) {

        // Nội dung chính + Nút Đặt Phòng
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp) // Chừa chỗ cho Bottom Bar
                .padding(16.dp)
        ) {

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Về trang trước",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = rememberAsyncImagePainter(room.image_url),
                contentDescription = "Ảnh phòng",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(room.room_type, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                val price = room.price_per_night
                val priceDiscount = price * 0.9


// Hiển thị giá gốc
                Text(
                    text = "${formatPrice(price)} VND",
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )

// Hiển thị giá giảm
                Text(
                    text = "${formatPrice(priceDiscount)} VND",
                    color = Color(0xFFFFA500),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(27.dp))
            Text("Diện tích: ${room.area}m²", fontStyle = FontStyle.Italic,fontSize = 18.sp)
            Text("Loại giường: ${room.bed_type}", fontStyle = FontStyle.Italic,fontSize = 18.sp)
            Text("Sức chứa: ${room.capacity} khách", fontStyle = FontStyle.Italic,fontSize = 18.sp)

            Spacer(modifier = Modifier.height(50.dp))

            // Dịch vụ tiện ích
            // Dịch vụ tiện ích
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF3399FF), Color(0xFFE0F0FF))
                        )
                    )
                    .padding(36.dp)
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ServiceItem("Dịch vụ spa", R.drawable.ic_spa)
                        ServiceItem("Bar", R.drawable.ic_bar)
                        ServiceItem("Vui chơi giải trí", R.drawable.ic_game)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ServiceItem("Bể bơi", R.drawable.ic_pool)
                        ServiceItem("Free wifi", R.drawable.ic_wifi)
                        ServiceItem("Dọn phòng", R.drawable.ic_clean)
                    }
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
                    val token = sharedPreferences.getString("user_token", "") ?: ""

                    /*val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    val checkInTime = LocalTime.parse(viewModel.search.checkInTime, formatter)
                    val checkOutTime = LocalTime.parse(viewModel.search.checkOutTime, formatter)
                    val durationHours = Duration.between(checkInTime, checkOutTime).toHours().coerceAtLeast(1)
                    val pricePerHour = room.price_per_night / 24
                    val totalPrice = durationHours * pricePerHour*/
                    val bookingRequest = BookingRequest(
                        room_id = room.room_id,
                        check_in = viewModel.search.checkInDate, // bạn có thể lấy từ ngày đã chọn
                        check_out = viewModel.search.checkOutDate, // bạn có thể lấy từ ngày đã chọn,
                        total_price = room.price_per_night
                    )

                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.apiService.bookRoom("Bearer $token", bookingRequest)

                            if (response.isSuccessful) {
                                val body = response.body()
                                if (body?.success == true) {
                                    navController.navigate("BookingSuccess") {
                                        popUpTo("Booking_Screen") { inclusive = true }
                                    }
                                } else {
                                    snackbarHostState.showSnackbar(body?.message ?: "Đặt phòng thất bại")
                                }
                            } else {
                                snackbarHostState.showSnackbar("Lỗi server: ${response.code()}")
                            }

                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Lỗi kết nối: ${e.message}")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1975DC))
            ) {
                Text("Đặt Phòng Ngay", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        // Thanh điều hướng ở dưới cùng
        BottomBar(
            navController = navController,
            selectedIndex = 2,
            modifier = Modifier.align(Alignment.BottomCenter) // 2 là Booking
        )
    }
}


// Hàm ServiceItem đặt cuối file, không cần import
@Composable
fun ServiceItem(label: String, iconResId: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 12.sp)
    }
}
@Composable
fun BottomBar(
    navController: NavController,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFFF5F5F5)),
    ) {
        val tabs = listOf("Home", "Search", "Booking", "Profile")
        val icons = listOf(
            R.drawable.home_black_icon,
            R.drawable.search_icon,
            R.drawable.booking_icon,
            R.drawable.profile_icon
        )
        val labels = listOf("Trang chủ", "Tìm kiếm", "Booking", "Profile")

        tabs.forEachIndexed { index, route ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .clickable {
                        navController.navigate(route) {
                            popUpTo("Home") { inclusive = true }
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = icons[index]),
                    contentDescription = "${labels[index]} icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = labels[index],
                    fontWeight = FontWeight.Bold,
                    color = if (index == selectedIndex) Color(0xFF7F7F7F) else Color(0xFF7F7F7F),
                    fontSize = 12.sp
                )
            }
        }
    }
}


