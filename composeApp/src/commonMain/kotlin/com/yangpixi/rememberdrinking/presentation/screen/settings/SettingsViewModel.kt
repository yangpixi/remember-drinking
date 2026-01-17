package com.yangpixi.rememberdrinking.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangpixi.rememberdrinking.data.repository.UserRepoImpl
import com.yangpixi.rememberdrinking.domain.model.User
import com.yangpixi.rememberdrinking.presentation.screen.UiState.UiState
import com.yangpixi.rememberdrinking.util.AuthManager
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
    private val authManager: AuthManager
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
                    userRepo.getCurrentUser()
                }
            }
        }
    }

}