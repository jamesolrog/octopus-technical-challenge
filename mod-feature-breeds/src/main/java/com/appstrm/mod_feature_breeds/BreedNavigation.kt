package com.appstrm.mod_feature_breeds

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.appstrm.mod_feature_breeds.breedlist.BreedListRoute
import com.appstrm.mod_feature_breeds.breedlist.breedListScreen
import com.appstrm.mod_feature_breeds.breedlist.BreedListRoute
import com.appstrm.mod_feature_breeds.breedlist.breedListScreen
import com.appstrm.mod_feature_breeds.detail.breedDetailScreen
import com.appstrm.mod_feature_breeds.detail.navigateToBreedDetail

const val BreedGraphRoute = "breedGraph"

fun NavGraphBuilder.breedGraph(navController: NavController) {
    navigation(
        route = BreedGraphRoute,
        startDestination = BreedListRoute,
    ) {
        breedListScreen(
            onBreedClick = { breedId ->
                navController.navigateToBreedDetail(breedId)
            }
        )
        breedDetailScreen(
            onBackButtonPress = {
                navController.navigateUp()
            }
        )
    }
}