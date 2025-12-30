package com.yangpixi.rememberdrinking

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.yangpixi.rememberdrinking.presentation.component.TopBar
import com.yangpixi.rememberdrinking.presentation.screen.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    val snackbarHostState = remember { SnackbarHostState() } //使用snackbar替代原生Toast
    val navController = rememberNavController() //获取navController供NavHost使用
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    AppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                TopBar(title = "主页面", scrollBehavior,)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                startDestination = "homepage"
            ) {
                composable("homepage") {
                    HomeScreen()
                }
            }
        }
    }
}