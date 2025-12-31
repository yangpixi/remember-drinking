package com.yangpixi.rememberdrinking.presentation.screen.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yangpixi.rememberdrinking.domain.model.WaterRecord
import com.yangpixi.rememberdrinking.util.parseToDateTime
import org.koin.compose.viewmodel.koinViewModel

/**
 * @author yangpixi
 * @date 2025/12/31 13:33
 * @description 历史喝水界面
 */

@Composable
fun HistoryScreen() {
    val viewModel = koinViewModel<HistoryViewModel>()

    val recordList by viewModel.todayRecordList.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = recordList,
            key = { record: WaterRecord -> record.id }
        ) {
            DrinkItem(
                amount = it.amountMl,
                time = it.recordTime
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun DrinkItem(
    amount: Long,
    time: Long
) {
    val localDateTime = parseToDateTime(time)
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "喝水量: ${amount}ml"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "喝水时间: $localDateTime"
            )
        }
    }
}

