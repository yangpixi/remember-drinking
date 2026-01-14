package com.yangpixi.rememberdrinking.presentation.screen.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberdrinking.composeapp.generated.resources.Res
import rememberdrinking.composeapp.generated.resources.drop

/**
 * @author yangpixi
 * @date 2026/1/4 12:51
 * @description 登录页面
 */

@Composable
fun LoginScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<LoginViewModel>()
    val usernameValue by viewModel.usernameValue.collectAsState()
    val passwordValue by viewModel.passwordValue.collectAsState()

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope() // 获取协程，方便调用suspend方法

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(Res.drawable.drop),
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

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
                            viewModel.updateUsernameValue(it)
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

                    Spacer(modifier = Modifier.height(10.dp))

                    // 密码输入框
                    OutlinedTextField(
                        value = passwordValue,
                        onValueChange = {
                            viewModel.updatePasswordValue(it)
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
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    viewModel.doLogin(
                        usernameValue,
                        passwordValue
                    )
                }
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("登录")
        }

        TextButton(
            onClick = {
                navController.navigate("register")
            },
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
        ) {
            Text("还没有账号？去注册")
        }

    }
}