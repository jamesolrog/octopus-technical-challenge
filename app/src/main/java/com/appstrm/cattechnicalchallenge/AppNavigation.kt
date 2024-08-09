package com.appstrm.cattechnicalchallenge

import androidx.navigation.NavGraphBuilder
import com.appstrm.mod_feature_breeds.breedGraph

internal fun NavGraphBuilder.appNavigationGraph(appState: AppState) {
    breedGraph(appState.navHostController)
}