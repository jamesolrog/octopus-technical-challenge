package com.appstrm.cattechnicalchallenge

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navHostController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(snackbarHostState, navHostController, coroutineScope) {
    AppState(
        snackbarHostState = snackbarHostState,
        navHostController = navHostController,
        coroutineScope = coroutineScope,
    )
}

@Stable
class AppState(
    val navHostController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope,
) {
    fun showSnackbar(snackbarVisuals: SnackbarVisuals) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(snackbarVisuals)
        }
    }
}