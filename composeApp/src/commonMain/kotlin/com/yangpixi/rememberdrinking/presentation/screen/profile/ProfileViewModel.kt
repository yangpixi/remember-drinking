package com.yangpixi.rememberdrinking.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangpixi.rememberdrinking.data.api.UserApi
import com.yangpixi.rememberdrinking.data.repository.UserRepoImpl
import com.yangpixi.rememberdrinking.util.AuthManager
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch

/**
 * @author yangpixi
 * @date 2026/1/13 20:37
 * @description viewModel
 */

class ProfileViewModel(
    private val userRepo: UserRepoImpl,
    private val authManager: AuthManager,
    private val userApi: UserApi,
    private val globalSnackBarUtils: GlobalSnackBarUtils
) : ViewModel() {
    val currentUser = userRepo.currentUser

    fun doLogout() {
        viewModelScope.launch {
            authManager.removeToken()
        }
    }

    fun doUpdateAvatar(file: PlatformFile?) {
        file?.let {
            viewModelScope.launch {
                try {
                    userApi.updateAvatar(file.readBytes(), file.name)
                    globalSnackBarUtils.sendEvent("上传头像成功")
                    userRepo.getCurrentUser()
                } catch (e: Exception) {
                    globalSnackBarUtils.sendEvent("头像上传失败!")
                }
            }
        }
    }
}