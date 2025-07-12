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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.AlertDialog
import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import com.example.myapplication.model.LoginRequest
import com.example.myapplication.remote.RetrofitClient
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.myapplication.model.UpdateUserRequest
import com.example.myapplication.model.ApiResponse
@Composable
fun Profile(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var avatarUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            avatarUri = uri // ✅ Cập nhật avatarUri
        }
    }

    var userName by remember { mutableStateOf("Nguyễn Văn A") }
    var sex by remember { mutableStateOf("Nam") }
    var birthday by remember { mutableStateOf("1/1/2025") }
    var CCCD by remember { mutableStateOf("XXXX XXXX XXXX") }
    var phone by remember { mutableStateOf("0XXX XXX XXX") }

    // Biến kiểm soát việc hiển thị dialog cập nhật tên
    var showNameDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }

    var showSexDialog by remember { mutableStateOf(false) }
    var newSex by remember { mutableStateOf("") }

    var showBỉthdayDialog by remember { mutableStateOf(false) }
    var newBỉthday by remember { mutableStateOf("") }

    var showCCCDDialog by remember { mutableStateOf(false) }
    var newCCCD by remember { mutableStateOf("") }

    var showPhoneDialog by remember { mutableStateOf(false) }
    var newPhone by remember { mutableStateOf("") }


    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
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
            Spacer(modifier = Modifier.width(320.dp))
            Image(
                painter = painterResource(id = R.drawable.exit_icon),
                contentDescription = "Exit Icon",
                modifier = Modifier
                    .size(25.dp)
                    .background(Color.White)
                    .clickable {
                        val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("user_token", "")
                        editor.apply()
                        navController.navigate("Login") {
                            popUpTo("Home") { inclusive = true }
                        }
                    }

            )
        }
        Box(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .background(Color(0xFF4FC0FF))
                .clickable (
                    interactionSource = interactionSource,
                    indication = null
                ) { launcher.launch("image/*") }  // Click toàn bộ Box
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
                if (avatarUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(avatarUri),
                        contentDescription = "Avatar Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(68.dp).clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.avatar),
                        contentDescription = "Default Avatar",
                        modifier = Modifier.size(68.dp)
                    )
                }
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
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { showNameDialog = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tên",
                color = Color(0xFF000000),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = userName,
                color = Color(0xFF000000),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { showSexDialog = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Giới tính",
                color = Color(0xFF000000),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = sex,
                color = Color(0xFF000000),
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    newBỉthday = birthday
                    showBỉthdayDialog = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ngày Sinh",
                color = Color(0xFF000000),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = birthday,
                color = Color(0xFF000000),
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    newCCCD = CCCD
                    showCCCDDialog = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CCCD/Pastpost",
                color = Color(0xFF000000),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = CCCD,
                color = Color(0xFF000000),
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    newPhone = phone
                    showPhoneDialog = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SĐT",
                color = Color(0xFF000000),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = phone,
                color = Color(0xFF000000),
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }

        Divider(
            color = Color(0xFFC2C2C2),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // hoặc .width(200.dp) nếu muốn ngắn hơn
                .padding(vertical = 8.dp)
        )

        //Open Name
        if (showNameDialog) {
            AlertDialog(
                onDismissRequest = { showNameDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        userName = newName // Cập nhật tên mới lên giao diện
                        showNameDialog = false

                        val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
                        val token = sharedPreferences.getString("user_token", "") ?: ""
                        coroutineScope.launch {
                            try {
                                val response = RetrofitClient.apiService.updateUser("Bearer $token", UpdateUserRequest(fullName = newName ,gender = "",birth_date = "" ,phone = "", identity_number= ""))
                                if (response.isSuccessful) {
                                    val body = response.body()
                                    if (body?.success == true) {
                                        // Show Snackbar on success
                                        snackbarHostState.showSnackbar("Đổi tên thành công!")

                                    } else {
                                        // Show Snackbar on error
                                        snackbarHostState.showSnackbar(body?.message ?: "Lỗi Đổi tên")
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
                    }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showNameDialog = false }) {
                        Text("Huỷ")
                    }
                },
                title = { Text("Cập nhật tên") },
                text = {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Tên mới") },
                        singleLine = true
                    )
                }
            )
            userName = newName;
        }
        if (showSexDialog) {
            val genderOptions = listOf("Nam", "Nữ", "Khác")
            AlertDialog(
                onDismissRequest = { showSexDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        sex = newSex
                        showSexDialog = false
                    }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSexDialog = false }) {
                        Text("Huỷ")
                    }
                },
                title = { Text("Cập nhật giới tính") },
                text = {
                    Column {
                        genderOptions.forEach { gender ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { newSex = gender }
                                    .padding(4.dp)
                            ) {
                                androidx.compose.material3.RadioButton(
                                    selected = (newSex == gender),
                                    onClick = { newSex = gender }
                                )
                                Text(text = gender)
                            }
                        }
                    }
                }
            )
        }
        if (showBỉthdayDialog) {
            AlertDialog(
                onDismissRequest = { showBỉthdayDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        birthday = newBỉthday
                        showBỉthdayDialog = false
                    }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showBỉthdayDialog = false }) {
                        Text("Huỷ")
                    }
                },
                title = { Text("Cập nhật ngày sinh") },
                text = {
                    OutlinedTextField(
                        value = newBỉthday,
                        onValueChange = { newBỉthday = it },
                        label = { Text("Ngày sinh") },
                        singleLine = true
                    )
                }
            )
        }
        if (showCCCDDialog) {
            AlertDialog(
                onDismissRequest = { showCCCDDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        CCCD = newCCCD
                        showCCCDDialog = false
                    }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCCCDDialog = false }) {
                        Text("Huỷ")
                    }
                },
                title = { Text("Cập nhật CCCD") },
                text = {
                    OutlinedTextField(
                        value = newCCCD,
                        onValueChange = { newCCCD = it },
                        label = { Text("CCCD") },
                        singleLine = true
                    )
                }
            )
        }
    }
}

