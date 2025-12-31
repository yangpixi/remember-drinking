package com.yangpixi.rememberdrinking.presentation.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import com.yangpixi.rememberdrinking.domain.model.WaterRecord
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * @author yangpixi
 * @date 2025/12/31 13:33
 * @description viewModel
 */

class HistoryViewModel(
    private val waterRepo: WaterRepo
) : ViewModel() {
    private val _todayRecordList: StateFlow<List<WaterRecord>> = waterRepo
        .getDrinkListToday()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val todayRecordList: StateFlow<List<WaterRecord>> = _todayRecordList


}
