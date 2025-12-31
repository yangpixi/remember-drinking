package com.yangpixi.rememberdrinking.presentation.screen.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangpixi.rememberdrinking.BuildConfig
import com.yangpixi.rememberdrinking.data.repository.WaterRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * @author yangpixi
 * @date 2025/12/27 22:34
 * @description viewModel
 */

class HomeViewModel(
    private val waterRepo: WaterRepo,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _goal: StateFlow<Int> = dataStore.data.map { preferences ->
        preferences[BuildConfig.GOAL_KEY] ?: 0
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
    val goal: StateFlow<Int> = _goal

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

    fun setOrUpdateGoal(goal: Int) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[BuildConfig.GOAL_KEY] = goal
            }
        }
    }


}