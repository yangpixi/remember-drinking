package com.yangpixi.rememberdrinking.presentation.screen.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

/**
 * @author yangpixi
 * @date 2026/1/7 22:46
 * @description 注册页面
 */

@Composable
fun RegisterScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<RegisterViewModel>()

    val usernameValue by viewModel.usernameValue.collectAsState()
    val passwordValue by viewModel.passwordValue.collectAsState()
    val phoneVale by viewModel.phoneValue.collectAsState()
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 用户名输入框
                    OutlinedTextField(
                        value = usernameValue,
                        onValueChange = {
                            viewModel.updateUsername(it)
                        },
                        label = {
                            Text("用户名")
                        },
                        placeholder = {
                            Text("请输入")
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 密码输入框
                    OutlinedTextField(
                        value = passwordValue,
                        onValueChange = {
                            viewModel.updatePassword(it)
                        },
                        label = {
                            Text("密码")
                        },
                        placeholder = {
                            Text("请输入")
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    OutlinedTextField(
                        value = phoneVale,
                        onValueChange = {
                            viewModel.updatePhone(it)
                        },
                        label = {
                            Text("手机号")
                        },
                        placeholder = {
                            Text("请输入")
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                scope.launch {
                    viewModel.doRegister(
                        usernameValue,
                        passwordValue,
                        phoneVale
                    )
                }
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("注册")
        }
    }
}
