package com.yangpixi.rememberdrinking.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

/**
 * @author yangpixi
 * @date 2026/1/25 16:14
 * @description 通用可展开子项组件
 */

@Composable
fun ExpandableItem(
    title: String,
    isEnabled: Boolean,
    onValueChanged: (String) -> Unit,
    inputValue: String
) {
    var isExpanded by remember { mutableStateOf(false) } // 展开状态

    LaunchedEffect(isEnabled) {
        if (!isEnabled) {
            isExpanded = false
        }
    }

    val rotateState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f
    )

    val contentColor = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (isEnabled) 1f else 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = isEnabled) {
                    isExpanded = !isExpanded
                }
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title)

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = contentColor,
                modifier = Modifier.rotate(rotateState),
                contentDescription = "ExpandingArrow"
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    value = inputValue,
                    onValueChange = {
                        onValueChanged(it)
                    },
                    placeholder = {
                        Text("请输入")
                    },
                    singleLine = true
                )
            }
        }
    }
}