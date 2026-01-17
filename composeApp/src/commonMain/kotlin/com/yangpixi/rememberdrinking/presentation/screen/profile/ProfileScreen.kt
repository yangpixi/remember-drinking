package com.yangpixi.rememberdrinking.presentation.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.yangpixi.rememberdrinking.BuildConfig
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import org.koin.compose.viewmodel.koinViewModel

/**
 * @author yangpixi
 * @date 2026/1/13 20:37
 * @description 个人资料界面
 */

@Composable
fun ProfileScreen(
    navController: NavController
) {

    val viewModel = koinViewModel<ProfileViewModel>()

    val currentUser by viewModel.currentUser.collectAsState()

    val launcher = rememberFilePickerLauncher(
        type = FileKitType.Image,
        mode = FileKitMode.Single
    ) { file ->
        viewModel.doUpdateAvatar(file) // 不能使用断言，否则会出现NPE
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = BuildConfig.BASE_URL + currentUser!!.avatar, // 能进入该页面，肯定存在数据
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .clickable(onClick = {
                        launcher.launch()
                    })
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = currentUser!!.username
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {

                Text(
                    text = "修改手机号",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable(onClick = {

                        })
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyLarge
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(10.dp, 5.dp)
                )

                Text(
                    text = "修改密码",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable(onClick = {

                        })
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyLarge
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(10.dp, 5.dp)
                )

                Text(
                    text = "退出登录",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable(onClick = {
                            viewModel.doLogout()
                            navController.navigate("settings")
                        })
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}