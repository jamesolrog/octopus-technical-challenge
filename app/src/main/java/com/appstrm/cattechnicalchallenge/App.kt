package com.appstrm.cattechnicalchallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.appstrm.mod_feature_breeds.BreedGraphRoute

@Composable
fun App(
    modifier: Modifier = Modifier,
) {
    val appState = rememberAppState()

    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            NavHost(
                navController = appState.navHostController,
                startDestination = BreedGraphRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                appNavigationGraph(appState)
            }
        }
    }

    MaterialTheme {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) { innerPadding ->
            navHost(innerPadding)
        }
    }
}