package com.appstrm.mod_data_breeds

import javax.inject.Inject

class GetBreedImagesUseCase @Inject constructor(
    private val productsRepository: BreedsRepository
) {
    suspend fun getBreedImages(breedId: String) = productsRepository.getBreedImages(breedId)
}