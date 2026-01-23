package com.yangpixi.rememberdrinking.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * @author yangpixi
 * @date 2026/1/23 13:48
 * @description 密码修改弹窗
 */

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isNotEmpty by remember {
        derivedStateOf { password.isNotEmpty() && confirmPassword.isNotEmpty() }
    }

    val isMatch by remember {
        derivedStateOf { password == confirmPassword }
    }

    val showMismatchError by remember {
        derivedStateOf {
            password.isNotEmpty() && confirmPassword.isNotEmpty() && !isMatch
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("修改密码") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("新密码") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("确认密码") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = showMismatchError, // 错误时变红
                    supportingText = {
                        if (showMismatchError) {
                            Text("两次输入的密码不一致")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(password) },
                enabled = isNotEmpty && isMatch
            ) {
                Text("确认修改")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}