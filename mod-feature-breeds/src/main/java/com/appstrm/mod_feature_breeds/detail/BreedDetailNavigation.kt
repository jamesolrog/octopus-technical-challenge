package com.appstrm.mod_feature_breeds.detail

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.appstrm.mod_feature_breeds.breedlist.BreedListRoute

private const val breedIdArg = "breedId"
const val BreedDetailRoute = "$BreedListRoute/{$breedIdArg}"

fun NavGraphBuilder.breedDetailScreen(
    onBackButtonPress: () -> Unit,
) {
    composable(
        route = BreedDetailRoute,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
            )
        }
    ) {
        val viewModel: BreedDetailViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        BreedDetailScreen(
            uiState = uiState,
            onBackPress = onBackButtonPress,
        )
    }
}

fun NavController.navigateToBreedDetail(breedId: String) {
    navigate(route = "$BreedListRoute/$breedId") {
        launchSingleTop = true
    }
}

internal class BreedDetailArgs(val breedId: String) {
    constructor(
        savedStateHandle: SavedStateHandle
    ) : this (
        breedId = checkNotNull<String>(savedStateHandle[breedIdArg])
    )
}
