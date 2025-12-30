package com.yangpixi.rememberdrinking.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import com.yangpixi.rememberdrinking.db.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * @author yangpixi
 * @date 2025/12/27 22:34
 * @description viewModel
 */

class HomeViewModel(
    private val waterRepo: WaterRepo
) : ViewModel() {
    private val _goal = MutableStateFlow<Int>(1500)
    val goal: StateFlow<Int> = _goal

    private val _current = MutableStateFlow<Long>(0)
    val current: StateFlow<Long> = _current

    private val _totalWater: StateFlow<Long> = waterRepo
        .getTotalDrinkToday()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(10000),
            initialValue = 0L
        )

    val totalWater: StateFlow<Long> = _totalWater

    fun updateProgress(volume: Long) {
        waterRepo.doDrink(volume)
    }


}