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
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults

@Composable
fun Search(navController: NavController){
    var hotelName by remember { mutableStateOf("") }
    var hotelStartDay by remember { mutableStateOf("") }
    var hotelEndDay by remember { mutableStateOf("") }
    var adult by remember { mutableStateOf("") }
    var chidrent by remember { mutableStateOf("") }
    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    Box(
        modifier = Modifier
            //.fillMaxSize()
            .fillMaxWidth()
            .padding(0.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(250.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.search_background_image),
                contentDescription = "Search background image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .height(600.dp)
                    .padding(top = 190.dp)
                    .clip(RoundedCornerShape(48.dp))
                    .background(Color(0xFFFFFFFF))
            ){
                Column {
                    Text(
                        text = "Tìm kiếm khách sạn dành cho bạn",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF007EF2),
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(top = 48.dp, start = 40.dp , end = 24.dp)
                    )
                    Text(
                        text = "Khám phá nhu cầu đặt lịch của bạn ngay:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 40.dp , end = 24.dp)
                    )
                    Text(
                        text = "Tên khách sạn",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF000000),
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(top = 30.dp, start = 60.dp , end = 24.dp)
                    )
                    OutlinedTextField(
                        value = hotelName,
                        onValueChange = { hotelName = it },
                        label = {  },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding( top = 16.dp , start = 40.dp),
                        shape = RoundedCornerShape(10.dp),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Ngày đặt phòng",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF000000),
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Ngày trả phòng",
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
                    ){
                        OutlinedTextField(
                            value = hotelStartDay,
                            onValueChange = { hotelStartDay = it },
                            label = { Text("dd/mm/yy") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.calendar),
                                    contentDescription = "Calendar Icon",
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .clip(RoundedCornerShape(10.dp)),
                        )

                        OutlinedTextField(
                            value = hotelEndDay,
                            onValueChange = { hotelEndDay = it },
                            label = { Text("dd/mm/yy") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.calendar),
                                    contentDescription = "Calendar Icon",
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(10.dp)),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Người lớn",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF000000),
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Trẻ em",
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
                    ){
                        OutlinedTextField(
                            value = hotelStartDay,
                            onValueChange = { hotelStartDay = it },
                            label = { Text("dd/mm/yy") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.calendar),
                                    contentDescription = "Calendar Icon",
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .clip(RoundedCornerShape(10.dp)),
                        )

                        OutlinedTextField(
                            value = hotelEndDay,
                            onValueChange = { hotelEndDay = it },
                            label = { Text("dd/mm/yy") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.calendar),
                                    contentDescription = "Calendar Icon",
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(10.dp)),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Phòng",
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
                            value = hotelStartDay,
                            onValueChange = { hotelStartDay = it },
                            label = { Text("dd/mm/yy") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.calendar),
                                    contentDescription = "Calendar Icon",
                                    modifier = Modifier.size(10.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .clip(RoundedCornerShape(10.dp)),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(320.dp)
                                .height(50.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF007EF2)),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Tìm kiếm",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
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
                                color = Color(0xFF000000),
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
                                color = Color(0xFF000000),
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
                                color = Color(0xFF000000),
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
                                color = Color(0xFF000000),
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
                                color = Color(0xFF000000),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
