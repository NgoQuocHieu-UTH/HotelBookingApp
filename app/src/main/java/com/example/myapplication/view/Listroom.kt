package com.example.myapplication.view
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.example.myapplication.model.Room
import com.example.myapplication.remote.RetrofitClient
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.sp  // Dòng quan trọng này
import androidx.compose.foundation.border
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.example.myapplication.viewmodel.SearchViewModel
import com.example.myapplication.model.SearchRequest
import com.google.gson.Gson
import java.net.URLEncoder

@Composable
fun Listroom(navController: NavController, viewModel: SearchViewModel) {
    val interactionSource = remember { MutableInteractionSource() }


    Box(modifier = Modifier.fillMaxSize()) {
        // Nội dung chính hiển thị phía trên
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)) // chừa chỗ cho thanh bottom nav
        {
            // Bản đồ
            Image(
                painter = painterResource(id = R.drawable.placeholder_map),
                contentDescription = "Bản đồ khách sạn",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Các icon chức năng
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.directions),
                            contentDescription = "Đường đi",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(text = "Đường đi")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.place),
                            contentDescription = "Gần đó",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(text = "Gần đó")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.call),
                            contentDescription = "Gọi điện thoại",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(text = "Gọi điện thoại")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = "Chia sẻ",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(text = "Chia sẻ")
                }
            }

            // Tiêu đề khách sạn
            Text(
                text = viewModel.search.hotelName,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )


            // Danh sách phòng
            RoomListScreen(navController = navController, viewModel = viewModel)
        }


        // Thanh điều hướng dưới cùng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
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
            val selectedIndex = 1 // "Search" đang được chọn ở màn này

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
                        color = if (index == selectedIndex) Color(0xFF7F7F7F) else Color(0xFF7F7F7F),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}





@Composable
fun RoomListScreen(navController: NavController, viewModel: SearchViewModel) {
    val roomList = remember { mutableStateListOf<Room>() }
    val coroutineScope = rememberCoroutineScope()

    val searchInfo = viewModel.search

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.apiService.searchRooms(
                    SearchRequest(searchInfo.hotelName, searchInfo.checkInDate, searchInfo.checkOutDate, searchInfo.checkInTime, searchInfo.checkOutTime, searchInfo.numberOfGuests)
                )
                if (response.isSuccessful && response.body()?.success == true) {
                    roomList.clear()
                    roomList.addAll(response.body()!!.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(items = roomList) { room ->
            RoomCard(room = room, navController = navController)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}




@Composable
fun RoomCard(room: Room, navController: NavController, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(8.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5FAFE)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ảnh phòng
            Image(
                painter = rememberAsyncImagePainter(room.image_url),
                contentDescription = "Hình ảnh phòng",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Thông tin chi tiết
            Column(
                modifier = Modifier.fillMaxHeight().weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = room.room_type,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "• ${room.area}m2", fontSize = 8.sp)
                        Text(text = "• ${room.bed_type}", fontSize = 8.sp)
                        Text(text = "• ${room.capacity} khách", fontSize = 8.sp)
                        Text(text = "• ${room.description}", fontSize = 8.sp)
                    }
                    Column {
                        Text(text = "• Phòng tắm, vòi sen", fontSize = 8.sp)
                        Text(text = "• Mini bar, trà, cà phê", fontSize = 8.sp)
                        Text(text = "• Dọn phòng", fontSize = 8.sp)
                        Text(text = "• Hồ bơi vô cực", fontSize = 8.sp)
                    }
                }


                // Phần giá và nút
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${room.price_per_night.toInt()} VNĐ",
                        fontSize = 12.sp,
                        color = Color(0xFFFFA500),
                        fontFamily = FontFamily.Serif // Kiểu chữ có chân, trang trọng
                    )

                    val gson = Gson()
                    val roomJson = URLEncoder.encode(gson.toJson(room), "UTF-8")

                    Button(
                        onClick = { navController.navigate("booking/$roomJson") },
                        modifier = Modifier
                            .height(22.dp)
                            .width(55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1975DC)
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Đặt",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                }
            }
        }
    }
}