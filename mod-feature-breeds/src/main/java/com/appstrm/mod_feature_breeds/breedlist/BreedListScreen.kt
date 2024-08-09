package com.appstrm.mod_feature_breeds.breedlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    uiState: BreedListUiState,
    onBreedClick: (breedId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Breeds") },
                scrollBehavior = topAppBarScrollBehavior,
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            when(uiState) {
                is BreedListUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is BreedListUiState.Ready -> BreedGrid(
                    breedSummaries = uiState.breeds,
                    onBreedClick = onBreedClick,
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                )
                is BreedListUiState.Error -> Text(uiState.message, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BreedGrid(
    breedSummaries: List<BreedSummary>,
    onBreedClick: (breedId: String) -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
    ) {
        breedSummaries.forEach {
            item {
                Card(
                    onClick = { onBreedClick(it.id) },
                ) {
                    Text(it.name, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    MaterialTheme {
        BreedListScreen(
            uiState = BreedListUiState.Ready(
                breeds = listOf(
                    BreedSummary(
                        id = "test",
                        name = "test"
                    ),
                )
            ),
            onBreedClick = {},
        )
    }
}