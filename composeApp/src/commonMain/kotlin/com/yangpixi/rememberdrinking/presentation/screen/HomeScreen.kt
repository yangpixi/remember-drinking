package com.yangpixi.rememberdrinking.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yangpixi.rememberdrinking.presentation.component.ProgressCircle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import rememberdrinking.composeapp.generated.resources.Res
import rememberdrinking.composeapp.generated.resources.drinkingReminder
import rememberdrinking.composeapp.generated.resources.mainCardTitle

/**
 * @author yangpixi
 * @date 2025/12/27 21:25
 * @description 主页面
 */

@Composable
fun HomeScreen(

) {
    val viewModel = koinViewModel<HomeViewModel>()

    val goal by viewModel.goal.collectAsState()
    val current by viewModel.totalWater.collectAsState()
    Column {
        // 提醒栏
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.tertiaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                10.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(Res.string.drinkingReminder),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // 实际内容
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                10.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(text = stringResource(Res.string.mainCardTitle))
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "目标：${goal}ml，进度：${current}ml")
                Spacer(modifier = Modifier.height(26.dp))

                ProgressCircle(
                    progress = current,
                    goal = goal,
                    circleWidth = 20.dp,
                    circleColor = MaterialTheme.colorScheme.secondary,
                    arcColor = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.updateProgress(200)
                        },
                    ) {
                        Text("已经喝水啦")
                    }
                }
            }
        }
    }
}
