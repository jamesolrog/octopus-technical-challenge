package com.appstrm.mod_feature_breeds.breedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appstrm.mod_data_breeds.GetBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BreedSummary(
    val id: String,
    val name: String,
)

sealed class BreedListUiState {
    data object Loading: BreedListUiState()

    data class Ready(
        val breeds: List<BreedSummary>,
    ): BreedListUiState()

    data class Error(
        val message: String,
    ): BreedListUiState()
}

@HiltViewModel
class BreedListViewModel @Inject constructor(
    getBreedsUseCase: GetBreedsUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow<BreedListUiState>(BreedListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val breeds = getBreedsUseCase.getBreeds()
                val breedSummaries = breeds.map {
                    BreedSummary(
                        id = it.id,
                        name = it.name,
                    )
                }
                _uiState.update {
                    BreedListUiState.Ready(breedSummaries)
                }
            } catch (e: Exception) {
                _uiState.update {
                    BreedListUiState.Error(message = e.localizedMessage ?: "ERROR")
                }
            }
        }
    }
}