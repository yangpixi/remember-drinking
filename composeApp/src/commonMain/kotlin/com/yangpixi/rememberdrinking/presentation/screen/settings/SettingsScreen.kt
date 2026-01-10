package com.yangpixi.rememberdrinking.presentation.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.yangpixi.rememberdrinking.util.AuthManager
import org.koin.compose.koinInject

/**
 * @author yangpixi
 * @date 2025/12/31 13:35
 * @description 设置界面
 */

@Composable
fun SettingsScreen(
    navController: NavController
) {

    val authManager = koinInject<AuthManager>()
    val authStatus by authManager.authStatus.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when (authStatus) {
            is AuthManager.AuthStatus.Authenticated -> {
                LoginStatusComponent(
                    avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/1/1f/Oryctolagus_cuniculus_Rcdo.jpg",
                    username = "yangpixi",
                    onClick = { navController.navigate("login") }
                )
            }
            else -> {
                LoginStatusComponent(
                    avatarUrl = "https://upload.wikimedia.org/wikipedia/zh/e/e5/Gawr_Gura.png",
                    username = "guest",
                    onClick = { navController.navigate("login") }
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Card(
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = "同步",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable(onClick = {})
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyLarge
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(10.dp, 5.dp)
                )

                Text(
                    text = "关于",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable(onClick = {
                            navController.navigate("about")
                        })
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun LoginStatusComponent(
    avatarUrl: String,
    username: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "你好! $username",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}