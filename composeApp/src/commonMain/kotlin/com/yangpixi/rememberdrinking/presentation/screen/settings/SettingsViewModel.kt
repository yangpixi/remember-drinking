package com.yangpixi.rememberdrinking.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangpixi.rememberdrinking.data.repository.UserRepoImpl
import com.yangpixi.rememberdrinking.domain.model.User
import com.yangpixi.rememberdrinking.platform.RecordSchedule
import com.yangpixi.rememberdrinking.presentation.screen.UiState.UiState
import com.yangpixi.rememberdrinking.util.AuthManager
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * @author yangpixi
 * @date 2025/12/31 13:36
 * @description viewModel
 */

class SettingsViewModel(
    private val userRepo: UserRepoImpl,
    private val authManager: AuthManager,
    private val globalSnackBarUtils: GlobalSnackBarUtils,
    private val recordSchedule: RecordSchedule
) : ViewModel() {

    val authStatus = authManager.authStatus
    val uiState: StateFlow<UiState<User>> = combine(
        authManager.authStatus,
        userRepo.currentUser
    ) { _, user ->
        when {
            user != null -> {
                UiState.Success(user)
            }

            else -> {
                UiState.Loading
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    init {
        viewModelScope.launch {
            combine(
                authManager.authStatus,
                userRepo.currentUser
            ) { auth, user ->
                auth is AuthManager.AuthStatus.Authenticated && user == null
            }.collect { res ->
                if (res) {
                    try {
                        userRepo.getCurrentUser()
                    } catch (e: Exception) {
                        globalSnackBarUtils.sendEvent("服务器连接失败，请稍后再试")
                    }
                }
            }
        }
    }

    fun doUpload() {
        viewModelScope.launch {
            if (authStatus.value is AuthManager.AuthStatus.Unauthenticated) {
                globalSnackBarUtils.sendEvent("请先登录")
            } else {
                recordSchedule.doUploadRecordsWork()
            }
        }
    }

}