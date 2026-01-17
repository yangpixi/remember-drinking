package com.yangpixi.rememberdrinking

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.yangpixi.rememberdrinking.platform.NotificationScheduler
import com.yangpixi.rememberdrinking.presentation.component.BottomBar
import com.yangpixi.rememberdrinking.presentation.component.BottomNavItem
import com.yangpixi.rememberdrinking.presentation.component.TopBar
import com.yangpixi.rememberdrinking.presentation.screen.about.AboutScreen
import com.yangpixi.rememberdrinking.presentation.screen.auth.login.LoginScreen
import com.yangpixi.rememberdrinking.presentation.screen.auth.register.RegisterScreen
import com.yangpixi.rememberdrinking.presentation.screen.history.HistoryScreen
import com.yangpixi.rememberdrinking.presentation.screen.home.HomeScreen
import com.yangpixi.rememberdrinking.presentation.screen.profile.ProfileScreen
import com.yangpixi.rememberdrinking.presentation.screen.settings.SettingsScreen
import com.yangpixi.rememberdrinking.util.GlobalSnackBarUtils
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    val snackbarHostState = remember { SnackbarHostState() } //使用snackbar替代原生Toast
    val navController = rememberNavController() //获取navController供NavHost使用
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val globalSnackBarUtils = koinInject<GlobalSnackBarUtils>()
    val scheduler = koinInject<NotificationScheduler>()
    val scope = rememberCoroutineScope()

    scope.launch {
        scheduler.requestPermission()
        scheduler.scheduleNotification(
            title = "Reminder",
            content = "记得喝水哦",
            id = 0,
            delayMillis = 1 * 60 * 60 * 1000 // 默认为一小时提醒一次
        )
    }

    LaunchedEffect(Unit) {
        globalSnackBarUtils.uiEvent.collect { ele ->
            snackbarHostState.showSnackbar(ele)
        }
    }

    val bottomNavList = listOf(
        BottomNavItem(
            name = "主页",
            route = "homepage",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            name = "历史",
            route = "history",
            icon = Icons.Default.History
        ),
        BottomNavItem(
            name = "设置",
            route = "settings",
            icon = Icons.Default.Settings
        )
    )

    // 添加一个listener，实现topBar标题的动态变化
    var currentTitle by remember { mutableStateOf(bottomNavList.first().name) }
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val newTitle = when (destination.route) {
                "homepage" -> bottomNavList.find { it.route == "homepage" }?.name
                "history" -> bottomNavList.find { it.route == "history" }?.name
                "settings" -> bottomNavList.find { it.route == "settings" }?.name
                "login" -> "登录" // 由于登录界面不在bottomBar里面，故使用硬编码
                "register" -> "注册"
                "about" -> "关于"
                "profile" -> "资料"
                else -> currentTitle
            }
            if (newTitle != null) {
                currentTitle = newTitle
            }
        }
    }

    AppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                TopBar(title = currentTitle, scrollBehavior)
            },
            bottomBar = {
                BottomBar(navController = navController, navigationItems = bottomNavList)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                startDestination = "homepage",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(300)
                    )
                }
            ) {
                composable("homepage") {
                    HomeScreen()
                }

                composable("history") {
                    HistoryScreen()
                }

                composable("settings") {
                    SettingsScreen(navController)
                }

                composable("login") {
                    LoginScreen(navController)
                }

                composable("register") {
                    RegisterScreen(navController)
                }

                composable("about") {
                    AboutScreen()
                }

                composable("profile") {
                    ProfileScreen(navController = navController)
                }
            }
        }
    }
}