package com.example.myapplication.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.model.BookingInfo
import com.example.myapplication.remote.RetrofitClient
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import com.example.myapplication.R
import androidx.navigation.NavController

fun formatPrice(price: Double): String {
    return try {
        val formatter = DecimalFormat("#,###")
        formatter.format(price)
    } catch (e: Exception) {
        price.toString()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bookings(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bookingList = remember { mutableStateListOf<BookingInfo>() }
    val snackbarHostState = remember { SnackbarHostState() }

    // Hàm load danh sách booking
    fun loadBookings(token: String) {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.apiService.getMyBookings("Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        bookingList.clear()
                        bookingList.addAll(list)
                    }
                } else {
                    snackbarHostState.showSnackbar("Không thể tải danh sách phòng")
                }
            } catch (e: Exception) {
                snackbarHostState.showSnackbar("Lỗi kết nối: ${e.message}")
            }
        }
    }

    LaunchedEffect(Unit) {
        val token = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            .getString("user_token", "") ?: ""
        loadBookings(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Phòng đã đặt", fontWeight = FontWeight.Bold) })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            BookingsBottomBar(navController = navController, selectedIndex = 2)
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F6FA))
        ) {
            items(bookingList) { booking ->
                BookingCard(
                    booking = booking,
                    onCancelled = {
                        // Cập nhật UI sau khi hủy phòng thành công
                        val index = bookingList.indexOfFirst { it.booking_id == booking.booking_id }
                        if (index != -1) {
                            bookingList[index] = booking.copy(booking_status = "CANCELLED")
                        }
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Đã hủy phòng thành công")
                        }
                    },
                    onFailed = { errorMsg ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(errorMsg)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BookingCard(
    booking: BookingInfo,
    onCancelled: () -> Unit,
    onFailed: (String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(booking.image_url),
                contentDescription = "Ảnh phòng",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                booking.room_type,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                "Nhận: ${booking.check_in} - Trả: ${booking.check_out}",
                fontSize = 14.sp,
                color = Color(0xFF555555)
            )

            Text(
                "Tổng tiền: ${formatPrice(booking.total_price)} VND",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFDA542E)
            )

            Text(
                "Trạng thái: ${booking.booking_status}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF007EF2)
            )

            if (!booking.booking_status.equals("CANCELLED", ignoreCase = true)) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        val token = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
                            .getString("user_token", "") ?: ""

                        coroutineScope.launch {
                            try {
                                val response = RetrofitClient.apiService.cancelBooking(
                                    "Bearer $token",
                                    mapOf("booking_id" to booking.booking_id)
                                )
                                if (response.isSuccessful) {
                                    onCancelled()
                                } else {
                                    onFailed("Hủy phòng thất bại: ${response.code()}")
                                }
                            } catch (e: Exception) {
                                onFailed("Lỗi: ${e.message}")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hủy phòng", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun BookingsBottomBar(navController: NavController, selectedIndex: Int = 2) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.align(Alignment.BottomCenter)
            .height(70.dp)
            .background(Color(0xFFF5F5F5)),
    ) {
        val tabs = listOf("Home", "Search", "Bookings", "Profile")
        val icons = listOf(
            R.drawable.home_black_icon,
            R.drawable.search_icon,
            R.drawable.booking_blue_icon,
            R.drawable.profile_icon
        )
        val labels = listOf("Trang chủ", "Tìm kiếm", "Booking", "Profile")

        tabs.forEachIndexed { index, route ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
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
                    color = if (index == selectedIndex) Color(0xFF007EF2) else Color(0xFF7F7F7F),
                    fontSize = 12.sp
                )
            }
        }
    }
}

