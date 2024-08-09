package com.appstrm.mod_feature_breeds.detail

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.appstrm.cattechnicalchallenge.data.breeds.models.Image
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailScreen(
    uiState: BreedDetailUiState,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(uiState.breedSummary.name) },
                scrollBehavior = topAppBarScrollBehavior,
                navigationIcon = {
                    IconButton(onBackPress) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Back")
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (val imageState = uiState.imagesState) {
                is BreedImagesState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                is BreedImagesState.Ready -> BreedInfoGrid(
                    breedSummary = uiState.breedSummary,
                    images = imageState.images,
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                )

                is BreedImagesState.Error -> Text(
                    imageState.message,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun BreedInfoGrid(
    breedSummary: CatBreedSummary,
    images: List<Image>,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 8.dp),
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            Text(
                text = breedSummary.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            BreedInfoRow(label = "Origin", value = breedSummary.origin)
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            BreedInfoRow(label = "Temperament", value = breedSummary.temperament)
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            BreedInfoRow(label = "Life Span", value = breedSummary.lifeSpan)
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            BreedInfoRow(label = "Child Friendly", value = breedSummary.childFriendly)
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            BreedInfoRow(
                label = "Intelligence",
                value = breedSummary.intelligence,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(images) { image ->
            val showShimmer = remember { mutableStateOf(true) }
            val randomHeight = remember { Random.nextInt(150, 250) }
            AsyncImage(
                model = image.url,
                contentDescription = breedSummary.name,
                modifier = Modifier
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = showShimmer.value
                        ),
                        shape = RoundedCornerShape(8.dp),
                    )
                    .then(
                        if (showShimmer.value) {
                            Modifier.heightIn(min = randomHeight.dp)
                        } else {
                            Modifier.wrapContentHeight()
                        }
                    )
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                onSuccess = { showShimmer.value = false },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
        )
        val transition = rememberInfiniteTransition(label = "InfiniteTransition")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ),
            label = "Shimmer"
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BreedInfoRow(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            modifier = Modifier.basicMarquee()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BreedInfoRow(
    label: String,
    value: Int,
    modifier: Modifier = Modifier,
    max: Int = 5,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(Modifier.width(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (i in 1..max) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(if (i <= value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    MaterialTheme {
        BreedDetailScreen(
            uiState = BreedDetailUiState(
                breedSummary = CatBreedSummary(),
                imagesState = BreedImagesState.Ready(emptyList()),
            ),
            onBackPress = {},
        )
    }
}