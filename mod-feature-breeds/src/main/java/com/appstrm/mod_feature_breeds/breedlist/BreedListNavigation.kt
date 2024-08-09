package com.appstrm.mod_feature_breeds.breedlist

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions

const val BreedListRoute = "breeds"

fun NavGraphBuilder.breedListScreen(
    onBreedClick: (breedId: String) -> Unit,
) {
    composable(
        route = BreedListRoute,
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
        val viewModel: BreedListViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        BreedListScreen(uiState = uiState, onBreedClick)
    }
}

fun NavController.navigateToBreedList(builder: NavOptionsBuilder.() -> Unit) {
    navigate(
        route = BreedListRoute,
        navOptions = navOptions(builder),
    )
}