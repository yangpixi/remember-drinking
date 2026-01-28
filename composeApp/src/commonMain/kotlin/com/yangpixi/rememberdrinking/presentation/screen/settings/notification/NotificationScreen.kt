package com.yangpixi.rememberdrinking.presentation.screen.settings.notification

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.yangpixi.rememberdrinking.presentation.component.ExpandableItem
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

/**
 * @author yangpixi
 * @date 2026/1/25 15:45
 * @description 通知设置界面
 */

@Composable
fun NotificationScreen() {

    val viewModel = koinViewModel<NotificationViewModel>()
    val globalSnackBarUtils = koinInject<GlobalSnackBarUtils>()
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
    val uiState by viewModel.uiState.collectAsState()
    val isEnable by viewModel.isEnable.collectAsState()
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    BindEffect(controller) // 绑定context到controller，便于安卓申请权限(moko库)

    Column(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("通知总开关")

                Switch(
                    checked = isEnable,
                    onCheckedChange = {
                        if (it) {
                            scope.launch {
                                // 申请通知权限
                                try {
                                    controller.providePermission(Permission.REMOTE_NOTIFICATION)
                                    viewModel.updateEnabledState(it)
                                } catch (e: Exception) {
                                    globalSnackBarUtils.sendEvent("只有开启通知权限才可以进行喝水提醒")
                                }
                            }
                            viewModel.scheduleNotification() // 开启通知
                        } else {
                            viewModel.updateEnabledState(it)
                            viewModel.cancelNotificationSchedule() // 关闭通知
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            ExpandableItem(
                title = "通知间隔(单位: 分钟, 最小设置15)",
                isEnabled = isEnable,
                onValueChanged = {
                    viewModel.updateDelayTime(it)
                },
                inputValue = uiState.currentConfig.delayTime
            )

            HorizontalDivider(
                modifier = Modifier
                    .padding(10.dp, 5.dp)
            )

            ExpandableItem(
                title = "通知内容",
                isEnabled = isEnable,
                onValueChanged = {
                    viewModel.updateContent(it)
                },
                inputValue = uiState.currentConfig.content
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.saveSettings()
                },
                enabled = uiState.hasChanges,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text("立即保存")
            }
        }
    }
}