package com.example.myapplication.view


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

import androidx.compose.material3.SnackbarHostState

import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHost

import com.example.myapplication.model.RegisterRequest
import com.example.myapplication.remote.RetrofitClient

@Composable
fun Register(navController: NavController){
    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        //verticalArrangement = Arrangement.Center
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tạo Tài Khoản",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1975DC)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center)
            {
                Text(
                    text = "Điền thông tin của bạn hoặc liên kết với tài",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF000000)
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "khoản đã có",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF000000)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Tên người dùng") },
                modifier = Modifier.fillMaxWidth(0.9f),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "User Icon",
                        modifier = Modifier.size(16.dp)
                    )
                },
                shape = RoundedCornerShape(16.dp),

            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 8.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.email_icon),
                        contentDescription = "User Icon",
                        modifier = Modifier.size(20.dp)
                    )
                },
                shape = RoundedCornerShape(16.dp),
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(0.9f),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.key_icon),
                        contentDescription = "User Icon",
                        modifier = Modifier.size(16.dp)
                    )
                },
                shape = RoundedCornerShape(16.dp),
                visualTransformation = PasswordVisualTransformation()
                )
        }

        Spacer(modifier = Modifier.height(60.dp))
        /*Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF1975DC)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Đăng ký",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }*/
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF1975DC))
                .clickable {
                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.apiService.registerUser(
                                RegisterRequest(user, email, password)
                            )
                            if (response.isSuccessful) {
                                val body = response.body()
                                if (body?.success == true) {
                                    snackbarHostState.showSnackbar("Đăng ký thành công!")
                                    navController.navigate("Login") {
                                        popUpTo("Register") { inclusive = true }
                                    }
                                } else {
                                    snackbarHostState.showSnackbar(body?.message ?: "Lỗi đăng ký")
                                }
                            } else {
                                snackbarHostState.showSnackbar("Lỗi server: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Lỗi kết nối: ${e.message}")
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Đăng ký",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        SnackbarHost(hostState = snackbarHostState)
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.8f)
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Liên kết với",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF7F7F7F)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .weight(0.8f)
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Facebook Circle
            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                color = Color(0xFFD9D9D9),
                shape = CircleShape,
                shadowElevation = 4.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gg_facebook),
                    contentDescription = "Facebook Logo",
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(40.dp))

            // Google Circle
            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                color = Color(0xFFD9D9D9),
                shape = CircleShape,
                shadowElevation = 4.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icons_google),
                    contentDescription = "Google Logo",
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        val annotatedString = buildAnnotatedString {
            append("Đã có tài khoản? ")
            pushStringAnnotation(tag = "LOGIN", annotation = "Login")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF1975DC),
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append("Đăng nhập ngay")
            }
            pop()
        }

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate("Login") {
                            popUpTo("Register") { inclusive = true }
                        }
                    }
            },
            style = androidx.compose.ui.text.TextStyle(
                color = Color(0xFF7F7F7F),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

