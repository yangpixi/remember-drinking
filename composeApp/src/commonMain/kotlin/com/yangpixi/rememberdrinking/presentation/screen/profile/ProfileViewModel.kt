package com.yangpixi.rememberdrinking.presentation.screen.profile

import androidx.lifecycle.ViewModel
import com.yangpixi.rememberdrinking.data.repository.UserRepoImpl

/**
 * @author yangpixi
 * @date 2026/1/13 20:37
 * @description viewModel
 */

class ProfileViewModel(
    userRepo: UserRepoImpl
) : ViewModel() {
    val currentUser = userRepo.currentUser
}