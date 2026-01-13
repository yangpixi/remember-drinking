package com.yangpixi.rememberdrinking.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * @author yangpixi
 * @date 2026/1/13 10:57
 * @description 设置单次喝水量的弹窗
 */

@Composable
fun DrinkAmountDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    onChangeRequest: (Int) -> Unit,
    itemList: List<AmountItem>,
    selected: Int
) {

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "喝水量"
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    itemList.forEach {
                        Spacer(modifier = Modifier.width(3.dp))
                        SelectableItem(
                            onClick = {
                                onChangeRequest(it.id)
                            },
                            selected = selected == it.id,
                            amount = it.amount
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onConfirmRequest
                    ) {
                        Text("确认")
                    }
                }

            }
        }
    }
}

@Composable
fun SelectableItem(
    onClick: () -> Unit,
    selected: Boolean,
    amount: Int
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.secondary.copy(0.5f)
        } else {
            MaterialTheme.colorScheme.secondaryContainer
        },
        animationSpec = tween(300)
    )

    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            MaterialTheme.colorScheme.onSecondaryContainer
        }
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .selectable(
                selected = selected,
                onClick = onClick
            )
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp, 6.dp),
            text = amount.toString(),
            color = contentColor,
            fontWeight = FontWeight.Bold
        )
    }
}

data class AmountItem(
    val amount: Int,
    val id: Int
)