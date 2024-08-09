package com.appstrm.mod_feature_breeds.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appstrm.cattechnicalchallenge.data.breeds.models.Image
import com.appstrm.cattechnicalchallenge.data.breeds.models.Weight
import com.appstrm.mod_data_breeds.GetBreedImagesUseCase
import com.appstrm.mod_data_breeds.GetBreedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CatBreedSummary(
    val id: String = "",
    val name: String = "-",
    val weight: Weight = Weight("-", "-"),
    val temperament: String = "-",
    val origin: String = "-",
    val description: String = "-",
    val lifeSpan: String = "-",
    val intelligence: Int = -1,
    val childFriendly: Int = -1,
)

sealed interface BreedImagesState {
    data object Loading: BreedImagesState

    data class Ready(
        val images: List<Image>,
    ): BreedImagesState

    data class Error(
        val message: String,
    ): BreedImagesState
}

data class BreedDetailUiState(
    val breedSummary: CatBreedSummary = CatBreedSummary(),
    val imagesState: BreedImagesState = BreedImagesState.Loading,
)

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    getBreedUseCase: GetBreedUseCase,
    getBreedImagesUseCase: GetBreedImagesUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val breedDetailArgs: BreedDetailArgs = BreedDetailArgs(savedStateHandle)

    private val _uiState = MutableStateFlow<BreedDetailUiState>(BreedDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                try {
                    val breed = getBreedUseCase.getBreed(breedDetailArgs.breedId)
                    val breedSummary = breed.let {
                        CatBreedSummary(
                            id = it.id,
                            name = it.name,
                            weight = it.weight,
                            temperament = it.temperament,
                            origin = it.origin,
                            description = it.description,
                            lifeSpan = it.lifeSpan,
                            intelligence = it.intelligence,
                            childFriendly = it.childFriendly,
                        )
                    }
                    it.copy(breedSummary = breedSummary)
                } catch (e: Exception) {
                    it.copy(breedSummary = CatBreedSummary()) // If an error occurs, show a blank breed for now
                }
            }
        }
        viewModelScope.launch {
            _uiState.update {
                try {
                    val breedImages = getBreedImagesUseCase.getBreedImages(breedId = breedDetailArgs.breedId)
                    it.copy(imagesState = BreedImagesState.Ready(breedImages))
                } catch (e: Exception) {
                    it.copy(imagesState = BreedImagesState.Error(e.localizedMessage ?: "ERROR"))
                }
            }
        }
    }
}