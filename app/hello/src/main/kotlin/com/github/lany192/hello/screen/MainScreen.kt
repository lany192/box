package com.github.lany192.hello.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.lany192.hello.dialog.AlertCustomDialog
import com.github.lany192.hello.dialog.AnimatedCustomDialog
import com.github.lany192.hello.dialog.CustomDialog
import com.github.lany192.hello.dialog.FullScreenDialog

@Composable
fun MainScreen(navController: NavController) {
    var showDialog1 by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var showDialog3 by remember { mutableStateOf(false) }
    var showDialog4 by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Main Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("login") }) {
            Text("Go to Login Screen")
        }
        Button(onClick = { showDialog1 = true }) {
            Text("Show Dialog 1")
        }
        Button(onClick = { showDialog2 = true }) {
            Text("Show Dialog 2")
        }
        Button(onClick = { showDialog3 = true }) {
            Text("Show Dialog 3")
        }
        Button(onClick = { showDialog4 = true }) {
            Text("Show Dialog 4")
        }
        if (showDialog1) {
            CustomDialog(onDismissRequest = { showDialog1 = false }) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("This is a custom dialog", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showDialog1 = false }) {
                        Text("Close")
                    }
                }
            }
        }
        if (showDialog2) {
            AlertCustomDialog(
                title = "Confirm Action",
                message = "Are you sure you want to perform this action?",
                onConfirm = {
                    showDialog2 = false
                    // 处理确认操作
                },
                onDismiss = { showDialog2 = false }
            )
        }
        if (showDialog3) {
            FullScreenDialog(onDismissRequest = { showDialog3 = false }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("This is a full screen dialog", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showDialog3 = false }) {
                        Text("Close")
                    }
                }
            }
        }

        AnimatedCustomDialog(
            showDialog = showDialog4,
            onDismissRequest = { showDialog4 = false }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("This is an animated dialog", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { showDialog4 = false }) {
                    Text("Close")
                }
            }
        }
    }
}