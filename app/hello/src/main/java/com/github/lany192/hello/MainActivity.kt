package com.github.lany192.hello

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.lany192.extension.log
import com.github.lany192.hello.ui.theme.AndroidJenkinsDemoTheme
import com.github.lany192.toolkit.BoxToolKit
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidJenkinsDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoginScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidJenkinsDemoTheme {
        Greeting("Android")
    }
}

@Composable
fun LoginScreen() {
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
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
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
            Toast.makeText(context, "当前进程名称：${BoxToolKit.getCurrentProcess()}", Toast.LENGTH_LONG).show()
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
    }
}