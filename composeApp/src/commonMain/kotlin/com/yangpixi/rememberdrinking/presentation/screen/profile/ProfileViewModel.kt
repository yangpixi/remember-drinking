package com.yangpixi.rememberdrinking.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangpixi.rememberdrinking.data.repository.UserRepoImpl
import com.yangpixi.rememberdrinking.util.AuthManager
import kotlinx.coroutines.launch

/**
 * @author yangpixi
 * @date 2026/1/13 20:37
 * @description viewModel
 */

class ProfileViewModel(
    userRepo: UserRepoImpl,
    private val authManager: AuthManager
) : ViewModel() {
    val currentUser = userRepo.currentUser

    fun doLogout() {
        viewModelScope.launch {
            authManager.removeToken()
        }
    }
}