package com.example.myapplication.view

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.remote.RetrofitClient
import com.example.myapplication.model.LoginRequest
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.ClickableText

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation

import com.example.myapplication.model.LoginResponse

@Composable
fun Login(navController: NavController) {
    // Declare state variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Declare a coroutine scope and SnackbarHostState
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // UI components
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chào mừng",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1975DC)
            )
        }

        // Description Row
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            Text(
                text = "Đăng nhập để tiếp tục trải nghiệm cùng chúng tôi",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF000000),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input
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
                    contentDescription = "Email Icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            shape = RoundedCornerShape(16.dp),
        )

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 8.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.key_icon),
                    contentDescription = "Password Icon",
                    modifier = Modifier.size(16.dp)
                )
            },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Login Button
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF1975DC))
                .clickable {
                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.apiService.loginUser(
                                LoginRequest(email, password)
                            )
                            if (response.isSuccessful) {
                                val body = response.body()
                                if (body?.success == true) {
                                    // Show Snackbar on success
                                    snackbarHostState.showSnackbar("Đăng nhập thành công!")

                                    // Store token and navigate to the main screen
                                    //saveTokenToPreferences(body?.token)

                                    navController.navigate("Profile") {
                                        popUpTo("Login") { inclusive = true }
                                    }
                                } else {
                                    // Show Snackbar on error
                                    snackbarHostState.showSnackbar(body?.message ?: "Lỗi đăng nhập")
                                }
                            } else {
                                // Show server error
                                snackbarHostState.showSnackbar("Lỗi server: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            // Show connection error
                            snackbarHostState.showSnackbar("Lỗi kết nối: ${e.message}")
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Đăng nhập",
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

        // Link to Register
        val annotatedString = buildAnnotatedString {
            append("Đã chưa có tài khoản? ")
            pushStringAnnotation(tag = "REGISTER", annotation = "Register")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF1975DC),
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append("Đăng ký ngay")
            }
            pop()
        }

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "REGISTER", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate("Register") {
                            popUpTo("Login") { inclusive = true }
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

fun saveTokenToPreferences(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("user_token", token)  // Lưu token vào SharedPreferences
    editor.apply()
}