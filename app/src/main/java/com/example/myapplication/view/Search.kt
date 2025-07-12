package com.example.myapplication.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Icon

import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.border

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults


import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

import android.app.TimePickerDialog

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.SearchViewModel


import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHost
import com.example.myapplication.model.SearchInfo

@Composable
fun Search(navController: NavController, viewModel: SearchViewModel) {

    var hotelName by remember { mutableStateOf("") }
    var checkInDate by remember { mutableStateOf("") }
    var checkOutDate by remember { mutableStateOf("") }
    var checkInTime by remember { mutableStateOf("") }
    var checkOutTime by remember { mutableStateOf("") }
    var numberOfGuests by remember { mutableStateOf("") }


    val hotelSuggestions = listOf(
        "Khách sạn La Vela Saigon",
        "Khách sạn des Arts Saigon",
        "Khách sạn Liberty Central Saigon Riverside"
    )

    var showSuggestions by remember { mutableStateOf(true) }
    val filteredSuggestions = hotelSuggestions.filter {
        it.contains(hotelName, ignoreCase = true) && hotelName.isNotBlank()
    }

    val interactionSource = remember { MutableInteractionSource() }

    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()





    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.search_background_image),
            contentDescription = "Search background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 190.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Tìm kiếm khách sạn dành cho bạn",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color(0xFF007EF2),
                modifier = Modifier.padding(top = 32.dp, start = 24.dp)
            )

            Text(
                text = "Khám phá nhu cầu đặt lịch của bạn ngay:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFFFFD700),
                modifier = Modifier.padding(start = 24.dp, top = 8.dp)
            )

            Text(
                text = "Tên khách sạn",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 24.dp, start = 24.dp)
            )

            OutlinedTextField(
                value = hotelName,
                onValueChange = {
                    hotelName = it
                    showSuggestions = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                shape = RoundedCornerShape(10.dp),
                label = { Text("") }
            )
            SnackbarHost(hostState = snackbarHostState)
            if (showSuggestions && filteredSuggestions.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEFEFEF))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                ) {
                    filteredSuggestions.forEach { suggestion ->
                        Text(
                            text = suggestion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    hotelName = suggestion
                                    showSuggestions = false
                                }
                                .padding(12.dp),
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
            if (showSuggestions && hotelName.isNotBlank() && filteredSuggestions.isEmpty()) {
                Text(
                    text = "Không tìm thấy khách sạn phù hợp",
                    modifier = Modifier.padding(start = 24.dp, top = 4.dp),
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            // Check-in Section
            Text(
                text = "Nhận phòng",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 24.dp, top = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatePickerField(
                    label = "Ngày nhận",
                    selectedDate = checkInDate,
                    onDateSelected = { checkInDate = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                TimePickerField(
                    label = "Giờ nhận",
                    selectedTime = checkInTime,
                    onTimeSelected = { checkInTime = it },
                    modifier = Modifier.weight(1f)
                )
            }

            // Check-out Section
            Text(
                text = "Trả phòng",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 24.dp, top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatePickerField(
                    label = "Ngày trả",
                    selectedDate = checkOutDate,
                    onDateSelected = { checkOutDate = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                TimePickerField(
                    label = "Giờ trả",
                    selectedTime = checkOutTime,
                    onTimeSelected = { checkOutTime = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Số lượng người",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    fontSize = 15.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    value = numberOfGuests,
                    onValueChange = { numberOfGuests = it },
                    label = { Text("") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.user_icon),
                            contentDescription = "Calendar Icon",
                            modifier = Modifier.size(10.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(10.dp)),
                )
            }
            // Search Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF007EF2))
                    .clickable {
                        when {
                            hotelName.isBlank() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Tên khách sạn không được để trống.")
                                }
                            }
                            !hotelSuggestions.contains(hotelName.trim()) -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Khách sạn chưa được hỗ trợ.")
                                }
                            }
                            else -> {
                                viewModel.updateSearch(
                                    SearchInfo(
                                        hotelName = hotelName,
                                        checkInDate = checkInDate,
                                        checkOutDate = checkOutDate,
                                        checkInTime = checkInTime,
                                        checkOutTime = checkOutTime,
                                        numberOfGuests = numberOfGuests
                                    )
                                )
                                navController.navigate("Listroom")
                            }
                        }
                    }
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tìm kiếm",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    //.align(Alignment.BottomCenter)
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
                        painter = painterResource(id = R.drawable.home_black_icon),
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
                        .weight(1f).padding(top = 8.dp)
                        .clickable (
                            interactionSource = interactionSource,
                            indication = null
                        ){
                            navController.navigate("Listroom") {
                                popUpTo("Home") { inclusive = true }
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.search_blue_icon),
                        contentDescription = "search icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Tìm kiếm",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF007EF2),
                        fontSize = 12.sp
                    )
                }

                Column(
                    modifier = Modifier.weight(1f).padding(top = 8.dp)
                        .clickable (
                        interactionSource = interactionSource,
                        indication = null
                    ){
                        navController.navigate("Bookings") {
                            popUpTo("Search") { inclusive = true }
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
}


@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val date = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        modifier = modifier // ✅ sử dụng modifier truyền vào
            .clip(RoundedCornerShape(10.dp))
            .clickable { datePickerDialog.show() },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Calendar Icon",
                modifier = Modifier.size(16.dp)
            )
        }
    )
}

@Composable
fun BottomNavItem(
    iconRes: Int,
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val color = if (selected) Color(0xFF007EF2) else Color(0xFF7F7F7F)
    Column(
        modifier = Modifier
            //.weight(1f)
            .clickable { onClick() }
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = 12.sp
        )
    }
}
@Composable
fun TimePickerField(
    label: String,
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = remember {
        android.app.TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                val formattedTime = String.format("%02d:%02d", hour, minute)
                onTimeSelected(formattedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // 24h format
        )
    }

    OutlinedTextField(
        value = selectedTime,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { timePickerDialog.show() },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.calendar), // bạn cần icon "clock"
                contentDescription = "Clock Icon",
                modifier = Modifier.size(16.dp)
            )
        }
    )
}
