package com.github.lany192.hello.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.lany192.extension.log
import com.github.lany192.hello.BoxImage
import com.github.lany192.hello.R
import com.github.lany192.toolkit.BoxToolKit
import java.util.regex.Pattern

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")

//    val scanLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
//        if (result.contents != null) {
//            Toast.makeText(context, "扫描结果: ${result.contents}", Toast.LENGTH_LONG).show()
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxImage(
            imageUrl = "https://img1.baidu.com/it/u=3277373589,2105735670&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1067",
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = "欢迎使用cursor测试demo",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("账号") }
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("密码") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(onClick = {
            log("哈哈哈点击了")
//            if (passwordPattern.matcher(password.value).matches()) {
//                Toast.makeText(context, "账号: ${username.value}, 密码: ${password.value}", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(context, "密码必须至少6个字符，且包含数字、大小写字母", Toast.LENGTH_LONG).show()
//            }
            Toast.makeText(
                context,
                "当前进程名称：${BoxToolKit.getCurrentProcess()}",
                Toast.LENGTH_LONG
            ).show()
        }) {
            Text("登录")
        }

//        Button(onClick = {
//            val options = ScanOptions()
//            options.setPrompt("请对准二维码进行扫描")
//            scanLauncher.launch(options)
//        }) {
//            Text("扫描二维码")
//        }

        Button(onClick = { navController.navigate("detail") }) {
            Text("扫描二维码")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AndroidJenkinsDemoTheme {
//        LoginScreen()
//    }
//}