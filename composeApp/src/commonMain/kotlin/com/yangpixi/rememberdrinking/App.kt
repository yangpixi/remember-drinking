package com.yangpixi.rememberdrinking

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.yangpixi.rememberdrinking.presentation.screen.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    val snackbarHostState = remember { SnackbarHostState() } //使用snackbar替代原生Toast
    val navController = rememberNavController() //获取navController供NavHost使用

    AppTheme {
        Scaffold(
            modifier = Modifier.safeContentPadding().fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },

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