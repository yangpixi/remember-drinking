package com.yangpixi.rememberdrinking.presentation.screen.settings.notification

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangpixi.rememberdrinking.BuildConfig
import com.yangpixi.rememberdrinking.platform.NotificationScheduler
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author yangpixi
 * @date 2026/1/25 15:46
 * @description viewModel
 */

class NotificationViewModel(
    private val dataStore: DataStore<Preferences>,
    private val globalSnackBarUtils: GlobalSnackBarUtils,
    private val notificationScheduler: NotificationScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())

    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    private val _isEnabled = MutableStateFlow(false)

    val isEnable: StateFlow<Boolean> = _isEnabled.asStateFlow()

    private var originalConfig: NotificationConfig? = null

    init {
        initializeData()
    }

    // 从dateStore里面加载配置
    fun initializeData() {
        viewModelScope.launch {
            val preferences = dataStore.data.first()
            val delayTime = preferences[BuildConfig.NOTIFY_DELAY_TIME]

            val config = NotificationConfig(
                delayTime = delayTime ?: "60", // 默认60min
                content = preferences[BuildConfig.NOTIFY_CONTENT] ?: "记得喝水哦" // 默认值
            )

            _isEnabled.value = preferences[BuildConfig.NOTIFY_IS_ENABLED] ?: false
            originalConfig = config

            _uiState.update {
                it.copy(
                    currentConfig = config,
                    hasChanges = false
                )
            }
        }
    }

    // 提供方法给view层调用
    fun scheduleNotification() {
        val config = _uiState.value.currentConfig
        viewModelScope.launch {
            notificationScheduler.scheduleNotification(
                id = 1,
                title = "提醒",
                content = config.content,
                delayMillis = config.delayTime.toLong() * 60 * 1000,
            )
        }
    }

    fun cancelNotificationSchedule() {
        notificationScheduler.cancelAllNotification()
    }

    // 修改具体配置
    fun updateEnabledState(state: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[BuildConfig.NOTIFY_IS_ENABLED] = state
            }
        }
        _isEnabled.value = state
    }

    fun updateDelayTime(time: String) {
        updateConfig { it.copy(delayTime = time) }
    }

    fun updateContent(content: String) {
        updateConfig { it.copy(content = content) }
    }

    // 统一的修改配置方法
    private fun updateConfig(change: (NotificationConfig) -> NotificationConfig) {
        _uiState.update { currentState ->
            val newConfig = change(currentState.currentConfig)

            val isChanged = newConfig != originalConfig

            currentState.copy(
                currentConfig = newConfig,
                hasChanges = isChanged
            )

        }
    }

    fun saveSettings() {
        val currentState = _uiState.value
        viewModelScope.launch {
            val configToSave = currentState.currentConfig

            if (configToSave.delayTime.isBlank() || configToSave.content.isBlank()) {
                globalSnackBarUtils.sendEvent("通知间隔或提醒内容不能为空")
            } else if (configToSave.delayTime.toLong() < 15L) {
                globalSnackBarUtils.sendEvent("通知间隔不能小于15分钟")
            } else {
                dataStore.edit { preferences ->
                    preferences[BuildConfig.NOTIFY_DELAY_TIME] = configToSave.delayTime
                    preferences[BuildConfig.NOTIFY_CONTENT] = configToSave.content
                }
                originalConfig = configToSave

                _uiState.update {
                    it.copy(hasChanges = false)
                }

                // 先取消先前的通知配置，再重新开启，防止配置不生效
                cancelNotificationSchedule()
                scheduleNotification()
                globalSnackBarUtils.sendEvent("保存成功")
            }

        }
    }


}

// 用户配置数据模型(是否开启通知的变量独立)
data class NotificationConfig(
    val delayTime: String = "60", // 使用String存储时间，方便进行判空(单位 分钟)
    val content: String = "记得喝水哦"
)

// UI 状态：包含数据模型 + UI 逻辑状态（是否加载中、是否有变更）
data class NotificationUiState(
    val currentConfig: NotificationConfig = NotificationConfig(),
    val hasChanges: Boolean = false
)